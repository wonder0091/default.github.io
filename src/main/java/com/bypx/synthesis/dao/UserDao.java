package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.User;
import com.bypx.synthesis.bean.InputParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {
   @Autowired
   private JdbcTemplate jdbcTemplate;


    public void add(User user){
        String sql="insert into user(id,name,password,photo_url,create_time,number)" +
                "    values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getId(), user.getAccount(), user.getPass(), user.getPhotoUrl(),
                user.getCreateTime(), user.getNumber());
    }
    public void update(User user){
        String sql="update user set name=?,password=? where id=?";
        jdbcTemplate.update(sql, user.getAccount(), user.getPass(), user.getId());
    }
    public void delete(String id){
        String sql="delete from user where id=?";
        jdbcTemplate.update(sql,id);
    }
    public void delPhoto(String id){
        String sql=" delete from images where id_=? ";
        jdbcTemplate.update(sql,id);
    }
    public User getAccount(String account){
        String sql=" select id,name,password,photo_url,create_time from user where name=? ";
        List<User> list = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setPass(resultSet.getString("password"));
                user.setPhotoUrl(resultSet.getString("photo_url"));
                user.setCreateTime(resultSet.getDate("create_time"));
                return user;
            }
        },account);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
    public List select(User user){
        return null;
    }

    public Map login(User user) {
        //数据库的索引
        //若查不到，不会返回null，而是抛出异常，所以service要处理异常
        String sql="select * from user where name=? and password=?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql,
                user.getAccount(), user.getPass());//只查询一条数据
        return map;
    }

    public Map signIn(User user) {
        Map map = new HashMap();
        //进入页面时判断是否已经签到，若未签到插入一条数据，已经签到则更新数据
            String sql="insert into attendance(user_id,sign_in,create_time_)" +
                "    values(?,?,?)";
         jdbcTemplate.update(sql, user.getAccount(), user.getDatehm(), user.getDate());
        return map;
    }

    public Map signOut(User user) {
        Map map = new HashMap();
        //进入页面时判断是否已经签到，若未签到插入一条数据，已经签到则更新数据
        String sql="update attendance set sign_out=? where user_id=?";
        jdbcTemplate.update(sql, user.getSignOut(), user.getAccount());
        return map;
    }

    public Map show(User user) {
        String sql=" SELECT title_,content,DATE_FORMAT(create_time,'%Y-%m-%d') AS create_time  FROM notice WHERE  id_=(SELECT MAX(id_) FROM notice ) ";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql);
        return map;
    }

    public List queryA(InputParam param) {
        Integer start = (param.getPage()-1)*param.getSize();
        String sql="select * from notice where 1=1";
        sql+=formatSqlA(param);
        sql+=" order by create_time asc limit ?,?";

        List<Map<String,Object>> list =
                jdbcTemplate.queryForList(sql,start,param.getSize());
        return list;
    }

    public List queryAt(InputParam param) {
        Integer start = (param.getPage()-1)*param.getSize();
        String sql="select * from attendance where 1=1";
        sql+=formatSqlAt(param);
        sql+=" order by create_time_ desc,sign_in desc limit ?,?";

        List<Map<String,Object>> list =
                jdbcTemplate.queryForList(sql,start,param.getSize());
        return list;
    }


    public List query(InputParam param) {
        Integer start =(param.getPage()-1)*param.getSize();
        String sql="select * from user where 1=1";
        sql+=formatSql(param);
        sql+=" order by create_time asc limit ?,?";

        List<Map<String,Object>> list =
                jdbcTemplate.queryForList(sql,start,param.getSize());
        return list;
    }
    public List queryProduct(InputParam param) {
        Integer start =(param.getPage()-1)*param.getSize();
        String sql="  SELECT id_,goods_cate,name_,cover_,img_src,goods_desc,total_,Price_,Freight_,Status_,Sort_ FROM goods where 1=1";
        sql+=formatSqlP(param);
        sql+=" order by id_ asc limit ?,?";

        List<Map<String,Object>> list =
                jdbcTemplate.queryForList(sql,start,param.getSize());
        return list;
    }


    public List queryPhoto(InputParam param) {
        Integer start =(param.getPage()-1)*param.getSize();
        String sql="  SELECT id_,image_name,image_link,DATE_FORMAT(add_time,'%Y-%m-%d') AS add_time FROM images where 1=1";
        sql+=formatSqlP(param);
        sql+=" order by add_time asc limit ?,?";

        List<Map<String,Object>> list =
                jdbcTemplate.queryForList(sql,start,param.getSize());
        return list;
    }

    public Long queryCount(InputParam param) {
        String sql="select count(*) as total from user ";
       Map<String,Object> map = jdbcTemplate.queryForMap(sql);
        return Long.valueOf(map.get("total").toString());
    }

    public Long queryCountA(InputParam param) {
        String sql="select count(*) as total from notice ";
        Map<String,Object> map = jdbcTemplate.queryForMap(sql);
        return Long.valueOf(map.get("total").toString());
    }
    public Long queryCountProduct(InputParam param) {
        String sql="select count(*) as total from goods ";
        Map<String,Object> map = jdbcTemplate.queryForMap(sql);
        return Long.valueOf(map.get("total").toString());
    }
    public Long queryCountP(InputParam param) {
        String sql="select count(*) as total from images ";
        Map<String,Object> map = jdbcTemplate.queryForMap(sql);
        return Long.valueOf(map.get("total").toString());
    }
    public Long queryCountAt(InputParam param) {
        String sql="select count(*) as total from attendance";
        Map<String,Object> map = jdbcTemplate.queryForMap(sql);
        return Long.valueOf(map.get("total").toString());
    }

    private String formatSql(InputParam param){
        String sql="";
        if(!StringUtils.isEmpty(param.getName())){
            sql+=" and name like '%"+param.getName()+"%'";
        }
        if(!StringUtils.isEmpty(param.getNickName())){
            sql+=" and nickName like '%"+param.getNickName()+"%'";
        }
        if(!StringUtils.isEmpty(param.getPassword())){
            sql+=" and password  ='"+param.getPassword()+"'";
        }
        return sql;
    }
    private String formatSqlP(InputParam param){
        String sql="";
        if(!StringUtils.isEmpty(param.getName())){
            sql+=" and image_name ='"+param.getName()+"'";
        }
        if(!StringUtils.isEmpty(param.getPhoto())){
            sql+=" and image_link ='"+param.getPhoto()+"'";
        }
        return sql;
    }
    private String formatSqlA(InputParam param){
        String sql="";
        if(!StringUtils.isEmpty(param.getContent()) ){
            sql+=" and content like '%"+param.getContent()+"%' or title_ like '%"+param.getContent()+"%'" ;
        }
        return sql;
    }

    private String formatSqlAt(InputParam param){
        String sql="";
        if(!StringUtils.isEmpty(param.getDate())){
            sql+=" and create_time_ ='"+param.getDate()+"' ";
        }
        return sql;
    }

    public void upImg(User user) {
        String sql="insert into images(image_name,image_link,add_time)" +
                "    values(?,?,?)";
        jdbcTemplate.update(sql, user.getName(), user.getPhotoUrl(),new Date());
    }
}
