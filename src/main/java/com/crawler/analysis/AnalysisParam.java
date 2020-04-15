package com.crawler.analysis;

import com.crawler.utils.RedisUtils;
import com.crawler.utils.ReturnResult;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.ShardedJedis;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class AnalysisParam {

    public static ReturnResult startAnalysisParam(HttpServletRequest request) {
        ShardedJedis shardedJedis = RedisUtils.initShardedJedis();
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
        String value = shardedJedis.get("crawler:" + keyword);

        int page;
        if (StringUtils.isBlank(value)) {
            System.out.println("该关键词是首次采集。。。");
            page = 1;
        } else {
            System.out.println("该页已经采集，加一页。。。");
            page = Integer.parseInt(value) + 1;
            System.out.println("当前采集页码为>>>>>>   " + page);
        }
        param = param + "&first=" + ((page - 1) * 10);
        shardedJedis.set("crawler:" + keyword, page + "");
        shardedJedis.expire("crawler:" + keyword, 20 * 60);
        RedisUtils.closeShardedJedis(shardedJedis);
        returnResult.setCode("0");
        returnResult.setMsg("success");
        returnResult.setData(param);
        return returnResult;
    }
}