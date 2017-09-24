jQuery.sap.require("jquery.sap.resources");
sap.ui.define([], function() {
	"use strict";

	var Utils = {
		
		generateRandomNumber: function(min, max) {
			return Math.random() * (max - min) + min;
		},
		
		getDefaultCurrencyCode: function() {
			return "EUR";
		},
		
		showInfoMessage: function(sMessage) {
			sap.m.MessageToast.show(sMessage, {
				duration: 3000
			});
		},
		
		getDefaultResourceBundle: function() {
			var oBundle = jQuery.sap.resources({
				url: "i18n/i18n.properties",
				locale: "en_GB"
			});
			return oBundle;			
		}
	};

	return Utils;
});