package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Menu;
import com.bypx.synthesis.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MenuInterface {
    public List<Menu> query();
    public List<Menu> querys(Menu menu);

    public void add(Menu menu);
    public void edit(Menu menu);
    public void delete(Menu menu);
    public void delMR(Menu menu);

    List<Menu> queryPid(String id);

    List<Menu> queryRidByUid(String id);
}
