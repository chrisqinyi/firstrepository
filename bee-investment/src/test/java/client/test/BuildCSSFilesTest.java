package client.test;

import client.build.BuildCSSFiles;

public class BuildCSSFilesTest {

    public static void main(String[] args) {
        BuildCSSFiles.outputCSSFiles();
        String css = BuildCSSFiles.getCSSLinkString("account");
        String css2 = BuildCSSFiles.getCSSLinkString("login");
        System.out.println(css);
        System.out.println("====");
        System.out.println(css2);

    }

}
