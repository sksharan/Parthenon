requirejs.config({
    baseUrl: '/',
    paths: {
        'angular': 'lib/angular/angular',
        'angular-resource': 'lib/angular-resource/angular-resource',
        'angular-route': 'lib/angular-route/angular-route',
        'bootstrap': 'lib/bootstrap/dist/js/bootstrap',
        'jquery': 'lib/jquery/dist/jquery',
        'tether': 'lib/tether/dist/js/tether'
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
        },
        'bootstrap': {
            deps: ['jquery', 'tether']
        }
    }
});

/** http://stackoverflow.com/questions/34567939/how-to-fix-the-error-error-bootstrap-tooltips-require-tether-http-github-h */
require(['tether'], function(tether) {
    window.Tether = tether;
});

require(['js/ParthenonApp',
         'js/controller/PlayerController',
         'js/filter/RangeFilter',
         'js/service/PlayerService',
         'bootstrap'], function(app) {

    $(document).tooltip({
        animation: false,
        selector: '[data-toggle="tooltip"]',
        template: '<div class="tooltip" role="tooltip">' +
                  '    <div class="tooltip-arrow"></div>' +
                  '    <div class="tooltip-inner pixel-text pixel-text-regular p-b-0"></div>' +
                  '</div>'
    });

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
