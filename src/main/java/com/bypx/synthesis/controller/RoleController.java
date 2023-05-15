package com.bypx.synthesis.controller;

import com.bypx.synthesis.bean.*;
import com.bypx.synthesis.dao.RoleInterface;
import com.bypx.synthesis.service.RoleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("role")

public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private RoleInterface roleInterface;

    @RequestMapping("queryRole")
    @ResponseBody
    public Map queryRole(Role role, Integer page, Integer size){
        Map map = roleService.queryRole(role,page,size);
        return map;
    }
    @RequestMapping("query")
    public ResultEntity query(InputParam param){
        ResultEntity res = roleService.query(param);
        return res;
    }
//添加角色
    @RequestMapping("/addRole")
    @ResponseBody
    public Map <String,Object>addRole(Role role){
        Map result = new HashMap();
        Role r = roleInterface.getReName(role.getName());
        Role f = roleInterface.getReFlag(role.getFlag());
        if(r!=null && r.getName()!=null){
            result.put("success",false);
            result.put("msg","该角色名已存在");
            return result;
        }
        if(f!=null && f.getFlag()!=null){
            result.put("success",false);
            result.put("msg","该标识已存在");
            return result;
        }
        if(StringUtils.isEmpty(role.getName())){
            result.put("success",false);
            result.put("msg","角色名不能为空");
            return result;
        }
        if(StringUtils.isEmpty(role.getFlag())){
            result.put("success",false);
            result.put("msg","标识不能为空");
            return result;
        }
        role.setId(UUID.randomUUID().toString().replaceAll("-",""));
        role.setCreateTime(new Date());
        roleInterface.addRole(role);
        result.put("success",true);
        result.put("msg","添加成功");
        return result;
    }

//编辑角色
    @RequestMapping("/editRole")
    @ResponseBody
    public Map <String,Object>editRole(Role role) throws Exception{
        Map result = new HashMap();
        Role oldRole =  roleInterface.getName(role.getId());
        Role reRole = roleInterface.getReName(role.getName());
        Role oldFlag = roleInterface.getFlag(role.getId());
        Role reFlag = roleInterface.getReFlag(role.getFlag());

        if(StringUtils.isEmpty(role.getName())){
            result.put("success",false);
            result.put("msg","角色名不能为空");
            return result;
        }
        if(StringUtils.isEmpty(role.getFlag())){
            result.put("success",false);
            result.put("msg","标识不能为空");
            return result;
        }

        try{
            if(reRole!=null && reRole.getName()!=null && !(oldRole.getName().equals(role.getName())) && role.getName().equals(reRole.getName())){
                result.put("success",false);
                result.put("msg","该角色名已存在");
                return result;
            }
            if(reFlag!=null && reFlag.getFlag()!=null && !(oldFlag.getFlag().equals(role.getFlag())) && role.getFlag().equals(reFlag.getFlag())){
                result.put("success",false);
                result.put("msg","该标识已存在");
                return result;
            }

        }catch (Exception e){
            roleInterface.editRole(role);
            result.put("success",true);
            result.put("msg","编辑成功");
            return result;
        }
        roleInterface.editRole(role);
        result.put("success",true);
        result.put("msg","编辑成功");
        return result;
    }
    //删除角色
    @RequestMapping("delRole")
    @ResponseBody
    public Map delRole(String id){
        Map map = new HashMap();
        if(StringUtils.isEmpty(id)){
            map.put("success",false);
            map.put("msg","数据为空");
        }
        String[] roles = id.split(",");
        for (int i = 0; i < roles.length; i++) {
            roleService.delRole(roles[i]);
        }
        map.put("success",true);
        map.put("msg","删除成功");
        return map;
    }

//查找角色列表
    @RequestMapping("/queryRoleList")
    @ResponseBody
    public Map queryRoleList(Role role){
        Map result = new HashMap();
        List<Role> list =  roleInterface.queryRoleList(role);
        result.put("success",true);
        result.put("data",list);
        return result;
    }
    @RequestMapping("/assMenu")
    @ResponseBody
    public Map<String,Object> assMenu(RoleMenu roleMenu){
        Map map =  new HashMap();
        roleInterface.delMenuById(roleMenu.getRoleId());
       if(roleMenu.getMenuId()!=null){
           String [] s = roleMenu.getMenuId().split(",");
           for (int i = 0; i < s.length; i++) {
               roleMenu.setId(UUID.randomUUID().toString().replaceAll("-",""));
               roleMenu.setMenuId(s[i]);
               roleInterface.assMenu(roleMenu);
           }
       }
        map.put("data",roleMenu);
        map.put("success",true);
        map.put("msg","关联成功");
        return map;
    }
//查询角色已关联的菜单
    @RequestMapping("queryAssMenu")
    @ResponseBody
    public Map queryAssMenu(RoleMenu rm){
        Map map  = new HashMap();
        List<Map<String,Object>> list = roleInterface.queryAssMenu(rm);
        map.put("success",true);
        map.put("data",list);
        return map;
    }


}
