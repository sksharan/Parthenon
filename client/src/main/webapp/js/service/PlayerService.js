define(['js/ParthenonApp', 'js/ParthenonUrl'], function(app, url) {
    app.factory("PlayerService", ['$resource', function($resource) {
        var paramDefaults = {};
        var actions = {
            getPlayers: {
                method: 'GET',
                isArray: true
            }
        };
        return $resource(url.PLAYER_URL, paramDefaults, actions);
    }]);
});