package com.tajiang.leifeng.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.sina.weibo.sdk.component.view.LoadingBar;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.OrderDetailActivity;
import com.tajiang.leifeng.adapter.OrderPassMeListAdapter;
import com.tajiang.leifeng.base.BaseFragment;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.utils.DensityUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;
import com.tajiang.leifeng.view.dialog.ProgressDialog;
import com.tajiang.leifeng.view.pullrefresh.LoadingLayout;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshBase;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<ListView>, HttpResponseListener {

    private boolean enableRefreshOnResume = false;
    private int pager = 1;

    private View ll_orderEmpty;

    private PullToRefreshListView plv_passmeOrder;

    private ListView xlv_passmeOrder;

    private OrderPassMeListAdapter orderPassMeListAdapter;

    private List<Order> orderList = new ArrayList<>();

    private AnimLoadDialog loadDialog;
    private ProgressDialog loadingDialog;


    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_order);
    }

    protected void initView() {
        ll_orderEmpty   = findViewById(R.id.ll_orderEmpty);
        plv_passmeOrder = findViewById(R.id.plv_passmeOrder);
        xlv_passmeOrder = plv_passmeOrder.getRefreshableView();

        plv_passmeOrder.setPullLoadEnabled(false);
        plv_passmeOrder.setScrollLoadEnabled(true);
        xlv_passmeOrder.setEmptyView(ll_orderEmpty);
    }

    protected void initAdapter() {
        orderPassMeListAdapter = new OrderPassMeListAdapter(getContext(), orderList, R.layout.item_list_order_pass_me_copy);
        orderPassMeListAdapter.setOnItemCompleteListener(new OrderPassMeListAdapter.OnItemCompleteListener() {
            @Override
            public void OnItemComplete() {  //在订单页确认订单后刷新当前订单列表
                TJHttpUtil.getInstance(OrderFragment.this).getOrderList(1);
            }
        });

    }

    protected void initListener() {
        plv_passmeOrder.setOnRefreshListener(this);
    }

    public void initData() {
        loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setCancelable(false);
        xlv_passmeOrder.setAdapter(orderPassMeListAdapter);

        xlv_passmeOrder.setDivider(new ColorDrawable(getResources().getColor(R.color.com_layout_bg)));
        xlv_passmeOrder.setDividerHeight(DensityUtils.dp2px(getActivity(), 3));

    }

    @Override
    public void onResume() {
        super.onResume();
        if (enableRefreshOnResume && UserUtils.getInstance().isLogin()) {
            // 加载点单数据
            loadingDialog.show();
            TJHttpUtil.getInstance(this).getOrderList(1);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        pager = 1;
        TJHttpUtil.getInstance(this).getOrderList(pager);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        TJHttpUtil.getInstance(this).getOrderList(pager + 1);
    }

    @Override
    public void reFreshCurrentPageData() {
        super.reFreshCurrentPageData();
        doRefreshCouponAndWallet();
    }

    //TODO..........
    /**
     * 刷新用户钱包和优惠卷
     */
    private void doRefreshCouponAndWallet() {
        //-----Retrofit 加载点单数据
        loadingDialog.show();
        OrderFragment.this.enableRefreshOnResume = true;
        TJHttpUtil.getInstance(this).getOrderList(1);
    }

    public void refreshDate() {
        xlv_passmeOrder.setSelection(0);
        pager = 1;
        TJHttpUtil.getInstance(this).getOrderList(pager);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    xlv_passmeOrder.setSelection(0);
                    plv_passmeOrder.doPullRefreshing(true, 500);
                }
                break;
            case OrderPassMeListAdapter.REQUEST_CODE_DETAIL:
                if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle != null && bundle.getBoolean("is_operation_finished")){
                    TJHttpUtil.getInstance(this).getOrderList(1);

                }
            }
                break;
            default:
                break;
        }

    }


    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_GET_ORDER:
                Pager<Order> orderPager = (Pager<Order>) response.getData();
                List<Order> orderListResult = orderPager.getList();
                pager = orderPager.getPage();

                if (pager == 1) {
                    orderList.clear();
                }

                orderList.addAll(orderListResult);
                orderPassMeListAdapter.notifyDataSetChanged();

                plv_passmeOrder.onPullDownRefreshComplete();
                plv_passmeOrder.onPullUpRefreshComplete();

                if (pager == orderPager.getLastPage()) {
                    plv_passmeOrder.setHasMoreData(false);
                }
                break;
            case TJRequestTag.TAG_ORDER_CANCEL:
                showToast("取消订单成功");
                // 加载点单数据
                TJHttpUtil.getInstance(this).getOrderList(1);
                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}