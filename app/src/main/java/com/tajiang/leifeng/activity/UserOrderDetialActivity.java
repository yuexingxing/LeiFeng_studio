package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.UserOrderPassMeDetailListAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.utils.ToastUtils;

public class UserOrderDetialActivity extends BaseActivity implements HttpResponseListener {

    private int type;

    private ListView lv_goodUserOrderDetail;

    private UserOrderPassMeDetailListAdapter userOrderPassMeDetailListAdapter;

    private Order order;

    private boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle arg0) {
        Intent intent = getIntent();
        type = intent.getIntExtra("UERR_ORDER_TYPE", -1);
        order = (Order) intent.getSerializableExtra("UERR_ORDER");
        super.onCreate(arg0);

        lv_goodUserOrderDetail.setAdapter(userOrderPassMeDetailListAdapter);

    }


    @Override
    protected void initTopBar() {
        setTitle("订单详情");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_order_mess_detail);
    }

    @Override
    protected void initView() {
        lv_goodUserOrderDetail = (ListView) findViewById(R.id.lv_goodUserOrderDetail);
    }

    @Override
    protected void initAdapter() {
        userOrderPassMeDetailListAdapter = new UserOrderPassMeDetailListAdapter(this, order.getOrderGoodsList(), R.layout.item_list_user_order_pass_me, order);
    }

    @Override
    protected void initDates() {

    }

    public void doEvaluateGood(String goodId, int numStar) {
        isRefresh = true;
        TJHttpUtil.getInstance(this).userOrderGoodEvaluate(goodId, numStar);
    }

    @Override
    public void onBackPressed() {
        if (isRefresh) {
            setResult(type + 1);
        }
        super.onBackPressed();
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_GOOD_EVALUATE:
                if ((Boolean) response.getData()) {
                    ToastUtils.showShort("评价成功");
                }
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
