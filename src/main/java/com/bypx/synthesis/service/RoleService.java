package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.InputParam;
import com.bypx.synthesis.bean.ResultEntity;
import com.bypx.synthesis.bean.Role;
import com.bypx.synthesis.bean.User;
import com.bypx.synthesis.dao.RoleDao;
import com.bypx.synthesis.dao.RoleInterface;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {
    @Resource
    private RoleInterface roleInterface;
    @Resource
    private RoleDao roleDao;

    public Map queryRole(Role role, Integer page, Integer size) {
        Map map = new HashMap();
        Page<Role> rolesParamPage = PageHelper.startPage(page,size);
        roleInterface.queryRole(role);
        map.put("rows", rolesParamPage.getResult());
        map.put("total", rolesParamPage.getTotal());
        return map;
    }

    public ResultEntity query(InputParam param) {
        //每一个表格数据的查询    包含两项
        //1.每页的展示数据--前端传过来的条数，经过sql得到的数据
        //2.总共有多少条--后端查询出结果
        List list = roleDao.query(param);
        Long total = roleDao.queryCount(param);
        return ResultEntity.successPage(list,total);
    }

    public void delRole(String id) {
        roleInterface.delRole(id);
    }

}
