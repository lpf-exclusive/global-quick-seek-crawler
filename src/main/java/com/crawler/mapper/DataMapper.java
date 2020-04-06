package com.crawler.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper {

    @Insert("REPLACE INTO server_ip(`host`,real_ip,update_time) VALUES(#{host},#{newIP},NOW());")
    int saveIP(String newIP, String host);
}