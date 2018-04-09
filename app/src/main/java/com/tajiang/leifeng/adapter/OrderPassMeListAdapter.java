package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.HomeActivity;
import com.tajiang.leifeng.activity.OrderDetailActivity;
import com.tajiang.leifeng.activity.OrderPayActivity;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.fragment.OrderFragment;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.OrderGoods;
import com.tajiang.leifeng.utils.DateUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.OrderStateBtnView;
import com.tajiang.leifeng.view.dialog.ActionSheetDialog;
import com.tajiang.leifeng.view.dialog.HintDialog;

import java.util.List;

public class OrderPassMeListAdapter extends CommonAdapter<Order> implements OnClickListener, HttpResponseListener {

    public static final int REQUEST_CODE_DETAIL = 2;

    private OnItemCompleteListener onItemCompleteListener;

    public void setOnItemCompleteListener(OnItemCompleteListener onItemCompleteListener) {
        this.onItemCompleteListener = onItemCompleteListener;
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_ORDER_COMPLETE:
                //接口回调，确认订单以后，刷新订单页面的数据
                onItemCompleteListener.OnItemComplete();
                break;
            case TJRequestTag.TAG_ORDER_HURRY:
                if ((Integer)response.getData() != 600) {
                    ToastUtils.show("宝宝你已经催过单啦！请耐心等候。", 1);
                } else {
                    ToastUtils.show("催单成功", 1);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {

    }

    public interface OnItemCompleteListener{
        public void OnItemComplete();
    }

    public OrderPassMeListAdapter(Context context, List<Order> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, final Order item) {

        initLayout(helper, item);

        Button hurryBtn = helper.getView(R.id.hurry_order_btn);
        // 订单状态
        final TextView tvStateOrder = helper.getView(R.id.tvStateOrder);
        // 订单操作按钮
        final OrderStateBtnView tvOperateOrder = helper.getView(R.id.tvOperateOrder);

        long createDate = DateUtils.stringToLong(item.getCreateDate(), "yyyy-MM-dd HH:mm");
        long hurry = createDate + 30 * 60 * 1000;
        String time = DateUtils.getYMDHMDate(hurry);
        if (DateUtils.compareNowTime(time)) {
            hurryBtn.setVisibility(View.GONE);
        } else {
            hurryBtn.setVisibility(View.VISIBLE);
        }

        switch (item.getOrderState()) {
            case Order.STATE_CANNEL_1:
            case Order.STATE_CANNEL_2:
            case Order.STATE_CANNEL_3:
            case Order.STATE_CANNEL_4:

                initItemCannel(tvStateOrder);
                tvOperateOrder.setVisibility(View.GONE);
                hurryBtn.setVisibility(View.GONE);
                tvOperateOrder.cannelTimer();

                break;
            case Order.STATE_NO_PAY:
                tvOperateOrder.setState(item, OrderStateBtnView.PAY_MODE);
                tvOperateOrder.setTextColor(getContext().getResources().getColor(R.color.red));
                tvOperateOrder.setBackgroundResource(R.drawable.shape_rect_round_line_red);
                tvOperateOrder.setVisibility(View.VISIBLE);
                hurryBtn.setVisibility(View.GONE);
                initItemNoPay(tvStateOrder);
                tvOperateOrder.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent2PayActivity(item);
                    }
                });

                break;
            case Order.STATE_PAY:
                tvOperateOrder.setState(item, OrderStateBtnView.TRANSFER_MODE);
                tvOperateOrder.setText("联系送餐人");
                tvOperateOrder.setTextColor(getContext().getResources().getColor(R.color.com_green));
                tvOperateOrder.setBackgroundResource(R.drawable.shape_rect_round_line_green);
                tvOperateOrder.setVisibility(View.GONE);
                //配送倒计时结束监听
                tvOperateOrder.setTimeOverListener(new OrderStateBtnView.OnTimeOverListener() {
                    @Override
                    public void onTimeOver() {
                        initItemTransfering(tvStateOrder);
                    }
                });
                //点击事件默认联系送餐人
                tvOperateOrder.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initCallDialog(item.getTakeInfo());
                    }
                });

                initItemHasPay(tvStateOrder);   //已接单 (不展示，送餐联系人 申请退货 确认收到)
                tvOperateOrder.setVisibility(View.GONE);
                break;
            case Order.STATE_DELIVERY:
            case Order.STATE_TRANSFERED:
                initItemHasTransfered(tvStateOrder); //如果订单已经送达，显示已送达,停止配送倒计时
                tvOperateOrder.setText("确认收到");
                tvOperateOrder.setVisibility(View.VISIBLE);
                tvOperateOrder.setOnClickListener(new OnClickListener() {  //显示确认送达按钮
                    @Override
                    public void onClick(View v) {
                        //TODO....确认送达..
                        doCompleteOrder(item);
                    }
                });
                hurryBtn.setVisibility(View.GONE);
                break;
            case Order.STATE_COMPLETE:

                initItemComplete(tvStateOrder);
                tvOperateOrder.setTextColor(getContext().getResources().getColor(R.color.com_green));
                tvOperateOrder.setVisibility(View.GONE);
                tvOperateOrder.cannelTimer();
                hurryBtn.setVisibility(View.GONE);

                break;
        }

        helper.getView(R.id.rectItemContentPassMe).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("ORDER_ID", item.getOrderId());
                startActivityAndForResult(intent, REQUEST_CODE_DETAIL);
            }
        });

        hurryBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TJHttpUtil.getInstance(OrderPassMeListAdapter.this).userOrderHurry(item.getOrderId());
            }
            });
    }

    /**
     * 确认收货
     */
    private void doCompleteOrder(Order item) {
        final Order order = item;
        final HintDialog hintDialog = new HintDialog(mContext);
        hintDialog.setMessage("你确定收到套餐了吗？");
        hintDialog.setPositiveButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintDialog.dismiss();
                TJHttpUtil.getInstance(OrderPassMeListAdapter.this).userOrderComplete(order.getOrderId());
            }
        });
        hintDialog.show();
    }


    private void intent2PayActivity(Order order) {
        Intent intent = new Intent(getContext(), OrderPayActivity.class);
        intent.putExtra("ORDER", order);
        intent.putExtra("ORDER_TYPE", OrderPayActivity.ORDER_TYPE_PAY_AGAIN);
        startActivity(intent);
    }

    private void initLayout(ViewHolder helper, Order item) {
        helper.getView(R.id.tvNameStoreOrder).setVisibility(item.getShopName() == null ? View.GONE : View.VISIBLE);
        helper.setText(R.id.tvNameStoreOrder, item.getShopName());
        helper.setFoodImageByUrl(R.id.tv_picOrder, item.getOrderGoodsList().get(0).getGoodsImage());
//        setOrderName(helper, item, R.id.tvNameGoodOrder);
        helper.setText(R.id.tvTimeOrder, item.getCreateDate());
        helper.setText(R.id.tvPackageNum, "套餐数量 x " + item.getOrderGoodsList().size());
        helper.setText(R.id.tvPriceOrder, "￥" + item.getFinalMoney());
    }

    // 初始化已取消的订单
    private void initItemCannel(TextView tvStateOrder) {

        // 设置订单状态
        tvStateOrder.setText("已退单");
        tvStateOrder.setTextColor(getContext().getResources().getColor(R.color.text_black_1));

    }

    // 初始化已完成
    private void initItemComplete(TextView tvStateOrder) {

        // 设置订单状态
        tvStateOrder.setText("已完成");
        tvStateOrder.setTextColor(getContext().getResources().getColor(R.color.text_black_1));

    }

    // 初始化没有支付的界面样式
    private void initItemNoPay(TextView tvStateOrder) {

        // 设置订单状态
        tvStateOrder.setText("待支付");
        tvStateOrder.setTextColor(getContext().getResources().getColor(R.color.red));

    }

    // 初始化已支付的界面样式
    private void initItemHasPay(TextView tvStateOrder) {

        // 设置订单状态
        tvStateOrder.setText("已接单");
        tvStateOrder.setTextColor(getContext().getResources().getColor(R.color.com_green));

    }

    // 初始化已支付的界面样式
    private void initItemTransfering(TextView tvStateOrder) {
        // 设置订单状态
        tvStateOrder.setText("配送中");
        tvStateOrder.setTextColor(getContext().getResources().getColor(R.color.com_green));
    }

    // 初始化已支付的界面样式
    private void initItemHasTransfered(TextView tvStateOrder) {
        // 设置订单状态
        tvStateOrder.setText("已送达");
        tvStateOrder.setTextColor(getContext().getResources().getColor(R.color.com_green));
    }

    private void initCallDialog(final String takerPhone) {

        if (!TextUtils.isEmpty(takerPhone)) {
            ActionSheetDialog actionSheetDialog = new ActionSheetDialog(getContext())
                    .builder()
                    .setCanceledOnTouchOutside(true)
                    .setTitle("联系号码")
                    .setCanceledOnTouchOutside(false);

            String[] phoneArr = takerPhone.split(",");

            for (final String phone : phoneArr) {
                actionSheetDialog.addSheetItem(phone, ActionSheetDialog.SheetItemColor.Green,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onSheetItemClick(int which) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.CALL");
                                intent.setData(Uri.parse("tel:" + phone)); // num为电话号码
                                getContext().startActivity(intent);
                            }
                        });
            }
            actionSheetDialog.show();
        } else {
            ToastUtils.showShort("没有联系人的号码");
        }
    }

    /**
     * 生成订单的名称
     */
    private void setOrderName(ViewHolder helper, Order item, int tvId) {
        List<OrderGoods> orderGoodList = item.getOrderGoodsList();
        if (item.getOrderGoodsList().size() == 1) {
            helper.setText(tvId, item.getOrderGoodsList().get(0).getGoodsName() + "*" + orderGoodList.get(0).getGoodsQty());
        } else {
            int sumGoodNum = 0;
            for (OrderGoods orderGoods : orderGoodList) {
                sumGoodNum += orderGoods.getGoodsQty();
            }
            helper.setText(tvId, "共" + sumGoodNum + "份套餐");
        }
    }

    @Override
    public void onClick(View v) {
        Order order = (Order) v.getTag();
        switch (v.getId()) {
            case R.id.btnDoPayOrderNoPay:
                Intent intent = new Intent(getContext(), OrderPayActivity.class);
                intent.putExtra("ORDER", order);
                intent.putExtra("ORDER_TYPE", OrderPayActivity.ORDER_TYPE_PAY_AGAIN);
                getContext().startActivity(intent);
                break;
        }
    }
}
