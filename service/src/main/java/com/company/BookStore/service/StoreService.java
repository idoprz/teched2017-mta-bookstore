package com.company.BookStore.service;

import java.util.List;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sap.cloud.sdk.hana.connectivity.cds.CDSQuery;
import com.sap.cloud.sdk.hana.connectivity.cds.CDSSelectQueryBuilder;
import com.sap.cloud.sdk.hana.connectivity.cds.CDSSelectQueryResult;       

import com.sap.cloud.sdk.hana.connectivity.handler.CDSDataSourceHandler;
import com.sap.cloud.sdk.hana.connectivity.handler.DataSourceHandlerFactory;


import com.sap.cloud.sdk.service.prov.api.EntityData;       
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.operations.Read;       
import com.sap.cloud.sdk.service.prov.api.request.QueryRequest;
import com.sap.cloud.sdk.service.prov.api.request.ReadRequest;       
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;
import com.sap.cloud.sdk.service.prov.api.response.ReadResponse;


import com.sap.cloud.sdk.service.prov.api.operations.Create;
import com.sap.cloud.sdk.service.prov.api.operations.Delete;
import com.sap.cloud.sdk.service.prov.api.request.CreateRequest;
import com.sap.cloud.sdk.service.prov.api.request.DeleteRequest;
import com.sap.cloud.sdk.service.prov.api.response.CreateResponse;
import com.sap.cloud.sdk.service.prov.api.response.DeleteResponse;

import com.sap.cloud.sdk.hana.connectivity.cds.CDSException;

public class StoreService {

	private static Logger logger = LoggerFactory.getLogger(StoreService.class);
	
	
	@Query(entity = "Book", serviceName = "store")
	public QueryResponse getAllProposedBooks(QueryRequest queryRequest) {
	    try {
			QueryResponse queryResponse =  QueryResponse.setSuccess().setEntityData(getEntitySet(queryRequest)).response();
			return queryResponse;
		} catch (Exception e) {
			return null;
    	}
	}
	
	@Read(entity = "Book", serviceName = "store")
	public ReadResponse getProposedBooks(ReadRequest readRequest){
		try {
			ReadResponse readResponse = ReadResponse.setSuccess().setData(readEntity(readRequest)).response();
			return readResponse;
		} catch (Exception e) {
	       	return null;
	    }
	}
	
	@Create(entity = "Book", serviceName = "store")
    public CreateResponse createSalesOrderLineItems(CreateRequest createRequest) {
        CreateResponse createResponse = CreateResponse.setSuccess().setData(createEntity( createRequest)).response();
        return createResponse;
    }
    
    @Delete(entity = "Book", serviceName ="store")
    public DeleteResponse deleteSalesOrder(DeleteRequest deleteRequest) {
            deleteEntity(deleteRequest);
            DeleteResponse deleteResponse = DeleteResponse.setSuccess().response();
        	return deleteResponse;
    }

	
	private List<EntityData> getEntitySet(QueryRequest queryRequest) {
		String fullQualifiedName = queryRequest.getEntityMetadata().getNamespace()+ "." +queryRequest.getEntityMetadata().getName();			
		CDSDataSourceHandler dsHandler = DataSourceHandlerFactory.getInstance().getCDSHandler(getConnection(), queryRequest.getEntityMetadata().getNamespace());
		try {
			CDSQuery cdsQuery = new CDSSelectQueryBuilder(fullQualifiedName).build();   		
			CDSSelectQueryResult cdsSelectQueryResult = dsHandler.executeQuery(cdsQuery);			
			return cdsSelectQueryResult.getResult();
		} catch (Exception e) {
			logger.error("==> Eexception while fetching query data from CDS: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private EntityData readEntity(ReadRequest readRequest) throws Exception {
		CDSDataSourceHandler dsHandler = DataSourceHandlerFactory.getInstance().getCDSHandler(getConnection(), readRequest.getEntityMetadata().getNamespace());
		EntityData ed = dsHandler.executeRead(readRequest.getEntityMetadata().getName(), readRequest.getKeys(), readRequest.getEntityMetadata().getElementNames());
		return ed;
	}
	
	 private void deleteEntity(DeleteRequest deleteRequest){
        CDSDataSourceHandler dsHandler = DataSourceHandlerFactory.getInstance().getCDSHandler(getConnection(), deleteRequest.getEntityMetadata().getNamespace());
        try{
            dsHandler.executeDelete(deleteRequest.getEntityMetadata().getName(), deleteRequest.getKeys());
        } catch (CDSException e){
            logger.error("Eexception while deleting an entity in CDS: " + e.getMessage());
        }
    }
    
    
    private EntityData createEntity(CreateRequest createRequest)
    {   
        CDSDataSourceHandler dsHandler = DataSourceHandlerFactory.getInstance().getCDSHandler(getConnection(), createRequest.getEntityMetadata().getNamespace());
        EntityData ed = null;
        try{
            ed = dsHandler.executeInsert(createRequest.getData(), true);
        } catch (CDSException e){
            logger.error("Eexception while creating an entity in CDS: " + e.getMessage());
        }
        return ed;
    }
	
	private static Connection getConnection(){
		Connection conn = null;
		Context ctx;
		try {
			ctx = new InitialContext();
			conn = ((DataSource) ctx.lookup("java:comp/env/jdbc/java-hdi-container")).getConnection();
			System.out.println("conn = " + conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	


}