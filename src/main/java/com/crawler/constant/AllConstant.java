package com.crawler.constant;

public class AllConstant {

    /**
     * <p>
     * Title: UrlConstant
     * </P>
     * <p>
     * Description: 采集url常量
     * </p>
     * <p>
     * Copyright: 深圳市东昂科技有限公司 Copyright (c) 2018
     * </p>
     *
     * @author Administrator
     * @version 1.0
     * @since 2018年10月4日
     */
    public interface UrlConstant {
        String BING_URL = "https://bing.com/search";
    }

    /**
     * <p>
     * Title: WebDriverConstant
     * </P>
     * <p>
     * Description: webDriver
     * </p>
     * <p>
     * Copyright: 深圳市东昂科技有限公司 Copyright (c) 2018
     * </p>
     *
     * @author Administrator
     * @version 1.0
     * @since 2019年1月8日
     */
    public interface WebDriverConstant {
        /**
         * driver的路径
         */
        String DRIVER_PATH = System.getProperty("user.home") + "/chromedriver.exe";
    }
}
