package client.test;

import client.build.BuildCSSFiles;
import client.build.Constant;

public class BuildCSSFilesTest {

    public static void main(String[] args) {
        System.setProperty(Constant.DEV_TEST_FLAG, "true");
        BuildCSSFiles.outputCSSFiles();
        String css = BuildCSSFiles.getCSSLinkString("account");
        String css2 = BuildCSSFiles.getCSSLinkString("login");
        System.out.println(css);
        System.out.println("====");
        System.out.println(css2);

        
        String css3 = BuildCSSFiles.getTestCSSLinkString();
        System.out.println("====");
        System.out.println(css3);
    }

}
