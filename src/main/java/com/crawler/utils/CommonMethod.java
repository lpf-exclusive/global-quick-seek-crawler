package com.crawler.utils;

import java.io.File;
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

    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        if ((file.isFile()) && (file.exists())) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean deleteDirectory(String sPath) {
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;

        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        if (dirFile.delete()) {
            return true;
        }
        return false;
    }

    public static boolean DeleteFolderByName(String sPath, String forder) {
        boolean flag = false;
        File file = new File(sPath);
        if (!file.exists()) {
            return flag;
        }
        if (file.isFile()) {
            return deleteFile(sPath);
        }
        File[] files = file.listFiles();
        for (File file2 : files) {
            if (file2.getName().contains(forder)) {
                flag = deleteDirectory(file2.getPath());
            }
        }
        return flag;
    }
}
