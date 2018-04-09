package com.tajiang.leifeng.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.view.dialog.CDialog;

import java.util.Timer;
import java.util.TimerTask;

public class UserWalletNewPassWordPayActivity extends BaseActivity implements HttpResponseListener, GridPasswordView.OnPasswordChangedListener {

    private TextView tvDescTextNewPswPay;

    private GridPasswordView gpvNewPswPay;

    private boolean isFirstInputPsw = true;

    private boolean isModifyPassWord = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    gpvNewPswPay.clearPassword();
                    tvDescTextNewPswPay.setText("请再次输入支付密码");
                    break;
                case 1:
                    gpvNewPswPay.clearPassword();
                    break;
            }


        }
    };

    private String psdPay;
    private String mPsw;

    @Override
    protected void initTopBar() {
        isModifyPassWord = getIntent().getBooleanExtra("ModifyPassWord", false);
        mPsw = getIntent().getStringExtra("psw");


        setTitle(isModifyPassWord ? "修改支付密码" : "设置支付密码");
        enableNavLeftImage();

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_wallet_new_psw_pay);
    }

    @Override
    protected void initView() {
        tvDescTextNewPswPay = (TextView) findViewById(R.id.tvDescTextNewPswPay);
        gpvNewPswPay = (GridPasswordView) findViewById(R.id.gpvNewPswPay);
    }

    @Override
    protected void initDates() {
        super.initDates();

        showInput();

        tvDescTextNewPswPay.setText(isModifyPassWord ? "请设置新的支付密码" : "首次使用钱包请设置支付密码");

    }

    private void showInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void initListener() {
        super.initListener();

        gpvNewPswPay.setOnPasswordChangedListener(this);
    }

    @Override
    public void onTextChanged(String psw) {

    }

    @Override
    public void onInputFinish(final String psw) {

        if (isFirstInputPsw) {

            psdPay = psw;

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            };

            Timer timer = new Timer();
            timer.schedule(timerTask, 300);

            isFirstInputPsw = false;

        } else {

            if (psdPay.equals(psw)) {

                if(isModifyPassWord){
                    TJHttpUtil.getInstance(UserWalletNewPassWordPayActivity.this).userWalletPayPswModifly(mPsw, psw);
                }else {
                    TJHttpUtil.getInstance(UserWalletNewPassWordPayActivity.this).userWalletPayPswCreate(psw);
                }

            } else {

                showToast("两次输入的密码不一致");

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                };

                Timer timer = new Timer();
                timer.schedule(timerTask, 300);

            }

        }
    }

    /**
     * 创建密码成功
     */
    private void creatPsdSucced() {
        CDialog cDialog = new CDialog(this, R.layout.dialog_psw_pay_success);
        cDialog.show();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 1000);
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_WELLAT_CREATE:
                // 创建密码成功
                creatPsdSucced();

                break;
            case TJRequestTag.TAG_USER_WELLAT_MODIFLY:

                // 创建密码成功
                creatPsdSucced();

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
