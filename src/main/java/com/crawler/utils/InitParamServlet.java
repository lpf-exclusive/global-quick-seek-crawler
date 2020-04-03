package com.crawler.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

public class InitParamServlet
        extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static WidebandMng widebandMng = null;

    public static String getPPPIP() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                String networkName = networkInterface.getName();
                if (networkName.indexOf("ppp") == 0) {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if ((inetAddress != null) && ((inetAddress instanceof Inet4Address))) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void init() {
        Date lateTime = new Date();
        ServletContext application = getServletContext();
        application.setAttribute("latetime", lateTime);
        application.setAttribute("lastrestart", lateTime);
        widebandMng = new WidebandMng();
        Timer timerUpdateData = new Timer();
        widebandMng.setApplication(application);
        timerUpdateData.schedule(widebandMng, 12000L, 5000L);
        SystemStatus.initExclude();
    }

    public static class WidebandMng extends TimerTask {
        private ServletContext gApplication = null;

        public void setApplication(ServletContext application) {
            this.gApplication = application;
        }

        public synchronized void run() {
            String ip = InitParamServlet.getPPPIP();
            if ((ip == null) || (ip.length() <= 0)) {
                reStartWideband();
            }
            String queryUrl = "http://120.24.5.25:8080/TestPro/saveIP.do?IDConfig=3&IP=" + ip;

            System.out.println("定时器启动....");
            try {
                URL url = new URL(queryUrl);
                try {
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2");
                    con.connect();
                    con.getInputStream();
                } catch (IOException e) {
                    reStartWideband();
                    e.printStackTrace();
                }
                return;
            } catch (MalformedURLException e) {
                reStartWideband();
                e.printStackTrace();
            }
        }

        public synchronized void reStartWideband() {
            Date lastrestartTimer = (Date) this.gApplication.getAttribute("lastrestart");
            Date lateTime2 = new Date();

            long difftime = lateTime2.getTime() - lastrestartTimer.getTime();
            if (difftime <= 120000L) {
                return;
            }
            try {
                Process process = Runtime.getRuntime().exec("C:\\wideband.bat");
                process.waitFor();
            } catch (IOException localIOException) {
            } catch (InterruptedException localInterruptedException) {
            }
            this.gApplication.setAttribute("lastrestart", lateTime2);
        }

        private Boolean connectSrvOk() {
            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod("http://wvpn10.xicp.net/test.jsp");
            try {
                int statusCode = httpClient.executeMethod(getMethod);
                if (statusCode == 200) {
                    byte[] responseBody = getMethod.getResponseBody();
                    String response = new String(responseBody, "UTF-8");
                    if (response.equals("12345")) {
                        return Boolean.valueOf(true);
                    }
                }
            } catch (Exception localException) {
            }
            return Boolean.valueOf(false);
        }
    }
}
