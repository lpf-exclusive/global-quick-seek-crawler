package com.crawler.service.impl;

import com.crawler.mapper.DataMapper;
import com.crawler.service.DataService;
import com.crawler.utils.IPUtils;
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
    private DataMapper dataMapper;

    @Override
    public boolean saveIP() {
        String host = IPUtils.getHost();//计算机名
        String realIP = IPUtils.getRealIP();//新IP
        try {
            int result = dataMapper.saveIP(computerId, realIP, host);
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }
}