(function ($) {
    var swprintpdf = {
        init: function (boxobj, args) {
            return (function () {
                var newWin = swprintpdf.getNewWin(boxobj, args);
                var doc = newWin.document;
                var a = swprintpdf.addAElementToDoc(doc, args);
                swprintpdf.openAndPrint(newWin, doc, a);
            })();
        },
        //获得新窗口document
        getNewWin: function (boxobj, args) {
            return (function () {
                var windowAttr = "location=yes,statusbar=no,directories=no,menubar=no,titlebar=no,toolbar=no,dependent=no";
                windowAttr += ",width=" + args.popWd + ",height=" + args.popHt + "";
                windowAttr += ",resizable=yes,screenX=" + args.popX + ",screenY=" + args.popY + ",personalbar=no,scrollbars=yes";

                var newWin = window.open(args.winname, "_blank", windowAttr);
                var doc = newWin.document;

                doc.open();
                doc.write(' <!DOCTYPE html>'
                    + ' <html lang="en">'
                    + ' <head>'
                    + ' 	<meta charset="UTF-8" />'
                    + ' 	<title>' + args.popTitle + '</title>'
                    + ' </head>'
                    + ' <body>'
                    + ' </body>'
                    + ' </html>');
                doc.close();
                return newWin;
            })();
        },
        //新窗口中创建 a 标签
        addAElementToDoc: function (doc, args) {
            return (function () {
                var a = doc.createElement('a');
                var url = args.pdfUrl;
                a.href = url;
                return a;
            })();
        },
        //打开并打印
        openAndPrint: function (newWin, doc, a) {
            return (function () {
                a.click();
                setTimeout(function () {
                    $(doc).ready(function () {
                        newWin.focus();
                        newWin.print();
                    });
                }, 2000);
            })();
        }
    }

    $.fn.swprintpdfCreate = function (options) {
        var args = $.extend({
            popWd: "1000px",
            popHt: "700px",
            popX: "200px",
            popY: "200px",
            popTitle: "打印",
            popClose: false,
            extraCss: "",
            extraHead: "",
            pdfUrl: "/action/print",
            winname: "swpdfprint.html"
        }, options);
        swprintpdf.init(this, args);
    }
})(jQuery);