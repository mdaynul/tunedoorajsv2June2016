/**
 */

angular.module('musicApp').service('MusicService',['$http','Config','UserService','ngSpinner','FileUploadService',
    function($http, Config, UserService, ngSpinner, FileUploadService){

    this.getSongs = function (callback) {
        // var userDetail = UserService.getToken();
        return $http({
            method: 'POST',
            url: Config.serverUrl +'/songs'
        }).success(function (resp) {
            callback ? callback(resp) : angular.noop();
        }).then(function (resp) {
            return resp.data;
        });
    };
    this.getGallery = function(song, callback){
        song['gallery'] = {};
        function showError(elt, err) {
            var content = "<p class='alert alert-danger'>" + err + "</p>";
            song['gallery']['html'] = content;
        }
        function showContent(elt, type, urls) {
            var content = '';
            for(var url in urls){
                content = content + '<img src="' + urls[url]
                    +'" class="img-responsive" style="width: 250px; height: 100px" alt="Responsive image"><br/>';
            }
            song['gallery']['html'] = content;
        }

        function getMimeType(filename){
            var regexAll = /[^\\]*\.(\w+)$/;
            var total = filename.match(regexAll);
            // var filename = total[0];
            var extension = total[1];
            return "image/"+extension;
        }

        function getContents(zip) {
            var urls = [];
            var keys = Object.keys(zip.files);
            for (var key in keys) {
                var filename = keys[key];
                console.log(filename);
                var mimeType = getMimeType(filename);
                var anIMage = URL.createObjectURL(new Blob([zip.file(filename).asArrayBuffer()], {type: mimeType}))
                zip.files[filename]['url'] = anIMage;
                urls.push(anIMage)
            }
            song['gallery']['urls'] = urls;
            return urls;
        }
        ngSpinner.spin();
        JSZipUtils.getBinaryContent('/song/gallery?id='+37, function(err, data) {
            var elt = document.getElementById('jszip_utils');
            if(err) {
                showError(elt, err);
                return;
            }

            try {
                var zip = new JSZip(data);
                showContent(elt, "" + data, getContents(zip));
                ngSpinner.stop()
            } catch(e) {
                console.log(e);
                ngSpinner.stop();
                showError(elt, e);
            }
        });
    };


    this.getActiveProfile = function (callback) {
        return $http({
            method: 'POST',
            url: Config.serverUrl +'/song/services'
        }).success(function (resp) {
            callback? callback(resp) : '';
        }).then(function (resp) {
            return resp.data;
        })
    };

    this.uploadSongs = function (formData, callback) {

        var data = new FormData();
        var now = Date.now();
        formData.file.name = formData.tuneName.split(' ').join('_') + 'audio_recording_' + now;
        formData.file.lastModified = now;

        data.append('file', formData.file);
        data.append('title', formData.tuneName);

        var val = {};
        val['file'] = formData.file;
        val['title'] = formData.tuneName;
        FileUploadService.uploadFile(val);

/*        $http.post(Config.serverUrl +'/music/save', data, {
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined,
                'userAgent': navigator.userAgent
            }
        }).then(function (response) {
                callback ? callback(response) : response
        }, function (response) {
            console.log(response)
            }, function (response) {
            console.log("Progress", response)
            });
        */
        // .success(function(response){
        //     callback ? callback(response) : response
        // })
        // .error(function(){
        //     // todo handle
        // });

        // $http({
        //     method: 'POST',
        //     url: Config.serverUrl +'/music/save',
        //     data: data,
        //     contentType: false,
        //     cache: false,
        //     processData: false
        // }).success(function (resp) {
        //     callback(resp);
        // });
    };

}]);