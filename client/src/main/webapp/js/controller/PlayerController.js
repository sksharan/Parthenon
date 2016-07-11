define(['js/ParthenonApp', 'js/filter/RangeFilter', 'js/service/PlayerService'], function(app) {
    app.controller('PlayerController',
            ['$scope', '$timeout', 'PlayerService', function($scope, $timeout, PlayerService) {

        /** Fetch updated list of players from server every few seconds. */
        $scope.players = [];
        (function getPlayers() {
            PlayerService.getPlayers().$promise.then(function(result) {
                $scope.players = result;
                $timeout(getPlayers, 5000);
            });
        })();

        /** Keep track of players that are considered "selected". */
        $scope.selectedPlayers = [];
        $scope.toggleSelectedPlayer = function(name) {
            if ($scope.isSelectedPlayer(name)) {
                $scope.selectedPlayers.splice($scope.selectedPlayers.indexOf(name), 1);
            } else {
                $scope.selectedPlayers.push(name);
            }
        };
        $scope.isSelectedPlayer = function(name) {
            return $scope.selectedPlayers.indexOf(name) !== -1;
        }

        /** Returns true if the player has no health remaining. */
        $scope.isDead = function(health) {
            return Math.floor(health) === 0;
        };

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

        $scope.isHeldInMainHand = function(item) {
            return item.type == "HELD_IN_MAIN_HAND";
        }
        $scope.isHeldInOffHand = function(item) {
            return item.type == "HELD_IN_OFF_HAND";
        }
        $scope.isEquippedBoots = function(item) {
            return item.type == "EQUIPPED_BOOTS";
        }
        $scope.isEquippedChestplate = function(item) {
            return item.type == "EQUIPPED_CHESTPLATE";
        }
        $scope.isEquippedHelmet = function(item) {
            return item.type == "EQUIPPED_HELMET";
        }
        $scope.isEquippedLeggings = function(item) {
            return item.type == "EQUIPPED_LEGGINGS";
        }
        $scope.isGeneral = function(item) {
            return item.type == "GENERAL";
        }

        /** Converts the given item name into a CSS-friendly name. The conversion is similar
         *  to the one done in the inventory icon Lua script. */
        $scope.toCssName = function(itemName) {
            return itemName.replace(/ANVIL\([0-9]+\)/, "ANVIL")
                           .replace(/SWORD\([0-9]+\)/, "SWORD")
                           .replace(/BOW\([0-9]+\)/, "BOW")
                           .replace(/PICKAXE\([0-9]+\)/, "PICKAXE")
                           .replace(/SPADE\([0-9]+\)/, "SPADE")
                           .replace(/AXE\([0-9]+\)/, "AXE")
                           .replace(/HOE\([0-9]+\)/, "HOE")
                           .replace(/HELMET\([0-9]+\)/, "HELMET")
                           .replace(/CHESTPLATE\([0-9]+\)/, "CHESTPLATE")
                           .replace(/LEGGINGS\([0-9]+\)/, "LEGGINGS")
                           .replace(/BOOTS\([0-9]+\)/, "BOOTS")
                           .replace(/SHEARS\([0-9]+\)/, "SHEARS")
                           .replace(/SADDLE\([0-9]+\)/, "SADDLE")
                           .replace(/SHIELD\([0-9]+\)/, "SHIELD")
                           .replace(/FISHING_ROD\([0-9]+\)/, "FISHING_ROD")
                           .replace(/FLINT_AND_STEEL\([0-9]+\)/, "FLINT_AND_STEEL")
                           .replace(/long_/, "")
                           .replace(/strong_/, "")
                           .replace(/ facing[a-zA-Z0-9 ]*/, "")
                           .replace(/[ \(\)=\/]/g, "_")
                           .replace(/_/g, "");
        }
    }]);
});
