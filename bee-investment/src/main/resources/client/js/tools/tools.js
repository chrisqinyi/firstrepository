var commonUtils = {};

commonUtils.includeJS = function(jsPath) {
    var innerHtmlCode = '<script type="text/javascript" src="' + jsPath + '"></script>';
    $("head").append(innerHtmlCode);
};

commonUtils.includeFeatureJS = function(jsSrc) {
    var jsPath = '../' + jsSrc;
    this.includeJS(jsPath);
};

