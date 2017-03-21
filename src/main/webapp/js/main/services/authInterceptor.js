musicApp.factory('authInterceptor', ['$q','$log', '$injector','ngSpinner',
    function($q, $log, $injector, ngSpinner) {
    // $log.debug('$log is here to show you that this is a regular factory with injection');

    var interceptor = {
        'request': function (config) {
            ngSpinner.spin();

            var userToken = JSON.parse(sessionStorage.getItem('userToken'));
            var isFbUrl = (/api.facebook.com/).test(config.url);
            if(userToken !== null && isFbUrl == false){
                config.headers['authCode'] = userToken['authCode'];
                config.headers['authToken'] = userToken['authToken'];
                config.headers['userAgent'] = userToken['userAgent'];
            }

            return config || $q.when(config);
        },
        'requestError': function (rejection) {
            ngSpinner.stop();
            return $q.reject(rejection);
        },
        'response': function (response) {
            ngSpinner.stop();
            return response || $q.when(response);
        },
        'responseError': function (rejection) {
            ngSpinner.stop();
            return $q.reject(rejection);
        }
    };

    return interceptor;
}]);

musicApp.config(function($httpProvider){
    $httpProvider.interceptors.push('authInterceptor');
});