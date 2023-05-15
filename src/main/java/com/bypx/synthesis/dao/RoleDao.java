package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.InputParam;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List query(InputParam param) {
        Integer start =(param.getPage()-1)*param.getSize();
        String sql="select * from jxc_role where 1=1 ";
        sql+=formatSql(param);
        sql+=" order by create_time asc limit ?,? ";

        List<Map<String,Object>> list =
                jdbcTemplate.queryForList(sql,start,param.getSize());
        return list;
    }

    public Long queryCount(InputParam param) {
        String sql="select count(*) as total from jxc_role ";
        Map<String,Object> map = jdbcTemplate.queryForMap(sql);
        return Long.valueOf(map.get("total").toString());
    }

    private String formatSql(InputParam param){
        String sql="";
        if(!StringUtils.isEmpty(param.getName())){
            sql+=" and name_ like '%"+param.getName()+"%'";
        }

        return sql;
    }
}
