define(['js/ParthenonApp', 'js/service/PlayerService'], function(app) {
    app.controller('PlayerController',
            ['$scope', 'PlayerService', function($scope, PlayerService) {
        $scope.players = PlayerService.getPlayers();
    }]);
});