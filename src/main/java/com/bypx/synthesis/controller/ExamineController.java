package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.InputParam;
import com.bypx.synthesis.bean.Outbound;
import com.bypx.synthesis.bean.Products;
import com.bypx.synthesis.bean.ResultEntity;
import com.bypx.synthesis.dao.ExamineInterface;
import com.bypx.synthesis.service.ExamineService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("examine")
public class ExamineController {
    @Resource
    private ExamineService examineService;

    @RequestMapping("queryEx")
    @ResponseBody
    public Map queryEx(Outbound outbound,Integer page,Integer size){
        Map map = examineService.queryEx(outbound,page,size);
        return map;
    }

//撤销订单
    @RequestMapping("delete")
    public Map delete(String id){
        Map map = new HashMap();
        if(StringUtils.isEmpty(id)){
            map.put("success",false);
            map.put("msg","数据为空");
        }
        examineService.delete(id);
        map.put("success",true);
        map.put("msg","撤销成功");
        return map;
    }

}
