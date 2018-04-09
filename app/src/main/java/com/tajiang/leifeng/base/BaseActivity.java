package com.tajiang.leifeng.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.UserLoginActivity;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.utils.ImageUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.StatusBarUtil;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;
import com.tajiang.leifeng.view.dialog.ProgressDialog;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener ,HttpResponseListener{

    protected final String TAG = this.getClass().getSimpleName();

    private View contentView;

    private ViewStub leftRectNav;
    private ViewStub titleRectNav;
    private ViewStub rightRectNav;

    private TextView mTxtName;

    private LinearLayout rootNav;

    private FrameLayout rootContent;
    private FrameLayout rectNavRight;

    private FrameLayout leftNav;

    private LinearLayout ll_root;

    private OnClickNavRightListener onClickNavRightListener;

    private ProgressDialog animLoadDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        super.setContentView(R.layout.layout_base);
        TJApp.getIns().addActivity(this);
        initStateBar();

        titleRectNav = (ViewStub) findViewById(R.id.common_nav_title);
        leftRectNav = (ViewStub) findViewById(R.id.common_nav_left);
        rightRectNav = (ViewStub) findViewById(R.id.common_nav_right);

        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        rootNav = (LinearLayout) findViewById(R.id.common_nav);
        rootContent = (FrameLayout) findViewById(R.id.common_content_root);

        initTopBar();
        initLayout();
        initView();
        initAdapter();
        initDates();
        initListener();

    }

    protected void initStateBar() {
        // 设置状态栏的颜色
        StatusBarUtil.setColor(this, getResources().getColor(R.color.common_title_white), 0);
    }

    protected void initListener() {

    }

    protected void initAdapter() {

    }

    protected void initDates() {

    }

    protected abstract void initTopBar();

    protected abstract void initLayout();

    protected abstract void initView();

    protected void unableNav() {
        rootNav.setVisibility(View.GONE);
    }

    protected void setTitle(String title) {
        titleRectNav.setLayoutResource(R.layout.layout_content_top_title_normal);
        mTxtName = (TextView) titleRectNav.inflate();
        mTxtName.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        String textTitle = getResources().getString(titleId);
        titleRectNav.setLayoutResource(R.layout.layout_content_top_title_normal);
        mTxtName = (TextView) titleRectNav.inflate();
        mTxtName.setText(textTitle);
    }

    @Override
    public void setContentView(int layoutResID) {
        contentView = getLayoutInflater().inflate(layoutResID, null);
        rootContent.addView(contentView);
    }

    public <T extends View> T findViewByID(int id) {
        return (T) contentView.findViewById(id);
    }


    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    @Override
    public void setContentView(View view) {
        rootContent.addView(view);
    }

    protected void enableNavLeftImage() {
        leftRectNav.setLayoutResource(R.layout.layout_content_top_nav_left);
        leftNav = (FrameLayout) leftRectNav.inflate();
        leftNav.setOnClickListener(this);
    }

    protected void enableNavLeftText() {
        leftRectNav.setLayoutResource(R.layout.layout_content_top_nav_left_text);
        TextView leftNavTextView = (TextView) leftRectNav.inflate();
        leftNavTextView.setText("返回");
        leftNavTextView.setOnClickListener(this);
    }

    protected void enableNavRightText(String textRight) {

        if (rectNavRight == null) {
            rightRectNav.setLayoutResource(R.layout.layout_content_top_nav_right_text);
            rectNavRight = (FrameLayout) rightRectNav.inflate();
        }

        TextView textNavRight = (TextView) rectNavRight.findViewById(R.id.tv_navRight);
        textNavRight.setText(textRight);
        textNavRight.setOnClickListener(this);
    }

    protected void enableNavRightImage(int resId) {
        rightRectNav.setLayoutResource(R.layout.layout_content_top_nav_right_img);
        FrameLayout fl_imgNavRight = (FrameLayout) rightRectNav.inflate();
        ImageView iv_imgNavRight = (ImageView) fl_imgNavRight.findViewById(R.id.iv_imgNavRight);
        iv_imgNavRight.setImageResource(resId);
        fl_imgNavRight.setOnClickListener(this);
    }

    protected void enableNavRightView(int resId) {
        rightRectNav.setLayoutResource(resId);
        FrameLayout fl_imgNavRight = (FrameLayout) rightRectNav.inflate();
        fl_imgNavRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_navRight:
                onClickNavRight();
                break;
            case R.id.fl_imgNavLeft:
                onBackPressed();
                break;
            case R.id.fl_imgNavRight:
                onClickNavRight();
                break;
        }
    }

    protected void onClickNavRight() {

    }

    public interface OnClickNavRightListener {
        void onClickNavRight();
    }

    public void setOnClickNavRightListener(OnClickNavRightListener onClickNavRightListener) {
        this.onClickNavRightListener = onClickNavRightListener;
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(BaseResponse response, int requestTag) {
        switch (response.getErrorCode()) {
            case TJHttpUtil.CODE_RESPONSE_NEED_LOGIN:
                Intent intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
                break;
            default:
                ToastUtils.showShort(response.getMoreInfo());
                break;
        }
    }

    @Override
    public void onFailed(BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onResume() {
        super.onResume();
        // 友盟分析
        //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)
        MobclickAgent.onPageStart("SplashScreen");
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        // 友盟分析
        // (仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
        MobclickAgent.onPageEnd("SplashScreen");
        MobclickAgent.onPause(this);
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
        TJApp.getIns().finishActivity(this);
    }

    protected TJApp getApp() {
        return TJApp.getIns();
    }

    protected void intent2Activity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void logV(String msg) {
        LogUtils.v(TAG, msg);
    }

    protected void logV(int msg) {
        LogUtils.v(TAG, msg + "");
    }

    protected void logD(String msg) {
        LogUtils.d(TAG, msg);
    }

    protected void logD(int msg) {
        LogUtils.d(TAG, msg + "");
    }

    protected void logI(String msg) {
        LogUtils.i(TAG, msg);
    }

    protected void logI(int msg) {
        LogUtils.i(TAG, msg + "");
    }

    protected void logW(String msg) {
        LogUtils.w(TAG, msg);
    }

    protected void logW(int msg) {
        LogUtils.w(TAG, msg + "");
    }

    protected void logE(String msg) {
        LogUtils.e(TAG, msg);
    }

    protected void logE(int msg) {
        LogUtils.e(TAG, msg + "");
    }

    protected void showToast(CharSequence msg) {
        ToastUtils.showShort(msg);
    }

    protected void loadImage(ImageView imageView, String imgUrl, int placeImgResId) {
        ImageUtils.loadImage(this, imageView, imgUrl, placeImgResId);
    }

    protected void loadImage(ImageView imageView, String imgUrl) {
        ImageUtils.loadImage(this, imageView, imgUrl, null);
    }

    protected void showLoading() {
        if (animLoadDialog == null) {
            animLoadDialog = new ProgressDialog(this);
        }
        animLoadDialog.show();
    }

    protected void dismissLoading() {
        if (animLoadDialog != null) {
            animLoadDialog.dismiss();
        }
    }

    protected Context getContext() {
        return this;
    }

}
