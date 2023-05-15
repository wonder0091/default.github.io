package com.bypx.synthesis.bean;

import org.springframework.stereotype.Controller;

public class Product {
    String id;
    String name;
    String title;
    String photo;
    String Price;
    String total;
    String Sort;
    String Freight;
    String goods;
    String Status;

    public Product(){ }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public String getFreight() {
        return Freight;
    }

    public void setFreight(String freight) {
        Freight = freight;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", photo='" + photo + '\'' +
                ", Price='" + Price + '\'' +
                ", total='" + total + '\'' +
                ", Sort='" + Sort + '\'' +
                ", Freight='" + Freight + '\'' +
                ", goods='" + goods + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
