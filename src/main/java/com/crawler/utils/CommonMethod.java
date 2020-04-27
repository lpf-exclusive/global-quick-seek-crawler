package com.crawler.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonMethod {

    /**
     * <p>
     * 方法名称：currentTime
     * </p>
     * <p>
     * 方法描述：获取当前时间
     * </p>
     *
     * @author Administrator
     * @since 2019年1月12日
     * <p>
     * history 2019年1月12日 Administrator 创建
     * <p>
     */
    public static String currentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * 休眠
     * @param millis
     */
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
