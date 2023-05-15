package com.bypx.synthesis.bean;

import org.springframework.stereotype.Controller;

public class Classify {
    String name;
    String Sort;
    Integer pId;
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "Classify{" +
                "name='" + name + '\'' +
                ", Sort='" + Sort + '\'' +
                ", pId=" + pId +
                ", id=" + id +
                '}';
    }
}
