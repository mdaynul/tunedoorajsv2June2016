musicApp.service("FileUploadService",
    ['$timeout', '$window','Config','$rootScope', function ($timeout, $window, Config, $rootScope) {

        var _this = this;

        _this.uploadInProgress = false;
        _this.progressVisible = false;
        _this.progress = 0;

        _this.uploadFile = function(fileObj) {
            _this.progress = 0;
            $rootScope.$broadcast("fileupload:progress");
            if(_this.uploadInProgress){
                console.log("File upload is already in progress");
                return false;
            }
            _this.uploadInProgress = true;
            
            _this.progressVisible = true;

            $rootScope.$broadcast("fileupload:uploadInProgress");

            // $rootScope.$apply();
            var fd = new FormData();
            fd.append('file', fileObj.file);
            fd.append('title', fileObj.title);

            var xhr = new XMLHttpRequest();
            xhr.upload.addEventListener("progress", uploadProgress, false);
            xhr.addEventListener("load", uploadComplete, false);
            xhr.addEventListener("error", uploadFailed, false);
            xhr.addEventListener("abort", uploadCanceled, false);
            xhr.open("POST", "/music/save");

            xhr.send(fd)

        };

        function uploadProgress(evt) {
            if (evt.lengthComputable) {
                _this.progress = Math.round(evt.loaded * 100 / evt.total);
            } else {
                _this.progress = 'unable to compute'
            }
            $rootScope.$broadcast("fileupload:progress");
            $rootScope.$apply();
            console.log("uploadProgress", _this.progress)
        }

        function uploadComplete(evt) {
            /* This event is raised when the server send back a response */
            // _this.progressVisible = false;
            _this.uploadInProgress = false;
            $rootScope.$broadcast("fileupload:progress");
            $rootScope.$broadcast("fileupload:uploadInProgress");
            $rootScope.$apply();

        }

        function uploadFailed(evt) {
            alert("There was an error attempting to upload the file.");
        }

        function uploadCanceled(evt) {
            _this.progressVisible = false;
            alert("The upload has been canceled by the user or the browser dropped the connection.")
        }

    }]);
