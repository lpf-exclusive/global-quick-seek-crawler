package com.crawler.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class IPUtils {

    public static String getHost() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostName();//获取计算机名字
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRealIP() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                String networkName = networkInterface.getName();
                if (networkName.indexOf("ppp") == 0) {
                    Enumeration<InetAddress> enumeration = networkInterface.getInetAddresses();
                    while (enumeration.hasMoreElements()) {
                        InetAddress inetAddress = enumeration.nextElement();
                        if (inetAddress != null && (inetAddress instanceof Inet4Address)) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveIP(String computerId) {
        String realIP = getRealIP();
        System.out.println("========定时器启动========" + realIP + "========");
        if (StringUtils.isBlank(realIP)) {
            NetworkUtil.dial();
        }
        String queryUrl = "http://120.24.5.25:8080/TestPro/saveIP.do?IDConfig=" + computerId + "&IP=" + realIP;
        try {
            URL url = new URL(queryUrl);
            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
                con.connect();
                con.getInputStream();
            } catch (IOException e) {
                NetworkUtil.dial();
            }
        } catch (MalformedURLException e) {
            NetworkUtil.dial();
        }
    }
}
