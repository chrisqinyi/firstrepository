package client;

import client.build.BuildCSSFiles;
import client.build.BuildHTMLFiles;
import client.build.BuildJSFiles;
import client.build.Constant;
import client.build.IOTools;

public class ClientGenerator {

    public static void main(String[] args) {
        try {
            IOTools.delFolder(Constant.IO_OUT_PATH);
            BuildCSSFiles.outputCSSFiles();
            BuildJSFiles.outputJSFiles();
            BuildHTMLFiles.outputHTMLFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
