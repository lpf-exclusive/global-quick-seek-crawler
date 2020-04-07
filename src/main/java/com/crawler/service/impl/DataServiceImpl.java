package com.crawler.service.impl;

import com.crawler.service.DataService;
import com.crawler.utils.HttpUtils;
import com.crawler.utils.IPUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {

    @Value("${computer.id}")
    private String computerID;

    @Value("${transit.ip}")
    private String IP;

    @Value("${transit.port}")
    private String PORT;

    @Override
    public boolean saveIP() {
        String realIP = IPUtils.getRealIP();//新IP
        if (StringUtils.isBlank(realIP) || "null".equals(realIP)) {
            return false;
        }
        String url = "http://" + IP + ":" + PORT + "/saveIP.do?IDConfig=" + computerID + "&IP=" + realIP + "";
        String result = HttpUtils.doGet(url);
        if (result.contains("成功")) {
            return true;
        }
        return false;
    }
}