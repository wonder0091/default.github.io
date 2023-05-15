package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.*;
import com.bypx.synthesis.dao.ExamineDao;
import com.bypx.synthesis.dao.ExamineInterface;
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
public class ExamineService {
    @Resource
    private ExamineInterface examineInterface;

    public Map queryEx(Outbound outbound, Integer page, Integer size) {
        Map map = new HashMap();
        Page<Outbound> outboundPage = PageHelper.startPage(page,size);
        examineInterface.queryEx(outbound);
        map.put("rows",outboundPage.getResult());
        map.put("total",outboundPage.getTotal());
        return map;
    }

    public Map delete(String id){
        Map result = new HashMap();
        examineInterface.delete(id);
        return result;
    }

}
