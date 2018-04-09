package com.tajiang.leifeng.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jungly.gridpasswordview.GridPasswordView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.model.ActiveInfo;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.OrderPre;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.pay.AliPayResult;
import com.tajiang.leifeng.pay.IPayCallBack;
import com.tajiang.leifeng.pay.TJPayUtils;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.BuyCarUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.utils.WeChatUtils;
import com.tajiang.leifeng.view.dialog.LoadingDialog;
import com.tajiang.leifeng.view.dialog.ProgressDialog;
import com.tajiang.leifeng.view.dialog.PswPayDialog;

import java.util.Timer;
import java.util.TimerTask;

public class OrderPayActivity extends BaseActivity implements IPayCallBack, HttpResponseListener {

    public static final int ORDER_TYPE_PAY_NOW = 1;
    public static final int ORDER_TYPE_PAY_AGAIN = 2;

    private double sumPrice;

    private Order order;
    private String orderId;
    private String payPsw;

//    private OrderPre orderPre;

    private View view_couponFoodPay;
    private View view_zhifubaoFoodPay;
    private View view_weixinFoodPay;
    private View view_wellatFoodPay;

    private TextView tvSumPriceOrder;
    private TextView tvNumWellatFoodPay;

    private ImageView iv_zhifubaoRadio;
    private ImageView iv_weixinRadio;
    private ImageView iv_wellatRadio;

    private Button btnPayOrder;

    private int currentPayType = TJPayUtils.PAY_TAPE_ALIPAY;

    private int orderType = ORDER_TYPE_PAY_NOW;

//    private LoadingDialog loadingDialog;
    private ProgressDialog loadingDialog;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            String resultStatus = (String) msg.obj;

            //支付成功
            if (TextUtils.equals(resultStatus, "9000")) {

                ToastUtils.show("支付成功", Toast.LENGTH_SHORT);

                //跳转到食堂模块首页
                BuyCarUtils.intent2FoodActivity(getContext());

                if (orderType == ORDER_TYPE_PAY_NOW) {
                    BuyCarMapUtils.getCurBuyCarUtils().clearBuyCar();
                }

            } else if (TextUtils.equals(resultStatus, "8000")) {
                // 判断resultStatus 为非“9000”则代表可能支付失败
                // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                ToastUtils.show("支付结果确认中", Toast.LENGTH_SHORT);
            } else {
                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                ToastUtils.show("取消支付", Toast.LENGTH_SHORT);

                if (orderType == ORDER_TYPE_PAY_NOW) {

                    //跳转到食堂模块首页
                    BuyCarUtils.intent2FoodActivity(getContext());
                    BuyCarMapUtils.getCurBuyCarUtils().clearBuyCar();

                }

            }

        }
    };

    private PswPayDialog pswPayDialog;
    private Handler handlerUI = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: // 输入支付密码失败
                    pswPayDialog.clearPassword();
                    showToast("支付密码错误，请重新输入");
                    break;
            }
        }
    };

    @Override
    protected void initTopBar() {
        setTitle("支付");
        enableNavLeftImage();

        orderType = (int) getIntent().getSerializableExtra("ORDER_TYPE");
        sumPrice = Double.valueOf(getIntent().getStringExtra("sumPrice"));
        orderId = getIntent().getStringExtra("ORDER_ID");

        /**特殊处理 - 为微信设置订单类型*/
        TJApp.setTypeOrder(orderType);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_order_pay);
    }

    @Override
    protected void initView() {
        view_couponFoodPay = findViewById(R.id.view_couponFoodPay);
        view_zhifubaoFoodPay = findViewById(R.id.view_zhifubaoFoodPay);
        view_weixinFoodPay = findViewById(R.id.view_weixinFoodPay);
        view_wellatFoodPay = findViewById(R.id.view_wellatFoodPay);

        tvSumPriceOrder = (TextView) findViewById(R.id.tvSumPriceOrder);
        tvNumWellatFoodPay = (TextView) findViewById(R.id.tvNumWellatFoodPay);

        iv_zhifubaoRadio = (ImageView) findViewById(R.id.iv_zhifubaoRadio);
        iv_weixinRadio = (ImageView) findViewById(R.id.iv_weixinRadio);
        iv_wellatRadio = (ImageView) findViewById(R.id.iv_wellatRadio);

        btnPayOrder = (Button) findViewById(R.id.btnPayOrder);
    }


    @Override
    protected void initListener() {
        super.initListener();

        view_couponFoodPay.setOnClickListener(this);
        view_zhifubaoFoodPay.setOnClickListener(this);
        view_weixinFoodPay.setOnClickListener(this);
        view_wellatFoodPay.setOnClickListener(this);
        btnPayOrder.setOnClickListener(this);

        view_couponFoodPay.setClickable(false);

    }

    @Override
    protected void initDates() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        tvSumPriceOrder.setText("￥" + sumPrice);
        TJHttpUtil.getInstance(this).userDoGetInfor();
