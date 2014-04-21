package client.build;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;

public class JSGen {

    public static final String VAR = "var ";
    public static final String EQUAL = " = ";
    public static final String SINGLE_QUOTE = "'";
    public static final String JS_OUT_SINGLE_QUOTE = "\\'";
    public static final String SEMICOLON = ";";

   /* private static String convert2JSVar(String input) {
        return StringUtils.replace(input, SINGLE_QUOTE, JS_OUT_SINGLE_QUOTE);
    }

    @SuppressWarnings("unused")
    private static String genVarfromHBS(String srcFilesPath) throws IOException {
        File srcFP = new File(srcFilesPath);
        if (!srcFP.exists() || !srcFP.isDirectory()) {
            System.err.println("JSGen.genVarfromHBS - Must be Directory");
            return "";
        }
        File[] hbsFiles = srcFP.listFiles(new FilenameFilter() {

            public boolean accept(File f, String name) {
                return name.endsWith(".hbs") || name.endsWith(".HBS");
            }
        });

        if (hbsFiles.length == 0) {
            System.out.println("There is no HBS file in this folder [" + srcFilesPath + "]");
            return "";
        }

        StringBuffer sb = new StringBuffer();

        for (File hbsFile : hbsFiles) {
            if (hbsFile.length() > 0) {
                String hbsFileName = IOTools.getFileName(hbsFile.getPath());
                String hbsFileContent = convert2JSVar(IOTools.getFileContent(hbsFile.getPath(), false));
                sb.append(VAR + hbsFileName + EQUAL + SINGLE_QUOTE + hbsFileContent + SINGLE_QUOTE + SEMICOLON + IOTools.BREAK_LINE);
            }
        }

        return sb.toString();
    }*/

    private static String getJSContent(String srcFilesPath) throws IOException {
        File srcFP = new File(srcFilesPath);
        if (!srcFP.exists() || !srcFP.isDirectory()) {
            System.err.println("JSGen.getJSContent - Must be Directory");
            return "";
        }
        File[] jsFiles = srcFP.listFiles(new FilenameFilter() {

            public boolean accept(File f, String name) {
                return name.endsWith(".js") || name.endsWith(".JS");
            }
        });
        if (jsFiles.length < 0) {
            System.out.println("There is no JS file in this folder [" + srcFilesPath + "]");
            return "";
        }
        StringBuffer sb = new StringBuffer();

        for (File jsFile : jsFiles) {
            String jsFileContent = IOTools.getFileContent(jsFile.getPath(), true);
            sb.append(jsFileContent);
        }

        return sb.toString();
    }

    private static void genJS(String srcPath, String outPath) throws IOException {
        // String f = JSGen.genVarfromHBS(srcPath);
        // f = f + JSGen.getJSContent(srcPath);
        String f = JSGen.getJSContent(srcPath);
        if (f != "") {
            IOTools.outputFile(outPath, IOTools.getOutputFileName(srcPath) + ".js", f);
        }
    }

    public static void outputSpecialJSFiles(String groupPath, String outPath) throws IOException {

        File srcGroupPath = new File(groupPath);
        File outFilePath = new File(outPath);
        if (!outFilePath.exists()) {
            outFilePath.mkdirs();
        }
        if (!srcGroupPath.isDirectory() || !outFilePath.isDirectory()) {
            System.err.println("JSGen.outputSpecialJSFiles - need folder path");
        }

        File[] subGroupPath = srcGroupPath.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        if (subGroupPath.length > 0) {
            for (File subPath : subGroupPath) {
                genJS(subPath.getPath(), outPath);
            }
        }

    }

}
