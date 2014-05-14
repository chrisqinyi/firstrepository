package client;

import client.build.BuildCSSFiles;
import client.build.BuildHTMLFiles;
import client.build.BuildJSFiles;
import client.build.Constant;
import client.build.IOTools;

public class ClientGenerator {

    public static void main(String[] args) {
        try {
            System.setProperty(Constant.DEV_TEST_FLAG, "true");

            // Clean
            IOTools.delFolder(Constant.IO_OUT_PATH);
            
            // Client
            BuildCSSFiles.outputCSSFiles();
            BuildJSFiles.outputJSFiles();
            BuildHTMLFiles.outputHTMLFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
