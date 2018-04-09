package com.tajiang.leifeng.activity;

import android.view.View;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.view.dialog.CallDialog;

public class UserServiceCenterActivity extends BaseActivity {

    private View rect_adviceServiceCenter;
    private View rectCallServiceCenter;
    private View rect_questionServiceCenter;

    @Override
    protected void initTopBar() {
        setTitle("客服中心");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_service_center);
    }

    @Override
    protected void initView() {
        rect_adviceServiceCenter = findViewById(R.id.rect_adviceServiceCenter);
        rectCallServiceCenter = findViewById(R.id.rectCallServiceCenter);
        rect_questionServiceCenter = findViewById(R.id.rect_questionServiceCenter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        rect_adviceServiceCenter.setOnClickListener(this);
        rectCallServiceCenter.setOnClickListener(this);
        rect_questionServiceCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.rect_adviceServiceCenter:
                intent2Activity(UserAdviseActivity.class);
                break;
            case R.id.rectCallServiceCenter:
                CallDialog callDialog = new CallDialog(this, TJCst.CUSTOMER_SERVICES_PHONE);
                callDialog.show();
                break;

            case R.id.rect_questionServiceCenter:
                intent2Activity(WebQuestionActivity.class);
                break;
        }
    }
}

