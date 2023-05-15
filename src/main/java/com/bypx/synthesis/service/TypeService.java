package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.Menu;
import com.bypx.synthesis.bean.Type;
import com.bypx.synthesis.dao.MenuDao;
import com.bypx.synthesis.dao.MenuInterface;
import com.bypx.synthesis.dao.TypeDao;
import com.bypx.synthesis.dao.TypeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class TypeService {
    @Resource
    private TypeInterface typeInterface;

    public List query(){
        return typeInterface.query();
    }

    public Map<String,Object> add(Type type){
        Map result = new HashMap();
        if(StringUtils.isEmpty(type.getName())){
            result.put("success",false);
            result.put("msg","分类名不能为空");
            return result;
        }
        type.setId(UUID.randomUUID().toString().replaceAll("-",""));
        type.setCreateTime(new Date());
        typeInterface.add(type);
        result.put("data",type);
        result.put("msg","添加成功");
        result.put("success",true);
        return result;
    }

    public Map<String,Object> edit(Type type){
        Map result = new HashMap();
        if(StringUtils.isEmpty(type.getName())){
            result.put("success",false);
            result.put("msg","分类名不能为空");
            return result;
        }
        typeInterface.edit(type);
        result.put("data",type);
        result.put("msg","编辑成功");
        result.put("success",true);
        return result;
    }

    public List show(Type type){
        Map result = new HashMap();
        List list = typeInterface.show(type);
        result.put("success",true);
        result.put("data",list.get(0));
        return list;
    }
    public Map queryPid(String id){
        Map map = new HashMap();
        typeInterface.queryPid(id);
        return map;
    }

    public Map<String,Object> delete(Type type){
        Map result = new HashMap();
        typeInterface.delete(type);
        result.put("success",true);
        result.put("msg","删除成功");
        return result;
    }

}
