package com.tajiang.leifeng.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderGoods implements Serializable{

	private static final long serialVersionUID = -525985592619417890L;
	
	private String id;

	private String orderId;

    private String goodsId;

    private String goodsName;

    private BigDecimal goodsPrice;

    private Integer goodsQty;

    private String goodsImage;

    private BigDecimal mealFee; //餐盒费

    private BigDecimal goodsPayPrice;

    private BigDecimal goodsMarketPrice;

    private String storeId;

    private String buyerId;

    private String goodsType;
    
    private EvaluateGoods evaluateGoods;


    public BigDecimal getMealFee() {
        return mealFee;
    }

    public void setMealFee(BigDecimal mealFee) {
        this.mealFee = mealFee;
    }

    public BigDecimal getGoodsMarketPrice() {
        return goodsMarketPrice;
    }

    public void setGoodsMarketPrice(BigDecimal goodsMarketPrice) {
        this.goodsMarketPrice = goodsMarketPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsQty() {
        return goodsQty;
    }

    public void setGoodsQty(Integer goodsQty) {
        this.goodsQty = goodsQty;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage == null ? null : goodsImage.trim();
    }

    public BigDecimal getGoodsPayPrice() {
        return goodsPayPrice;
    }

    public void setGoodsPayPrice(BigDecimal goodsPayPrice) {
        this.goodsPayPrice = goodsPayPrice;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType == null ? null : goodsType.trim();
    }

	public EvaluateGoods getEvaluateGoods() {
		return evaluateGoods;
	}

	public void setEvaluateGoods(EvaluateGoods evaluateGoods) {
		this.evaluateGoods = evaluateGoods;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}