package com.crawler.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtils {

    public static Document getDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).header("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                    .timeout(8000).get();
        } catch (Exception e) {
            if (e.toString().contains("SSL")) {
                IgnoreCertificateUtil.trustEveryone();
            }
            System.out.println("获取" + url + "的页面失败" + e);
        }
        return doc;
    }

    public static Document getDocument(String url, String cookie) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).header("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36")
                    .cookie("cookie", cookie).timeout(8000).get();
        } catch (Exception e) {
            if (e.toString().contains("SSL")) {
                IgnoreCertificateUtil.trustEveryone();
            }
            System.out.println("获取" + url + "的页面失败" + e);
        }
        return doc;
    }
}