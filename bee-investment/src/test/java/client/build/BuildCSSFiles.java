package client.build;

import java.io.IOException;

public class BuildCSSFiles {

    public static void outputCSSFiles() {
        try {
            IOTools.copyFile(Constant.IO_SRC_CSS_PATH, Constant.IO_OUT_CSS_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCSSLinkString(String featureName) {
        StringBuffer sb = new StringBuffer();
        // Out CSS LIB
        sb.append(IOTools.buildHtmlLinkString(Constant.IO_OUT_CSS_LIB_PATH, Constant.HTML_CSS_LIB_PATH));
        // Out CSS BASE
        sb.append(IOTools.buildHtmlLinkString(Constant.IO_OUT_CSS_BASE_PATH, Constant.HTML_CSS_BASE_PATH));
        // Out CSS Feature
        sb.append(IOTools.buildHTMLLinkFeatureString(featureName, Constant.IO_OUT_CSS_FEATURES_PATH, Constant.HTML_CSS_FEATURES_PATH));

        return sb.toString();
    }

}
