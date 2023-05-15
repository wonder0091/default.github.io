package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.Product;
import com.bypx.synthesis.dao.ProductDao;
import com.bypx.synthesis.dao.ProductInterface;
import com.bypx.synthesis.dao.UserDao;
import com.bypx.synthesis.dao.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.rmi.server.UID;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    @Autowired
    private ProductInterface productInterface;

    public Map<String,Object> delProduct(String Sort) {
        Map result = new HashMap();
        if(StringUtils.isEmpty(Sort)){
            result.put("success",false);
            result.put("msg","序号不能为空");
            return result;
        }
        String[] sArr = Sort.split(",");
        for (int i = 0; i < sArr.length; i++) {
            productInterface.delProduct(sArr[i]);
        }
        productInterface.delProduct(Sort);
        result.put("success",true);
        result.put("msg","删除成功");
        return result;
    }

    public Map<String,Object> info(String Sort){
        Map result = new HashMap();
        Product pdc = productInterface.info(Sort);
        if(pdc!=null){
            result.put("success",true);
            result.put("data",pdc);
            return result;
        }else {
            result.put("success",false);
            result.put("msg","数据不存在");
            return result;
        }
    }

    public Map<String,Object> editProduct(Product pdc){
        Map result = new HashMap();
        if(StringUtils.isEmpty(pdc.getTitle())){
            result.put("success",false);
            result.put("msg","标题不能为空");
            return result;
        }
        if(StringUtils.isEmpty(pdc.getName())){
            result.put("success",false);
            result.put("msg","商品名不能为空");
            return result;
        }
        if(StringUtils.isEmpty(pdc.getPrice())){
            result.put("success",false);
            result.put("msg","价格不能为空");
            return result;
        }
        if(StringUtils.isEmpty(pdc.getTotal())){
            result.put("success",false);
            result.put("msg","库存不能为空");
            return result;
        }
        if(StringUtils.isEmpty(pdc.getFreight())){
            result.put("success",false);
            result.put("msg","邮费不能为空");
            return result;
        }
        if(StringUtils.isEmpty(pdc.getStatus())){
            result.put("success",false);
            result.put("msg","状态不能为空");
            return result;
        }

        if(StringUtils.isEmpty(pdc.getSort())){
            result.put("success",false);
            result.put("msg","序号错误  ");
            return result;
        }
        productInterface.editProduct(pdc);
        result.put("success",true);
        result.put("msg","编辑成功");
        return result;
        }
}
