package com.tajiang.leifeng.adapter;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.GoodSet;
import com.tajiang.leifeng.model.Goods;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.BuyCarUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.BadgeView;
import com.tajiang.leifeng.view.HExpandableTextView;
import com.tajiang.leifeng.view.NumControlView;
import com.tajiang.leifeng.view.NumControlView.OnNumChangeListener;
import com.tajiang.leifeng.view.dialog.CDialog;

import java.util.List;

import static java.lang.Integer.MAX_VALUE;

/**
 * 选餐 -  ListView的Adapter
 */
public class FoodSelectListAdapter extends CommonAdapter<Goods> {

    private Activity mActivity;
    private View buycarView;
    private BadgeView badgeView;

    private CDialog foodDetail;
    private int mPackFee;
    private Integer state;

    private SparseBooleanArray collapsedStatus;

    public FoodSelectListAdapter(Context context, List<Goods> datas, int itemLayoutId, Activity activity, View car, BadgeView badgeView, Integer state, int packFee) {
        super(context, datas, itemLayoutId);

        mActivity = activity;
        this.mPackFee = packFee;
        this.buycarView = car;
        this.badgeView = badgeView;
        this.state = state;
        this.collapsedStatus = new SparseBooleanArray();
        mDatas = datas;
    }

    public void clearAllData() {
        this.collapsedStatus.clear();
        this.mDatas.clear();
    }

