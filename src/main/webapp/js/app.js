'use strict';
var myApp = angular.module('loginApp',[]);

myApp.controller("signupCtrl", ['$scope', '$http', function ($scope, $http) {
        $scope.signup = function () {
            $http({
                method: 'POST',
                url: 'signup',
                data: $.param({email: $scope.email, password: $scope.password, name: $scope.name}),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).then(function (response) {
                location.assign("/login");
            });
        }
    }]
);

myApp.controller("loginCtrl", ['$scope', '$http', function ($scope, $http) {

    $scope.login = function () {

        $http({
            method: 'POST',
            url: 'login',
            data: $.param({email: $scope.email, password: $scope.password, userAgent: navigator.userAgent}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            location.assign("/");
        });
    }
}]);