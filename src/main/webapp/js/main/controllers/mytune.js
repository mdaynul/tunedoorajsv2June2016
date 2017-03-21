/**
 */

angular.module('musicApp')
    .controller('MyTuneCtrl', function (MusicService, songList, $log, $uibModal) {
        this.songs = [];
        (function(_this){
            _this.songs = songList;

            _this.open = function (song, size) {
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: '/html/activeService.html',
                    controller: 'MyTuneActiveServicesModalInstanceCtrl',
                    controllerAs: 'ctrl',
                    size: size,
                    resolve: {
                        ActiveService: function () {
                            return MusicService.getActiveProfile();
                        },
                        Song : function(){
                            return song;
                        }
                    }
                });

                modalInstance.result.then(function (selectedItem) {
                    console.log(selectedItem);
                    modalInstance.close(selectedItem);
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            };
            _this.showGallery = function(song, size){
                var modalInstance = $uibModal.open({
                    animation: true,
                    templateUrl: '/html/galleryContents.html',
                    controller: 'MyTuneGalleryModalInstanceCtrl',
                    controllerAs: 'ctrl',
                    size: size,
                    resolve: {
                        Gallery: function () {
                            return MusicService.getGallery(song);
                        },
                        Song : function () {
                            return song
                        }
                    }
                });
                modalInstance.result.then(function (selectedItem) {
                    console.log(selectedItem);
                    modalInstance.close(selectedItem);
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            }

        })(this);

    });



musicApp.controller('PurchasedServiceModalInstanceCtrl',
    function ($uibModal, $uibModalInstance, Song, $log, $scope) {
        var _this = this;
        _this.song = Song;
        _this.done = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });

musicApp.controller('MyTuneGalleryModalInstanceCtrl',
    function ($uibModal, $uibModalInstance, Song, Gallery, $braintree, $log, $scope, $timeout) {

    var _this = this;
    // _this.services = Gallery;
    _this.service = null;
    $scope.song = Song;
    _this.song = Song;

    $timeout(function () {
        $scope.$apply(function () {
            _this.song = Song;
        });
    }, 3000);

    _this.selectedImage = '';
    _this.panzoomModel = {};
    _this.panzoomConfig = {
        zoomLevels: 12,
        neutralZoomLevel: 5,
        scalePerZoomLevel: 1.5,
        // initialZoomToFit: shark
    };

    _this.showImage = function (index) {
        console.log("Show Image", Song, index);
        _this.selectedImage = _this.song.gallery.urls[index];
    };

    _this.showPurchasedServices = function(size){
        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: '/html/purchasedServices.html',
            controller: 'PurchasedServiceModalInstanceCtrl',
            controllerAs: 'ctrl',
            size: size,
            resolve: {
                Song : function () {
                    return _this.song
                }
            }
        });
        modalInstance.result.then(function (selectedItem) {
            console.log(selectedItem);
            modalInstance.close(selectedItem);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    _this.ok = function (size) {
        console.log(Song, _this.service);
    };

    _this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});

musicApp.controller('MyTuneActiveServicesModalInstanceCtrl',
    function ($uibModal, $uibModalInstance, ActiveService, Song, $braintree, $log) {

    var _this = this;
    _this.services = ActiveService;
    _this.service = null;


    _this.addedServices = [];
    _this.currency = null;

    _this.getTotal = function(){
        var total = 0;
        for(var i=0; i< _this.addedServices.length; i++){
            total = total + parseInt(_this.addedServices[i].suggestedCost);
            _this.currency = _this.addedServices[i].currency;
        }
        return total;
    };

    _this.addOrRemoveItem = function(item){
        if(item.isAdded){
            removeItem(item);
        } else {
            addItem(item);
        }
    };

        
    var addItem = function(item){
        item['isAdded'] = true;
        _this.addedServices.push(item);
    };

    var removeItem = function(item){
        var addedItemIndex = null;
        for(var i=0; i< _this.addedServices.length; i++){
            var isItemAdded = (item.id === _this.addedServices[i].id);
            if(isItemAdded){
                addedItemIndex = i;
                delete item['isAdded'];
                break;
            }
        }
        _this.addedServices.splice(addedItemIndex, 1);
    };


    _this.ok = function (size) {
        console.log(Song, _this.service);

        //open braintree modal

        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: '/html/braintreeForm.html',
            controller: 'BraintreeServicesModalInstanceCtrl',
            controllerAs: 'modalCtrl',
            size: size,
            resolve: {
                clientToken : function() {
                    return $braintree.getClientToken().success(function(data) {
                        console.log(data);
                    }).then(function(resp){
                        return resp.data;
                    });
                },
                Song : function () {
                    return Song
                },
                SelectedService : function () {
                    return _this.addedServices;
                },
                TotalAmount: function () {
                    return _this.getTotal()
                },
                Currency:function () {
                    return _this.currency;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            console.log(selectedItem);
            $uibModalInstance.close(selectedItem);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });

    };

    _this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});


musicApp.controller('BraintreeServicesModalInstanceCtrl',
    function ($uibModalInstance, SelectedService, Song, clientToken, TotalAmount, Currency, $braintree, $http, $scope) {

        var _this = this;

        _this.song = Song;
        _this.selectedServices = SelectedService;
        _this.payload = null;
        _this.isPaymentMethodReady = false;
        _this.isError = false;
        _this.success = null;
        _this.shouldDisplayResult = false;

        _this.dropinOptions = {
            onReady: function (payload) {
                $scope.$apply(function(){
                    _this.isPaymentMethodReady = true;
                });
                console.log("On Ready "+ payload);
            },

            onError: function (error) {
                $scope.$apply(function(){
                    _this.isError = true;
                    // display error
                });
                console.log("On Error", error);
            },

            onPaymentMethodReceived: function(payload) {
                $scope.$apply(function(){
                    _this.payload = payload;
                });
                console.log("onPaymentMethodReceived >> ", payload);
            }
        };
        
        _this.paymentInitiated = function(){
            return _this.payload != null;
        };

        _this.getCurrency = function(){
            return Currency;
        };
        _this.getTotalAmount = function(){
            return TotalAmount;
        };

        _this.reset = function(){
            _this.payload = null;
        };

        _this.cancel = function(){
            _this.payload = null;
            // close this modal
            $uibModalInstance.dismiss('cancel');
        };

        _this.payButton = function() {};

        var getServiceIds = function () {
            var id = [];
            for(var ind in SelectedService){
                id.push(SelectedService[ind].id);
            }
            return id.join(",");
        };
        
        _this.sendEmail = function() {

            $http({
                method: 'POST',
                url: '/invoice/email',
                data:_this.payload
            }).then(function (response) {
                console.log("Email done");
                // close all modal
            }, function (response) {
                console.log("Error on sending Email");

            });

        };
        _this.payButtonClicked = function() {

            console.log(_this.payload);

            _this.payload['amount'] = TotalAmount;
            _this.payload['musicId'] = Song.id;
            _this.payload['musicName'] = Song.title;
            _this.payload['services'] = getServiceIds();
            
            $http({
                method: 'POST',
                url: '/braintree/checkout',
                data:_this.payload
            }).then(function (response) {
                var message = JSON.parse(response.data.message);
                _this.success = message.target;
                _this.shouldDisplayResult = true;
                var invoiceHtml = response.data.invoice;
                if(response.data.invoice){
                    _this.success['invoice'] = response.data.invoice;
                }
            }, function (response) {
                _this.isError = true;
                _this.errors = response.data;
            });
        };
        _this.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

    });