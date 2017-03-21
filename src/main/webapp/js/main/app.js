'use strict';

/**
 * @ngdoc overview
 * @name musicApp
 * @description
 * # musicApp
 *
 * Main module of the application.
 */

var musicApp = angular
  .module('musicApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch', 'ui.bootstrap', 'braintree-angular', 'panzoom', 'panzoomwidget',
    'angularAudioRecorder', 'angularSoundManager', 'angular-bind-html-compile',
    'angular.socialmediashare'
  ]);
musicApp.config(function ($routeProvider, $locationProvider) {
    // $locationProvider.html5Mode(true).hashPrefix('!');
    $routeProvider
      .when('/', {
        templateUrl: '/html/home.html',
        controller: 'MainCtrl',
        controllerAs: 'ctrl',
        resolve:{
            userToken : function (UserService) {
                UserService.setToken();
            }
        }
      })
      .when('/mytune', {
        templateUrl: '/html/mytune.html',
        controller: 'MyTuneCtrl',
        controllerAs: 'ctrl',
          resolve : {
              songList : function (MusicService) {
                  return MusicService.getSongs();
              }
          }
      })
      .when('/recordtune', {
        templateUrl: '/html/recordtune.html',
        controller: 'RecordTuneCtrl',
        controllerAs: 'ctrl'
      })
      //  link to copyright profile
      .when('/profile', {
        templateUrl: '/html/ecoprofile.html',
        controller: 'EcoProfileCtrl',
        controllerAs: 'ctrl',
        resolve:{
          CurrentProfile : function (EcoProfileService) {
              return EcoProfileService.getProfile();
          }
        }
      })
    // .when('/logout', {
    //     templateUrl: ''
    // })
      .otherwise({
        redirectTo: '/'
      });
  });

musicApp.constant('clientTokenPath', '/braintree/token');
// musicApp.constant('clientTokenPath', '/client-token');
musicApp.config(function (recorderServiceProvider) {
    var isHttps = (window.location.protocol == 'https:');
    if(!isHttps){
        recorderServiceProvider
            .forceSwf(true)
            .setSwfUrl('lib/recorder.swf')
            .withMp3Conversion(true);
    }
});

musicApp.run(function($FB){
    $FB.init('386469651480295');
});
