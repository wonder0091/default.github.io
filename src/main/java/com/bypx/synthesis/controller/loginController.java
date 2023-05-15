package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bypx.synthesis.service.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class loginController {
    @Autowired
    HttpServletRequest request;

    @RequestMapping("login")
//    @ResponseBody   //加上注解表示返回的是数据不是页面
    public Map login(User user){
       Map map = loginService.login(user);
        return map;
    }

    @RequestMapping("index")
    @ResponseBody
    public Map index(User user){
        Map map = loginService.login(user);
        return map;
    }

    @RequestMapping("login2")
    @ResponseBody
    public List<User> login2(User user, HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if ("ccname".equals(cookie.getName())) {
            }
        }

        List <User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new User(i+""));
        }
        return list;
    }

    @RequestMapping("test")
    @ResponseBody
    public String test(HttpServletResponse response){
        Cookie cookie = new Cookie("ccname","javacookie");
        cookie.setPath("/");
        cookie.setMaxAge(7*24*60*60);//单位是秒
        response.addCookie(cookie);
        return "";
    }

    @Resource
    LoginService loginService;
    @RequestMapping("register")
    public Map register(User user, MultipartFile photo, HttpServletRequest request){
        //数据的校验可以写在controller
        Map res = loginService.register(user);
        return res;
    }

    @RequestMapping("signIn")
    public Map signIn(User user){
        Map res = loginService.signIn(user);
        return res;
    }

    @RequestMapping("signOut")
    public Map signOut(User user){
        Map res = loginService.signOut(user);
        return res;
    }

    @RequestMapping("show")
    public Map show(User user){
       Map map = loginService.show(user);
        return map;
    }

    @RequestMapping("userName")
    public Object userName(HttpServletRequest request){
        Map result = new HashMap();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        result.put("success",true);
        result.put("user", user);
        return result;
    }

}
