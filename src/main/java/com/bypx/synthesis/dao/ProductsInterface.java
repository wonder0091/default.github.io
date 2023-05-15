package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Outbound;
import com.bypx.synthesis.bean.Products;
import com.bypx.synthesis.bean.Type;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductsInterface {
    List<Products> queryPro(Products products);
    List getRname(String id);
    void add(Outbound outbound);
    public void adopt(Products products);
    public void updateTotal(Products products);
    public Products queryReName(String typeId);
    public Products queryTotal(String typeId);
    public List<Products> queryTotalByName(String name);
    // 根据id修改订单状态
    public void updateStatus(String id);
    public void updateEnd(String id);

    void cause(Outbound outbound);
}
