package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.*;
import com.bypx.synthesis.service.OutboundService;
import com.bypx.synthesis.service.WarehousingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("outbound")

public class OutboundController {
    @Resource
    private OutboundService outboundService;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("query")
    @ResponseBody
    public Map query(Outbound outbound,Integer page,Integer size){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        outbound.setCreatorId(user.getId());
        Map map = outboundService.query(outbound,page,size);
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
        outboundService.delete(id);
        map.put("success",true);
        map.put("msg","撤销成功");
        return map;
    }
//修改订单
@RequestMapping("/edit")
@ResponseBody
public Map <String,Object>edit(Outbound outbound) throws Exception{
    Map result = new HashMap();
    if(outbound.getPrice()==null || outbound.getPrice()<=0){
        result.put("success",false);
        result.put("msg","单价不能小于零");
        return result;
    }
    if(outbound.getNumber()==null || outbound.getNumber()<=0){
        result.put("success",false);
        result.put("msg","数量不能小于零");
        return result;
    }
    outbound.setStatus(1);
    outboundService.edit(outbound);
    result.put("success",true);
    result.put("msg","编辑成功");
    return result;
}
}
