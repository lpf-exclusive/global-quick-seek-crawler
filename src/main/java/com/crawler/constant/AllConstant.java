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

    /**
     * <p>
     * Title: SuffixConstant
     * </P>
     * <p>
     * Description: Suffix
     * </p>
     * <p>
     * Copyright: 深圳市东昂科技有限公司 Copyright (c) 2018
     * </p>
     *
     * @author Administrator
     * @version 1.0
     * @since 2019年1月8日
     */
    public interface SuffixConstant {

        String[] SuffixParam = {
                "commerce",
                "trade",
                "trading",
                "inland",
                "home",
                "domestic",
                "internal",
                "interior",
                "international",
                "foreign",
                "external",
                "import",
                "importation",
                "importer",
                "export",
                "exportation",
                "exporter",
                "commercial",
                "channels",
                "transaction",
                "middleman",
                "dealer",
                "wholesaler",
                "retailer",
                "tradesman",
                "merchant",
                "buyer",
                "purchase",
                "sale",
                "bulk",
                "wholesale",
                "retail",
                "cash",
                "market",
                "consumption",
                "demand",
                "outlet",
                "offer"
        };
    }
}
