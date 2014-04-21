package client.test;

import client.build.BuildCSSFiles;
import client.build.BuildHTMLFiles;
import client.build.BuildJSFiles;

public class BuildHTMLFilesTest {

    public static void main(String[] args) {
        try {
            BuildCSSFiles.outputCSSFiles();
            BuildJSFiles.outputJSFiles();

            // BuildHTMLFiles.buildHTML("account");
            // BuildHTMLFiles.buildHTML("login");
            BuildHTMLFiles.outputHTMLFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
