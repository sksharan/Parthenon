define(['js/ParthenonApp', 'js/service/PlayerService'], function(app) {
    app.controller('PlayerController',
            ['$scope', 'PlayerService', function($scope, PlayerService) {
        $scope.players = PlayerService.getPlayers();

        /** Returns the number of full heart icons used to represent the player's health. */
        $scope.numFullHeartIcons = function(health) {
            return Math.floor(health/2);
        }
        /** Returns the number of half heart icons used to represent the player's health. */
        $scope.numHalfHeartIcons = function(health) {
            return health % 2;
        }
        /** Returns the number of empty heart icons used to represent the player's health. */
        $scope.numEmptyHeartIcons = function(health) {
            return 10 - $scope.numFullHeartIcons(health) - $scope.numHalfHeartIcons(health);
        }

        /** Returns the number of full hunger icons used to represent the player's health. */
        $scope.numFullHungerIcons = function(foodLevel) {
            return Math.floor(foodLevel/2);
        }
        /** Returns the number of half hunger icons used to represent the player's health. */
        $scope.numHalfHungerIcons = function(foodLevel) {
            return foodLevel % 2;
        }
        /** Returns the number of empty hunger icons used to represent the player's health. */
        $scope.numEmptyHungerIcons = function(foodLevel) {
            return 10 - $scope.numFullHungerIcons(foodLevel) - $scope.numHalfHungerIcons(foodLevel);
        }
    }]);
});
