package client;

import client.build.BuildCSSFiles;
import client.build.BuildHTMLFiles;
import client.build.BuildJSFiles;

public class ClientGenerator {

    public static void main(String[] args) {
        try {
            BuildCSSFiles.outputCSSFiles();
            BuildJSFiles.outputJSFiles();
            BuildHTMLFiles.outputHTMLFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
