package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.InputParam;
import com.bypx.synthesis.bean.Outbound;
import com.bypx.synthesis.bean.ResultEntity;
import com.bypx.synthesis.dao.OutboundDao;
import com.bypx.synthesis.dao.OutboundInterface;
import com.bypx.synthesis.dao.ProductsDao;
import com.bypx.synthesis.dao.ProductsInterface;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class OutboundService {

    @Resource
    private OutboundInterface outboundInterface;

    public Map query(Outbound outbound ,Integer page,Integer size){
        Map map = new HashMap();
        Page<Outbound> outboundPage = PageHelper.startPage(page,size);
        outboundInterface.query(outbound);
        map.put("rows",outboundPage.getResult());
        map.put("total",outboundPage.getTotal());
        return map;
    }

//    public ResultEntity query(InputParam param) {
//        //每一个表格数据的查询    包含两项
//        //1.每页的展示数据--前端传过来的条数，经过sql得到的数据
//        //2.总共有多少条--后端查询出结果
//        List list = outboundDao.query(param);
//        Long total = outboundDao.queryCount(param);
//        return ResultEntity.successPage(list,total);
//    }


    public Map delete(String id){
        Map result = new HashMap();
        outboundInterface.delete(id);
        return result;
    }

    public Map edit(Outbound outbound) {
        Map result = new HashMap();
        outboundInterface.edit(outbound);
        return result;
    }
}
