requirejs.config({
    baseUrl: '/',
    paths: {
        'angular': 'lib/angular/angular',
        'jquery': 'lib/jquery/dist/jquery'
    },
    shim: {
        'angular': {
            exports: 'angular'
        }
    }
});

require(['js/app'], function(app) {
    app.init();
    requirejs(['js/main']);
});
