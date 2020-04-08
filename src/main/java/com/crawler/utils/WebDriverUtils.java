package com.crawler.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverUtils {
    /**
     * <p>
     * 方法名称：initWebDriver
     * </p>
     * <p>
     * 方法描述：谷歌浏览器初始化
     * </p>
     *
     * @param driverPath
     * @param website
     * @return
     * @author Administrator
     * @since 2018年10月10日
     * <p>
     * history 2018年10月10日 Administrator 创建
     * <p>
     */
    public static WebDriver initWebDriver(String driverPath, String website) {
        // 设置chromedriver路径
        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-images");
        chromeOptions.addArguments("--disable-plugins");
        chromeOptions.addArguments("--disable-javascript");
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-gpu");//--disable-gpu : 谷歌文档提到需要加上这个属性来规避bug
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get(website);
        return driver;
    }
}