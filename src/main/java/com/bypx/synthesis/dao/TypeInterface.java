package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Menu;
import com.bypx.synthesis.bean.Products;
import com.bypx.synthesis.bean.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeInterface {
    public List<Type> query();

    public List<Type> queryPid(String id);
    public void add(Type type);
    public void edit(Type type);
    public List<Type> show(Type type);
    public void delete(Type type);



}
