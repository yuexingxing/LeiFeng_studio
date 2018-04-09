package com.tajiang.leifeng.activity;

import android.view.View;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.utils.UserUtils;

public class UserSetActivity extends BaseActivity implements HttpResponseListener {

	private View view_pswChangeUserInfor;
	private View view_aboutUserSet;
	private View view_companyUserSet;
	private View view_clearCacheUserSet;
	private View view_imageQualityUserSet;

	private TextView tv_cache_size;
	
	private View btn_loginOutUserSet;

	@Override
	protected void initTopBar() {
		setTitle("设置");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_set);
	}

	@Override
	protected void initView() {
		view_aboutUserSet = findViewById(R.id.view_aboutUserSet);
		view_companyUserSet = findViewById(R.id.view_companyUserSet);
//		view_clearCacheUserSet = findViewById(R.id.view_clearCacheUserSet);
		view_imageQualityUserSet = findViewById(R.id.view_imageQualityUserSet);
		view_pswChangeUserInfor = findViewById(R.id.view_pswChangeUserInfor);

//		tv_cache_size = (TextView) findViewById(R.id.tv_cache_size);
		btn_loginOutUserSet = (View) findViewById(R.id.btn_loginOutUserSet);
	}

	@Override
	protected void initDates() {
		super.initDates();
//		try {
//			tv_cache_size.setText(DataCleanManager.getCacheSize(this));
//		} catch (Exception e) {
//			LogUtils.e(e.getMessage());
//		}
	}

	@Override
	protected void initListener() {
		super.initListener();
		view_aboutUserSet.setOnClickListener(this);
		view_companyUserSet.setOnClickListener(this);
//		view_clearCacheUserSet.setOnClickListener(this);
		view_imageQualityUserSet.setOnClickListener(this);
		view_pswChangeUserInfor.setOnClickListener(this);
		
		btn_loginOutUserSet.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
			case R.id.view_pswChangeUserInfor:
				intent2Activity(UserInforChangePasswordActivity.class);
				break;
			case R.id.view_aboutUserSet:
				intent2Activity(UserAboutUsActivity.class);
				break;
			case R.id.view_companyUserSet:
				intent2Activity(UserSetBusinessCooperationActivity.class);
				break;
//			case R.id.view_clearCacheUserSet:
//				//TODO......清理缓存........
//				new CleanCacheDialog(this,tv_cache_size).show();;
//				break;
			case R.id.btn_loginOutUserSet:
				TJHttpUtil.getInstance(this).doLogout();
				break;
		}
	}

	@Override
	public void onSuccess(BaseResponse response, int requestTag) {
		switch (requestTag) {
			case TJRequestTag.TAG_USER_LOGOUT:
				UserUtils.getInstance().clearUserInfor();
				intent2Activity(UserLoginActivity.class);
				onBackPressed();
				break;
		}
	}

	@Override
	public void onFailed(BaseResponse response, int requestTag) {
		super.onFailed(response, requestTag);
	}

	@Override
	public void onFinish(int requestTag) {
		super.onFinish(requestTag);
	}
}
