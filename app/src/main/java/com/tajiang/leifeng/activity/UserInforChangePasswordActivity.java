package com.tajiang.leifeng.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpHandler;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;

public class UserInforChangePasswordActivity extends BaseActivity implements  HttpResponseListener {
	
	private String pswNow;
	private String pswNew;
	private String repswNew;

	private EditText et_pswNowUserChangePsw;
	private EditText et_pswNewUserChangePsw;
	private EditText et_rePswNewUserChangePsw;

	private Button btn_modifyPswUserChangePsw;
	
	private AnimLoadDialog animLoadDialog;

	@Override
	protected void initTopBar() {
		setTitle("修改密码");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_infor_change_password);
	}

	@Override
	protected void initView() {

		et_pswNowUserChangePsw = (EditText) findViewById(R.id.et_pswNowUserChangePsw);
		et_pswNewUserChangePsw = (EditText) findViewById(R.id.et_pswNewUserChangePsw);
		et_rePswNewUserChangePsw = (EditText) findViewById(R.id.et_rePswNewUserChangePsw);

		btn_modifyPswUserChangePsw = (Button) findViewById(R.id.btn_modifyPswUserChangePsw);

	}

	@Override
	protected void initListener() {

		btn_modifyPswUserChangePsw.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		super.onClick(view);

		switch (view.getId()) {
		
		// 点击了修改密码
		case R.id.btn_modifyPswUserChangePsw:
			if(checkModifyPswInfor(et_pswNowUserChangePsw, et_pswNewUserChangePsw, et_rePswNewUserChangePsw)){
				// 修改密码
//				TJHttpClient.getInstance(getApplication()).post(new UserModifyPasswordReq(pswNow,pswNew,repswNew),this, 0);
				TJHttpUtil.getInstance(this).doUserModifyPassword(pswNow, pswNew);
			}
			break;
		}

	}

	/**
	 * 检验修改密码的提交信息
	 */
	private boolean checkModifyPswInfor(EditText et_pswNowUserChangePsw , EditText et_pswNewUserChangePsw, EditText et_rePswNewUserChangePsw) {

		pswNow =  et_pswNowUserChangePsw.getText().toString().trim();
		pswNew =  et_pswNewUserChangePsw.getText().toString().trim();
		repswNew =  et_rePswNewUserChangePsw.getText().toString().trim();
		
		if(!TextUtils.isEmpty(pswNow) && !TextUtils.isEmpty(pswNew)  && !TextUtils.isEmpty(repswNew) ){
			
			if( pswNow.equals(pswNew)){
				ToastUtils.showShort("新密码不能和旧密码一样");
				return false;
			}
			
			else if( pswNew.length() < 6){
				ToastUtils.showShort("密码的位数不能少于6位");
				return false;
			}

			else if( !pswNew.equals(repswNew)){
				ToastUtils.showShort("两次输入的密码不一致");
				return false;
			}
			
			else{
				return true;
			}
			
		}else{
			
			if(TextUtils.isEmpty(pswNow) ){
				ToastUtils.showShort("请输入原密码");
			}
			
			else if(TextUtils.isEmpty(pswNew)){
				ToastUtils.showShort("请输入新密码");
			}

			else if(TextUtils.isEmpty(repswNew)){
				ToastUtils.showShort("请再输入一次新密码");
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
		ToastUtils.showShort("修改密码成功,请重新登录");
		onBackPressed();

		animLoadDialog.dismiss();
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
