package com.tajiang.leifeng.activity;

import android.view.View;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;

public class UserPassVerifySuccessActivity extends BaseActivity{
	
	private View view_backHomeOrderRap;

	@Override
	protected void initTopBar() {
		setTitle("完善个人信息");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_pass_success);
	}

	@Override
	protected void initView() {
		view_backHomeOrderRap = findViewById(R.id.view_backHomeOrderRap);
	}
	
	@Override
	protected void initListener() {
		super.initListener();
		view_backHomeOrderRap.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		onBackPressed();
		
	}

}
