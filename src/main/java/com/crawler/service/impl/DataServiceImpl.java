package com.crawler.service.impl;

import com.crawler.service.DataService;
import com.crawler.utils.IPUtils;
import com.crawler.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {
    private static Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    @Value("${computer.id}")
    private String computerId;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean saveIP() {
        String host = IPUtils.getHost();//计算机名
        String realIP = IPUtils.getRealIP();//新IP
        boolean hasKey = redisUtils.hHasKey("server_ip", computerId);
        if (hasKey) {
            redisUtils.hDel("server_ip", computerId);
            return redisUtils.hSet("server_ip", computerId, realIP);
        } else {
            System.out.println("不存在");
            return redisUtils.hSet("server_ip", computerId, realIP);
        }
    }
}