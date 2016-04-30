define(['angular'], function(angular) {
    var app = angular.module('parthenonApp', []);
    app.init = function() {
        angular.bootstrap(document, ['parthenonApp']);
    }
    return app;
});