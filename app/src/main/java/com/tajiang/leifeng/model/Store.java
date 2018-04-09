package com.tajiang.leifeng.model;

import java.io.Serializable;

public class Store implements Serializable{

    private String id;

    private String name;

    private String logo;

    private String schoolId;

    private Integer state;

    private Integer sales;

    private String notice;

    private String sellerName;

    private Byte recommend;

    private Pager<Goods> goodsPager;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Byte getRecommend() {
        return recommend;
    }

    public void setRecommend(Byte recommend) {
        this.recommend = recommend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Pager<Goods> getGoodsPager() {
        return goodsPager;
    }

    public void setGoodsPager(Pager<Goods> goodsPager) {
        this.goodsPager = goodsPager;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", state=" + state +
                ", sales=" + sales +
                ", notice='" + notice + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", recommend=" + recommend +
                ", goodsPager=" + goodsPager +
                '}';
    }
}