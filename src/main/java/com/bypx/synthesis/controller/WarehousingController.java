package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.InputParam;
import com.bypx.synthesis.bean.Outbound;
import com.bypx.synthesis.bean.ResultEntity;
import com.bypx.synthesis.bean.User;
import com.bypx.synthesis.service.OutboundService;
import com.bypx.synthesis.service.WarehousingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("warehousing")

public class WarehousingController {
    @Resource
    private WarehousingService warehousingService;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("query")
    public Map query(Outbound outbound,Integer page,Integer size){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        outbound.setCreatorId(user.getId());
        Map map = warehousingService.query(outbound,page,size);
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
        warehousingService.delete(id);
        map.put("success",true);
        map.put("msg","撤销成功");
        return map;
    }

}
