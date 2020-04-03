package com.crawler.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DataMapper {

    @Update("UPDATE server_ip SET real_ip =#{newIP},update_time =#{updateTime} WHERE host =#{oldHost};")
    int saveIP(String newIP, String updateTime, String host);
}