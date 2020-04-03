package com.crawler.analysis;

import com.crawler.constant.AllConstant;
import com.crawler.utils.CommonMethod;
import com.crawler.utils.SystemStatus;
import com.crawler.utils.WebDriverUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisList {

    /**
     * 解析列表页
     *
     * @param queryUrl
     * @return
     */
    public static List<Map<String, String>> startAnalysisList(String queryUrl) {
        ArrayList<Map<String, String>> searchResultList = new ArrayList<>();
        WebDriver driver = WebDriverUtils.initWebDriver(AllConstant.WebDriverConstant.DRIVER_PATH, queryUrl);
        CommonMethod.sleep(3000);
        String html = driver.getPageSource();
        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);
        driver.close();
        driver.quit();

        /**
         * 此处针对采集必应被封重新拨号处理，这是谷歌的，针对必应还应重新修改判断条件
         */
//        if (currentUrl.contains("sorry")) {
//            System.out.println("已经被封....,重新拨号....");
//            NetworkUtil.dial();//重新拨号
//            IPUtils.saveIP(computerId);//保存新的ip
//
//            String host = IPUtils.getHost();//计算机名
//            String newIP = IPUtils.getRealIP();//新IP
//            System.out.println("计算机名》》》" + host + "新IP》》》" + newIP);
//            boolean result = dataService.saveIP(host, newIP);
//            if (result) {
//                System.out.println("更新数据库IP信息成功....");
//            }
//            return null;
//        }
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("ol#b_results>li.b_algo");
        for (Element element : elements) {
            String longLink = element.select("h2>a").attr("href");
            String website = null;
            Matcher m = Pattern.compile("[http]*[s]*://[\\w\\.-]*").matcher(longLink);
            if (m.find()) {
                website = m.group();
            }
            String title = element.select("h2").text();
            String synopsis = element.select("div.b_caption>p").text();

            if (!SystemStatus.isExclude(website)) {
                Map<String, String> dataItem = new HashMap();
                dataItem.put("url", website);
                dataItem.put("title", title);
                dataItem.put("content", synopsis);
                dataItem.put("longLink", longLink);
                searchResultList.add(dataItem);
            }
        }
        return searchResultList;
    }
}