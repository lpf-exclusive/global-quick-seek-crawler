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
            map.put(sessionName, sessionValue);
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
        String pageStr = map.get(keyword);
        if (StringUtils.isBlank(pageStr)) {
            System.out.println("该关键词是首次采集。。。");
            page = 1;
        } else {
            System.out.println("该页已经采集，加一页。。。");
            page = Integer.parseInt(pageStr) + 1;
            System.out.println("当前采集页码为>>>>>>   " + page);
        }
        param = param + "&first=" + ((page - 1) * 10);
        httpSession.setAttribute(keyword, page);
        returnResult.setCode("0");
        returnResult.setMsg("success");
        returnResult.setData(param);
        return returnResult;
    }
}