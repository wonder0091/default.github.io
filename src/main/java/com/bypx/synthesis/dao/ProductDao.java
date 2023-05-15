package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ProductDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void upProduct(Product product) {
        String sql = "insert into goods(name_,img_src,goods_cate,Price_,total_,Sort_,Freight_,Status_,goods_desc)" +
                "    values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, product.getTitle(), product.getPhoto(), product.getName(), product.getPrice(), product.getTotal(),
                product.getSort(), product.getFreight(), product.getStatus(), product.getGoods());
    }

    public void editProduct(Product product) {
        String sql = " update goods set name_=?,img_src=?,goods_cate=?,Price_=?,total_=?,Freight_=?,Status_=?,goods_desc=? where Sort_=? ";
        jdbcTemplate.update(sql, product.getTitle(), product.getPhoto(), product.getName(), product.getPrice(), product.getTotal(),
                product.getFreight(), product.getStatus(), product.getGoods(), product.getSort());
    }

    public void delProduct(String Sort) {
        String sql = " delete from goods where Sort_=? ";
        jdbcTemplate.update(sql, Sort);
    }

    public Product info(String Sort) {
        String sql = " select name_,img_src,goods_cate,Price_,total_,Sort_,Freight_,Status_,goods_desc from goods where Sort_=? ";
        List<Product> list = jdbcTemplate.query(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                Product pdc = new Product();
                pdc.setTitle(resultSet.getString("name_"));
                pdc.setPhoto(resultSet.getString("img_src"));
                pdc.setName(resultSet.getString("goods_cate"));
                pdc.setPrice(resultSet.getString("Price_"));
                pdc.setTotal(resultSet.getString("total_"));
                pdc.setSort(resultSet.getString("Sort_"));
                pdc.setFreight(resultSet.getString("Freight_"));
                pdc.setStatus(resultSet.getString("Status_"));
                pdc.setGoods(resultSet.getString("goods_desc"));
                return pdc;
            }
        }, Sort);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<Product> showImg(Product product) {
        String sql = " select image_link from images where 1=1 order by add_time asc ";
        List<Product> list = jdbcTemplate.query(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                Product product1 = new Product();
                product1.setPhoto(resultSet.getString("image_link"));
                return product1;
            }
        });
    return list;
    }

}