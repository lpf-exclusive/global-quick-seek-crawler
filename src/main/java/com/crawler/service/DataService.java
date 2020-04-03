package com.crawler.service;

public interface DataService {

    /**
     * 保存ip信息
     *
     * @return
     */
    boolean saveIP(String host, String newIP);
}