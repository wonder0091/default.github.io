package com.bypx.synthesis.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.stereotype.Controller;

import java.util.*;

public class Menu {
    private String text;
    private Integer order;
    private String id;
    private String pId;
    private String url;
    private String icon;
    private String flag;
    private List<Menu> menus;
    private List menu;

    public List getMenu() {
        return menu;
    }

    public void setMenu(List menu) {
        this.menu = menu;
    }

    public List<Menu> getMenus() {
      return menus;
    }
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "text='" + text + '\'' +
                ", order=" + order +
                ", id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", flag='" + flag + '\'' +
                ", menus=" + menus +
                ", menu=" + menu +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu1 = (Menu) o;
        return Objects.equals(text, menu1.text) &&
                Objects.equals(order, menu1.order) &&
                Objects.equals(id, menu1.id) &&
                Objects.equals(pId, menu1.pId) &&
                Objects.equals(url, menu1.url) &&
                Objects.equals(icon, menu1.icon) &&
                Objects.equals(flag, menu1.flag) &&
                Objects.equals(menus, menu1.menus) &&
                Objects.equals(menu, menu1.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, order, id, pId, url, icon, flag, menus, menu);
    }
}
