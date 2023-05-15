package com.bypx.synthesis.service;

import com.bypx.synthesis.bean.Menu;
import com.bypx.synthesis.bean.User;
import com.bypx.synthesis.bean.UserRole;
import com.bypx.synthesis.config.redis.RedisUtil;
import com.bypx.synthesis.dao.MenuDao;
import com.bypx.synthesis.dao.MenuInterface;
import com.bypx.synthesis.dao.UserInterface;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

@Service
@Transactional
public class MenuService {
    @Autowired
    private MenuInterface menuInterface;
    @Resource
    private HttpServletRequest request;
    @Resource
    private RedisUtil redisUtil;

    public List query(){
        return menuInterface.query();
    }

    public List<Menu> queryMenu(Menu menuss) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Menu> rid = menuInterface.queryRidByUid(user.getId());
        ArrayList<Menu> result = new ArrayList<>();
        ArrayList<Menu> pareMenu = new ArrayList<>();
        ArrayList<Menu> childMenu = new ArrayList<>();

        for (int i = 0; i < rid.size(); i++) {
            if (rid != null && rid.get(i).getId() != null) {
                menuss.setId(rid.get(i).getId());
                List<Menu> menus = menuInterface.querys(menuss);
                for (int j = 0; j < menus.size(); j++) {
                    if (menus.get(j).getpId().equals("-1")) {
                        pareMenu.add(menus.get(j));
                    } else {
                        childMenu.add(menus.get(j));
                    }

                }

            for (int k = 0; k < childMenu.size() ; k++) {
                for (int j = 0; j < pareMenu.size() ; j++) {
                    if(childMenu.get(k).getpId().equals(pareMenu.get(j).getId())) {
                        List<Menu> t = pareMenu.get(j).getMenus();
                        if (t == null) {
                            t = new ArrayList<>();
                        }
                        //二级菜单去重
                        if(!t.contains(childMenu.get(k))){
                            t.add(childMenu.get(k));
                            pareMenu.get(j).setMenus(t);
                        }

                        if(!result.contains(pareMenu.get(j))){
                            result.add(pareMenu.get(j));
                        }

                    }
                }

            }
         }

     }
        //一级菜单去重
        ArrayList newList = new ArrayList(new LinkedHashSet(result));
            //菜单存入缓存
            try {
                String key = "ss" + UUID.randomUUID().toString();
                boolean hasKey = redisUtil.hasKey(key);
                List menu = null;
                if (hasKey) {
                    menu = (List) redisUtil.get(key);
                } else {
                    menu = newList;
                    redisUtil.set(key, menu, 60 * 30);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return newList;
            }

            return newList;
        }

    private void findChildMenu(List<Menu> menus, Menu parentMenu) {
        for (Menu menu : menus) {
            if (menu!=null && menu.getpId()!=null && menu.getpId().equals(parentMenu.getId())) {
                List<Menu> childMenus = parentMenu.getMenus();
                if (childMenus == null) {
                    childMenus = new ArrayList<>();
                }
                if (!childMenus.contains(menu)) {
                    childMenus.add(menu);
                }
                parentMenu.setMenus(childMenus);
            }
        }
    }


    public static ArrayList getSingle(ArrayList list) {

        ArrayList newList = new ArrayList<>();                    //1,创建新集合

        Iterator it = list.iterator();                            //2,根据传入的集合(老集合)获取迭代器

        while (it.hasNext()) {                                    //3,遍历老集合

            Object obj = it.next();                                //记录住每一个元素

            if (!newList.contains(obj)) {                        //如果新集合中不包含老集合中的元素

                newList.add(obj);                                //将该元素添加
            }
        }
        return newList;
    }


        public Map<String,Object> add(Menu menu){
        Map result = new HashMap();
        if(StringUtils.isEmpty(menu.getText())){
            result.put("success",false);
            result.put("msg","分类名不能为空");
            return result;
        }
        if(menu.getOrder()==null){
            result.put("success",false);
            result.put("msg","序号不能为空");
            return result;
        }
        menu.setId(UUID.randomUUID().toString().replaceAll("-",""));
        menuInterface.add(menu);
        result.put("data",menu);
        result.put("msg","添加成功");
        result.put("success",true);
        return result;
    }

    public Map queryPid(String id){
        Map map = new HashMap();
        menuInterface.queryPid(id);
        return map;
    }

    public Map<String,Object> edit(Menu menu){
        Map result = new HashMap();
        if(StringUtils.isEmpty(menu.getText())){
            result.put("success",false);
            result.put("msg","分类名不能为空");
            return result;
        }
        if(menu.getOrder()==null){
            result.put("success",false);
            result.put("msg","序号不能为空");
            return result;
        }
        menuInterface.edit(menu);
        result.put("data",menu);
        result.put("msg","编辑成功");
        result.put("success",true);
        return result;
    }

    public Map<String,Object> delete(Menu menu){
        Map result = new HashMap();
        menuInterface.delMR(menu);
        menuInterface.delete(menu);
        result.put("success",true);
        result.put("msg","删除成功");
        return result;
    }

}
