'use strict';

/**
 * @ngdoc function
 * @name musicApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the musicApp
 */
musicApp.controller('EcoProfileCtrl', ['EcoProfileService','CurrentProfile', function (EcoProfileService, CurrentProfile) {

  var _this = this;
  this.ecoEntity = CurrentProfile;
  console.log(CurrentProfile);

  this.saveProfile=function () {
    EcoProfileService.saveProfile(_this.ecoEntity, function(resp){
      console.log(resp)
    });
  };
}]);
