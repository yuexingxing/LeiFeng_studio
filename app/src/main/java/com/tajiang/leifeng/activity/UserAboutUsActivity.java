package com.tajiang.leifeng.activity;

import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.utils.AppUtils;

public class UserAboutUsActivity extends BaseActivity{
	
	private TextView tv_versionApp;

	@Override
	protected void initTopBar() {
		setTitle("关于我们");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_about_us);
	}

	@Override
	protected void initView() {
		tv_versionApp = (TextView) findViewById(R.id.tv_versionApp);
	}
	
	@Override
	protected void initDates() {
		super.initDates();
		
		tv_versionApp.setText("V" + AppUtils.instance(TJApp.getIns()).getVersionName());
		
	}

}
