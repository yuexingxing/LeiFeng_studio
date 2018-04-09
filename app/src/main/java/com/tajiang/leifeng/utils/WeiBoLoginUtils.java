package com.tajiang.leifeng.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.constant.TJCst;

public class WeiBoLoginUtils {
	
	private Context context;
	
	 private AuthInfo mAuthInfo;

	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
	
	private OnWeiBoLoginListener onWeiBoLoginListener;
	
	public WeiBoLoginUtils(Context context) {
		
		this.context = context;
		 // 创建微博实例
        //mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
        mAuthInfo = new AuthInfo(context, TJCst.WEIBO_APP_KEY, TJCst.WEIBO_REDIRECT_URL, TJCst.WEIBO_SCOPE);
        mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
		
	}
	
	public SsoHandler login() {
		 mSsoHandler.authorize(new AuthListener());
		 return mSsoHandler;
	}
	
	/**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			// 从这里获取用户输入的 电话号码信息
			// String phoneNum = mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                
            	// 获取授权信息
//                Toast.makeText(context, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
//                Toast.makeText(context,mAccessToken.toString(), Toast.LENGTH_SHORT).show();
                
                String accessToken = mAccessToken.getToken();
                String uId = mAccessToken.getUid();
                
                onWeiBoLoginListener.onLoginSuccessInWeiBo(accessToken, uId);
                
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = context.getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(context,  R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(context,  "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    public interface OnWeiBoLoginListener {
    	public void onLoginSuccessInWeiBo(String accessToken, String uId);
    }

	public void setOnWeiBoLoginListener(OnWeiBoLoginListener onWeiBoLoginListener) {
		this.onWeiBoLoginListener = onWeiBoLoginListener;
	}

}
