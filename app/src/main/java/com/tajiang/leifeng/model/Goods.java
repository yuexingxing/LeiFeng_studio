package com.tajiang.leifeng.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Goods implements Serializable{

	private int classId;   //菜品分类id

	private int dayLimit; //每日限额

	private int daySalesNum = 0; // 今日销量

	private int hotTag; //是否热门

	private int hotsellTag; // 是否热销

	private int monthSellQty; // 月销量

	private int partTag; // 计算份数

	private int requiredTag; // 是否必选菜

	private int shopId; // 商家id

	private String shortName; // 别名

	private int goodsId;  //菜品id

	private String goodsName;  //菜品名称

	private String remark;  //描述

	private BigDecimal sellPrice;  //菜品价格

	private Integer specialTag;  //是否特价

	private String goodsImage;   //菜品图片

	private BigDecimal specialPrice;  //特价

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getSpecialTag() {
		if (specialTag == null) {
			return 0;
		} else {
			return specialTag;
		}
	}

	public int getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(int dayLimit) {
		this.dayLimit = dayLimit;
	}

	public int getDaySalesNum() {
		return daySalesNum;
	}

	public void setDaySalesNum(int daySalesNum) {
		this.daySalesNum = daySalesNum;
	}

	public int getHotTag() {
		return hotTag;
	}

	public void setHotTag(int hotTag) {
		this.hotTag = hotTag;
	}

	public int getHotsellTag() {
		return hotsellTag;
	}

	public void setHotsellTag(int hotsellTag) {
		this.hotsellTag = hotsellTag;
	}

	public int getMonthSellQty() {
		return monthSellQty;
	}

	public void setMonthSellQty(int monthSellQty) {
		this.monthSellQty = monthSellQty;
	}

	public int getPartTag() {
		return partTag;
	}

	public void setPartTag(int partTag) {
		this.partTag = partTag;
	}

	public int getRequiredTag() {
		return requiredTag;
	}

	public void setRequiredTag(int requiredTag) {
		this.requiredTag = requiredTag;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setSpecialTag(Integer specialTag) {
		this.specialTag = specialTag;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public BigDecimal getSpecialPrice() {
		if (specialPrice != null) {
			return specialPrice;
		} else {
			return new BigDecimal(0);
		}
	}

	public void setSpecialPrice(BigDecimal specialPrice) {
		this.specialPrice = specialPrice;
	}
}