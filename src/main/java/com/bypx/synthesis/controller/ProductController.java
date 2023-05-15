package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.Product;
import com.bypx.synthesis.dao.ProductDao;
import com.bypx.synthesis.dao.ProductInterface;
import com.bypx.synthesis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("goods")

public class ProductController {
    @Autowired
    private ProductDao productDao;
    @Resource
    private ProductService productService;
    @Resource
    private ProductInterface productInterface;

    @RequestMapping("/upload")
    @ResponseBody
    public Object upload(Product product) throws IOException {
        Map map = new HashMap();
        Product pdc = productInterface.info(product.getSort());
        if(pdc!=null){
            map.put("success",false);
            map.put("msg","该序号已存在，请修改");
            return map;
        }
        if(StringUtils.isEmpty(product.getTitle())){
            map.put("success",false);
            map.put("msg","标题不能为空");
            return map;
        }
        if(StringUtils.isEmpty(product.getName())){
            map.put("success",false);
            map.put("msg","商品名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(product.getPrice())){
            map.put("success",false);
            map.put("msg","价格不能为空");
            return map;
        }
        if(StringUtils.isEmpty(product.getTotal())){
            map.put("success",false);
            map.put("msg","库存不能为空");
            return map;
        }
        if(StringUtils.isEmpty(product.getFreight())){
            map.put("success",false);
            map.put("msg","邮费不能为空");
            return map;
        }
        if(StringUtils.isEmpty(product.getStatus())){
            map.put("success",false);
            map.put("msg","状态不能为空");
            return map;
        }

        if(StringUtils.isEmpty(product.getSort())){
            map.put("success",false);
            map.put("msg","序号错误  ");
            return map;
        }

        productInterface.upProduct(product);
        map.put("success",true);
        map.put("msg","添加成功");
        return map;
    }

    @RequestMapping("/editProduct")
    @ResponseBody
    public Map <String,Object>editProduct(Product pdc){
        Map result = productService.editProduct(pdc);
        return result;
    }

    @RequestMapping("/delProduct")
    @ResponseBody
    public Map <String,Object>delProduct(String Sort){
        Map result = productService.delProduct(Sort);
        return result;
    }

    @RequestMapping("/info")
    @ResponseBody
    public Map <String,Object>info(String Sort){
        Map result = productService.info(Sort);
        return result;
    }

    @RequestMapping("/showImg")
    @ResponseBody
    public Map showImg(Product product){
        Map result = new HashMap();
        List<Product> list =  productInterface.showImg(product);
        list.add(product);
        result.put("success",true);
        result.put("data",list);
        result.put("msg","添加成功");
        return result;
    }
}
