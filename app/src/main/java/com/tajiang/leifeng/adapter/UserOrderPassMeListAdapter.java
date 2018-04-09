package com.tajiang.leifeng.adapter;

import java.util.List;

import android.content.Context;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.OrderGoods;

public class UserOrderPassMeListAdapter extends CommonAdapter<Order>{

	public UserOrderPassMeListAdapter(Context context, List<Order> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Order item) {
		
		helper.setFoodImageByUrl(R.id.iv_picOrderUserPassMe, item.getOrderGoodsList().get(0).getGoodsImage());
		
//		helper.setText(R.id.tv_timeOrderUserPassMe, item.getFinishedTime());
//		helper.setText(R.id.tv_nameStoreOrderUserPassMe, item.getStoreName());
		helper.setText(R.id.tv_sumMoneyOrderUserPassMe, item.getFinalMoney() + "");
//		helper.setText(R.id.tv_nameStoreOrderUserPassMe, item.getStoreName()+ "");
		
		setOrderName(helper, item, R.id.tv_nameOrderOrderUserPassMe);
		
	}
	
	/**'
	 * 生成订单的名称
	 * @param helper
	 * @param item
	 */
	private void setOrderName(ViewHolder helper, Order item, int nameOrderTextViewId) {
		List<OrderGoods> orderGoodList = item.getOrderGoodsList();
		
		if(item.getOrderGoodsList().size() == 1){
			helper.setText(nameOrderTextViewId ,item.getOrderGoodsList().get(0).getGoodsName() +"*" +orderGoodList.get(0).getGoodsQty());
		}else{
			
			int sumGoodNum = 0;
			
			for(OrderGoods orderGoods : orderGoodList){
				sumGoodNum += orderGoods.getGoodsQty();
			}
			
			helper.setText(nameOrderTextViewId ,  "共"+sumGoodNum + "份套餐");
			
		}
	}

}
