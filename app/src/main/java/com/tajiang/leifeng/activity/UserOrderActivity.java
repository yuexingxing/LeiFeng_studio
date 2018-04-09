package com.tajiang.leifeng.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.base.FragmentController;
import com.tajiang.leifeng.fragment.UserOrderPassMeFragment;

public class UserOrderActivity extends BaseActivity {
	
	private UserOrderPassMeFragment userOrderPassAwayFragment;
	
	private List<Fragment> fragmentList = new ArrayList<Fragment>();

	private FragmentController fragmentController;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initFragment();
	}
	
	@Override
	protected void initTopBar() {
		setTitle("历史订单");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_order);
	}

	@Override
	protected void initView() {
	}
	
	@Override
	protected void initAdapter() {
	}
	
	@Override
	protected void initDates() {


	}
	
	private void initFragment() {
		userOrderPassAwayFragment = new UserOrderPassMeFragment();

		fragmentList.add(userOrderPassAwayFragment);

		fragmentController = new FragmentController(this, R.id.flContentOrder, fragmentList);
	}

}
