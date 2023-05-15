package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.Outbound;
import com.bypx.synthesis.bean.User;
import com.bypx.synthesis.bean.InputParam;
import com.bypx.synthesis.bean.ResultEntity;
import com.bypx.synthesis.dao.LoginInterface;
import com.bypx.synthesis.dao.PhotoInterface;
import com.bypx.synthesis.dao.UserDao;
import com.bypx.synthesis.dao.UserInterface;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)   //可以打在类上
//事务在特定的情况下会失效：
//1. 数据库表造成的
//2. 在servie里捕获异常（写一个统一的异常类，在controller里写try catch）
//3. 使用了自定义异常，默认只会处理运行时异常

public class LoginService {
    @Autowired
    private  HttpServletRequest request;
    @Resource
    private UserDao userDao;
    @Resource
    UserInterface userInterface;
    @Resource
    PhotoInterface photoInterface;
    @Resource
    LoginInterface loginInterface;

    public Map register(User user){
        Map map = new HashMap();
        map.put("code",0);
        try{
            //数据校验 判断不为空
            if(StringUtils.isEmpty(user.getAccount())){
                map.put("msg","不能为空");
                return map;
            }
            User u = userInterface.getAccount(user.getAccount());
            if(u!=null){
                map.put("success",false);
                map.put("msg","帐号已存在");
                return map;
            }

            //图片不为空才进行上传
            if(user.getPhoto()!=null && user.getPhoto().getSize()>0){
                //图片的存放路径 路径要写在项目外面
                String path = "F:\\temp";
                //图片名称，不可重复的随机名称
                //UUID默认格式xxx-xxx-xxx 把-替换成空字符串就生成个随机字符串
                String newName = UUID.randomUUID().toString().replaceAll("-","");
                //取图片的原名，为了取文件的后缀名
                String oldName = user.getPhoto().getOriginalFilename();
                //按名称的.来拆分，最后一个就是后缀名
                String[] arr = oldName.split("\\.");
                //组成新文件名，新的名称+原文件后缀名
                newName = newName+"."+arr[arr.length-1];
                //创建空文件，让上传文件写入
                File targetFile=new File(path,newName);
                //将上传文件写入硬盘的空文件
                user.getPhoto().transferTo(targetFile);
                user.setPhotoUrl("/img/"+newName);
            }

            //取id字段 判断
            //用户的注册，写入数据库
            //dao一般根据表来创建
            //生成数据库主键id
            if(StringUtils.isEmpty(user.getId())){
                user.setId(UUID.randomUUID().toString().replaceAll("-",""));
                user.setCreateTime(new Date());
                userInterface.add(user);
            }else{
                userInterface.update(user.getAccount(), user.getPass(), user.getId());
            }
            map.put("photo", user.getPhotoUrl());
            map.put("code",1);
        }catch(Exception e){
            map.put("msg",e.getMessage());
        }
        return map;
    }

    public Map signIn(User user){
        Map map = new HashMap();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dhm = new SimpleDateFormat("HH:mm");
        String d = sdf.format(date);
        String hm = dhm.format(date);
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        user.setAccount(user.getAccount());
        user.setDate(d);
        user.setDatehm(hm);
        userInterface.signIn(user);
        map.put("success",true);
        map.put("data", user.getDatehm());
        return map;
    }

    public Map signOut(User user){
        Map map = new HashMap();
        Date date = new Date();
        SimpleDateFormat dhm = new SimpleDateFormat("HH:mm");
        String hm = dhm.format(date);
        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        user.setAccount(user.getAccount());
        user.setSignOut(hm);
        userInterface.signOut(user);
        map.put("success",true);
        map.put("data", user.getSignOut());
        return map;
    }


    public Map show(User user){
        Map map = new HashMap();
        List list = userInterface.show(user);
        if(list.size()>0){
            map.put("success",true);
            map.put("data",list.get(0));
        }
        return map;
    }


    public Map login(User user){
        Map map = new HashMap();
        map.put("code",0);
        try {
            List list =  loginInterface.login(user);
            if(list.size()==0){
                map.put("code",0);
                map.put("msg","帐号不存在");
                return map;
            }
            map.put("code",1);
            map.put("nextUrl","/index");
            request.getSession().setAttribute("user", user);
            User uu = loginInterface.queryUidByUname(user.getAccount());
            user.setId(uu.getId());

        }catch (Exception e){
            map.put("msg","账号或者密码错误");
        }
        return map;
    }

    public Map query(User user, Integer page, Integer size){
        Map map = new HashMap();
        Page<User> userPage = PageHelper.startPage(page,size);
        userInterface.query(user);
        map.put("rows",userPage.getResult());
        map.put("total",userPage.getTotal());
        return map;
    }

    public ResultEntity queryProduct(InputParam param) {
        List list = userDao.queryProduct(param);
        Long total = userDao.queryCountProduct(param);
        return ResultEntity.successPage(list,total);
    }

    public Map queryPhoto(User user, Integer page, Integer size) {
        Map map = new HashMap();
        Page<User> userParamPage = PageHelper.startPage(page,size);
        photoInterface.queryPhoto(user);
        map.put("rows", userParamPage.getResult());
        map.put("total", userParamPage.getTotal());
        return map;
    }

    public ResultEntity queryA(InputParam param) {
        List list = userDao.queryA(param);
        Long total = userDao.queryCountA(param);
        return ResultEntity.successPage(list,total);
    }

    public ResultEntity queryAt(InputParam param) {
        List list = userDao.queryAt(param);
        Long total = userDao.queryCountAt(param);
        return ResultEntity.successPage(list,total);
    }
    public void dele(String id) {
        userInterface.delete(id);
    }
    public void delePhoto(String id) {
        userDao.delPhoto(id);
    }


}
