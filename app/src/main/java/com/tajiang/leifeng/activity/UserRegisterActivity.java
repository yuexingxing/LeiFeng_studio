package com.tajiang.leifeng.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.model.TokenInfo;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.CodeUtils;
import com.tajiang.leifeng.utils.CommonUtils;
import com.tajiang.leifeng.utils.GsonObjUtil;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.MD5Utils;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.utils.Utils;
import com.tajiang.leifeng.view.CountDownButton;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;

public class UserRegisterActivity extends BaseActivity implements HttpResponseListener{
	
	private boolean isPassWordVisiable = false;
	
	private String phoneName;
	private String code;
	private String psw;
	private String codeInvite;

	private View view_pswSeeAble;
	
	private ImageView iv_pswSeeAble;
	private ImageView iv_code;

	private EditText et_code;
	private EditText et_phoneRegister;
	private EditText et_codeRegister;
	private EditText et_pswRegister;
	private EditText etCodeInviteRegister;
	
	private Button btn_register;
	private CountDownButton btn_getCodeRegister;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	}

	@Override
	protected void initTopBar() {
		setTitle("注册");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_register);
	}

	@Override
	protected void initView() {
		view_pswSeeAble = findViewById(R.id.view_pswSeeAble);
		
		et_phoneRegister = (EditText) findViewById(R.id.et_phoneRegister);
		et_codeRegister = (EditText) findViewById(R.id.et_codeRegister);
		et_pswRegister = (EditText) findViewById(R.id.et_pswRegister);

		etCodeInviteRegister = (EditText) findViewById(R.id.etCodeInviteRegister);

		iv_pswSeeAble = (ImageView) findViewById(R.id.iv_pswSeeAble);

		btn_register = (Button) findViewById(R.id.btn_register);
		et_code = (EditText) findViewById(R.id.iv_code);
		iv_code = (ImageView) findViewById(R.id.code);
		btn_getCodeRegister = (CountDownButton) findViewById(R.id.btn_getCodeRegister);

		iv_code.setImageBitmap(CodeUtils.getInstance().createBitmap());
	}
	
	@Override
	protected void initListener() {
		
		et_phoneRegister.addTextChangedListener(new PhoneEditWatcher(et_phoneRegister));
		
		view_pswSeeAble.setOnClickListener(this);

		btn_register.setOnClickListener(this);
		iv_code.setOnClickListener(this);
		btn_getCodeRegister.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		
		switch (view.getId()) {
			// 点击切换密码的可见和不可见
		case R.id.view_pswSeeAble:
			switchPswVisiable(et_pswRegister, iv_pswSeeAble);
			break;
			// 点击了获取验证码
		case R.id.btn_getCodeRegister:
			
			if(checkPhoneInfor(et_phoneRegister)){

				if(TextUtils.isEmpty(et_code.getText().toString())) {
					ToastUtils.showShort("图形验证码不能为空!");
				} else {
					if (et_code.getText().toString().equalsIgnoreCase(CodeUtils.getInstance().getCode())) {
						TJHttpUtil.getInstance(this).getRegisterCode(TJCst.REG_CODE, phoneName);	//获取手机注册验证码
					} else {
						ToastUtils.showShort("图形验证码输入不正确!");
					}
				}
			}
			
			break;
			// 点击了注册按钮
		case R.id.btn_register:
			if(	checkRegisterInfor(et_phoneRegister, et_codeRegister, et_pswRegister)){

				TJHttpUtil.getInstance(this).doRegister(codeInvite, psw, phoneName, code);
			}
			break;
		//重新生成验证码
		case R.id.code:
				iv_code.setImageBitmap(CodeUtils.getInstance().createBitmap());
			break;
		}
		
	}

	/**
	 * 切换密码的可见状态
	 */
	private void switchPswVisiable(EditText pswEditText, ImageView stateImage) {
		if (isPassWordVisiable) {
			pswEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
			pswEditText.setSelection(pswEditText.getText().toString().length());
			stateImage.setImageResource(R.drawable.login_icon_see_unable);
			isPassWordVisiable = false;
		} else {
			pswEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			pswEditText.setSelection(pswEditText.getText().toString().length());
			stateImage.setImageResource(R.drawable.login_icon_see_able);
			isPassWordVisiable = true;
		}
	}
	
	/**
	 * 
	 * 验证所有注册信息格式
	 * @param et_phoneRegister
	 * @param et_codeRegister
	 * @param et_pswRegister
	 * @return
	 */
	private boolean checkRegisterInfor(EditText et_phoneRegister, EditText et_codeRegister,EditText et_pswRegister) {
		
		phoneName = et_phoneRegister.getText().toString().replace(" ", "").trim();
		code = et_codeRegister.getText().toString().trim();
		psw = et_pswRegister.getText().toString().trim();
		codeInvite = etCodeInviteRegister.getText().toString().trim();
		
		if(!TextUtils.isEmpty(phoneName) && !TextUtils.isEmpty(code) && !TextUtils.isEmpty(code)){
			
			if(phoneName.length() < 11){
				ToastUtils.showShort("您输入的号码不足11位数字");
				return false;
			}
			
			else if(code.length() < 6){
				ToastUtils.showShort("您输入的号码不足6位数字,请输入完整的验证码");
				return false;
			}

			else if(psw.length() < 6){
				ToastUtils.showShort("密码的长度必须大于6位");
				return false;
			}
			
			else {
				return true;
			}
			
		}else{
			
			if(TextUtils.isEmpty(phoneName)){
				ToastUtils.showShort("手机号码不能");
			}
			
			else if(TextUtils.isEmpty(code)){
				ToastUtils.showShort("请填写验证码");
			}
			
			else if(TextUtils.isEmpty(psw)){
				ToastUtils.showShort("请输入您要设置的密码");
			}
		
			return false;
		
		}
		
		
		
	}
	
	/**
	 * 检测手机号码输入框格式
	 * @param et_phoneForgetPsw
	 * @return
	 */
	private boolean checkPhoneInfor(EditText et_phoneForgetPsw) {
		
		phoneName = et_phoneForgetPsw.getText().toString().replaceAll(" ", "").trim();
		
		if(!TextUtils.isEmpty(phoneName) ){
			
			if(phoneName.length() < 11){
				ToastUtils.showShort("您输入的号码有误");
				return false;
			}
			
			else {
				return true;
			}
			
		}else{
			ToastUtils.showShort("手机号码不能为空");
			
			return false;
		}
		
	}

	@Override
	public void onStart(int requestTag) {
		showLoading();
	}

	@Override
	public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
		switch (requestTag) {
			case TJRequestTag.TAG_GET_CODE:
				btn_getCodeRegister.startCount();
				break;
			case TJRequestTag.TAG_USER_REGISTER:
				String mClientId = (String) SharedPreferencesUtils.get(this, SharedPreferencesUtils.CHANNEL_ID, "");
				TJHttpUtil.getInstance(this).doLogin(phoneName, psw, mClientId, Utils.getMIME(UserRegisterActivity.this));

				break;

			case TJRequestTag.TAG_USER_LOGIN:
				TokenInfo info = (TokenInfo) response.getData();
				TJApp.token = info.getToken();
				TJApp.timeout = info.getTimeout();

				//保存用户名跟密码
				SharedPreferencesUtils.put(UserRegisterActivity.this, SharedPreferencesUtils.LOGIN_USER_NAME, phoneName);
				SharedPreferencesUtils.put(UserRegisterActivity.this, SharedPreferencesUtils.LOGIN_USER_PSW, psw);

				//保存用户登录token
				SharedPreferencesUtils.put(UserRegisterActivity.this, SharedPreferencesUtils.USER_TOKENINFO, GsonObjUtil.getGsonInstance().toJson(info));

				//登录完成获取用户信息
				TJHttpUtil.getInstance(this).userDoGetInfor();
				break;
			case TJRequestTag.TAG_USER_INFO:
//				toHomeAcitivity(response);
				User user2 = (User) response.getData();
				UserUtils.getInstance().saveUserInfor(GsonObjUtil.getGsonInstance().toJson(user2));
				UserUtils.getInstance().initUser();
				finish();
				intent2Activity(HomeActivity.class);
//                User user = (User) response.getData();
				break;
		}
	}

	@Override
	public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
		dismissLoading();
	}

	@Override
	public void onFinish(int requestTag) {
		dismissLoading();
	}

	private class PhoneEditWatcher implements TextWatcher {
		
		private EditText phoneEditText;
		
		public PhoneEditWatcher(EditText phoneEditText) {
			this.phoneEditText = phoneEditText;
		}
	
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	
		}
	
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			   if (s == null || s.length() == 0) return;
		        StringBuilder sb = new StringBuilder();
		        for (int i = 0; i < s.length(); i++) {
		            if (i != 3 && i != 8 && s.charAt(i) == ' ') {
		                continue;
		            } else {
		                sb.append(s.charAt(i));
		                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
		                    sb.insert(sb.length() - 1, ' ');
		                }
		            }
		        }
		        if (!sb.toString().equals(s.toString())) {
		            int index = start + 1;
		            if (sb.charAt(start) == ' ') {
		                if (before == 0) {
		                    index++;
		                } else {
		                    index--;
		                }
		            } else {
		                if (before == 1) {
		                    index--;
		                }
		            }
		            phoneEditText.setText(sb.toString());
		            phoneEditText.setSelection(index);
		        }
			
		}
	
		@Override
		public void afterTextChanged(Editable s) {
			
		}
		
	}
	
}
