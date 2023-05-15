package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.Classify;
import com.bypx.synthesis.dao.classifyDao;
import com.bypx.synthesis.dao.classifyInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class classifyService {
    @Resource
    private classifyDao classifyDao;
    @Resource
    private classifyInterface classifyInterface;

    public List query(){
        return classifyDao.query();
    }
    public Map<String,Object> add(Classify classify){
        Map result = new HashMap();
        if(StringUtils.isEmpty(classify.getName())){
            result.put("success",false);
            result.put("msg","分类名不能为空");
            return result;
        }
        if(StringUtils.isEmpty(classify.getSort())){
            result.put("success",false);
            result.put("msg","序号不能为空");
            return result;
        }
        classifyInterface.add(classify);
        result.put("data",classify);
        result.put("msg","添加成功");
        result.put("success",true);
        return result;
    }

    public Map<String,Object> edit(Classify classify){
        Map result = new HashMap();
        if(StringUtils.isEmpty(classify.getName())){
            result.put("success",false);
            result.put("msg","分类名不能为空");
            return result;
        }
        if(StringUtils.isEmpty(classify.getSort())){
            result.put("success",false);
            result.put("msg","序号不能为空");
            return result;
        }
        classifyInterface.edit(classify);
        result.put("data",classify);
        result.put("msg","编辑成功");
        result.put("success",true);
        return result;
    }

    public Map<String,Object> show(Classify classify){
        Map result = new HashMap();
        classifyInterface.show(classify);
        result.put("data",classify);
        result.put("success",true);
        return result;
    }

    public Map<String,Object> delete(Classify classify){
        Map result = new HashMap();
        classifyInterface.delete(classify);
        result.put("success",true);
        result.put("msg","删除成功");
        return result;
    }

}
