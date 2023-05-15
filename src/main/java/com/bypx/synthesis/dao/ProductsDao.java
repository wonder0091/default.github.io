package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.InputParam;
import com.bypx.synthesis.bean.Products;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductsDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

//    public List query(InputParam param) {
//        Integer start =(param.getPage()-1)*param.getSize();
//        String sql="select * from jxc_product where 1=1 ";
//        sql+=formatSql(param);
//        sql+=" order by create_time asc limit ?,? ";
//
//        List<Map<String,Object>> list =
//                jdbcTemplate.queryForList(sql,start,param.getSize());
//        return list;
//    }

//    public Long queryCount(InputParam param) {
//        String sql="select count(*) as total from jxc_product ";
//        Map<String,Object> map = jdbcTemplate.queryForMap(sql);
//        return Long.valueOf(map.get("total").toString());
//    }
//
//    private String formatSql(InputParam param){
//        String sql="";
//        if(!StringUtils.isEmpty(param.getName())){
//            sql+=" and type_id_ like '%"+param.getName()+"%'";
//        }
//
//        return sql;
//    }

    public Map adopt(Products products) {
        Map map = new HashMap();
        String sql = " insert into jxc_product(id_,type_id_,total_,create_time) values(?,?,?,?)";
        jdbcTemplate.update(sql,products.getId(),products.getTypeId(),products.getTotal(),products.getCreateTime());
        return map;
    }
    public Map updateTotal(Products products) {
        Map map = new HashMap();
        String sql = " update jxc_product set total_=?  where type_id_=?";
        jdbcTemplate.update(sql,products.getTotal(),products.getTypeId());
        return map;
    }

    public Products queryReId(Products products){
        String sql = " select id_ from jxc_product where id_=? ";
        jdbcTemplate.queryForList(sql,products.getId());
        return products;
    }
    public List queryReName(String typeId){
        String sql = " select type_id_ from jxc_product where type_id_=? ";
        List list = jdbcTemplate.queryForList(sql,typeId);
        return list;
    }
    public List queryTotal(String id){
        String sql = " select total_ from jxc_product where id_=? ";
        List list = jdbcTemplate.queryForList(sql,id);
        return list;
    }

}
