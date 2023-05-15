package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.*;
import com.bypx.synthesis.dao.ProductsDao;
import com.bypx.synthesis.dao.ProductsInterface;
import com.bypx.synthesis.dao.RoleDao;
import com.bypx.synthesis.dao.RoleInterface;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductsService {
    @Resource
    private HttpServletRequest request;
    @Resource
    private ProductsInterface productsInterface;

//    public ResultEntity query(InputParam param) {
//        //每一个表格数据的查询    包含两项
//        //1.每页的展示数据--前端传过来的条数，经过sql得到的数据
//        //2.总共有多少条--后端查询出结果
//        List list = productsDao.query(param);
//        Long total = productsDao.queryCount(param);
//        return ResultEntity.successPage(list,total);
//    }


    public Map queryPro(Products products, Integer page, Integer size) {
        Map map = new HashMap();
        Page<Products> productsPage = PageHelper.startPage(page,size);
        productsInterface.queryPro(products);
        map.put("rows",productsPage.getResult());
        map.put("total",productsPage.getTotal());
        return map;
    }

    public Map add(Outbound outbound) {
        Map result = new HashMap();
        if(StringUtils.isEmpty(outbound.getTypeId())){
            result.put("success",false);
            result.put("msg","分类不能为空");
            return result;
        }
        if(outbound.getPrice()==null || outbound.getPrice()<=0 || outbound.getNumber()==null || outbound.getNumber()<=0){
            result.put("success",false);
            result.put("msg","单价或数量不能为空");
            return result;
        }
        if(outbound.getPrice()>100000  || outbound.getNumber()>=100000){
            result.put("success",false);
            result.put("msg","单价或数量超过上限");
            return result;
        }
        outbound.setId(UUID.randomUUID().toString().replaceAll("-",""));
        HttpSession session = request.getSession();
        User creator = (User) session.getAttribute("user");
        outbound.setCreator(creator.getAccount());
        outbound.setCreatorId(creator.getId());
        outbound.setStatus(1);
        outbound.setOrderType(1);
        outbound.setCreateTime(new Date());
        productsInterface.add(outbound);
        result.put("success",true);
        result.put("msg","提交成功");
        return result;
    }

    public Map substract(Outbound outbound) {
        Map result = new HashMap();
        if(StringUtils.isEmpty(outbound.getTypeId())){
            result.put("success",false);
            result.put("msg","分类不能为空");
            return result;
        }
        if(outbound.getPrice()==null || outbound.getPrice()<=0 || outbound.getNumber()==null || outbound.getNumber()<=0){
            result.put("success",false);
            result.put("msg","单价或数量不能小于0");
            return result;
        }
        if(outbound.getPrice()>100000  || outbound.getNumber()>=100000){
            result.put("success",false);
            result.put("msg","单价或数量超过上限");
            return result;
        }
        outbound.setId(UUID.randomUUID().toString().replaceAll("-",""));
        HttpSession session = request.getSession();
        User creator = (User) session.getAttribute("user");
        outbound.setCreator(creator.getAccount());
        outbound.setCreatorId(creator.getId());
        outbound.setStatus(1);
        outbound.setOrderType(2);
        outbound.setCreateTime(new Date());
        productsInterface.add(outbound);
        result.put("success",true);
        result.put("msg","提交成功");
        return result;
    }

    public Map adopt(Products products) {
        Map result = new HashMap();
        productsInterface.adopt(products);
        return result;
    }

}
