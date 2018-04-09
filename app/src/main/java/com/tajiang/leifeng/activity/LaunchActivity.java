package com.tajiang.leifeng.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.TokenInfo;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.AppUtils;
import com.tajiang.leifeng.utils.GsonObjUtil;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.utils.Utils;

public class LaunchActivity extends Activity implements HttpResponseListener {
	
	private TJApp tjApp;
	private TextView tvVersion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		tvVersion = (TextView) findViewById(R.id.tv_version);
		tvVersion.setText("版本号: " + AppUtils.instance(TJApp.getIns()).getVersionName());
		
		tjApp = (TJApp) getApplication();
		
		if(!tjApp.isRun()){
			tjApp.setRun(true);
		}

		if(SharedPreferencesUtils.contains(this, "FisrtLaunch")){
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					goHomeActivity();
				}
			}; 

			Timer timer = new Timer();
			timer.schedule(timerTask, 1000);
		}else{
			
			SharedPreferencesUtils.put(this, "FisrtLaunch", "FisrtLaunch");
			
			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
						Intent intent = new Intent(LaunchActivity.this , GuiderActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.anim_no, R.anim.anim_no);
						finish();
				}
			}; 
			Timer timer = new Timer();
			timer.schedule(timerTask, 2000);
		}
		
//		if(UserUtils.getInstance().isLogin()){
//			// 获取百度推送 pushDoGetChannelId
//			String channelId = (String) SharedPreferencesUtils.get(this, SharedPreferencesUtils.CHANNEL_ID, "");
//
//			if(!TextUtils.isEmpty(channelId)){
//				TJHttpUtils.getInstance(this).pushDoGetChannelId(channelId);
//			}
//
//			// 刷新用户信息
//			UserUtils.getInstance().doUserRefreshNetUserInfor();
//
//		}
		
	}

	private void goHomeActivity() {
		String tokenInfoStr = (String) SharedPreferencesUtils.get(TJApp.getContext(), SharedPreferencesUtils.USER_TOKENINFO, "");
		TokenInfo tokenInfo= GsonObjUtil.getGsonInstance().fromJson(tokenInfoStr, TokenInfo.class);
		String passWord = (String) SharedPreferencesUtils.get(this, SharedPreferencesUtils.LOGIN_USER_PSW, "");
		String userName = (String) SharedPreferencesUtils.get(this, SharedPreferencesUtils.LOGIN_USER_NAME, "");

		if (!"".equals(tokenInfoStr)) {
			TJApp.token = tokenInfo.getToken();
			TJApp.timeout = tokenInfo.getTimeout();
			//登录完成获取用户信息
			TJHttpUtil.getInstance(this).userDoGetInfor();
//			String mClientId = (String) SharedPreferencesUtils.get(this, SharedPreferencesUtils.CHANNEL_ID, "");
//			TJHttpUtil.getInstance(this).doLogin(userName, passWord, mClientId, Utils.getMIME(this));
		} else {
			Intent intent = new Intent(LaunchActivity.this , HomeActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
	}

	public void onBackPressed() {
		finish();
	}

	@Override
	public void onStart(int requestTag) {
		
	}

	@Override
	public void onSuccess(BaseResponse response, int requestTag) {
		switch(requestTag) {
			case TJRequestTag.TAG_USER_LOGIN:
				TokenInfo info = (TokenInfo) response.getData();
				TJApp.token = info.getToken();
				TJApp.timeout = info.getTimeout();

				//保存用户登录token
				SharedPreferencesUtils.put(this, SharedPreferencesUtils.USER_TOKENINFO, GsonObjUtil.getGsonInstance().toJson(info));

				//登录完成获取用户信息
				TJHttpUtil.getInstance(this).userDoGetInfor();
				break;
			case TJRequestTag.TAG_USER_INFO:
				User user2 = (User) response.getData();
				UserUtils.getInstance().saveUserInfor(GsonObjUtil.getGsonInstance().toJson(user2));
				UserUtils.getInstance().initUser();
				Intent intent = new Intent(LaunchActivity.this , HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.anim_no, R.anim.anim_no);
				finish();
				break;
		}
	}

	@Override
	public void onFailed(BaseResponse response, int requestTag) {
		
	}

	@Override
	public void onFinish(int requestTag) {

	}
}
