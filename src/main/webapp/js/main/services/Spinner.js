
musicApp.factory('ngSpinner', function () {
    var ngSpinner = function () {
        var self = this;

        this.spinning = false;
        this.requestCount = 0; // $http.pendingRequests.length;

        this.opts = {
            lines: 13, // The number of lines to draw
            length: 20, // The length of each line
            width: 6, // The line thickness
            radius: 13, // The radius of the inner circle
            corners: 1, // Corner roundness (0..1)
            rotate: 0, // The rotation offset
            direction: 1, // 1: clockwise, -1: counterclockwise
            color: '#000', // #rgb or #rrggbb or array of colors
            speed: 1, // Rounds per second
            trail: 60, // Afterglow percentage
            shadow: false, // Whether to render a shadow
            hwaccel: false, // Whether to use hardware acceleration
            className: 'spinner', // The CSS class to assign to the spinner
            zIndex: 2e9, // The z-index (defaults to 2000000000)
            top: '200px', // Top position relative to parent in px
            left: '50%' // Left position relative to parent in px
        };

        this.spinner = new Spinner(this.opts);

        this.spin = function (elId) {
            var el;
            self.requestCount += 1;
            if (self.requestCount !== 1) {
                return;
            }
            if (!elId) {
                el = document.getElementById('loading-indicator');
            } else {
                el = document.getElementById(elId);
            }
            self.spinning = true;
            el.style.display = 'block';
            self.el = el;
            self.spinner.spin(el);
            return self.spinner;
        };

        this.forceStop = function () {
            self.spinner.stop();
            self.spinning = false;
            self.requestCount = 0;
            if (self.el !== undefined) {
                self.el.style.display = 'none';
            }
        };

        this.stop = function () {
            self.requestCount -= 1;
            if (self.requestCount <= 0) {
                self.forceStop();
            }
        };

        this.isSpinning = function () {
            return self.spinning;
        };

    };
    return new ngSpinner();
});
