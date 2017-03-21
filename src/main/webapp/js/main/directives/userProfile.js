
angular
    .module('musicApp').directive('userProfile', function(UserService, $timeout) {
    return {
        restrict: 'AE',
        templateUrl: 'js/main/directives/partials/userProfile.html',
        controller:['$scope', function (scope) {
            // scope.user = UserService.getUserDetails();
        }],
        link: function(scope, elem, attrs) {
            UserService.getUserDetails(function(response){
                scope.user = response;
            });
        }
    };
});
