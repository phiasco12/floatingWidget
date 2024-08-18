var exec = require('cordova/exec');

var FloatingWidgetPlugin = {
    showFloatingWidget: function (success, error) {
        exec(success, error, "FloatingWidgetPlugin", "showFloatingWidget", []);
    }
};

module.exports = FloatingWidgetPlugin;