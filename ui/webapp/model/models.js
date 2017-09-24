sap.ui.define([
	"sap/ui/model/json/JSONModel",
	"sap/ui/Device",
	"book/ui/utils/utils"
], function(JSONModel, Device, utils) {
	"use strict";

	return {

		createDeviceModel: function() {
			var oModel = new JSONModel(Device);
			oModel.setDefaultBindingMode("OneWay");
			return oModel;
		},

		createNewBookDialogEmptyModel: function() {
			var oDialogModel = new JSONModel({
				data: {
					"bookId": parseInt(utils.generateRandomNumber(100, 9999)),
					"bookName": "",
					"isbn": "",
					"price": undefined,
					"priceCurrency": utils.getDefaultCurrencyCode(),
					"authorName": ""
				},
				"canCreate": false
			});

			return oDialogModel;
		},

		createNewNookPlaceholdersModel: function() {
			var oPlaceholdersModel = new JSONModel({
				"bookNamePL": "Book Name",
				"isbnPL": "ISBN",
				"pricePL": "Price",
				"authorNamePL": "Author Name"
			});

			// one way binding because placeholders cannot be changed from the UI
			oPlaceholdersModel.setDefaultBindingMode(sap.ui.model.BindingMode.OneWay);

			return oPlaceholdersModel;
		}


	};
});