package com.bypx.synthesis.bean;

import java.util.List;

public class ResultEntity {
    Integer code;
    Object obj;
    String msg;
    Long total;
    Object rows;
    String title;
    String content;

    public ResultEntity(Integer code) {
        this.code = code;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public ResultEntity(Object obj) {
        this.code = 1;
        this.obj = obj;
    }

    public ResultEntity(String msg) {
        this.msg = msg;
        this.code=0;
    }

    public ResultEntity() {

    }

    public  static ResultEntity success(Object obj){
        ResultEntity res = new ResultEntity(obj);
        return res;
    }

    public  static ResultEntity successPage(Object obj,Long total){
        ResultEntity res = new ResultEntity();
        res.setRows(obj);
        res.setTotal(total);
        return res;
    }

    public  static ResultEntity success(Object obj,Long total){
        ResultEntity res = new ResultEntity(obj);
        res.setTotal(total);
        return res;
    }

    public  static ResultEntity fail(String msg){
        ResultEntity res = new ResultEntity(msg);
        return res;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
