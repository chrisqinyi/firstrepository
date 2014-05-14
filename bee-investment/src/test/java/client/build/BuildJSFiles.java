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

            // Test
            if (Boolean.parseBoolean(System.getProperty(Constant.DEV_TEST_FLAG))) {
                // JS
                IOTools.copyFile(Constant.TEST_SRC_JS_PATH, Constant.TEST_OUT_JS_PATH);
                // mock
                IOTools.copyFile(Constant.TEST_SRC_MOCK_PATH, Constant.TEST_OUT_MOCK_PATH);
                // qunit
                IOTools.copyFile(Constant.TEST_SRC_QUNIT_PATH, Constant.TEST_OUT_QUNIT_PATH);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getJSLinkString(String featureName) {
        StringBuffer sb = new StringBuffer();
        sb.append(getCommonJSLinkString());

        // Out JS Feature
        sb.append(IOTools.buildHTMLLinkFeatureString(featureName, Constant.IO_OUT_JS_FEATURES_PATH, Constant.HTML_JS_FEATURES_PATH));

        // Test
        if (Boolean.parseBoolean(System.getProperty(Constant.DEV_TEST_FLAG))) {
            sb.append(IOTools.buildHtmlLinkString(Constant.TEST_OUT_JS_PATH, "../test/js/"));
            sb.append(IOTools.buildHTMLLinkFeatureString(featureName + "Mock", Constant.TEST_OUT_MOCK_PATH, "../test/mock/"));
        }

        return sb.toString();
    }

    public static String getCommonJSLinkString() {
        StringBuffer sb = new StringBuffer();
        // Out JS LIB
        sb.append(IOTools.buildHtmlLinkString(Constant.IO_OUT_JS_LIB_PATH, Constant.HTML_JS_LIB_PATH));
        // Out JS TOOLS
        sb.append(IOTools.buildHtmlLinkString(Constant.IO_OUT_JS_TOOLS_PATH, Constant.HTML_JS_TOOLS_PATH));
        // Out JS BASE
        sb.append(IOTools.buildHtmlLinkString(Constant.IO_OUT_JS_BASE_PATH, Constant.HTML_JS_BASE_PATH));
        return sb.toString();
    }

    public static String getTestJSLinkString(String featureName) {
        StringBuffer sb = new StringBuffer();
        sb.append(getCommonJSLinkString());

        // Out JS Feature
        sb.append(IOTools.buildHTMLLinkFeatureString(featureName, Constant.IO_OUT_JS_FEATURES_PATH, Constant.HTML_JS_FEATURES_PATH));

        // Test
        sb.append(IOTools.buildHtmlLinkString(Constant.TEST_OUT_JS_PATH, "js/"));
        sb.append(IOTools.buildHTMLLinkFeatureString(featureName + "Mock", Constant.TEST_OUT_MOCK_PATH, "mock/"));
        sb.append(IOTools.buildHTMLLinkFeatureString(featureName + "Test", Constant.TEST_OUT_QUNIT_PATH, "qunit/"));
        return sb.toString();
    }

    public static String getAllTestJSLinkString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getCommonJSLinkString());
        sb.append(IOTools.buildHtmlLinkString(Constant.IO_OUT_JS_FEATURES_PATH, Constant.HTML_JS_FEATURES_PATH));
        sb.append(IOTools.buildHtmlLinkString(Constant.TEST_OUT_JS_PATH, "js/"));
        sb.append(IOTools.buildHtmlLinkString(Constant.TEST_OUT_MOCK_PATH, "mock/"));
        sb.append(IOTools.buildHtmlLinkString(Constant.TEST_OUT_QUNIT_PATH, "qunit/"));
        return sb.toString();
    }

}
