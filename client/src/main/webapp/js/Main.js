requirejs.config({
    baseUrl: '/',
    paths: {
        'angular': 'lib/angular/angular',
        'angular-resource': 'lib/angular-resource/angular-resource',
        'angular-route': 'lib/angular-route/angular-route',
        'jquery': 'lib/jquery/dist/jquery'
    },
    shim: {
        'angular': {
            exports: 'angular'
        },
        'angular-resource': {
            deps: ['angular']
        },
        'angular-route': {
            deps: ['angular']
        }
    }
});

require(['js/ParthenonApp',
         'js/controller/PlayerController',
         'js/filter/RangeFilter',
         'js/service/PlayerService'], function(app) {

    app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/player', {
            templateUrl: 'js/template/PlayerTemplate.html',
            controller: 'PlayerController'
        }).
        otherwise({
            redirectTo: '/player'
        });
    }]);
    app.init();
});
