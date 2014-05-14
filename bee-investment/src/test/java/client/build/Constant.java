package client.build;

import java.io.File;

import org.springframework.util.StringUtils;

public class Constant {

    public static final String STRING_EMPTY = "";
    public static final String HTML_LINE_BREAK = "\n";

    public static final String TYPE_LIB = "lib";
    public static final String TYPE_FEATURES = "features";
    public static final String TYPE_BASE = "base";

    public static final String LIB_PATH = "/lib";
    public static final String FEATURES_PATH = "/features";
    public static final String BASE_PATH = "/base";
    public static final String TOOLS_PATH = "/tools";

    // SRC
    public static String IO_SRC_PATH = "/src/main/resources/client";

    public static String IO_SRC_BOARD_PATH;
    public static String IO_SRC_BASE_PATH;
    public static String IO_SRC_FEATURES_PATH;
    public static String IO_SRC_JS_PATH;
    public static String IO_SRC_CSS_PATH;

    public static String IO_SRC_CSS_LIB_PATH;
    public static String IO_SRC_CSS_FEATURES_PATH;
    public static String IO_SRC_CSS_BASE_PATH;
    public static String IO_SRC_JS_LIB_PATH;
    public static String IO_SRC_JS_BASE_PATH;

    // OUT
    public static String IO_OUT_PATH = "/dist";

    public static String IO_OUT_HTML_PATH;
    public static String IO_OUT_JS_PATH;
    public static String IO_OUT_CSS_PATH;

    public static String IO_OUT_JS_LIB_PATH;
    public static String IO_OUT_JS_FEATURES_PATH;
    public static String IO_OUT_JS_BASE_PATH;
    public static String IO_OUT_JS_TOOLS_PATH;
    public static String IO_OUT_CSS_LIB_PATH;
    public static String IO_OUT_CSS_FEATURES_PATH;
    public static String IO_OUT_CSS_BASE_PATH;

    // HTML use only
    public static final String HTML_JS_PATH = "../js";
    public static final String HTML_JS_LIB_PATH = HTML_JS_PATH + LIB_PATH + "/";
    public static final String HTML_JS_FEATURES_PATH = HTML_JS_PATH + FEATURES_PATH + "/";
    public static final String HTML_JS_BASE_PATH = HTML_JS_PATH + BASE_PATH + "/";
    public static final String HTML_JS_TOOLS_PATH = HTML_JS_PATH + TOOLS_PATH + "/";
    public static final String HTML_CSS_PATH = "../css";
    public static final String HTML_CSS_LIB_PATH = HTML_CSS_PATH + LIB_PATH + "/";
    public static final String HTML_CSS_FEATURES_PATH = HTML_CSS_PATH + FEATURES_PATH + "/";
    public static final String HTML_CSS_BASE_PATH = HTML_CSS_PATH + BASE_PATH + "/";

    public static final String DEV_TEST_FLAG = "DEV_TEST_FLAG";
    public static final String TEST_PATH = "/test";
    public static String TEST_SRC_PATH = "/src/test/resources/client";
    public static String TEST_SRC_BOARD_PATH;
    public static String TEST_SRC_CSS_PATH;
    public static String TEST_SRC_JS_PATH;
    public static String TEST_SRC_MOCK_PATH;
    public static String TEST_SRC_QUNIT_PATH;
    public static String TEST_OUT_CSS_PATH;
    public static String TEST_OUT_JS_PATH;
    public static String TEST_OUT_MOCK_PATH;
    public static String TEST_OUT_QUNIT_PATH;
    public static String TEST_OUT_PATH;

    static {
        String base = System.getProperty("user.dir");
        IO_SRC_PATH = convertFileSeparator(base + IO_SRC_PATH);
        IO_OUT_PATH = convertFileSeparator(base + IO_OUT_PATH);

        IO_SRC_BOARD_PATH = convertFileSeparator(IO_SRC_PATH + "/board");
        IO_SRC_BASE_PATH = convertFileSeparator(IO_SRC_PATH + "/base");
        IO_SRC_FEATURES_PATH = convertFileSeparator(IO_SRC_PATH + "/features");
        IO_SRC_JS_PATH = convertFileSeparator(IO_SRC_PATH + "/js");
        IO_SRC_CSS_PATH = convertFileSeparator(IO_SRC_PATH + "/css");

        IO_SRC_CSS_LIB_PATH = convertFileSeparator(IO_SRC_CSS_PATH + LIB_PATH);
        IO_SRC_CSS_FEATURES_PATH = convertFileSeparator(IO_SRC_CSS_PATH + FEATURES_PATH);
        IO_SRC_CSS_BASE_PATH = convertFileSeparator(IO_SRC_CSS_PATH + BASE_PATH);
        IO_SRC_JS_LIB_PATH = convertFileSeparator(IO_SRC_JS_PATH + LIB_PATH);
        IO_SRC_JS_BASE_PATH = convertFileSeparator(IO_SRC_JS_PATH + BASE_PATH);

        IO_OUT_HTML_PATH = convertFileSeparator(IO_OUT_PATH + "/view");
        IO_OUT_JS_PATH = convertFileSeparator(IO_OUT_PATH + "/js");
        IO_OUT_CSS_PATH = convertFileSeparator(IO_OUT_PATH + "/css");

        IO_OUT_JS_LIB_PATH = convertFileSeparator(IO_OUT_JS_PATH + LIB_PATH);
        IO_OUT_JS_FEATURES_PATH = convertFileSeparator(IO_OUT_JS_PATH + FEATURES_PATH);
        IO_OUT_JS_BASE_PATH = convertFileSeparator(IO_OUT_JS_PATH + BASE_PATH);
        IO_OUT_JS_TOOLS_PATH = convertFileSeparator(IO_OUT_JS_PATH + TOOLS_PATH);
        IO_OUT_CSS_LIB_PATH = convertFileSeparator(IO_OUT_CSS_PATH + LIB_PATH);
        IO_OUT_CSS_FEATURES_PATH = convertFileSeparator(IO_OUT_CSS_PATH + FEATURES_PATH);
        IO_OUT_CSS_BASE_PATH = convertFileSeparator(IO_OUT_CSS_PATH + BASE_PATH);

        TEST_SRC_PATH = convertFileSeparator(base + TEST_SRC_PATH);
        TEST_SRC_BOARD_PATH = convertFileSeparator(TEST_SRC_PATH + "/board");
        TEST_SRC_CSS_PATH = convertFileSeparator(TEST_SRC_PATH + "/css");
        TEST_SRC_JS_PATH = convertFileSeparator(TEST_SRC_PATH + "/js");
        TEST_SRC_MOCK_PATH = convertFileSeparator(TEST_SRC_PATH + "/mock");
        TEST_SRC_QUNIT_PATH = convertFileSeparator(TEST_SRC_PATH + "/qunit");
        TEST_OUT_CSS_PATH = convertFileSeparator(IO_OUT_PATH + TEST_PATH + "/css");
        TEST_OUT_JS_PATH = convertFileSeparator(IO_OUT_PATH + TEST_PATH + "/js");
        TEST_OUT_MOCK_PATH = convertFileSeparator(IO_OUT_PATH + TEST_PATH + "/mock");
        TEST_OUT_QUNIT_PATH = convertFileSeparator(IO_OUT_PATH + TEST_PATH + "/qunit");
        TEST_OUT_PATH = convertFileSeparator(IO_OUT_PATH + TEST_PATH);

    }

    private static String convertFileSeparator(String path) {
        String out = StringUtils.replace(path, "\\", File.separator);
        return StringUtils.replace(out, "/", File.separator);
    }
}
