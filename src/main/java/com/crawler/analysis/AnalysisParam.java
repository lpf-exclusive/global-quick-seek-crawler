package com.crawler.analysis;

import com.crawler.utils.ReturnResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class AnalysisParam {

    public static ReturnResult startAnalysisParam(HttpServletRequest request) {
        String param;
        ReturnResult returnResult = new ReturnResult();
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
        String page = request.getParameter("page");
        if (StringUtils.isBlank(page)) {
            returnResult.setCode("1");
            returnResult.setMsg("no page");
            return returnResult;
        } else {
            /**
             * 处理第几页类型参数pageNum
             */
            int pageNum = Integer.parseInt(page);
            if (pageNum == 1) {
                param = param + "&first=1";
            } else if (pageNum == 2) {
                param = param + "&first=11";
            } else if (pageNum > 2) {
                param = param + "&first=" + ((Integer.parseInt(page) - 1) * 14 - 3);
            }
        }
        returnResult.setCode("0");
        returnResult.setMsg("success");
        returnResult.setData(param);
        return returnResult;
    }
}