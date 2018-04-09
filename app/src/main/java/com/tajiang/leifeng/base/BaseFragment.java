package com.tajiang.leifeng.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.utils.ImageUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

@SuppressLint("InflateParams")
public abstract class BaseFragment extends Fragment implements OnClickListener, HttpResponseListener {

    private final String TAG = this.getClass().getSimpleName();

    private FrameLayout rootLayout;

    private LayoutInflater inflater;

    private View contentView;

    private BaseActivity activity;

    private TJApp tjApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate");

        activity = (BaseActivity) getActivity();
        tjApp = activity.getApp();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtils.d(TAG, "onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;

        rootLayout = (FrameLayout) inflater.inflate(R.layout.fragment_base, null);

        initLayout();
        initView();
        initAdapter();
        initData();
        initListener();

        return rootLayout;
    }


    protected <T extends View> T findViewById(int id) {
        return (T) contentView.findViewById(id);
    }

    protected void setContentView(int resLayoutId) {
        contentView = inflater.inflate(resLayoutId, null);
        rootLayout.addView(contentView);
    }

    protected abstract void initLayout();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initAdapter();

    protected abstract void initData();

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(BaseResponse response, int requestTag) {

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
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    protected TJApp getTjApp() {
        return tjApp;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    protected void intent2Activity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    protected View getRootLayoutView() {
        return rootLayout;
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
        ImageUtils.loadImage(getContext() , imageView, imgUrl, placeImgResId);
    }

    protected void loadImage(ImageView imageView, String imgUrl) {
        ImageUtils.loadImage(getContext() , imageView, imgUrl, null);
    }

    public Context getContext() {
        return getActivity();
    }

    public void reFreshCurrentPageData() {

    }

}
