/**
 */

musicApp.controller('RecordTuneCtrl', ['recorderService','MusicService', '$uibModal', '$log',
function (recorderService, MusicService, $uibModal, $log) {

    this.recorded = null;
    (function (_this) {
        _this.upload = function(id){
            MusicService.uploadSongs(_this.recorded);
        };
        _this.uploadFromFileSystem = false;
        _this.recordTune = false;

    })(this);

    (function (_this) {
        _this.open = function (size) {
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: '/html/tuneForm.html',
                controller: 'UploadTuneModalInstanceCtrl',
                controllerAs: 'ctrl',
                size: size,
                resolve: {
                    tune: function () {
                        return _this.recorded;
                    }
                }
            });

            modalInstance.result.then(function (selectedItem) {
                console.log(selectedItem);
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        };
    })(this);

    // this.changeVolume = function (value) {
    //     if (!volume) return;
    //     volumeLevel = value;
    //     volume.gain.value = value;
    // };

}]);

musicApp.controller('UploadTuneModalInstanceCtrl', function ($uibModalInstance, MusicService, tune) {

    var _this = this;
    _this.tune = tune;
    _this.tuneName = null;
    _this.isUploading = null;

    _this.ok = function () {
        if(!_this.tuneName) return false;
        _this.isUploading = true;
        MusicService.uploadSongs({file:_this.tune, tuneName:_this.tuneName},function(response){
            _this.isUploading = false;
        });
        $uibModalInstance.close(_this.tuneName);
    };

    _this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});