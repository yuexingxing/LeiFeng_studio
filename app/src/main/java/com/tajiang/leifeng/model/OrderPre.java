package com.tajiang.leifeng.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ciko on 16/4/26.
 */
public class OrderPre implements Serializable {

    private int currentPayType;
    private int typeOrder;

    private int adressId;
    private String voucherId;
    private String markerText;
    private String estimatedTimev2;

    private String priceSum;

    private List<GoodSet> goodSetList;

    public OrderPre(int currentPayType, int typeOrder, int adressId, String voucherId, String markerText, String estimatedTimev2, String priceSum, List<GoodSet> goodSetList) {
        this.currentPayType = currentPayType;
        this.typeOrder = typeOrder;
        this.adressId = adressId;
        this.voucherId = voucherId;
        this.markerText = markerText;
        this.estimatedTimev2 = estimatedTimev2;
        this.priceSum = priceSum;
        this.goodSetList = goodSetList;
    }

    public String getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(String priceSum) {
        this.priceSum = priceSum;
    }

    public int getCurrentPayType() {
        return currentPayType;
    }

    public void setCurrentPayType(int currentPayType) {
        this.currentPayType = currentPayType;
    }

    public int getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(int typeOrder) {
        this.typeOrder = typeOrder;
    }

    public int getAdressId() {
        return adressId;
    }

    public void setAdressId(int adressId) {
        this.adressId = adressId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getMarkerText() {
        return markerText;
    }

    public void setMarkerText(String markerText) {
        this.markerText = markerText;
    }

    public String getEstimatedTimev2() {
        return estimatedTimev2;
    }

    public void setEstimatedTimev2(String estimatedTimev2) {
        this.estimatedTimev2 = estimatedTimev2;
    }

    public List<GoodSet> getGoodSetList() {
        return goodSetList;
    }

    public void setGoodSetList(List<GoodSet> goodSetList) {
        this.goodSetList = goodSetList;
    }
}
