package com.tajiang.leifeng.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;

/**
 * Created by wushu on 2017-02-07.
 */
public class DrawbackDetailActivity extends BaseActivity{
    private TextView drawback_money;
    private TextView drawback_pay_mode;
    private TextView tv_submit_apply_time;
    private TextView tv_lf_applying_time;
    private TextView tv_access_time;
    private TextView tv_apply_success_time;
    private ImageView iv_lf_applying;
    private View line_lf_applying;
    private ImageView iv_access;
    private View line_access;
    private ImageView iv_apply_success;
    @Override
    protected void initTopBar() {
        setTitle("退款详情");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_drawback_detail);
    }

    @Override
    protected void initView() {
        drawback_money = (TextView) findViewById(R.id.drawback_money);
        drawback_pay_mode = (TextView) findViewById(R.id.drawback_pay_mode);
        tv_submit_apply_time = (TextView) findViewById(R.id.tv_submit_apply_time);
        tv_lf_applying_time = (TextView) findViewById(R.id.tv_lf_applying_time);
        tv_access_time = (TextView) findViewById(R.id.tv_access_time);
        tv_apply_success_time = (TextView) findViewById(R.id.tv_apply_success_time);
        iv_lf_applying = (ImageView) findViewById(R.id.iv_lf_applying);
        line_lf_applying = findViewById(R.id.line_lf_applying);
        iv_access = (ImageView) findViewById(R.id.iv_access);
        line_access = findViewById(R.id.line_access);
        iv_apply_success = (ImageView) findViewById(R.id.iv_apply_success);
    }

    @Override
    protected void initDates() {
        super.initDates();

    }
}
