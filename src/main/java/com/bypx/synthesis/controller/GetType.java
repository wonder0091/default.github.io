package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.Menu;
import com.bypx.synthesis.bean.Products;
import com.bypx.synthesis.bean.Type;
import com.bypx.synthesis.dao.TypeInterface;
import com.bypx.synthesis.service.MenuService;
import com.bypx.synthesis.service.TypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("type")
public class GetType {
    @Resource
    private TypeService typeService;
    @Resource
    private TypeInterface typeInterface;

    @RequestMapping("query")
    @ResponseBody
    public List query(){
        List list = typeService.query();
        return list;
    }

    @RequestMapping("add")
    @ResponseBody
    public Map<String,Object> add(Type type){
        Map map = typeService.add(type);
        return map;
    }
    @RequestMapping("queryPid")
    @ResponseBody
    public Map queryPid(Type type){
        Map map = typeService.queryPid(type.getId());
        List list = typeInterface.queryPid(type.getId());
        if(type.getId().isEmpty()){
            map.put("success",false);
            map.put("msg","数据为空");
            return map;
        }
        map.put("success",true);
        map.put("data",list);
        return map;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String,Object> delete(Type type){
        Map map = typeService.delete(type);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Type type){
        Map map = typeService.edit(type);
        return map;
    }
    @RequestMapping("show")
    @ResponseBody
    public List show(Type type){
        Map map = new HashMap();
        List list = typeService.show(type);
        map.put("success",true);
        map.put("data",list.get(0));
        return list;
    }

}
