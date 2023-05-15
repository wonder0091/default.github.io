package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.User;
import com.bypx.synthesis.bean.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserInterface {

    List<User> query(User user);
    //mybatis的所有接口，只推荐传一个参数，若有多个参数则封装成一个对象来传递
    public void add(User user);
    //一定要传多个参数的话，需要额外处理
    //1. interface的所有参数都要加注解@Param(变量的别名)
    //2. xml的parameterType改成Map 此时#{}里的取值是map里的key，即是param的名称
    public void update(@Param("account")String name,
                       @Param("nickName")String nickName,
                       @Param("id")String id);
    public void delete(String id);
    public List select(User user);
    public User getAccount(String id);
    public User getReAccount(String account);
    public User getNick(String id);
    public User getReNick(String nickName);
    public void resetPass(String id);
    public void editUser(User user);
    public void signIn(User user);
    public void signOut(User user);
    public List<User> show(User user);

    void addUser(User user);

    void ass(UserRole ur);
    List<UserRole> queryAssRole(UserRole ur);

    void delRoleByUid(UserRole ur);

    void deleUserRole(String id);
}
