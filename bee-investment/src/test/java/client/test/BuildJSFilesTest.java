package client.test;

import client.build.BuildJSFiles;
import client.build.Constant;

public class BuildJSFilesTest {

    public static void main(String[] args) {
        System.setProperty(Constant.DEV_TEST_FLAG, "true");
        BuildJSFiles.outputJSFiles();
//        String js = BuildJSFiles.getJSLinkString("account");
//        String js2 = BuildJSFiles.getJSLinkString("login");
//        System.out.println(js);
//        System.out.println("====");
//        System.out.println(js2);
//
//        String js3 = BuildJSFiles.getTestJSLinkString("login");
//        System.out.println("====");
//        System.out.println(js3);

        String js4 = BuildJSFiles.getAllTestJSLinkString();
        System.out.println("====");
        System.out.println(js4);
    }

}
