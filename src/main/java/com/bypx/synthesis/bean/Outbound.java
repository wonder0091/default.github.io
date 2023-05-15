package com.bypx.synthesis.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Outbound {
    private String id;
    private String typeId;
    private Integer number;
    private Integer price;
    private String creator;
    private String creatorId;
    private Integer orderType; // 0 全部 1 入库 2 出库
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    private Integer status; // 订单状态 1 在途 2 驳回 3 结束
    private String cause;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "Outbound{" +
                "id='" + id + '\'' +
                ", typeId='" + typeId + '\'' +
                ", number=" + number +
                ", price=" + price +
                ", creator='" + creator + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", orderType=" + orderType +
                ", createTime=" + createTime +
                ", status=" + status +
                ", cause='" + cause + '\'' +
                '}';
    }
}
