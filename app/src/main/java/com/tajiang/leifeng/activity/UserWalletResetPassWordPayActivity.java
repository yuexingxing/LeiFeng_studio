package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 12154 on 2016/2/22.
 */
public class UserWalletResetPassWordPayActivity extends BaseActivity implements HttpResponseListener {

    private TextView tv_getCodeResetPsd;

    private EditText etPhoneNum;
    private EditText etPhoneCode;

    private Button btnSubmitCode;

    private String phone;
    private String code;
    private int currentTime;


    @Override
    protected void initTopBar() {
        setTitle("重置支付密码");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_wallet_reset_psw_pay);
    }

    @Override
    protected void initView() {

        tv_getCodeResetPsd = (TextView) findViewById(R.id.tv_getCodeResetPsd);

        etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
        etPhoneCode = (EditText) findViewById(R.id.etPhoneCode);

        btnSubmitCode = (Button) findViewById(R.id.btnSubmitCode);

    }

    @Override
    protected void initListener() {
        super.initListener();

        tv_getCodeResetPsd.setOnClickListener(this);
        btnSubmitCode.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.tv_getCodeResetPsd:
                if (checkPhone()) {
                    startCount();
                    TJHttpUtil.getInstance(this).getRegisterCode(TJCst.PAY_PWD_CODE, phone);
                }

                break;
            case R.id.btnSubmitCode:

                if (checkPhone() && checkCode()) {
                    TJHttpUtil.getInstance(this).userWalletPayPswCode(phone, code);
                }

                break;
        }
    }

    private boolean checkPhone() {
        phone = etPhoneNum.getText().toString().trim();

        if (phone.length() < 11) {
            ToastUtils.showShort("您输入的号码不足11位数字");
            return false;
        }
        return true;
    }

    private boolean checkCode() {
        code = etPhoneCode.getText().toString().trim();

        if (phone.length() < 11) {
            ToastUtils.showShort("您输入的号码不足11位数字");
            return false;
        }

        return true;
    }


    /**
     * 验证码认证成功
     */
    private void doCodeAuthSuccess() {
        Intent intent = new Intent(this, UserWalletNewPassWordPayActivity.class);
        intent.putExtra("ModifyPassWord", true);
        startActivity(intent);

        Timer timer = new Timer();
        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        };
        timer.schedule(timerTask2, 800);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (currentTime != 0) {
                tv_getCodeResetPsd.setText(currentTime-- + "s");
            } else {
                tv_getCodeResetPsd.setText("重新获取");
                tv_getCodeResetPsd.setTextColor(getResources().getColor(R.color.com_green));
                tv_getCodeResetPsd.setClickable(true);
            }
        }
    };

    /**
     * 开始倒计时
     */
    public void startCount() {

        /** 倒计时总时间 */
        int sumTime = 60;

        // 点击的时候重置时间，文字颜色，背景，开始计时
        /** 当前显示的时间 */
        currentTime = sumTime;

        tv_getCodeResetPsd.setTextColor(getResources().getColor(R.color.text_black_2));

        // 启动计时任务
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1000);

        // 计时期间不可点击
        tv_getCodeResetPsd.setClickable(false);
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_WELLAT_CODE:

                if ((Boolean) response.getData()) {
                    doCodeAuthSuccess();
                } else {
                    showToast("验证失败，请重新获取");
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
