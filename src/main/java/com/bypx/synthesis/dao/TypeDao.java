package com.bypx.synthesis.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TypeDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List query(){
        String sql = "  select * from jxc_type ORDER BY create_time_ ASC ";
        return jdbcTemplate.queryForList(sql);
    }
}
