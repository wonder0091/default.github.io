package com.bypx.synthesis.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class MenuDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List query(){
        String sql = "  select * from jxc_menu ORDER BY order_ ASC ";
        return jdbcTemplate.queryForList(sql);
    }
}
