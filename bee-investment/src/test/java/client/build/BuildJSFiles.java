package client.build;

import java.io.IOException;

public class BuildJSFiles {

    public static void outputJSFiles() {
        try {
            // Copy public JS files [lib, base]
            IOTools.copyFile(Constant.IO_SRC_JS_PATH, Constant.IO_OUT_JS_PATH);
            // Out common JS files [common]
            JSGen.outputSpecialJSFiles(Constant.IO_SRC_BASE_PATH, Constant.IO_OUT_JS_BASE_PATH);
            // Out feature JS files [features]
            JSGen.outputSpecialJSFiles(Constant.IO_SRC_FEATURES_PATH, Constant.IO_OUT_JS_FEATURES_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getJSLinkString(String featureName) {
        StringBuffer sb = new StringBuffer();
        // Out JS LIB
        sb.append(IOTools.buildHtmlLinkString(Constant.IO_OUT_JS_LIB_PATH, Constant.HTML_JS_LIB_PATH));
        // Out JS TOOLS
        sb.append(IOTools.buildHtmlLinkString(Constant.IO_OUT_JS_TOOLS_PATH, Constant.HTML_JS_TOOLS_PATH));
        // Out JS BASE
        sb.append(IOTools.buildHtmlLinkString(Constant.IO_OUT_JS_BASE_PATH, Constant.HTML_JS_BASE_PATH));
        // Out JS Feature
        sb.append(IOTools.buildHTMLLinkFeatureString(featureName, Constant.IO_OUT_JS_FEATURES_PATH, Constant.HTML_JS_FEATURES_PATH));

        return sb.toString();
    }

}
