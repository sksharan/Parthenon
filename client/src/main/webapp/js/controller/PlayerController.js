define(['js/ParthenonApp', 'js/service/PlayerService'], function(app) {
    app.controller('PlayerController',
            ['$scope', 'PlayerService', function($scope, PlayerService) {
        $scope.players = PlayerService.getPlayers();

        /** Returns the number of full hearts used to represent the player's health. */
        $scope.numFullHearts = function(health) {
            return Math.floor(health/2);
        }
        /** Returns the number of half hearts used to represent the player's health. */
        $scope.numHalfHearts = function(health) {
            return health % 2;
        }
        /** Returns the number of empty hearts used to represent the player's health. */
        $scope.numEmptyHearts = function(health) {
            return 10 - (Math.floor(health/2)) - (health % 2);
        }
    }]);
});
