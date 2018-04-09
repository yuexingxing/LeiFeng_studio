package com.tajiang.leifeng.adapter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Voucher;
import com.tajiang.leifeng.utils.DateUtils;

public class UserCouponListAdapter extends CommonAdapter<Voucher> {

	private NumberFormat numberFormat = NumberFormat.getInstance();


	public UserCouponListAdapter(Context context, List<Voucher> mDatas,
								 int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper,final Voucher item) {

		helper.setText(R.id.tv_priceUserCoupon,  numberFormat.format(item.getFaceValue()) + "");
		helper.setText(R.id.tv_endTimeUserCoupon,  "有效期至:   " + item.getExpireTime());
		helper.setText(R.id.tv_type_description,  item.getPolicyName());
//		helper.getView(R.id.rl_root_view).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				ToastUtils.showShort("" + item.getType());
//			}
//		});

		TextView tvCouponPrice = helper.getView(R.id.tv_priceUserCoupon);
		LinearLayout contenCoupon = helper.getView(R.id.contenCoupon);
		TextView tvTypeDescription = helper.getView(R.id.tv_type_description);
		ImageView ivCouponMoney = helper.getView(R.id.iv_coupon_money);
//		ImageView imageView = (ImageView) contenCoupon.findViewById(R.id.iv_is_coupon_used);

		if (new BigDecimal(0.00).compareTo(item.getLimitPrice()) != 0) {  //满减红包
			helper.getView(R.id.tv_non_limit_coupon).setVisibility(View.GONE);
			helper.getView(R.id.ll_limit_coupon).setVisibility(View.VISIBLE);

			helper.setText(R.id.tv_coupon_limit_price,  numberFormat.format(item.getLimitPrice()) + "");
			helper.setText(R.id.tv_coupon_price,  numberFormat.format(item.getFaceValue()) + "");

		} else {
			helper.getView(R.id.tv_non_limit_coupon).setVisibility(View.VISIBLE);
			helper.getView(R.id.ll_limit_coupon).setVisibility(View.GONE);
		}

		switch (item.getState()) {
		case Voucher.STATE_USABLE:  //可以使用
//			contenCoupon.setBackgroundResource(R.drawable.bg_coupon_able);
			contenCoupon.setBackgroundResource(R.drawable.img_coupon_usable_background);
			tvTypeDescription.setTextColor(mContext.getResources().getColor(R.color.text_black_1));
			ivCouponMoney.setImageResource(R.drawable.icon_money_red);
			tvCouponPrice.setTextColor(mContext.getResources().getColor(R.color.red_money));

			((TextView)helper.getView(R.id.tv_limit_text_1)).setTextColor(mContext.getResources().getColor(R.color.text_black_1));
			((TextView)helper.getView(R.id.tv_limit_text_2)).setTextColor(mContext.getResources().getColor(R.color.text_black_1));

			((TextView)helper.getView(R.id.tv_non_limit_coupon)).setTextColor(mContext.getResources().getColor(R.color.text_black_1));
			((TextView)helper.getView(R.id.tv_endTimeUserCoupon)).setTextColor(mContext.getResources().getColor(R.color.text_black_1));

			((TextView)helper.getView(R.id.tv_coupon_limit_price)).setTextColor(mContext.getResources().getColor(R.color.red_orange));
			((TextView)helper.getView(R.id.tv_coupon_price)).setTextColor(mContext.getResources().getColor(R.color.red_orange));

			break;
		case Voucher.STATE_USED:  //已经使用
			contenCoupon.setBackgroundResource(R.drawable.img_coupon_used_background);
			tvTypeDescription.setTextColor(mContext.getResources().getColor(R.color.text_black_3));
			ivCouponMoney.setImageResource(R.drawable.icon_money_gray);
			tvCouponPrice.setTextColor(mContext.getResources().getColor(R.color.text_black_3));

			((TextView)helper.getView(R.id.tv_limit_text_1)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));
			((TextView)helper.getView(R.id.tv_limit_text_2)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));

			((TextView)helper.getView(R.id.tv_non_limit_coupon)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));
			((TextView)helper.getView(R.id.tv_endTimeUserCoupon)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));

			((TextView)helper.getView(R.id.tv_coupon_limit_price)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));
			((TextView)helper.getView(R.id.tv_coupon_price)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));

			break;

		case Voucher.STATE_DATE_OUT:  //已经过期
//			contenCoupon.setBackgroundResource(R.drawable.bg_coupon_used);
			contenCoupon.setBackgroundResource(R.drawable.img_coupon_out_od_date_background);
			tvTypeDescription.setTextColor(mContext.getResources().getColor(R.color.text_black_3));
			ivCouponMoney.setImageResource(R.drawable.icon_money_gray);
			tvCouponPrice.setTextColor(mContext.getResources().getColor(R.color.text_black_3));

			((TextView)helper.getView(R.id.tv_limit_text_1)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));
			((TextView)helper.getView(R.id.tv_limit_text_2)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));

			((TextView)helper.getView(R.id.tv_non_limit_coupon)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));
			((TextView)helper.getView(R.id.tv_endTimeUserCoupon)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));

			((TextView)helper.getView(R.id.tv_coupon_limit_price)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));
			((TextView)helper.getView(R.id.tv_coupon_price)).setTextColor(mContext.getResources().getColor(R.color.text_black_3));

			break;
		}
		
	}

}
