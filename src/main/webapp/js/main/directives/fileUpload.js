
angular.module('musicApp')
    .directive('fileUploadProgress', function(FileUploadService, $timeout) {
    return {
        restrict: 'AE',
        templateUrl: 'js/main/directives/partials/fileUploadProgress.html',
        link: function($scope, element, attr) {

            $scope.progress = -1;

            $scope.$on('fileupload:uploadInProgress',function(){
                $scope.progressVisible = FileUploadService.progressVisible;
            });

            $scope.$on('fileupload:progress',function(){
                $scope.progress = FileUploadService.progress;
            });

            $scope.$watch('progress',function(newVal){
                //console.log("$scope.progress", newVal);
                if(parseInt(newVal)>=99 && $scope.progressVisible==true) {
                    $timeout(function(){
                        $scope.progress= 0;
                        $scope.progressVisible = false;
                    }, 5000);
                }
            });

        }
    };
});
