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
		}
	};
});