package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Action;
import com.tajiang.leifeng.model.PredepositOrder;
import com.tajiang.leifeng.pay.AliPayResult;
import com.tajiang.leifeng.pay.IPayCallBack;
import com.tajiang.leifeng.pay.TJPayUtils;
import com.tajiang.leifeng.utils.ToastUtils;

import java.text.DecimalFormat;

/**
 * Created by 12154 on 2016/1/15.
 */
public class UserWalletAddPayActivity extends BaseActivity implements IPayCallBack, HttpResponseListener {

    private Action action;

    private TextView tvAddPay;
    private TextView tvNumAddPay;
    private TextView tvActionText;

    private View view_weixinFoodPay;
    private View view_zhifubaoFoodPay;

    private ImageView iv_zhifubaoRadio;
    private ImageView iv_weixinRadio;

    private Button btnDoPayAdd;

    /**
     * 支付方式默认走微信
     */
    private int currentPayType = TJPayUtils.PAY_TAPE_ALIPAY;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            String resultStatus = (String) msg.obj;

            //支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                ToastUtils.show("支付成功", Toast.LENGTH_SHORT);

                Intent intent = new Intent(UserWalletAddPayActivity.this , UserWalletActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_no, R.anim.slide_out_buttom);
                UserWalletAddPayActivity.this.finish();

            } else {
                // 判断resultStatus 为非“9000”则代表可能支付失败
                // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                if (TextUtils.equals(resultStatus, "8000")) {
                    ToastUtils.show("支付结果确认中", Toast.LENGTH_SHORT);
                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    ToastUtils.show("取消支付", Toast.LENGTH_SHORT);
                }
            }
        }

    };


    @Override
    protected void initTopBar() {
        setTitle("充值");
        enableNavLeftImage();

        action = (Action) getIntent().getSerializableExtra("Action");
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_wallet_add_pay);
    }

    @Override
    protected void initView() {

        view_weixinFoodPay = findViewById(R.id.view_weixinFoodPay);
        view_zhifubaoFoodPay = findViewById(R.id.view_zhifubaoFoodPay);

        tvAddPay = (TextView) findViewById(R.id.tvAddPay);
        tvNumAddPay = (TextView) findViewById(R.id.tvNumAddPay);
        tvActionText = (TextView) findViewById(R.id.tvActionText);

        iv_zhifubaoRadio = (ImageView) findViewById(R.id.iv_zhifubaoRadio);
        iv_weixinRadio = (ImageView) findViewById(R.id.iv_weixinRadio);

        btnDoPayAdd = (Button) findViewById(R.id.btnDoPayAdd);

    }

    @Override
    protected void initListener() {
        super.initListener();

        view_weixinFoodPay.setOnClickListener(this);
        view_zhifubaoFoodPay.setOnClickListener(this);

        btnDoPayAdd.setOnClickListener(this);
    }

    @Override
    protected void initDates() {
        super.initDates();

        tvActionText.setText(action.getName());

        tvAddPay.setText("￥" + getMoneyString(action.getPreAmountStart()) + "");
        tvNumAddPay.setText("充值金额：" + getMoneyString(action.getPreAmountStart() + action.getAwardAmount()) + "");

    }

    public String getMoneyString(Double num) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        return df.format(num);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);


        switch (view.getId()) {
            case R.id.btnDoPayAdd:

                TJHttpUtil.getInstance(this).doWellatPredeposit(currentPayType, action.getPreAmountStart());

                break;
            case R.id.view_zhifubaoFoodPay:

                currentPayType = TJPayUtils.PAY_TAPE_ALIPAY;

                iv_zhifubaoRadio.setImageResource(R.drawable.icon_radio_selected);
                iv_weixinRadio.setImageResource(R.drawable.icon_radio_normal);

                break;
            case R.id.view_weixinFoodPay:
                currentPayType = TJPayUtils.PAY_TAPE_WEICHATPAY;

                iv_zhifubaoRadio.setImageResource(R.drawable.icon_radio_normal);
                iv_weixinRadio.setImageResource(R.drawable.icon_radio_selected);

                break;
        }

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
            case TJRequestTag.TAG_WELLAT_PREDEPOSIT:
                PredepositOrder predepositOrder = (PredepositOrder) response.getData();

                if (currentPayType == TJPayUtils.PAY_TAPE_ALIPAY) {
                    TJPayUtils.aliPaySimple(this, this, predepositOrder.getPayInfo());
                } else {
                    TJApp.getIns().setTypeWeChatPay(3);
                    TJPayUtils.wxPayRecharge(predepositOrder, this);
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
