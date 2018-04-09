package com.tajiang.leifeng.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alipay.sdk.app.PayTask;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.PredepositOrder;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class TJPayUtils {
	
	public static final int PAY_TAPE_ALIPAY = 2;     //支付宝
	public static final int PAY_TAPE_WEICHATPAY =1;  //微信
	public static final int PAY_TAPE_WELLAT = 0;   //余额

	/**
	 * 支付宝支付
	 * @param callBack
	 * @param activity
	 */
 	public static void aliPay(String payInfo, final IPayCallBack callBack, final Activity activity) {
		aliPaySimple(callBack, activity, payInfo);
 	}

	public static void aliPaySimple(final IPayCallBack callBack, final Activity activity, final String payInfo) {
		Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);
                //回调支付结果
                callBack.payResult(result);
            }
        };

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
 	 * 微信支付
 	 * @param order
 	 * @param context
 	 */
 	public static void wxPayOrder(Order order, Context context) {
 		IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(TJCst.WEIXIN_APP_ID);

		PayReq req = new PayReq();
		req.appId = order.getAppid();
		req.partnerId = order.getPartnerid();
		req.prepayId = order.getPrepayid();
		req.packageValue = "Sign=WXPay";
		req.nonceStr = order.getNoncestr();
		req.timeStamp = order.getTimestamp();
		req.sign = order.getSign();
		msgApi.sendReq(req);
		
 	}

	/**
 	 * 微信支付
 	 * @param context
 	 */
 	public static void wxPayRecharge(PredepositOrder predepositOrder, Context context) {

 		IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(TJCst.WEIXIN_APP_ID);

		PayReq req = new PayReq();
		req.appId = predepositOrder.getAppid();
		req.partnerId = predepositOrder.getMch_id();
		req.prepayId = predepositOrder.getPrepay_id();
		req.packageValue = "Sign=WXPay";
		req.nonceStr = predepositOrder.getNonce_str();
		req.timeStamp = predepositOrder.getTimestamp();
		req.sign = predepositOrder.getSign();
		msgApi.sendReq(req);

 	}
}
