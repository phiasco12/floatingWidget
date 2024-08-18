var exec = require('cordova/exec');

var FloatingWidgetPlugin = {
    showFloatingWidget: function(successCallback, errorCallback) {
        exec(successCallback, errorCallback, "FloatingWidgetPlugin", "showFloatingWidget", []);
    }
};

module.exports = FloatingWidgetPlugin;
