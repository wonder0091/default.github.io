package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.Menu;
import com.bypx.synthesis.bean.Type;
import com.bypx.synthesis.bean.User;
import com.bypx.synthesis.bean.UserRole;
import com.bypx.synthesis.dao.MenuInterface;
import com.bypx.synthesis.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menu")
public class GetMenu {
    @Resource
    private MenuService MenuService;
    @Resource
    private MenuInterface menuInterface;

    @RequestMapping("query")
    @ResponseBody
    public List<Menu> query(){
        return MenuService.query();
    }

    @RequestMapping("queryMenu")
    @ResponseBody
    public List<Menu> queryMenu(Menu menu) throws  Exception{
        return MenuService.queryMenu(menu);
    }


    @RequestMapping("queryPid")
    @ResponseBody
    public Map queryPid(Menu menu){
        Map map = MenuService.queryPid(menu.getId());
        List list = menuInterface.queryPid(menu.getId());
        if(menu.getId()==null){
            map.put("success",false);
            map.put("msg","数据为空");
            return map;
        }
        map.put("success",true);
        map.put("data",list);
        return map;
    }

    @RequestMapping("add")
    @ResponseBody
    public Map<String,Object> add(Menu menu){
        Map map = MenuService.add(menu);
        return map;
    }
    @RequestMapping("delete")
    @ResponseBody
    public Map<String,Object> delete(Menu menu){
        Map map = MenuService.delete(menu);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Menu menu){
        Map map = MenuService.edit(menu);
        return map;
    }


}
