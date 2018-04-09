package com.tajiang.leifeng.adapter;

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
import com.tajiang.leifeng.utils.LogUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class UserChooseCouponListAdapter extends CommonAdapter<Voucher> {
    /**
     * true:显示可用红包; false 显示历史红包
     */
    private NumberFormat numberFormat = NumberFormat.getInstance();
    private Voucher defaultVoucher;
    private BigDecimal sumPrice;
    private ImageView imageView;
    private boolean isFirst = true;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private OnItemClick onItemClick;

    public interface OnItemClick{
        public void onItemClicked(Voucher voucher);
    }

    public UserChooseCouponListAdapter(Context context, List<Voucher> mDatas,
                                       Voucher defaultVoucher,
                                       BigDecimal sumPrice,
                                 int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.defaultVoucher = defaultVoucher;
        this.sumPrice = sumPrice;
    }

    @Override
    public void convert(final ViewHolder helper, final Voucher item) {
        LogUtils.e("Position = " + helper.getPosition());
        if (defaultVoucher != null && item.getId().equals(defaultVoucher.getId())) {
            imageView = helper.getView(R.id.iv_choose_coupon);
            imageView.setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_choose_coupon).setVisibility(View.INVISIBLE);
        }

        helper.setText(R.id.tv_priceUserCoupon,  numberFormat.format(item.getFaceValue()) + "");
        helper.setText(R.id.tv_endTimeUserCoupon,  "有效期至:   " + item.getExpireTime());
        helper.setText(R.id.tv_type_description,  item.getPolicyName());

        TextView tvCouponPrice = helper.getView(R.id.tv_priceUserCoupon);
        LinearLayout contenCoupon = helper.getView(R.id.contenCoupon);
        TextView tvTypeDescription = helper.getView(R.id.tv_type_description);
        ImageView ivCouponMoney = helper.getView(R.id.iv_coupon_money);

        if (new BigDecimal(0.00).compareTo(item.getLimitPrice()) != 0) {  //满减红包
            helper.getView(R.id.tv_non_limit_coupon).setVisibility(View.GONE);
            helper.getView(R.id.ll_limit_coupon).setVisibility(View.VISIBLE);

            helper.setText(R.id.tv_coupon_limit_price,  numberFormat.format(item.getLimitPrice()) + "");
            helper.setText(R.id.tv_coupon_price,  numberFormat.format(item.getFaceValue()) + "");

        } else {
            helper.getView(R.id.tv_non_limit_coupon).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_limit_coupon).setVisibility(View.GONE);
        }
        contenCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView != null) {
                    imageView.setVisibility(View.INVISIBLE);
                }
                helper.getView(R.id.iv_choose_coupon).setVisibility(View.VISIBLE);
                if (onItemClick != null) {
                    onItemClick.onItemClicked(item);
                }
            }
        });
        contenCoupon.setEnabled(false);
        switch (item.getState()) {
            case Voucher.STATE_USABLE:

                boolean isCouponUsable = false;

                if (sumPrice == null) {
                    break;
                }

                if (new BigDecimal(0.00).compareTo(item.getLimitPrice()) == 0
                        || sumPrice.compareTo(item.getLimitPrice()) >= 0) {
                    contenCoupon.setEnabled(true);
                    isCouponUsable = true;
                }

                contenCoupon.setBackgroundResource(isCouponUsable ? R.drawable.img_coupon_usable_background : R.drawable.img_coupon_disable_background);
                tvTypeDescription.setTextColor(mContext.getResources().getColor(isCouponUsable ? R.color.text_black_1 : R.color.text_black_3));
                ivCouponMoney.setImageResource(isCouponUsable ? R.drawable.icon_money_red : R.drawable.icon_money_gray);
                tvCouponPrice.setTextColor(mContext.getResources().getColor(isCouponUsable ? R.color.red_money : R.color.text_black_3));


                ((TextView)helper.getView(R.id.tv_limit_text_1)).setTextColor(mContext.getResources().getColor(isCouponUsable ? R.color.text_black_1 : R.color.text_black_3));
                ((TextView)helper.getView(R.id.tv_limit_text_2)).setTextColor(mContext.getResources().getColor(isCouponUsable ? R.color.text_black_1 : R.color.text_black_3));

                ((TextView)helper.getView(R.id.tv_non_limit_coupon)).setTextColor(mContext.getResources().getColor(isCouponUsable ? R.color.text_black_1 : R.color.text_black_3));
                ((TextView)helper.getView(R.id.tv_endTimeUserCoupon)).setTextColor(mContext.getResources().getColor(isCouponUsable ? R.color.text_black_1 : R.color.text_black_3));

                ((TextView)helper.getView(R.id.tv_coupon_limit_price)).setTextColor(mContext.getResources().getColor(isCouponUsable ? R.color.red_orange : R.color.text_black_3));
                ((TextView)helper.getView(R.id.tv_coupon_price)).setTextColor(mContext.getResources().getColor(isCouponUsable ? R.color.red_orange : R.color.text_black_3));

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
            case Voucher.STATE_DATE_OUT: //已经过期
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
