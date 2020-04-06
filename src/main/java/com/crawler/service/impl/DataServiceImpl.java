package com.crawler.service.impl;

import com.crawler.mapper.DataMapper;
import com.crawler.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Override
    public boolean saveIP(String host, String newIP) {
        int result = dataMapper.saveIP(newIP, host);
        if (result > 0) {
            return true;
        }
        return false;
    }
}