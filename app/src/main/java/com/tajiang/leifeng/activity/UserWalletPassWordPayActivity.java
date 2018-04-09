package com.tajiang.leifeng.activity;

import android.view.View;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;

/**
 * Created by 12154 on 2016/2/19.
 */
public class UserWalletPassWordPayActivity extends BaseActivity  {

    private View rectModifyPswPay;
    private View rectResetPswPay;

    @Override
    protected void initTopBar() {
        setTitle("设置支付密码");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_wallet_psw_pay);
    }

    @Override
    protected void initView() {
        rectModifyPswPay = findViewById(R.id.rectModifyPswPay);
        rectResetPswPay = findViewById(R.id.rectResetPswPay);
    }

    @Override
    protected void initDates() {
        super.initDates();
    }

    @Override
    protected void initListener() {
        super.initListener();
        rectModifyPswPay.setOnClickListener(this);
        rectResetPswPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.rectModifyPswPay:
                intent2Activity(UserWalletModifyPassWordPayActivity.class);
                break;
            case R.id.rectResetPswPay:
                intent2Activity(UserWalletResetPassWordPayActivity.class);
                break;
        }

    }
}
