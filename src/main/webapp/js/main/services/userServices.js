/**
 */

musicApp.service('UserService',['$http', '$window','Config', function ($http, $window, Config) {

    var userData = null;
    var userToken = null;
    var _this = this;

    _this.setToken = function (callback) {
        return $http({
            method: 'POST',
            url: '/token'
        }).success(function (response) {
            userToken = {
                'authCode': response.authCode,
                'authToken': response.authToken,
                'userAgent': navigator.userAgent
            };
            sessionStorage.setItem('userToken', JSON.stringify(userToken));
        }).then(function(){
            if(callback) return callback(userToken);
            else return userToken;
        });
    };

    this.getUserDetails = function (callback) {
        //TODO use of storage
        if (userData!==null) return userData;
        return $http({
            method: 'POST',
            url: '/user/profile'
        }).success(function (response) {
            userData = response;
        }).then(function(){
            if(callback) return callback(userData);
            else return userData;
        });
    };

    this.logout = function () {
        Storage.clear();
        // window.location.href = Config.serverUrl + '/logout'
        location.assign("/logout")
    }

}]);
