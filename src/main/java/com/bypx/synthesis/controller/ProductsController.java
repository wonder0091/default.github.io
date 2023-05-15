package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.*;
import com.bypx.synthesis.dao.ProductsDao;
import com.bypx.synthesis.dao.ProductsInterface;
import com.bypx.synthesis.dao.RoleInterface;
import com.bypx.synthesis.service.ProductsService;
import com.bypx.synthesis.service.RoleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("products")

public class ProductsController {
    @Resource
    private ProductsService productsService;
    @Resource
    private ProductsInterface productsInterface;
    @Resource
    private HttpServletRequest request;


    @RequestMapping("queryPro")
    @ResponseBody
    public Map queryPro(Products products,Integer page,Integer size){
        Map map = productsService.queryPro(products,page,size);
        return map;
    }
    @RequestMapping("getRname")
    @ResponseBody
    public List getRname(){
      User user = (User) request.getSession().getAttribute("user");
      List list = productsInterface.getRname(user.getId());
      return list;
    }

    //采购入库
    @RequestMapping("add")
    public Map add(Outbound outbound){
        Map map = productsService.add(outbound);
        return map;
    }
    //销售出库
    @RequestMapping("substract")
    public Map substract(Outbound outbound){
        Map map = productsService.substract(outbound);
        return map;
    }
    //审核入库
    @RequestMapping("adoptIn")
    @ResponseBody
    public Map adopt(Products products){
        Map map = new HashMap();
        Products name = productsInterface.queryReName(products.getTypeId());
        Products oldProducts = productsInterface.queryTotal(products.getTypeId());

        if (name!=null&& name.getTypeId()!=null){
            products.setTotal(oldProducts.getTotal()+products.getTotal());
            productsInterface.updateTotal(products);
            productsInterface.updateEnd(products.getId());
            map.put("success",true);
            map.put("msg","审核成功");
            return map;
        }
        products.setCreateTime(new Date());
        productsService.adopt(products);
        productsInterface.updateEnd(products.getId());
        map.put("success",true);
        map.put("msg","审核通过");
        return map;
    }
    //审核出库
    @RequestMapping("adoptOut")
    @ResponseBody
    public Map adoptOut(Products products){
        Map map = new HashMap();
        Products name = productsInterface.queryReName(products.getTypeId());
        Products oldProducts = productsInterface.queryTotal(products.getTypeId());
        if (name!=null&& name.getTypeId()!=null){
            if(oldProducts.getTotal()<products.getTotal()){
                map.put("success",false);
                map.put("msg","库存不足，请先采购");
                productsInterface.updateStatus(products.getId());
                return map;
            }
            products.setTotal(oldProducts.getTotal()-products.getTotal());
            productsInterface.updateTotal(products);
            productsInterface.updateEnd(products.getId());
            map.put("success",true);
            map.put("msg","审核成功");
            return map;
        }
        map.put("success",false);
        map.put("msg","库存不足，请先采购");
        productsInterface.updateStatus(products.getId());
        return map;
    }

    //驳回原因
    @RequestMapping("cause")
    @ResponseBody
    public Map cause(Outbound outbound){
        Map map = new HashMap();
        productsInterface.cause(outbound);
        map.put("success",true);
        map.put("msg","驳回成功");
        return map;
    }

    @RequestMapping("queryTotal")
    public Map queryTotal(Products products){
        Map map = new HashMap();
        List list = productsInterface.queryTotalByName(products.getTypeId());
        map.put("success",true);
        map.put("data",list);
        return map;
    }


}
