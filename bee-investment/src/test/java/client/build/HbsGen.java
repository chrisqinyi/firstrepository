package client.build;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;

public class HbsGen {

    private static String genHbsHtmlStr(String id, String content) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script id=\"");
        sb.append(id);
        sb.append("\" type=\"text/x-handlebars\">");
        sb.append(Constant.HTML_LINE_BREAK);
        sb.append(content);
        sb.append("</script>");
        return sb.toString();
    }

    public static String outputSpecialHbsStr(String specialPath) throws IOException {
        File srcFP = new File(specialPath);
        if (!srcFP.exists() || !srcFP.isDirectory()) {
            System.err.println("HbsGen.outputSpecialHbsStr - Must be Directory");
            return "";
        }
        File[] hbsFiles = srcFP.listFiles(new FilenameFilter() {

            public boolean accept(File f, String name) {
                return name.endsWith(".hbs") || name.endsWith(".HBS");
            }
        });

        if (hbsFiles.length == 0) {
            System.out.println("There is no HBS file in this folder [" + specialPath + "]");
            return "";
        }

        StringBuffer sb = new StringBuffer();

        for (File hbsFile : hbsFiles) {
            if (hbsFile.length() > 0) {
                String hbsFileName = IOTools.getFileName(hbsFile.getPath());
                String hbsFileContent = IOTools.getFileContent(hbsFile.getPath(), true);
                sb.append(genHbsHtmlStr(hbsFileName, hbsFileContent) + Constant.HTML_LINE_BREAK);
            }
        }
        return sb.toString();
    }

    public static String outputBaseHbsStr(String groupPath) throws IOException {
        File srcGroupPath = new File(groupPath);
        if (!srcGroupPath.isDirectory()) {
            System.err.println("HbsGen.outputBaseHbsStr - need folder path");
        }

        File[] subGroupPath = srcGroupPath.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        StringBuffer sb = new StringBuffer();
        if (subGroupPath.length > 0) {
            for (File subPath : subGroupPath) {
                sb.append(outputSpecialHbsStr(subPath.getPath()));
            }
        }
        return sb.toString();
    }
}
