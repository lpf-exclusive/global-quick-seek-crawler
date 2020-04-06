package com.crawler.controller;

import com.crawler.analysis.AnalysisList;
import com.crawler.analysis.AnalysisParam;
import com.crawler.constant.AllConstant;
import com.crawler.utils.ReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class BingCrawlerController {
    private static Logger logger = LoggerFactory.getLogger(BingCrawlerController.class);

    @RequestMapping(value = {"/search.do", "/searchNew.do", "/searchSearch.do"})
    @ResponseBody
    public ReturnResult search(HttpServletRequest request) {
        ReturnResult returnResult = new ReturnResult();

        ReturnResult paramResult = AnalysisParam.startAnalysisParam(request);
        if (!"success".equals(paramResult.getMsg())) {
            return paramResult;
        }
        String param = paramResult.getData().toString();
        //拼接请求链接
        String bingUrl = AllConstant.UrlConstant.BING_URL + "?q=" + param;
        logger.info("请求链接》》》" + bingUrl);
        List<Map<String, String>> searchResultList = AnalysisList.startAnalysisList(bingUrl);
        logger.info("数据条数》》》" + searchResultList.size());
        logger.info("==================================================");
        returnResult.setCode("0");
        returnResult.setMsg("success");
        returnResult.setData(searchResultList);
        return returnResult;
    }
}