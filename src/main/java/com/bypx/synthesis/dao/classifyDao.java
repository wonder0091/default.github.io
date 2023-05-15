package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Classify;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class classifyDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    public List query(){
        String sql = " select * from goods_classify ";
        return jdbcTemplate.queryForList(sql);
    }
    public void add(Classify classify){
        String sql = " insert into goods_classify(name,Sort) values(?,?)  ";
        jdbcTemplate.update(sql,classify.getName(),classify.getSort());
    }

    public void show(Classify classify){
        String sql = " select name,Sort from goods_classify where 1=1 ";
        jdbcTemplate.queryForList(sql);
    }
}
