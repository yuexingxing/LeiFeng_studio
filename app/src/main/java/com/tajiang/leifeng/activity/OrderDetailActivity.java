package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.OrderTrackAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.OrderGoods;
import com.tajiang.leifeng.model.TraceInfo;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.DateUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.dialog.ActionSheetDialog;
import com.tajiang.leifeng.view.dialog.HintDialog;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class
OrderDetailActivity extends BaseActivity implements HttpResponseListener {

    private String orderId;
    private Order mOrder;

    private View rectOperateOrder;
    private View rl_box_fee;

    private LinearLayout order_tracking_layout;  //订单跟踪layout
    private ScrollView order_detail_layout;  //订单详情layout
    private RelativeLayout order_detail_tab; //订单详情tab
    private RelativeLayout order_tracking_tab; //订单跟踪tab
    private View mListViewLine1;
    private View mListViewLine2;
    private ListView order_tracking_listview;  //订单跟踪listview
    private OrderTrackAdapter mOrderTrackAdapter;

    private RelativeLayout order_detail_status_relayout;
    private TextView status_tv;
    private RelativeLayout detail_failed_relayout;//退单失败
    private TextView tvBoxFeeOrderDetail;
    private TextView tvVoucherMoney;
    private TextView tvCallBusinessOrderDetail;
    private TextView tvNameStallsOrderDetail;
    private TextView tvTimeArriveOrderDetail;
    private TextView tvShippingFeeOrderDetail;

    private TextView tvAmountOrderDetail;
    private TextView tvAdressInfoOrderDetail;
    private TextView tvSchoolOrderDetail;
    private TextView tvRemarkOrderDetail;
    private TextView tvNegativeOrderDetail;
    private TextView tvPositiveOrderDetail;
    private TextView tvDateOrderDetail;
    private TextView tvSNOrderDetail;
    private TextView tvOrderCancelState;
    private TextView hurry_order_txt;
    private ImageView ivOrderStateImg;

    private LinearLayout llGoodListOrderDetail;

    private List<TraceInfo> mTrackList = new ArrayList<>();

    @Override
    protected void initTopBar() {
        setTitle("订单详情");
        enableNavLeftImage();
        orderId = getIntent().getStringExtra("ORDER_ID");
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_order_detail_copy);
    }

    @Override
    protected void initView() {
//        rectStateOrder = findViewById(R.id.rectStateOrder);
        order_detail_status_relayout = (RelativeLayout) findViewById(R.id.order_detail_status_relayout);
        status_tv = (TextView) findViewById(R.id.status_tv);
        detail_failed_relayout = (RelativeLayout) findViewById(R.id.detail_failed_relayout);
        order_detail_tab = (RelativeLayout) findViewById(R.id.order_detail_tab);
        order_tracking_tab = (RelativeLayout) findViewById(R.id.order_tracking_tab);
        order_tracking_layout = (LinearLayout) findViewById(R.id.order_tracking_layout);
        order_detail_layout = (ScrollView) findViewById(R.id.order_detail_layout);
        mListViewLine1 = findViewById(R.id.listview_line1);
        mListViewLine2 = findViewById(R.id.listview_line2);
        order_tracking_listview = (ListView) findViewById(R.id.order_tracking_listview);

        rectOperateOrder = findViewById(R.id.rectOperateOrder);
        rl_box_fee = findViewById(R.id.rl_box_fee);
        ivOrderStateImg = (ImageView) findViewById(R.id.ivOrderStateImg);
        tvOrderCancelState = (TextView) findViewById(R.id.tvOrderCancelState);
        hurry_order_txt = (TextView) findViewById(R.id.hurry_order_txt);
        tvBoxFeeOrderDetail = (TextView) findViewById(R.id.tvBoxFeeOrderDetail);
        tvVoucherMoney = (TextView) findViewById(R.id.tvVoucherMoney);
        tvCallBusinessOrderDetail = (TextView) findViewById(R.id.tvCallBusinessOrderDetail);
        tvSchoolOrderDetail = (TextView) findViewById(R.id.tvSchoolOrderDetail);
        tvAdressInfoOrderDetail = (TextView) findViewById(R.id.tvAdressInfoOrderDetail);
        tvAmountOrderDetail = (TextView) findViewById(R.id.tvAmountOrderDetail);
        tvShippingFeeOrderDetail = (TextView) findViewById(R.id.tvShippingFeeOrderDetail);
        llGoodListOrderDetail = (LinearLayout) findViewById(R.id.llGoodListOrderDetail);
        tvTimeArriveOrderDetail = (TextView) findViewById(R.id.tvTimeArriveOrderDetail);
        tvNameStallsOrderDetail = (TextView) findViewById(R.id.tvNameStallsOrderDetail);
        tvRemarkOrderDetail = (TextView) findViewById(R.id.tvRemarkOrderDetail);
        tvNegativeOrderDetail = (TextView) findViewById(R.id.tvNegativeOrderDetail);
        tvPositiveOrderDetail = (TextView) findViewById(R.id.tvPositiveOrderDetail);
        tvDateOrderDetail = (TextView) findViewById(R.id.tvDateOrderDetail);
        tvSNOrderDetail = (TextView) findViewById(R.id.tvSNOrderDetail);

        mListViewLine1.setVisibility(View.VISIBLE);
        mListViewLine2.setVisibility(View.GONE);
        order_tracking_layout.setVisibility(View.GONE);
        order_detail_layout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initAdapter() {
        mOrderTrackAdapter = new OrderTrackAdapter(this, mTrackList, R.layout.item_order_track);
    }

    @Override
    protected void initDates() {
        TJHttpUtil.getInstance(this).getOrderDetail(orderId);
        order_tracking_listview.setAdapter(mOrderTrackAdapter);
    }

    @Override
    protected void initListener() {
        order_detail_status_relayout.setOnClickListener(this);
        tvCallBusinessOrderDetail.setOnClickListener(this);
        hurry_order_txt.setOnClickListener(this);
        tvNegativeOrderDetail.setOnClickListener(this);
        tvPositiveOrderDetail.setOnClickListener(this);
        order_detail_tab.setOnClickListener(this);
        order_tracking_tab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.order_detail_status_relayout:
                intent2Activity(DrawbackDetailActivity.class);
                break;
            case R.id.tvPositiveOrderDetail:

                switch (mOrder.getOrderState()) {
                    case Order.STATE_NO_PAY:
                        intent2PayActivity();
                        break;
                    case Order.STATE_COMPLETE:
                        Intent intent = new Intent(this, EvaluateActivity.class);
                        intent.putExtra("ORDER_ID", mOrder.getOrderId());
                        startActivity(intent);
                        break;
                    case Order.STATE_TRANSFERED:
                    case Order.STATE_DELIVERY:
                        // 确认收货操作
                        doCompleteOrder();
                        break;
                    case Order.STATE_PAY:
                    case Order.STATE_TAKE:
                        Intent intent2 = new Intent(this, OrderCannelActivity.class);
                        intent2.putExtra("ORDER", mOrder);
                        startActivity(intent2);
                        break;
                }

                break;
            case R.id.tvCallBusinessOrderDetail:  // 点击联系送餐人
                showContactDialog("4009920278");
                break;
            case R.id.hurry_order_txt:
                TJHttpUtil.getInstance(OrderDetailActivity.this).userOrderHurry(mOrder.getOrderId());
                break;
            case R.id.tvNegativeOrderDetail:  // 点击底部左边的按钮
                switch (mOrder.getOrderState()) {
                    case Order.STATE_NO_PAY:
                        final HintDialog hintDialog1 = new HintDialog(this);
                        hintDialog1.setMessage("你确定要取消订单吗");
                        hintDialog1.setPositiveButtonListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hintDialog1.dismiss();
                                TJHttpUtil.getInstance(OrderDetailActivity.this).orderDoCancal(mOrder.getOrderId());
                            }
                        });
                        hintDialog1.show();
                        break;
                }

                break;

            case R.id.iv_serviceOrderDetail:
                intent2Activity(UserServiceCenterActivity.class);
                break;
            case R.id.order_detail_tab:
                mListViewLine1.setVisibility(View.VISIBLE);
                mListViewLine2.setVisibility(View.GONE);

                order_tracking_layout.setVisibility(View.GONE);
                order_detail_layout.setVisibility(View.VISIBLE);
                break;
            case  R.id.order_tracking_tab:
                mListViewLine1.setVisibility(View.GONE);
                mListViewLine2.setVisibility(View.VISIBLE);

                order_tracking_layout.setVisibility(View.VISIBLE);
                order_detail_layout.setVisibility(View.GONE);
                break;
        }
    }

    private void intent2PayActivity() {
        Intent intent = new Intent(this, OrderPayActivity.class);
        intent.putExtra("ORDER_ID", orderId);
        intent.putExtra("sumPrice", mOrder.getFinalMoney());
        intent.putExtra("ORDER_TYPE", OrderPayActivity.ORDER_TYPE_PAY_NOW);
        startActivity(intent);
    }

    private void doCompleteOrder() {
        final HintDialog hintDialog = new HintDialog(this);
        hintDialog.setMessage("你确定收到套餐了吗？");
        hintDialog.setPositiveButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintDialog.dismiss();
                TJHttpUtil.getInstance(OrderDetailActivity.this).userOrderComplete(mOrder.getOrderId());
            }
        });
        hintDialog.show();
    }

    private void showContactDialog(final String phoneInfo) {
//        if (order.getTakeInfo() != null) {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this)
                .builder()
                .setCanceledOnTouchOutside(true)
                .setTitle("联系号码")
                .setCanceledOnTouchOutside(false);

//            if (phoneInfo == null) {
//                showToast("没有联系人的号码");
//                return;
//            }
//            String[] phoneArr = phoneInfo.split(",");
//            if (phoneArr.length == 0) {
//                showToast("没有联系人的号码");
//                return;
//            }

//            for (final String phone : phoneArr) {
        actionSheetDialog.addSheetItem(phoneInfo, ActionSheetDialog.SheetItemColor.Green,
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onSheetItemClick(int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.CALL");
                        intent.setData(Uri.parse("tel:" + phoneInfo)); // num为电话号码
                        startActivity(intent);
                    }
                });
