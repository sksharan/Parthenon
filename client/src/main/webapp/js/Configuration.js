requirejs.config({
    baseUrl: '/',
    paths: {
        'angular': 'lib/angular/angular',
        'angular-route': 'lib/angular-route/angular-route',
        'jquery': 'lib/jquery/dist/jquery'
    },
    shim: {
        'angular': {
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular']
        }
    }
});

require(['js/ParthenonApp', 'js/ParthenonControllers'], function(app) {
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
