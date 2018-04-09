package com.tajiang.leifeng.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodSet implements Serializable{
	
	private int num;
	private Goods goods;
	private int mPackFee;

	public GoodSet(Goods goods,int num, int packFee) {
		this.goods = goods;
		this.num = num;
		this.mPackFee = packFee;
	}
	/**
	 * 按照Price计算总和,不包含原价
	 */
//	public BigDecimal getSumPrice() {
//		return goods.getPrice().multiply(new BigDecimal(count));
//	}

	/**
	 * 按照原价计算总和
	 */
	public BigDecimal getSumPrice() {
		if (goods.getSpecialPrice().doubleValue() != 0) {   //有特价的情况使用 原价累加
			return goods.getSpecialPrice().multiply(new BigDecimal(num));
		} else {
			return goods.getSellPrice().multiply(new BigDecimal(num));
		}
	}

	/**
	 * 按照原价计算总和(包含餐盒费)
	 */
	public BigDecimal getSumPriceWithBoxFee() {
		if (goods.getSpecialPrice().doubleValue() != 0) {   //有特价的情况使用 原价累加
			return goods.getSpecialPrice().add(new BigDecimal(mPackFee)).multiply(new BigDecimal(num));
		} else {
			return goods.getSellPrice().add(new BigDecimal(mPackFee)).multiply(new BigDecimal(num));
		}
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getmPackFee() {
		return mPackFee;
	}

	public void setmPackFee(int mPackFee) {
		this.mPackFee = mPackFee;
	}

	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	public void addCount() {
		num ++;
	}
	
	public void cutCount(){
		num --;
	}
}
