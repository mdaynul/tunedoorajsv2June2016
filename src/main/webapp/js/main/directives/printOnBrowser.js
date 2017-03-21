(function (angular) {
    'use strict';

    function printDirective() {
        var printSection = document.getElementById('printSection');

        // // if there is no printing section, create one
        if (!printSection) {
            printSection = document.createElement('div');
            printSection.id = 'printSection';
            printSection.class = 'container';
            document.body.appendChild(printSection);
        }

        function link(scope, element, attrs) {


            element.on('click', function () {
                var elemToPrint = document.getElementById(attrs.printElementId);
                if (elemToPrint) {

                    printElement(elemToPrint);
                    window.print();
                }
            });


        }

        function printElement(elem) {
            // clones the element you want to print
            var domClone = elem.cloneNode(true);
            angular.element(printSection).html('');
            // angular.element(printSection).html('<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
            // printSection.appendChild('<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
            printSection.appendChild(domClone);
            // Popup('<div>'+angular.element(elem).html()+'</div>');


        }

        function Popup(data)
        {
            var mywindow = window.open('', 'my div', 'height=600,width=800');
            mywindow.document.write('<html><head><title>my div</title>');
            mywindow.document.write('<link rel="stylesheet" href="http://www.dynamicdrive.com/ddincludes/mainstyle.css" type="text/css" />');
            mywindow.document.write('</head><body >');
            mywindow.document.write(data);
            mywindow.document.write('</body></html>');

            mywindow.print();
            //  mywindow.close();

            return true;
        }

        return {
            link: link,
            restrict: 'A'
        };
    }

    angular.module('musicApp').directive('ngPrint', [printDirective]);
}(window.angular));