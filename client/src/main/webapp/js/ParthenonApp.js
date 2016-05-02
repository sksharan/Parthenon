define(['angular', 'angular-resource', 'angular-route'], function(angular) {
    var app = angular.module('parthenonApp', ['ngResource', 'ngRoute']);
    app.init = function() {
        angular.bootstrap(document, ['parthenonApp']);
    }
    return app;
});