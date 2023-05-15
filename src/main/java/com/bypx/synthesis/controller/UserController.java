package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.*;
import com.bypx.synthesis.dao.UserDao;
import com.bypx.synthesis.dao.UserInterface;
import com.bypx.synthesis.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("user")

public class UserController {
    @Autowired
    private UserDao userDao;
    @Resource
    private LoginService loginService;
    @Resource
    UserInterface userInterface;

    @RequestMapping("query")
    public Map query(User user,Integer page,Integer size){
        Map map = loginService.query(user,page,size);
        return map;
    }

    @RequestMapping("queryA")
    public ResultEntity queryA(InputParam param){
        ResultEntity res = loginService.queryA(param);
        return res;
    }
    @RequestMapping("queryProduct")
    public ResultEntity queryProduct(InputParam param){
        ResultEntity res = loginService.queryProduct(param);
        return res;
    }

    @RequestMapping("queryPhoto")
    public Map queryPhoto(User user, Integer page, Integer size){
        Map map = loginService.queryPhoto(user,page,size);
        return map;
    }

    @RequestMapping("queryAt")
    public ResultEntity queryAt(InputParam param, User user){
        ResultEntity res = loginService.queryAt(param);
        return res;
    }

//删除用户
    @RequestMapping("dele")
    @ResponseBody
    public Map dele(String id){
        Map map = new HashMap();
        map.put("code",0);
        try{
            loginService.dele(id);
            userInterface.deleUserRole(id);
        }catch(Exception e){
            e.printStackTrace();
            map.put("msg",e.getMessage());
        }
        return map;
    }
//删除图片
    @RequestMapping("delPhoto")
    @ResponseBody
    public Map delPhoto(String id){
        Map map = new HashMap();
        map.put("code",0);
        try{
            loginService.delePhoto(id);
        }catch(Exception e){
            e.printStackTrace();
            map.put("msg",e.getMessage());
        }
        return map;
    }
//上传图片
    @RequestMapping("/upload")
    @ResponseBody
    public Object upload(String name, MultipartFile photo, User user) throws IOException {
        Map map = new HashMap();
        String fileName = photo.getOriginalFilename();//获取源文件名
        String suffix = fileName.substring(fileName.lastIndexOf("."));//文件后缀名
        String newFileName = user.getName()+suffix;
        File file = new File("f:/temp/"+newFileName);
        photo.transferTo(file);
        String link = "/img/"+newFileName;

        user.setPhotoUrl(link);
        user.setName(newFileName);
        userDao.upImg(user);
        map.put("success",true);
        map.put("name",newFileName);
        map.put("link",link);
        return map;
    }
//重置密码
    @RequestMapping("/resetPass")
    @ResponseBody
    public Object resetPass(String id){
        Map map = new HashMap();
        userInterface.resetPass(id);
        map.put("success",true);
        map.put("msg","重置成功");
        return map;
    }
//添加用户
    @RequestMapping("/addUser")
    @ResponseBody
    public Map <String,Object>addUser(User user){
        Map result = new HashMap();
        User u = userInterface.getReAccount(user.getAccount());
        User n = userInterface.getReNick(user.getNickName());
        if(u!=null && u.getAccount()!=null){
            result.put("success",false);
            result.put("msg","该用户已存在");
            return result;
        }
        if(n!=null && n.getNickName()!=null){
            result.put("success",false);
            result.put("msg","该昵称已存在");
            return result;
        }
        if(StringUtils.isEmpty(user.getAccount())){
            result.put("success",false);
            result.put("msg","帐号不能为空");
            return result;
        }
        if(StringUtils.isEmpty(user.getPass())){
            result.put("success",false);
            result.put("msg","密码不能为空");
            return result;
        }
        if(user.getBirth()==null){
            result.put("success",false);
            result.put("msg","出生日期不能为空");
            return result;
        }
        if(StringUtils.isEmpty(user.getNickName())){
            result.put("success",false);
            result.put("msg","昵称不能为空");
            return result;
        }
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setCreateTime(new Date());
        userInterface.addUser(user);
        result.put("success",true);
        result.put("msg","添加成功");
        return result;
    }
//编辑用户
    @RequestMapping("/editUser")
    @ResponseBody
    public Map <String,Object>editUser(User user) throws Exception{
        Map result = new HashMap();
        User oldUser = userInterface.getAccount(user.getId());
        User reUser = userInterface.getReAccount(user.getAccount());
        User oldNick = userInterface.getNick(user.getId());
        User reNick = userInterface.getReNick(user.getNickName());

        if(StringUtils.isEmpty(user.getAccount())){
            result.put("success",false);
            result.put("msg","帐号不能为空");
            return result;
        }
        if(StringUtils.isEmpty(user.getNickName())){
            result.put("success",false);
            result.put("msg","昵称不能为空");
            return result;
        }
        try{
            if(reUser!=null && reUser.getAccount()!=null && !(oldUser.getAccount().equals(user.getAccount())) && user.getAccount().equals(reUser.getAccount())){
                result.put("success",false);
                result.put("msg","该用户已存在");
                return result;
            }
            if(reNick!=null && reNick.getNickName()!=null && !(oldNick.getNickName().equals(user.getNickName())) && user.getNickName().equals(reNick.getNickName())){
                result.put("success",false);
                result.put("msg","该昵称已存在");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            userInterface.editUser(user);
            result.put("success",true);
            result.put("msg","编辑成功");
        }
        userInterface.editUser(user);
        result.put("success",true);
        result.put("msg","编辑成功");
        return result;
    }

//用户关联角色
    @RequestMapping("/ass")
    @ResponseBody
    public Map<String,Object> ass(UserRole ur){
        Map map =  new HashMap();
        String [] s = ur.getRoleName().split(",");
        userInterface.delRoleByUid(ur);
        for (int i = 0; i < s.length; i++) {
            ur.setId(UUID.randomUUID().toString());
            ur.setRoleName(s[i]);
            userInterface.ass(ur);
        }
        map.put("data",ur);
        map.put("success",true);
        map.put("msg","关联成功");
        return map;
    }
//查询用户已关联的角色

    @RequestMapping("queryAssRole")
    @ResponseBody
    public Map queryAssRole(UserRole ur){
        Map map  = new HashMap();
        List<UserRole> list = userInterface.queryAssRole(ur);
        map.put("success",true);
        map.put("data",list);
        return map;
    }

}
