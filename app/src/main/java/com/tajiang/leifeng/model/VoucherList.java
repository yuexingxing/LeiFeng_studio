package com.tajiang.leifeng.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexiuhui
 * */
public class VoucherList {
    private String shopId;  //商家id
    private String shopName;
    private int totalDelyFee;  //总配送费
    private int totalPackFee;  //总打包费
    private List<StallsFullcut> activityList;  //档口活动列表
    private List<Goods> orderGoodsList;  //商品列表
    private List<Voucher> voucherList;  //优惠卷列表

    private List<Voucher> unableList;  //不可用红包
    private List<Voucher> useableList; //可用红包

    public List<Voucher> getUnableList() {
        return unableList;
    }

    public void setUnableList(List<Voucher> unableList) {
        this.unableList = unableList;
    }

    public List<Voucher> getUseableList() {
        return useableList;
    }

    public void setUseableList(List<Voucher> useableList) {
        this.useableList = useableList;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getTotalDelyFee() {
        return totalDelyFee;
    }

    public void setTotalDelyFee(int totalDelyFee) {
        this.totalDelyFee = totalDelyFee;
    }

    public int getTotalPackFee() {
        return totalPackFee;
    }

    public void setTotalPackFee(int totalPackFee) {
        this.totalPackFee = totalPackFee;
    }

    public List<StallsFullcut> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<StallsFullcut> activityList) {
        this.activityList = activityList;
    }

    public List<Goods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<Goods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    public List<Voucher> getVoucherList() {
        if (voucherList != null) {
            return voucherList;
        } else {
            return new ArrayList<Voucher>();
        }
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }
}
