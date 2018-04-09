package com.tajiang.leifeng.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;

public class UserInforChangeUsernameActivity extends BaseActivity implements HttpResponseListener {
	
	private EditText et_nickUserInfor;
	
	private Button btn_modifyNikeUserInfor;
	
	private String nick;
	
	private AnimLoadDialog animLoadDialog;

	@Override
	protected void initTopBar() {
		setTitle("修改用户名");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_infor_change_username);
	}

	@Override
	protected void initView() {
		
		et_nickUserInfor = (EditText) findViewById(R.id.et_nickUserInfor);
		
		btn_modifyNikeUserInfor = (Button) findViewById(R.id.btn_modifyNikeUserInfor);

	}
	
	@Override
	protected void initListener() {
		super.initListener();
		
		btn_modifyNikeUserInfor.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		// 点击了修改昵称
		case R.id.btn_modifyNikeUserInfor:
			if(checkNikeInfor(et_nickUserInfor)){
				// 修改本地用户信息
				UserUtils.getInstance().modifyNickName(nick);
				// 修改昵称
				TJHttpUtil.getInstance(this).doModifyNickName(nick);
			}
			
			break;
		}
		
	}

	private boolean checkNikeInfor(EditText et_nickUserInfor) {
		
		nick = et_nickUserInfor.getText().toString().trim();
		
		if(!TextUtils.isEmpty(nick)){
			// 设置昵称的规则
			return true;
		}else {
			ToastUtils.showShort("请输入想要修改的昵称");
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
			case TJRequestTag.TAG_USER_MODIFY_NICK_NAME:
				ToastUtils.showShort("修改昵称成功");
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
