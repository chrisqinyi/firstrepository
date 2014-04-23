package client.build;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

import org.springframework.util.StringUtils;

public class IOTools {

    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final String BREAK_LINE = System.getProperty("line.separator");

    public static String getFileContent(String filePath, boolean withBreakline) throws IOException {
        File f = new File(filePath);
        if (!f.exists() || !f.isFile() || f.length() == 0 || !f.canRead()) {
            return Constant.STRING_EMPTY;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(Constant.STRING_EMPTY);

        FileInputStream fileInput = new FileInputStream(f);
        InputStreamReader inputStrReader = new InputStreamReader(fileInput, CHARACTER_ENCODING);
        BufferedReader buffereReader = new BufferedReader(inputStrReader);
        String line = Constant.STRING_EMPTY;
        while ((line = buffereReader.readLine()) != null) {
            sb.append(line);
            sb.append(withBreakline ? BREAK_LINE : Constant.STRING_EMPTY);
        }
        buffereReader.close();
        inputStrReader.close();
        fileInput.close();

        return sb.toString();
    }

    public static String getFileName(String filePath) {
        File f = new File(filePath);
        if (!f.exists() || !f.isFile() || f.length() == 0 || !f.canRead()) {
            return Constant.STRING_EMPTY;
        }
        return StringUtils.split(f.getName(), ".")[0];
    }

    public static String getOutputFileName(String srcPath) {
        return srcPath.substring(srcPath.lastIndexOf(File.separator) + 1);
    }

    public static void outputFile(String filePath, String fileName, String fileContent) throws IOException {
        File outFilePath = new File(filePath);
        if (!outFilePath.exists()) {
            outFilePath.mkdirs();
        }

        File outFile = new File(filePath + File.separator + fileName);
        if (!outFile.exists()) {
            outFile.createNewFile();
        }

        FileOutputStream fileOutput = new FileOutputStream(outFile);
        PrintWriter fileWrite = new PrintWriter(new OutputStreamWriter(fileOutput, CHARACTER_ENCODING));
        BufferedWriter buffereWriter = new BufferedWriter(fileWrite);
        buffereWriter.write(fileContent);
        buffereWriter.flush();
        buffereWriter.close();
    }

    public static void copyFile(String srcPath, String outPath) throws IOException {
        File src = new File(srcPath);
        File out = new File(outPath);
        if (!out.exists()) {
            out.mkdirs();
        }
        if (!src.isDirectory() || !out.isDirectory()) {
            System.err.println("IOTools.copyFile - not Directory");
            return;
        }

        File[] srcFiles = src.listFiles();

        if (srcFiles.length == 0) {
            System.out.println("IOTools.copyFile - there is no files in srcPath [" + srcPath + "]");
            return;
        }

        for (File srcFile : srcFiles) {
            if (srcFile.isFile()) {
                FileInputStream input = new FileInputStream(srcFile);
                FileOutputStream output = new FileOutputStream(outPath + File.separator + (srcFile.getName()).toString());

                FileChannel inFileChannel = input.getChannel();
                FileChannel outFileChannel = output.getChannel();

                inFileChannel.transferTo(0, inFileChannel.size(), outFileChannel);

                output.close();
                input.close();
                inFileChannel.close();
                outFileChannel.close();
            }
            if (srcFile.isDirectory()) {
                copyFile(srcPath + File.separator + srcFile.getName(), outPath + File.separator + srcFile.getName());
            }
        }

    }

    private static String buildCSSLink(String cssPath) {
        return "<link rel=\"stylesheet\" href=\"" + cssPath + "\" />";
    }

    private static String buildJSLink(String jsPath) {
        return "<script type=\"text/javascript\" src=\"" + jsPath + "\"></script>";
    }

    public static String buildHtmlLinkString(final String filePath, final String htmlPath) {
        StringBuffer sb = new StringBuffer();
        File path = new File(filePath);

        File[] pathFiles = path.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (filePath.indexOf("js") > -1) {
                    return name.endsWith(".js") || name.endsWith(".JS");
                } else {
                    return name.endsWith(".css") || name.endsWith(".CSS");
                }
            }
        });
        if (pathFiles.length > 0) {
            if (filePath.indexOf("js") > -1) {
                for (File subFile : pathFiles) {
                    sb.append(buildJSLink(htmlPath + IOTools.getOutputFileName(subFile.getPath())) + IOTools.BREAK_LINE);
                }
            } else {
                for (File subFile : pathFiles) {
                    sb.append(buildCSSLink(htmlPath + subFile.getName()) + IOTools.BREAK_LINE);
                }
            }
        }

        return sb.toString();
    }

    public static String buildHTMLLinkFeatureString(final String featureName, final String filePath, final String htmlPath) {
        String outStr = Constant.STRING_EMPTY;

        File path = new File(filePath);
        File[] pathFiles = path.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                if (filePath.indexOf("js") > -1) {
                    return name.equalsIgnoreCase(featureName + ".js") || name.equalsIgnoreCase(featureName + ".JS");
                } else {
                    return name.equalsIgnoreCase(featureName + ".css") || name.equalsIgnoreCase(featureName + ".CSS");
                }
            }
        });

        if (pathFiles.length > 0) {
            if (filePath.indexOf("js") > -1) {
                outStr = buildJSLink(htmlPath + featureName + ".js") + IOTools.BREAK_LINE;
            } else {
                outStr = buildCSSLink(htmlPath + featureName + ".css") + IOTools.BREAK_LINE;
            }
        }
        return outStr;
    }
}
