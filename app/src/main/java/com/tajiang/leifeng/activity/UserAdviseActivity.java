package com.tajiang.leifeng.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.model.School;

public class UserAdviseActivity extends BaseActivity implements HttpResponseListener {

    private EditText etContentFeedBack;

    private Button btnSubmitFeedBack;

    private String content;

    private School school;

    @Override
    protected void initTopBar() {
        setTitle("意见反馈");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_advice);
    }

    @Override
    protected void initView() {
        etContentFeedBack = (EditText) findViewById(R.id.etContentFeedBack);

        btnSubmitFeedBack = (Button) findViewById(R.id.btnSubmitFeedBack);

    }

    @Override
    protected void initDates() {
        super.initDates();

        school = TJApp.getIns().getSchool();

    }

    @Override
    protected void initListener() {
        super.initListener();
        btnSubmitFeedBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btnSubmitFeedBack:

                if (checkFeedBackContent()) {
                    TJHttpUtil.getInstance(this).doUserFeedBack(content);
                }

                break;
        }

    }

    private boolean checkFeedBackContent() {
        content = etContentFeedBack.getText().toString();

        if(TextUtils.isEmpty(content)){
            showToast("请填写反馈内容");
            return false;
        }

        return true;
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_FEED_BACK:
                    showToast("反馈成功");
                    finish();
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
