package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.InputParam;
import com.bypx.synthesis.bean.Outbound;
import com.bypx.synthesis.bean.ResultEntity;
import com.bypx.synthesis.dao.OutboundDao;
import com.bypx.synthesis.dao.WarehousingDao;
import com.bypx.synthesis.dao.WarehousingInterface;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class WarehousingService {

    @Resource
    private WarehousingInterface warehousingInterface;

    public Map query(Outbound outbound ,Integer page,Integer size){
        Map map = new HashMap();
        Page<Outbound> outboundPage = PageHelper.startPage(page,size);
        warehousingInterface.query(outbound);
        map.put("rows",outboundPage.getResult());
        map.put("total",outboundPage.getTotal());
        return map;
    }

    public Map delete(String id){
        Map result = new HashMap();
        warehousingInterface.delete(id);
        return result;
    }
}
