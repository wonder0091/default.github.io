package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.Classify;
import com.bypx.synthesis.service.classifyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("classify")
public class classifyController {
    @Resource
    private classifyService classifyService;

    @RequestMapping("query")
    @ResponseBody
    public List query(){
        return classifyService.query();
    }

    @RequestMapping("add")
    @ResponseBody
    public Map<String,Object> add(Classify classify){
        Map map = classifyService.add(classify);
        return map;
    }
    @RequestMapping("delete")
    @ResponseBody
    public Map<String,Object> delete(Classify classify){
        Map map = classifyService.delete(classify);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(Classify classify){
        Map map = classifyService.edit(classify);
        return map;
    }
    @RequestMapping("show")
    @ResponseBody
    public Map<String,Object> show(Classify classify){
        Map map = classifyService.show(classify);
        return map;
    }

}
