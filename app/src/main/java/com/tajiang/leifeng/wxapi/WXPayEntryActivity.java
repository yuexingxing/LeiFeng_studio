package com.tajiang.leifeng.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tajiang.leifeng.activity.OrderPayActivity;
import com.tajiang.leifeng.activity.UserWalletActivity;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.BuyCarUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler, HttpResponseListener {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, TJCst.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void initTopBar() {
    }

    @Override
    protected void initLayout() {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        // 充值支付
        if (TJApp.getIns().getTypeWeChatPay() == 3) {

            if (resp.errCode == 0) {
                Intent intent = new Intent(this, UserWalletActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                ToastUtils.showShort("微信支付成功");
            } else {
                ToastUtils.showShort("微信支付取消");
                finish();
            }

        }

        // 订单付款
        else {

            // 支付成功
            if (resp.errCode == 0) {
                ToastUtils.showShort("微信支付成功");

                BuyCarUtils.intent2FoodActivity(this);

            }

            // 取消支付
            else if (resp.errCode == -2 || resp.errCode == -1) {
                ToastUtils.showShort("微信支付取消");

                // 如果是新订单支付，取消则返回订单页面查看未支付订单
                if (TJApp.getTypeOrder() == OrderPayActivity.ORDER_TYPE_PAY_NOW) {
                    BuyCarUtils.intent2FoodActivity(this);
                }

                // 如果这个是未支付订单，取消则还是停留在支付页面
                else {
                    finish();
                }

            }

            BuyCarMapUtils.getCurBuyCarUtils().clearBuyCar();

        }

    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(BaseResponse response, int requestTag) {

    }

    @Override
    public void onFailed(BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {

    }
}