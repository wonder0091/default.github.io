package com.bypx.synthesis.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

@Repository
public class LogDao {
    @Resource
    JdbcTemplate jdbcTemplate;
    public void add(String name,String id){
        String sql="insert into loginfo(name,user_id,create_time)" +
                "    values(?,?,?)";
        jdbcTemplate.update(sql,name,id,new Date());
    }
}
