
musicApp.service('EcoProfileService',function(Config, $http, UserService){

    var userProfile = null;

    this.getProfile = function (callback) {
        var user = UserService.getUserDetails();
        return $http({
            method: 'POST',
            url: Config.serverUrl +'/user/eco/profile',
            params: {email:user.email}
        }).success(function (resp) {
            userProfile = resp;
            callback? callback(resp) : '';
        }).then(function (resp) {
            return resp.data;
        })

    };

    this.saveProfile = function (data, callback) {
        return $http({
            method: 'POST',
            url: Config.serverUrl +'/user/eco/profile/save',
            data: data
        }).success(function (resp) {
            callback? callback(resp) : '';
        });
    };

});