//        User user = UserUtils.getInstance().getUser();
//        tvNumWellatFoodPay.setText("余额：￥" + user.getAvailablePredeposit() + "");
//
//        if (sumPrice > user.getAvailablePredeposit()) {
//            setPayType(TJPayUtils.PAY_TAPE_ALIPAY);
//        } else {
//            setPayType(TJPayUtils.PAY_TAPE_WELLAT);
//        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.view_zhifubaoFoodPay:
                setPayType(TJPayUtils.PAY_TAPE_ALIPAY);
                break;
            case R.id.view_weixinFoodPay:
                setPayType(TJPayUtils.PAY_TAPE_WEICHATPAY);
                break;
            case R.id.view_wellatFoodPay:
                setPayType(TJPayUtils.PAY_TAPE_WELLAT);
                break;
            case R.id.btnPayOrder:

                double sumWallet = UserUtils.getInstance().getUser().getBalance();

                if (currentPayType == TJPayUtils.PAY_TAPE_WELLAT) {
                    if (sumWallet >= sumPrice) {
                        loadingDialog.setmMessageText("正在加载").show();
                        TJHttpUtil.getInstance(this).userWalletPayPswIsAuthReq();
                    } else {
                        showToast("雷锋钱包余额不足");
                    }
                } else {
                    doPay();
                }

                break;

        }
    }

    public void setPayType(int type) {
        currentPayType = type;

        switch (type) {
            case TJPayUtils.PAY_TAPE_ALIPAY:
                iv_wellatRadio.setImageResource(R.drawable.icon_radio_normal);
                iv_weixinRadio.setImageResource(R.drawable.icon_radio_normal);
                iv_zhifubaoRadio.setImageResource(R.drawable.icon_radio_selected);
                break;
            case TJPayUtils.PAY_TAPE_WEICHATPAY:
                iv_wellatRadio.setImageResource(R.drawable.icon_radio_normal);
                iv_zhifubaoRadio.setImageResource(R.drawable.icon_radio_normal);
                iv_weixinRadio.setImageResource(R.drawable.icon_radio_selected);
                break;
            case TJPayUtils.PAY_TAPE_WELLAT:
                iv_wellatRadio.setImageResource(R.drawable.icon_radio_selected);
                iv_zhifubaoRadio.setImageResource(R.drawable.icon_radio_normal);
                iv_weixinRadio.setImageResource(R.drawable.icon_radio_normal);
                break;
        }
    }

    /**
     * 进行支付操作
     */
    private void doPay() {

        if (currentPayType == TJPayUtils.PAY_TAPE_WEICHATPAY) {
            if (!WeChatUtils.isWXAppInstalledAndSupported(this)) {
                return;
            }
        }
        loadingDialog.setmMessageText("正在支付").show();
        TJHttpUtil.getInstance(this).doPayOrder(orderId, 2, payPsw, currentPayType);
    }

    @Override
    public void payResult(String result) {
        AliPayResult payResult = new AliPayResult(result);
        String resultStatus = payResult.getResultStatus();
        Message message = new Message();
        message.obj = resultStatus;
        handler.sendMessage(message);
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_PAY:
                // 钱包支付
                if (currentPayType == TJPayUtils.PAY_TAPE_WELLAT) {
                    BuyCarUtils.intent2FoodActivity(this);
                    ToastUtils.showShort("支付成功");

                    BuyCarMapUtils.getCurBuyCarUtils().clearBuyCar();
                    UserUtils.getInstance().doUserRefreshNetUserInfor();

                } else {
                    order = (Order) response.getData();
                    // 支付宝和微信支付
//                    if (order.getOrderState() != 20) {
                        if (currentPayType == TJPayUtils.PAY_TAPE_ALIPAY) {
                            TJPayUtils.aliPay(order.getPayInfo(), this, this);
                        } else {
                            TJPayUtils.wxPayOrder(order, this);
                        }
//                    }
//
//                    // 优惠券支付
//                    else {
//
//                        BuyCarUtils.intent2FoodActivity(this);
//                        overridePendingTransition(R.anim.anim_no, R.anim.slide_out_buttom);
//
//                        ToastUtils.showShort("支付成功");
//                        BuyCarMapUtils.getCurBuyCarUtils().clearBuyCar();
//
//                    }
                }

                break;

            case TJRequestTag.TAG_USER_WELLAT_IS_AUTH: // 是否设置过支付密码

                ActiveInfo activeInfo = (ActiveInfo) response.getData();

                if (activeInfo.getActiveState() == 1) {

                    if (pswPayDialog == null) {
                        pswPayDialog = new PswPayDialog(this);
                    }

                    pswPayDialog.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {

                        @Override
                        public void onTextChanged(String psw) {
                        }

                        @Override
                        public void onInputFinish(String psw) {
                            pswPayDialog.dismiss();
                            loadingDialog.setmMessageText("正在支付").show();
                            payPsw = psw;
                            doPay();
//                            TJHttpUtil.getInstance(OrderPayActivity.this).userWalletPayPswAuthReq(psw);
                        }
                    });
                    pswPayDialog.show();
                } else {
                    intent2Activity(UserWalletNewPassWordPayActivity.class);
                }

                break;

            case TJRequestTag.TAG_USER_WELLAT_AUTH:

                if ((Boolean) response.getData()) {
                    doPay();
                } else {
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            handlerUI.sendEmptyMessage(0);
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(timerTask, 500);
                }

                break;

            case TJRequestTag.TAG_USER_INFO:
                User userResult = (User) response.getData();
                UserUtils.getInstance().refreshUserInfor(userResult);
                tvNumWellatFoodPay.setText("余额：￥" + userResult.getBalance() + "");

                if (sumPrice > userResult.getBalance()) {
                    setPayType(TJPayUtils.PAY_TAPE_ALIPAY);
                } else {
                    setPayType(TJPayUtils.PAY_TAPE_WELLAT);
                }
                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onFinish(int requestTag) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
