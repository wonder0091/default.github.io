package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Product;
import com.bypx.synthesis.bean.Role;
import com.bypx.synthesis.bean.RoleMenu;
import com.bypx.synthesis.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleInterface {
    public List<Role> queryRole(Role role);

    void addRole(Role role);
    void editRole(Role role);
    void delRole(String id);
    List<Role> queryRoleList(Role role);
    public Role getName(String id);
    public Role getReName(String name);
    public Role getFlag(String id);
    public Role getReFlag(String flag);

    void assMenu(RoleMenu roleMenu);

    List<Map<String,Object>> queryAssMenu(RoleMenu roleMenu);

    void delMenuById(String id);
}
