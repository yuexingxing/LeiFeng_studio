package com.tajiang.leifeng.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;

/**
 * Created by wushu on 2017-02-06.
 */
public class ApplySuccessActivity extends BaseActivity{
    private TextView apply_success_time_tv;
    private LinearLayout apply_text_layout;
    private Object data;

    @Override
    protected void initTopBar() {
        setTitle("申请成功");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_apply_success);
    }

    @Override
    protected void initView() {
        apply_success_time_tv = (TextView) findViewById(R.id.apply_success_time_tv);
        apply_text_layout = (LinearLayout) findViewById(R.id.apply_text_layout);
    }

    @Override
    protected void initListener() {
        super.initListener();
        apply_success_time_tv.setOnClickListener(this);
        apply_text_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.apply_text_layout:
                finish();
                break;
        }
    }

    /**
     * @param flag
     */
    private void initData(boolean flag) {
        getData();
    }

    public void getData() {

    }
}