    @Override
    public void convert(final ViewHolder helper, final Goods item) {
        HExpandableTextView expandableTextView = helper.getView(R.id.expand_text_view);
        expandableTextView.setText("详情：" + item.getRemark(), collapsedStatus,
                helper.getPosition());
        expandableTextView.setVisibility(TextUtils.isEmpty(item.getRemark()) ? View.GONE : View.VISIBLE);

        Log.i("hexiuhui----", item.getGoodsName() + "---------" + item.getSpecialPrice());

        helper.setFoodImageByUrl(R.id.iv_picFoodSelect, item.getGoodsImage());
        helper.setText(R.id.tv_nameFoodSelect, item.getGoodsName());
        helper.setText(R.id.tv_priceFoodSelect, item.getSellPrice());
        helper.setText(R.id.tv_month_sellqty, "月售 " +item.getMonthSellQty() + "份");

        final NumControlView numControlView = helper.getView(R.id.ncv_numFoodSelect);

        TextView tvPriceFoodAction = helper.getView(R.id.tvPriceFoodAction);
        tvPriceFoodAction.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

        if(item.getSpecialPrice() != null && item.getSpecialPrice().doubleValue() != 0 ){
            tvPriceFoodAction.setText("￥" + item.getSpecialPrice());
//            helper.getView(R.id.iv_special_food).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_pic_special_price).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_tag_special_price).setVisibility(View.VISIBLE);
        }else {
//            helper.getView(R.id.iv_special_food).setVisibility(View.GONE);
            helper.getView(R.id.iv_pic_special_price).setVisibility(View.GONE);
            helper.getView(R.id.tv_tag_special_price).setVisibility(View.GONE);
            tvPriceFoodAction.setText("");
        }


        // TODO 餐厅的状态判断
        if (state.equals(1)) {

//            helper.getView(R.id.rect_restFoodSelect).setVisibility(View.GONE);

            if (item.getDayLimit() - item.getDaySalesNum() <= 0) {
//                helper.getView(R.id.rect_saleOutFoodSelect).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_out_of_detail).setVisibility(View.VISIBLE);
                helper.getView(R.id.ncv_numFoodSelect).setVisibility(View.GONE);
                helper.getView(R.id.tv_tag_special_price).setVisibility(View.GONE);
            } else {
//                helper.getView(R.id.rect_saleOutFoodSelect).setVisibility(View.GONE);
                helper.getView(R.id.tv_out_of_detail).setVisibility(View.GONE);
                helper.getView(R.id.ncv_numFoodSelect).setVisibility(View.VISIBLE);
                numControlView.setOnNumChangeListener(new OnNumChangeListener() {
                    @Override
                    public void OnCutButtonClick(View view, int num) {
                        BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().cutGood(item);
                        badgeView.setNum(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumCount());

                        if (item.getSpecialPrice().doubleValue() != 0 && numControlView.getNum() == 0) {
                            helper.getView(R.id.tv_tag_special_price).setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void OnAddButtonClick(View view, int num) {
                        BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().addGood(item, mPackFee);
                        badgeView.setNum(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumCount());

                        int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                        view.getLocationInWindow(start_location);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                        setAnim(buycarView, start_location);
                        if(numControlView.getNum() > 0
                                && item.getSpecialPrice().doubleValue() != 0
                                && helper.getView(R.id.tv_tag_special_price).getVisibility() == View.VISIBLE) {
                            helper.getView(R.id.tv_tag_special_price).setVisibility(View.GONE);
                            ToastUtils.showShortInCenter("结算时仅享受一份特价菜");
                        }
                    }
                });

                //初始化商品选购数量
                numControlView.setNumGood(0);

                // 根据购物车中商品的选购情况，设置相应的商品数量
                for (GoodSet goodSet : BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList()) {
                    if (goodSet.getGoods().getGoodsId() == item.getGoodsId()) {
                        numControlView.setNumGood(goodSet.getNum());
                        helper.getView(R.id.tv_tag_special_price).setVisibility(
                                goodSet.getNum() == 0? View.VISIBLE : View.GONE);
                        break;
                    }
                }

            }
        } else {
//            helper.getView(R.id.rect_restFoodSelect).setVisibility(View.VISIBLE);
//            helper.getView(R.id.rect_saleOutFoodSelect).setVisibility(View.GONE);
            helper.getView(R.id.tv_out_of_detail).setVisibility(View.GONE);

            helper.getView(R.id.ncv_numFoodSelect).setVisibility(View.GONE);
            helper.getView(R.id.tv_tag_special_price).setVisibility(View.GONE);
        }


        helper.getView(R.id.iv_picFoodSelect).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showGoodDetailDialog(item, numControlView.getNum());
            }
        });

    }

    private void showGoodDetailDialog(final Goods goods, int goodnum) {

        if (foodDetail == null) {
            foodDetail = new CDialog(mContext, R.layout.dialog_food_detail);
        }

        View contentView = foodDetail.getContentView();
        NumControlView ncv_numFoodDetailDialog = (NumControlView) contentView.findViewById(R.id.ncv_numFoodDetailDialog);
        TextView tv_nameFoodDetailDialog = (TextView) contentView.findViewById(R.id.tv_nameFoodDetailDialog);
        TextView tv_priceFoodDetailDialog = (TextView) contentView.findViewById(R.id.tv_priceFoodDetailDialog);
        TextView tv_groupFoodDetailDialog = (TextView) contentView.findViewById(R.id.tv_groupFoodDetailDialog);
        TextView tv_saleOutFoodSelectDialog = (TextView) contentView.findViewById(R.id.tv_saleOutFoodSelectDialog);

        if(state == 1){
            if (goods.getDayLimit() - goods.getDaySalesNum() <= 0) {
                tv_saleOutFoodSelectDialog.setVisibility(View.VISIBLE);
                ncv_numFoodDetailDialog.setVisibility(View.INVISIBLE);
            } else {
                tv_saleOutFoodSelectDialog.setVisibility(View.INVISIBLE);
                ncv_numFoodDetailDialog.setVisibility(View.VISIBLE);
            }
        }else {
            ncv_numFoodDetailDialog.setVisibility(View.INVISIBLE);
            tv_saleOutFoodSelectDialog.setVisibility(View.INVISIBLE);
        }

        // 加载套餐大图
        SimpleDraweeView simpleDraweeView =  foodDetail.findChildViewById(R.id.iv_picGoodDetial);
        loadImage(simpleDraweeView, goods.getGoodsImage());

        tv_nameFoodDetailDialog.setText(goods.getGoodsName());
        tv_priceFoodDetailDialog.setText(goods.getSellPrice() + "");
        tv_groupFoodDetailDialog.setText(goods.getRemark());
        ncv_numFoodDetailDialog.setNumGood(goodnum);

        ncv_numFoodDetailDialog.setOnNumChangeListener(new OnNumChangeListener() {
            @Override
            public void OnCutButtonClick(View view, int num) {
                BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().cutGood(goods);
                badgeView.setNum(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumCount());
                notifyDataSetChanged();
            }

            @Override
            public void OnAddButtonClick(View view, int num) {
                BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().addGood(goods, mPackFee);
                badgeView.setNum(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumCount());
                notifyDataSetChanged();
            }
        });

        foodDetail.show();

    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
//        animLayout.setId(MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        lp.leftMargin = x;
        lp.topMargin = y;

        view.setLayoutParams(lp);

        return view;
    }

    public void setState(Integer state) {
        this.state = state;
        notifyDataSetChanged();
    }

    /**
     * 添加商品动画
     *
     * @param shopCart
     * @param start_location
     */
    private void setAnim(View shopCart, int[] start_location) {


        final ImageView animImageView = new ImageView(mActivity);
        animImageView.setImageResource(R.drawable.icon_point_green);
        animImageView.setScaleType(ScaleType.CENTER_CROP);

        ViewGroup anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(animImageView);// 把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, animImageView, start_location);
        int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
        shopCart.getLocationInWindow(end_location);// shopCart是那个购物车

        // 计算位移
        int endX = 0 - start_location[0] + shopCart.getWidth();// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(400);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                animImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                animImageView.setVisibility(View.GONE);

                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.1f, 1f);
                PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.1f, 1f);
                ObjectAnimator.ofPropertyValuesHolder(buycarView, pvhY, pvhZ).setDuration(200).start();

            }
        });


    }

}
