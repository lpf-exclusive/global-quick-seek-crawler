package com.crawler.analysis;

import com.crawler.constant.AllConstant;
import com.crawler.utils.HttpUtils;
import com.crawler.utils.IPUtils;
import com.crawler.utils.ReturnResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;

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
            //剔掉旧后缀
            String[] oldKeywordArr = keyword.split(" ");
            for (String str : oldKeywordArr) {
                if (oldKeywordArr.length > 1) {
                    if ("distributor".equals(str) || "wholeseale".equals(str) || "retail".equals(str) || "store".equals(str) ||
                            "dropshipper".equals(str) || "resellers".equals(str) || "dealership".equals(str) || "jobber".equals(str) ||
                            "deal".equals(str) || "merchant".equals(str)) {
                        keyword = keyword.replace("", "")
                                .replace("distributor", "")
                                .replace("wholeseale", "")
                                .replace("retail", "")
                                .replace("store", "")
                                .replace("dropshipper", "")
                                .replace("resellers", "")
                                .replace("dealership", "")
                                .replace("jobber", "")
                                .replace("deal", "")
                                .replace("merchant", "").trim();
                    }
                }
            }
            //随机新后缀
            int i = new Random().nextInt(AllConstant.SuffixConstant.SuffixParam.length);
            String key = AllConstant.SuffixConstant.SuffixParam[i];
            param = keyword.replace(" ", " OR ") + " OR " + key;
        }

        /**
         * 处理国家参数country
         */
        String country = request.getParameter("cr");
        if (StringUtils.isNotBlank(country)) {
            param = param + " location:" + country.replace("country", "");
        }

        /**
         * 处理语言参数lr
         */
        String lr = request.getParameter("lr");
        if (StringUtils.isBlank(lr)) {
            lr = "lang_en";
        }
        param = param + " language:" + lr.replace("lang_", "");

        /**
         * 处理页码参数page
         */
        String ip = IPUtils.getIpAddress(request);
        //获取页码
        String getUrl = "http://120.24.5.25:8080/TestPro/getPage.do?IP=" + ip + "&keyword=" + keyword;
        String value = HttpUtils.doGet(getUrl);
        System.out.println("获取到的页码是>>>>>>   " + value);
        int page;
        if (StringUtils.isBlank(value) || "null".equals(value)) {
            System.out.println("该关键词是首次采集。。。");
            page = 1;
        } else {
            System.out.println("该页已经采集，加一页。。。");
            page = Integer.parseInt(value) + 1;
            System.out.println("当前采集页码为>>>>>>   " + page);
        }
        param = param + "&first=" + ((page - 1) * 10);
        //保存页码
        String saveUrl = "http://120.24.5.25:8080/TestPro/savePage.do?IP=" + ip + "&keyword=" + keyword + "&page=" + page;
        String result = HttpUtils.doGet(saveUrl);
        System.out.println("保存页码结果>>>>>>   " + result);
        returnResult.setCode("0");
        returnResult.setMsg("success");
        returnResult.setData(param);
        return returnResult;
    }
}