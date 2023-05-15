package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.InputParam;
import com.bypx.synthesis.bean.Outbound;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class WarehousingDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List query(InputParam param) {
        Integer start =(param.getPage()-1)*param.getSize();
        String sql="SELECT id_,type_id_,number_,price_,creater_,creater_id_,status_,cause_,DATE_FORMAT(create_date_,'%Y-%m-%d') AS create_date_  FROM jxc_product_order WHERE order_type_=1 ";
        sql+=formatSql(param);
        sql+=" order by create_date_ DESC limit ?,? ";

        List<Map<String,Object>> list =
                jdbcTemplate.queryForList(sql,start,param.getSize());
        return list;
    }

    public Long queryCount(InputParam param) {
        String sql="select count(*) as total from jxc_product_order ";
        Map<String,Object> map = jdbcTemplate.queryForMap(sql);
        return Long.valueOf(map.get("total").toString());
    }

    private String formatSql(InputParam param){
        String sql="";
        if(!StringUtils.isEmpty(param.getName())){
            sql+=" and type_id_ like '%"+param.getName()+"%'";
        }

        return sql;
    }

    public void add(Outbound outbound) {
        String sql = " insert into jxc_product_order(id_,type_id_,number_,price_,order_type_,status_,create_date_) values(?,?,?,?,?,?,?) ";
        jdbcTemplate.update(sql,outbound.getId(),outbound.getTypeId(),outbound.getNumber(),outbound.getPrice(),outbound.getOrderType(),outbound.getStatus(),outbound.getCreateTime());
    }

    public void delete(String id) {
        String sql = " delete from jxc_product_order where id_=?";
        jdbcTemplate.update(sql,id);
    }
}
