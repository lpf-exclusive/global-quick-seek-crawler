package com.crawler.utils;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 抓取网页时忽略网http/https的证书验证
 * <p>
 * Title: IgnoreCertificateUtil
 * </P>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: 深圳市东昂科技有限公司 Copyright (c) 2016
 * </p>
 *
 * @author Administrator
 * @version 1.0
 * @since 2016年6月3日
 */
public class IgnoreCertificateUtil {
    /**
     * 忽略证书,解析http/https协议网页内容通用方法
     * <p>
     * 方法名称：trustEveryone
     * </p>
     * <p>
     * 方法描述：
     * </p>
     *
     * @author sql
     * @since 2016年6月2日
     * <p>
     * history 2016年6月2日 sql 创建
     * <p>
     */
    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
