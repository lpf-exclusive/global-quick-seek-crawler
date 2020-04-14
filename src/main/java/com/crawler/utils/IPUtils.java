package com.crawler.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * @param request
     * @return String
     * @author HuaZai
     * @contact who.seek.me@java98k.vip
     * @title getIpAddress
     * <ul>
     * @description <li>获取IP地址
     * <li><strong>注意：</strong>IP地址经过多次反向代理后会有多个IP值，
     * <li>其中第一个IP才是真实IP，所以不能通过request.getRemoteAddr()获取IP地址，
     * <li>如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，
     * <li>X-Forwarded-For中第一个非unknown的有效IP才是用户真实IP地址。
     * </ul>
     * @createdTime 2017年12月30日 下午7:52:35
     * @version : V1.0.0
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;
        try {
            // 获取用户真是的地址
            String Xip = request.getHeader("X-Real-IP");
            // 获取多次代理后的IP字符串值
            String XFor = request.getHeader("X-Forwarded-For");
            if (StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
                // 多次反向代理后会有多个IP值，第一个用户真实的IP地址
                int index = XFor.indexOf(",");
                if (index >= 0) {
                    return XFor.substring(0, index);
                } else {
                    return XFor;
                }
            }
            ip = Xip;
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
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
}
