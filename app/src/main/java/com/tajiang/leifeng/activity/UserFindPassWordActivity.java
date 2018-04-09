package com.tajiang.leifeng.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.CountDownButton;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;

public class UserFindPassWordActivity extends BaseActivity implements OnClickListener,HttpResponseListener{
	
	
	public static final int MSG_RECEIVED_CODE = 1;
	
	private EditText et_phoneForgetPsw;
	private EditText et_codeForgetPsw;
	private EditText et_pswForgetPsw;
	
	private CountDownButton btn_getCodeForgetPsw;
	private Button btn_modifyForgetPsw;
	
	private String phone;
	private String code;
	private String psw;
	
	private AnimLoadDialog animLoadDialog;
	
	@Override
	protected void initTopBar() {
		setTitle("找回密码");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_find_password);
	}

	@Override
	protected void initView() {
		et_phoneForgetPsw = (EditText) findViewById(R.id.et_phoneForgetPsw);
		et_codeForgetPsw = (EditText) findViewById(R.id.et_codeForgetPsw);
		et_pswForgetPsw = (EditText) findViewById(R.id.et_pswForgetPsw);

		btn_getCodeForgetPsw = (CountDownButton) findViewById(R.id.btn_getCodeForgetPsw);
		btn_modifyForgetPsw = (Button) findViewById(R.id.btn_modifyForgetPsw);
	}
	
	@Override
	protected void initListener() {
		btn_getCodeForgetPsw.setOnClickListener(this);
		btn_modifyForgetPsw.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		
		switch (view.getId()) {
		
		// 点击了获取验证码
		case R.id.btn_getCodeForgetPsw:
			if(checkPhoneInfor(et_phoneForgetPsw)){
				//获取手机注册验证码
				TJHttpUtil.getInstance(this).getRegisterCode(TJCst.REG_CODE, phone);
			}
			break;
			// 点击了重置密码按钮
		case R.id.btn_modifyForgetPsw:
			if(checkResetPassWordInfor(et_phoneForgetPsw, et_codeForgetPsw, et_pswForgetPsw)){
				TJHttpUtil.getInstance(this).userDoModifyPsd(TJApp.getIns().getSchool().getSchoolId(), phone, code, psw);
			}
			break;
		}
		
		
	}

	/**
	 * 检测手机号码输入框格式
	 * @param et_phoneForgetPsw
	 * @return
	 */
	private boolean checkPhoneInfor(EditText et_phoneForgetPsw) {
		
		phone = et_phoneForgetPsw.getText().toString().replaceAll(" ", "").trim();
		
		if(!TextUtils.isEmpty(phone) ){
			
			if(phone.length() < 11){
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
	
	private boolean checkResetPassWordInfor(EditText et_phoneForgetPsw , EditText et_codeForgetPsw, EditText et_pswForgetPsw) {

		phone = et_phoneForgetPsw.getText().toString().replaceAll(" ", "").trim();
		code = et_codeForgetPsw.getText().toString().trim();
		psw = et_pswForgetPsw.getText().toString().trim();
		
		if( !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code) && !TextUtils.isEmpty(psw) ){
			
			if(phone.length() < 11){
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
			if (TextUtils.isEmpty(phone)) {
				ToastUtils.showShort("手机号码不能为空");
			}
			
			else if(TextUtils.isEmpty(code)){
				ToastUtils.showShort("验证码不能为空");
			}
			
			else if(TextUtils.isEmpty(psw)){
				ToastUtils.showShort("请输入您的新密码");
			}
			
			return false;
			
		}
	}

	@Override
	public void onStart(int requestTag) {
		animLoadDialog = new AnimLoadDialog(this);
		animLoadDialog.show();
	}

	@Override
	public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
		switch (requestTag) {
			// 获取验证码
			case TJRequestTag.TAG_GET_CODE:
				btn_getCodeForgetPsw.startCount();
				break;
			case TJRequestTag.TAG_USER_RESET_PASSWORD:
				ToastUtils.showShort("修改密码成功,请登录");
				onBackPressed();
				break;
		}
	}

	@Override
	public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
		animLoadDialog.dismiss();
	}

	@Override
	public void onFinish(int requestTag) {
		animLoadDialog.dismiss();
	}
}