//            }

        actionSheetDialog.show();

//        } else {
//            showToast("没有联系人的号码");
//        }

    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_operation_finished", true);
        switch (requestTag) {
            case TJRequestTag.TAG_ORDER_COMPLETE:
                OrderDetailActivity.this.setResult(RESULT_OK, OrderDetailActivity.this.getIntent().putExtras(bundle));
                OrderDetailActivity.this.finish();

                break;
            case TJRequestTag.TAG_ORDER_CANCEL:
                showToast("订单取消成功");
                OrderDetailActivity.this.setResult(RESULT_OK, OrderDetailActivity.this.getIntent().putExtras(bundle));
                OrderDetailActivity.this.finish();
                break;
            case TJRequestTag.TAG_ORDER_HURRY:
                if ((Integer) response.getData() != 600) {
                    ToastUtils.show("宝宝你已经催过单啦！请耐心等候。", 1);
                } else {
                    ToastUtils.show("催单成功", 1);
                }
                break;
            case TJRequestTag.TAG_GET_ORDERDETAIL:
                mOrder = (Order) response.getData();
                setOrderDetail();

                mTrackList.addAll(mOrder.getOrderTraceList());
                mOrderTrackAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void setOrderDetail() {
        long createDate = DateUtils.stringToLong(mOrder.getCreateDate(), "yyyy-MM-dd HH:mm");

        long hurry = createDate + 30 * 60 * 1000;
        String time = DateUtils.getYMDHMDate(hurry);
        if (DateUtils.compareNowTime(time)) {
            hurry_order_txt.setVisibility(View.GONE);
        } else {
            hurry_order_txt.setVisibility(View.VISIBLE);
        }

        switch (mOrder.getOrderState()) {
            case Order.STATE_CANNEL_1:
            case Order.STATE_CANNEL_2:
            case Order.STATE_CANNEL_3:
            case Order.STATE_CANNEL_4:
                hurry_order_txt.setVisibility(View.GONE);
                break;

            case Order.STATE_PAY:
                if (mOrder.getOrderState() == Order.STATE_TRANSFERED) {
                    hurry_order_txt.setVisibility(View.GONE);
                }
                break;
            case Order.STATE_COMPLETE:

                hurry_order_txt.setVisibility(View.GONE);

                break;
        }

        //餐盒费
        if (mOrder.getTotalPackFee() > 0) {
            tvBoxFeeOrderDetail.setText("￥" + mOrder.getTotalPackFee());
            rl_box_fee.setVisibility(View.VISIBLE);
        } else {
            rl_box_fee.setVisibility(View.GONE);
        }

        tvVoucherMoney.setText("￥ -" + mOrder.getVoucherMoney());

        tvNameStallsOrderDetail.setText(mOrder.getShopName() == null ? "" : mOrder.getShopName());

        String timeArrive = mOrder.getDelyFast() == 1 ? "尽快送达" : mOrder.getDelyStartDate() + " - " + mOrder.getDelyEndDate();

        tvTimeArriveOrderDetail.setText("送达时间: " + timeArrive);
        tvShippingFeeOrderDetail.setText("￥" + mOrder.getTotalDelyFee());
        tvAmountOrderDetail.setText("￥" + mOrder.getFinalMoney());

        tvRemarkOrderDetail.setText("备注：" + mOrder.getBuyerRemark());
        tvDateOrderDetail.setText("下单时间：" + mOrder.getCreateDate());
        tvSNOrderDetail.setText("订单编号：" + mOrder.getOrderId() + "");

        String sex = mOrder.getReceiverSex() == User.MAN ? "男神" : "女神";

        tvAdressInfoOrderDetail.setText(mOrder.getReceiverName() + "  " + sex + "  " + mOrder.getReceiverPhone());
        tvSchoolOrderDetail.setText(mOrder.getReceiverAddr());

        for (OrderGoods orderGoods : mOrder.getOrderGoodsList()) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_order_good_item, null);
            TextView nameGoodOrderDetail = (TextView) view.findViewById(R.id.nameGoodOrderDetail);
            TextView numGoodOrderDetail = (TextView) view.findViewById(R.id.numGoodOrderDetail);
            TextView priceGoodOrderDetail = (TextView) view.findViewById(R.id.priceGoodOrderDetail);

            nameGoodOrderDetail.setText(orderGoods.getGoodsName());
            numGoodOrderDetail.setText(orderGoods.getGoodsQty() + "份");

            if (orderGoods.getGoodsPrice() != null) {
                priceGoodOrderDetail.setText("￥" + orderGoods.getGoodsPrice());
            } else {
                priceGoodOrderDetail.setText("￥0.00");
            }

            llGoodListOrderDetail.addView(view);
        }

        setOrderState();
    }

    private void setOrderState () {
        switch (mOrder.getOrderState()) {
            case Order.STATE_CANNEL_1:
            case Order.STATE_CANNEL_2:
            case Order.STATE_CANNEL_3:
            case Order.STATE_CANNEL_4:
//                rectStateOrder.setVisibility(View.GONE);
                rectOperateOrder.setVisibility(View.GONE);
                tvOrderCancelState.setVisibility(View.VISIBLE);
                ivOrderStateImg.setBackgroundResource(R.drawable.order_cancel_the_order);
                break;
            case Order.STATE_NO_PAY:
                tvNegativeOrderDetail.setText("取消订单");
                tvPositiveOrderDetail.setText("去支付");
                tvOrderCancelState.setVisibility(View.GONE);
                ivOrderStateImg.setBackgroundResource(R.drawable.order_unpaid);

                break;
            case Order.STATE_TRANSFERED:
            case Order.STATE_DELIVERY:  //配送中
                tvOrderCancelState.setVisibility(View.GONE);
                ivOrderStateImg.setBackgroundResource(R.drawable.order_distribution);

                tvNegativeOrderDetail.setText("申请退单");
                tvPositiveOrderDetail.setText("确认收到");
                tvOrderCancelState.setVisibility(View.GONE);
//                /**
//                 * 1.在未送达的情况下：
//                 *   如果超过付款时间15分钟 显示 申请退单 和 确认收到按钮
//                 *   否则隐藏 申请退单 和 确认收到按钮
//                 * 2.送达后：
//                 *   直接显示 申请退单 和 确认收到按钮
//                 */
//                if (mOrder.getOrderState() != Order.STATE_TRANSFERED) {
//                    if ((new Date().getTime() - mOrder.getUpdatedAt()) > Order.COUNT_TIME_TRANSFER) {
//                        rectOperateOrder.setVisibility(View.VISIBLE);
//                    } else {
//                        rectOperateOrder.setVisibility(View.GONE);
//                    }
//                } else {
//                    rectOperateOrder.setVisibility(View.VISIBLE);
//                }
                break;
            case Order.STATE_PAY:
            case Order.STATE_TAKE:  //已接单的情况下
//                tvNegativeOrderDetail.setText("申请退单");
                tvPositiveOrderDetail.setText("申请退单");
                tvNegativeOrderDetail.setVisibility(View.GONE);
                tvOrderCancelState.setVisibility(View.GONE);
                ivOrderStateImg.setBackgroundResource(R.drawable.order_has_received_orders);
                break;
            case Order.STATE_COMPLETE:
                tvOrderCancelState.setVisibility(View.GONE);
                ivOrderStateImg.setBackgroundResource(R.drawable.order_carry_out);
//                order_detail_status_relayout.setVisibility(View.VISIBLE);
                tvPositiveOrderDetail.setText("评价");
                tvNegativeOrderDetail.setVisibility(View.GONE);
                rectOperateOrder.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {

    }
}
