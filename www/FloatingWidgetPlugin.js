var exec = require('cordova/exec');

var FloatingWidgetPlugin = {
    showFloatingWidget: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, "FloatingWidgetPlugin", "showFloatingWidget", []);
    },

    isWidgetDisplayed: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, "FloatingWidgetPlugin", "isWidgetDisplayed", []);
    },

    destroyFloatingWidget: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, "FloatingWidgetPlugin", "destroyFloatingWidget", []);
    }
};

module.exports = FloatingWidgetPlugin;


