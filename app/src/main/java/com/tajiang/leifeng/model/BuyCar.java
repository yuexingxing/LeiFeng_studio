package com.tajiang.leifeng.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;

public class BuyCar {
	
	private int sumCount = 0;
	private BigDecimal sumPrice = BigDecimal.ZERO; //不包含餐盒费的总价
	private BigDecimal sumPriceWithBoxFee = BigDecimal.ZERO;   //包含餐盒费的总价

	private List<GoodSet> goodSetList = new ArrayList<GoodSet>();

	public BigDecimal getSumPriceWithBoxFee() {
		return sumPriceWithBoxFee;
	}

	public void setSumPriceWithBoxFee(BigDecimal sumPriceWithBoxFee) {
		this.sumPriceWithBoxFee = sumPriceWithBoxFee;
	}

	public int getSumCount() {
		return sumCount;
	}

	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

	public List<GoodSet> getGoodSetList() {
		return goodSetList;
	}

	public void setGoodSetList(List<GoodSet> goodSetList) {
		this.goodSetList = goodSetList;
	}
	
	/**
	 * 将商品添加到购物车
	 * @param goods
	 */
	public void addGood(Goods goods, int packFee) {
		// 1. 先判断商品集合里是否包含此商品
		// 2. 有，则添加商品数量
		//     无，则添加该商品，并设置商品数量为1
		// 3. 修改购物车的总价和数量
		
		if(isContainGood(goodSetList, goods)){
			// 如果已经存在
			for(GoodSet goodSet : goodSetList){
				if(goodSet.getGoods().getGoodsId() == goods.getGoodsId()){
					goodSet.addCount();
				}
			}
		}else{
			// 如果不存在
			goodSetList.add(new GoodSet(goods, 1, packFee));
		}
		
		refreshCountAndPrice();
		
	}

	public boolean isContainGood(Goods goods) {
		return isContainGood(goodSetList, goods);
	}

	/**
	 * 去掉商品
	 * @param goods
	 */
	public void cutGood(Goods goods) {
		//1. 先判断是否存在此商品
		//2 有 -- 判断商品数量是否为1
		//            是，则直接将此商品商品列表中除去
		//            否，将商品的数量减一
		//    无，弹出提示
		if(isContainGood(goodSetList, goods)){
			
			for(int i = 0; i< goodSetList.size() ; i++){
				if(goodSetList.get(i).getGoods().getGoodsId() == goods.getGoodsId()){
					if(goodSetList.get(i).getNum()  == 1){
						goodSetList.remove(i);
					}else{
						goodSetList.get(i).cutCount();
					}
				}
			}
		}else{
			ToastUtils.showShort("无此商品");
		}
		
		refreshCountAndPrice();
		
	}


	private void refreshCountAndPrice() {
		sumCount = 0;
		sumPrice = BigDecimal.ZERO;
		sumPriceWithBoxFee =BigDecimal.ZERO;

		for(GoodSet goodSet : goodSetList){
			sumCount += goodSet.getNum();
			sumPrice = sumPrice.add(goodSet.getSumPrice());
			sumPriceWithBoxFee = sumPriceWithBoxFee.add(goodSet.getSumPriceWithBoxFee());
		}
	}

	
	private boolean isContainGood(List<GoodSet> goodSetList,Goods goods){
		for(GoodSet goodSet : goodSetList){
			if(goodSet.getGoods().getGoodsId() == goods.getGoodsId()){
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否有特价菜
	 * @return
     */
	public boolean haveSpecialOffer() {
		for (GoodSet goodSet : goodSetList) {
			if (goodSet.getGoods().getSpecialPrice().doubleValue() != 0) {
				return true;
			}
		}
		return false;
	}
}
