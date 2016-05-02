define(['angular', 'angular-route'], function(angular) {
    var app = angular.module('parthenonApp', ['ngRoute']);
    app.init = function() {
        angular.bootstrap(document, ['parthenonApp']);
    }
    return app;
});