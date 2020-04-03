package com.crawler.utils;

public class NetworkUtil {

    /**
     * 重新拨号
     */
    public static void dial() {
        try {
            Process process = Runtime.getRuntime().exec("C:\\wideband.bat");
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
