package client.build;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class BuildHTMLFiles {

    public static void outputHTMLFiles() throws IOException {
        File srcGroupPath = new File(Constant.IO_SRC_FEATURES_PATH);
        File[] subGroupPath = srcGroupPath.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        if (subGroupPath.length > 0) {
            for (File subPath : subGroupPath) {
                buildHTML(subPath.getName(), subPath.getPath());
            }
        }
    }

    public static void buildHTML(String featureName, String featurePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        // Board -1
        sb.append(IOTools.getFileContent(Constant.IO_SRC_BOARD_PATH + "/board_1.hbs", true));
        // CSS
        sb.append(BuildCSSFiles.getCSSLinkString(featureName));
        // Board -2
        sb.append(IOTools.getFileContent(Constant.IO_SRC_BOARD_PATH + "/board_2.hbs", true));
        // Base HBS
        sb.append(HbsGen.outputBaseHbsStr(Constant.IO_SRC_BASE_PATH));
        // Feature HBS
        sb.append(HbsGen.outputSpecialHbsStr(featurePath));
        // JS
        sb.append(BuildJSFiles.getJSLinkString(featureName));
        // Board -3
        sb.append(IOTools.getFileContent(Constant.IO_SRC_BOARD_PATH + "/board_3.hbs", true));
        // Output
        IOTools.outputFile(Constant.IO_OUT_HTML_PATH, featureName + ".html", sb.toString());
    }
}
