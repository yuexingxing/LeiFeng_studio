package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.BaseResponse;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 12154 on 2016/2/22.
 */
public class UserWalletModifyPassWordPayActivity extends BaseActivity implements HttpResponseListener,
        GridPasswordView.OnPasswordChangedListener {

    private TextView tvDescTextModifyPswPay;

    private GridPasswordView gpvModifyPswPay;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0: // 输入支付密码错误
                    showToast("密码错误");
                    gpvModifyPswPay.clearPassword();
                    break;
                case 1:

                    break;
            }
        }
    };

    @Override
    protected void initTopBar() {
        setTitle("修改密码");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_wallet_modify_psw_pay);
    }

    @Override
    protected void initView() {
        tvDescTextModifyPswPay = (TextView) findViewById(R.id.tvDescTextModifyPswPay);

        gpvModifyPswPay = (GridPasswordView) findViewById(R.id.gpvModifyPswPay);
    }

    @Override
    protected void initListener() {
        super.initListener();

        gpvModifyPswPay.setOnPasswordChangedListener(this);

    }

    @Override
    protected void initDates() {
        super.initDates();

    }

    @Override
    public void onTextChanged(String psw) {
    }

    @Override
    public void onInputFinish(final String psw) {
//        TJHttpUtil.getInstance(UserWalletModifyPassWordPayActivity.this).userWalletPayPswAuthReq(psw);
        Intent intent = new Intent(UserWalletModifyPassWordPayActivity.this, UserWalletNewPassWordPayActivity.class);
        intent.putExtra("ModifyPassWord", true);
        intent.putExtra("psw", psw);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(final BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_WELLAT_AUTH:
                TimerTask timerTask1 = new TimerTask() {
                    @Override
                    public void run() {
                        if ((Boolean) response.getData()) {

                            Intent intent = new Intent(UserWalletModifyPassWordPayActivity.this, UserWalletNewPassWordPayActivity.class);
                            intent.putExtra("ModifyPassWord", true);
                            startActivity(intent);

                        } else {
                            handler.sendEmptyMessage(0);
                        }
                    }
                };

                Timer timer = new Timer();
                timer.schedule(timerTask1, 300);

                // 跳转到修改界面后销毁这个界面
                if ((Boolean) response.getData()) {
                    TimerTask timerTask2 = new TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    };
                    timer.schedule(timerTask2, 800);
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
