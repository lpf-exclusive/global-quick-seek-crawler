package com.crawler.analysis;

import com.crawler.utils.ReturnResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AnalysisParam {

    public static ReturnResult startAnalysisParam(HttpServletRequest request) {
        String param;
        ReturnResult returnResult = new ReturnResult();
        /**
         * 获取session
         */
        HttpSession httpSession = request.getSession();
        Enumeration enumeration = httpSession.getAttributeNames();//获取session中所有的键值对
        Map<String, String> map = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String sessionName = enumeration.nextElement().toString();//获取session中的键值
            String sessionValue = httpSession.getAttribute(sessionName).toString();//根据键值取出session中的值
            map.put(sessionName + sessionValue, "");
        }

        /**
         * 处理关键词参数keyword
         */
        String keyword = request.getParameter("keyword");
        if (StringUtils.isBlank(keyword)) {
            returnResult.setCode("1");
            returnResult.setMsg("no keyword");
            return returnResult;
        } else {
            try {
                keyword = URLDecoder.decode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e2) {
                returnResult.setCode("1");
                returnResult.setMsg("keyword format error");
                return returnResult;
            }
            param = keyword;
        }

        /**
         * 处理国家参数country
         */
        String country = request.getParameter("cr");
        if (StringUtils.isNotBlank(country)) {
            param = param + "+location:" + country.replace("country", "");
        }

        /**
         * 处理语言参数lr
         */
        String lr = request.getParameter("lr");
        if (StringUtils.isBlank(lr)) {
            lr = "lang_en";
        }
        param = param + "+language:" + lr.replace("lang_", "");

        /**
         * 处理页码参数page
         */
        int page;
        while (true) {
            page = new Random().nextInt(15) + 1;
            if (!map.containsKey(keyword + page)) {
                System.out.println("没有采集过该页，开始采集。。。");
                break;
            }
            System.out.println("已经采集过该页，重新选择页码。。。");
        }
        param = param + "&first=" + ((page - 1) * 10);
        httpSession.setAttribute(keyword + page, "");
        returnResult.setCode("0");
        returnResult.setMsg("success");
        returnResult.setData(param);
        return returnResult;
    }
}