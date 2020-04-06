package com.crawler.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DataMapper {

    @Update("REPLACE INTO server_ip(id,host,real_ip,update_time) VALUES(#{id},#{host},#{realIP},NOW());")
    int saveIP(@Param("id") String computerId, @Param("realIP") String realIP, @Param("host") String host);
}