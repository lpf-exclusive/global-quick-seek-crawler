package com.crawler.utils;

import javax.servlet.http.HttpServlet;
import java.util.*;

public class SystemStatus extends HttpServlet {
    private static Map<String, Integer> mapUpdateStatus = new HashMap();
    private static List<String> excludeList = new ArrayList();

    private static synchronized Integer updateStatus(String itemName, boolean isQuery, boolean isAdd) {
        if (!isQuery) {
            setUpdateStatus(itemName, isAdd);
            return Integer.valueOf(-1);
        }
        if (itemName.length() > 0) {
            Integer runNum = mapUpdateStatus.get(itemName);
            if (runNum == null) {
                return Integer.valueOf(0);
            }
            return runNum;
        }
        if (itemName.length() == 0) {
            return getAllUpdateNum();
        }
        return Integer.valueOf(-1);
    }

    private static Integer getAllUpdateNum() {
        Integer runNum = Integer.valueOf(0);
        Iterator it = mapUpdateStatus.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            Integer value = mapUpdateStatus.get(key);
            runNum = Integer.valueOf(runNum.intValue() + value.intValue());
        }
        return runNum;
    }

    private static void setUpdateStatus(String itemName, boolean isAdd) {
        Integer runNum = mapUpdateStatus.get(itemName);
        if (runNum == null) {
            if (isAdd) {
                mapUpdateStatus.put(itemName, Integer.valueOf(1));
            }
        } else if (isAdd) {
            mapUpdateStatus.put(itemName, Integer.valueOf(runNum.intValue() + 1));
        } else {
            mapUpdateStatus.put(itemName, Integer.valueOf(runNum.intValue() - 1));
        }
    }

    static void initExclude() {
        excludeList.add("yellow");
        excludeList.add("b2b");
        excludeList.add("soft");
        excludeList.add("business");
        excludeList.add("tradev");
        excludeList.add("alibaba");
        excludeList.add("aliexpres");
        excludeList.add("globalsource");
        excludeList.add("ecvv");
        excludeList.add("amazon");
        excludeList.add("hotfrog");
        excludeList.add("nextag");
        excludeList.add("aol");
        excludeList.add("youtube");
        excludeList.add("EC21");
        excludeList.add("kijiji");
        excludeList.add("dhgate");
        excludeList.add("EC51");
        excludeList.add("blog");
        excludeList.add("forum");
        excludeList.add("made-in-china");
        excludeList.add("asia-manufacturer");
        excludeList.add("jhmbuttco");
        excludeList.add("indiamart");
        excludeList.add("thomex");
        excludeList.add("bikudo");
        excludeList.add("diytrade");
        excludeList.add("allproducts");
        excludeList.add("bizztrademarket");
        excludeList.add("sulekhab2b");
        excludeList.add("exportbureau");
        excludeList.add("trade-india");
        excludeList.add("madeinchina");
        excludeList.add("manufacture");
        excludeList.add("gongchang");
        excludeList.add("globalmarket");
        excludeList.add("diytrade");
        excludeList.add("allproducts");
        excludeList.add("bizztrademarket");
        excludeList.add("sulekhab2b");
        excludeList.add("exportbureau");
        excludeList.add("madeinchina");
        excludeList.add("manufacture");
        excludeList.add("gongchang");
        excludeList.add("globalmarket");
        excludeList.add("busytrade");
        excludeList.add(".apple");
        excludeList.add(".cn");
        excludeList.add(".edu");
        excludeList.add(".gov");
        excludeList.add(".org");
        excludeList.add("books");
        excludeList.add(".shop");
    }

    public static boolean isExclude(String strUrl) {
        for (int i = 0; i < excludeList.size(); i++) {
            String excStr = (String) excludeList.get(i);
            if (strUrl.indexOf(excStr) >= 0) {
                return true;
            }
        }
        return false;
    }

    public void markerStart(String itemName) {
        updateStatus(itemName, false, true);
    }

    public void markerEnd(String itemName) {
        updateStatus(itemName, false, false);
    }

    public boolean isUpdateData(String itemName) {
        if (updateStatus(itemName, true, false).intValue() > 0) {
            return true;
        }
        return false;
    }

    public boolean isUpdateDataExist() {
        if (updateStatus("", true, false).intValue() > 0) {
            return true;
        }
        return false;
    }
}
