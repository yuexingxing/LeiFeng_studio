package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.BuyCar;
import com.tajiang.leifeng.model.GoodSet;
import com.tajiang.leifeng.model.Goods;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.BuyCarUtils;
import com.tajiang.leifeng.view.NumControlView;
import com.tajiang.leifeng.view.NumControlView.OnNumChangeListener;

import java.util.List;

/**
 * 支付 - ListView的Adapter
 */
public class FoodPayListAdapter extends CommonAdapter<GoodSet> {

	private String CountNumberUnit = "";    //分数单位（4份）
	private BuyCar buyCar;
	
	private OnGoodNumChangeListener onItemGoListener;

	public FoodPayListAdapter(Context context, List<GoodSet> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		buyCar = BuyCarMapUtils.getCurBuyCarUtils().getBuyCar();
	}

	public void setCountNumberUnit(String countNumberUnit) {
		CountNumberUnit = countNumberUnit;
	}

	@Override
	public void convert(final ViewHolder helper,final GoodSet item) {
		
		Goods goods = item.getGoods();
		helper.setFoodImageByUrl(R.id.iv_picFoodPayMess, goods.getGoodsImage());
		
		helper.setText(R.id.iv_nameFoodPayMess, goods.getGoodsName());
		helper.setText(R.id.tv_goods_count, item.getNum() + CountNumberUnit);

		if(goods.getSpecialPrice().doubleValue() != 0 ) {
			helper.setText(R.id.iv_priceFoodPayMess, goods.getSpecialPrice() + "");
		}else {
			helper.setText(R.id.iv_priceFoodPayMess, goods.getSellPrice() + "");
		}

//		TextView ivPriceFoodPayAction = helper.getView(R.id.ivPriceFoodPayAction);
//		ivPriceFoodPayAction.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
//		if(goods.getSpecialOffer().doubleValue() != 0 ){
//			ivPriceFoodPayAction.setText("￥" + goods.getSpecialOffer());
//		}else {
//			ivPriceFoodPayAction.setText("");
//		}

//		NumControlView numControlView = helper.getView(R.id.iv_numFoodPayMess);
//		numControlView.setCountNumberUnit(CountNumberUnit);
//		numControlView.setNumGood(item.getCount());
//		numControlView.setOnNumChangeListener(new OnNumChangeListener() {
//			@Override
//			public void OnCutButtonClick(View view,int num) {
//				buyCar.cutGood(item.getGoods());
//				onItemGoListener.onGoodNumChange(helper.getPosition() , num);
//				notifyDataSetChanged();
//			}
//
//			@Override
//			public void OnAddButtonClick(View view,int num) {
//				buyCar.addGood(item.getGoods());
//				onItemGoListener.onGoodNumChange(helper.getPosition() , num);
//				notifyDataSetChanged();
//			}
//		});
//
//
//		numControlView.setNumGood(0);
//		//
//		for (GoodSet goodSet : BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList()) {
//			if (item.getGoods().getId().equals(goodSet.getGoods().getId())) {
//				numControlView.setNumGood(goodSet.getCount());
//			}
//		}
//		numControlView.setAddAndCutImgVisibility(false);
		
	}
	
	public OnGoodNumChangeListener getOnItemGoListener() {
		return onItemGoListener;
	}

	public void setOnGoodNumChangeListener(OnGoodNumChangeListener onItemGoListener) {
		this.onItemGoListener = onItemGoListener;
	}

	public interface OnGoodNumChangeListener{
		public void onGoodNumChange(int postion ,int num);
	}

}
