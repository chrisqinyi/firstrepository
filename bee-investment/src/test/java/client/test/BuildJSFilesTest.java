package client.test;

import client.build.BuildJSFiles;

public class BuildJSFilesTest {

    public static void main(String[] args) {
        BuildJSFiles.outputJSFiles();
        String js = BuildJSFiles.getJSLinkString("account");
        String js2 = BuildJSFiles.getJSLinkString("login");
        System.out.println(js);
        System.out.println("====");
        System.out.println(js2);
    }

}
