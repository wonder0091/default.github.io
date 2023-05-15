package com.bypx.synthesis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("login")
    public String login(){
        return "login";
    }
    @RequestMapping("register")
    public String register(){
        return "register";
    }
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("test")
    public String test(){
        return "test";
    }
    @RequestMapping("user")
    public String user(){
        return "user";
    }
    @RequestMapping("notice")
    public String notice(){
        return "notice";
    }
    @RequestMapping("attendance")
    public String attendance(){
        return "attendance";
    }

    @RequestMapping("images")
    public String images(){
        return "images";
    }
    @RequestMapping("classify")
    public String classify(){
        return "classify";
    }
    @RequestMapping("product")
    public String product(){
        return "product";
    }

    @RequestMapping("menu")
    public String menu(){
        return "menu";
    }
    @RequestMapping("type")
    public String type(){
        return "type";
    }
    @RequestMapping("products")
    public String products(){
        return "products";
    }
    @RequestMapping("role")
    public String role(){
        return "role";
    }
    @RequestMapping("outbound")
    public String outbound(){
        return "outbound";
    }
    @RequestMapping("warehousing")
    public String warehousing(){
        return "warehousing";
    }
    @RequestMapping("examine")
    public String examine(){
        return "examine";
    }
}
