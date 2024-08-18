var exec = require('cordova/exec');

var FloatingWidgetPlugin = {
    showFloatingWidget: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, "FloatingWidgetPlugin", "showFloatingWidget", []);
    },

    isWidgetDisplayed: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, "FloatingWidgetPlugin", "isWidgetDisplayed", []);
    }
};

module.exports = FloatingWidgetPlugin;

