package com.crawler.controller;

import com.crawler.utils.HtmlAnalysisUtils;
import com.crawler.utils.JsoupUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EmailCrawlerController {
    private static Logger logger = LoggerFactory.getLogger(EmailCrawlerController.class);

    @RequestMapping(value = {"/searchEmail.do"})
    @ResponseBody
    public String emailCrawler(HttpServletRequest request) {
        String url = request.getParameter("url");
        if (StringUtils.isBlank(url)) {
            return "参数不能为空!";
        }
        Document document = JsoupUtils.getDocument(url);
        return HtmlAnalysisUtils.getEmail(document.html());
    }
}