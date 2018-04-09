package com.tajiang.leifeng.activity;

import android.view.View;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.model.InviteInfo;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.dialog.ShareDialog;

public class UserInviteActivity extends BaseActivity implements HttpResponseListener{

    private String urlShare = "http://www.itajiang.com/shareApp/shareapp.html";
    private String inviteCode;

    private View btnInviteUser;

    private TextView tvDescInvite;
    private TextView tvNumPersomInvite;
    private TextView tvCodeInviteInvite;

    @Override
    protected void initTopBar() {
        setTitle("邀请好友");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_invite);
    }

    @Override
    protected void initView() {
        tvDescInvite = (TextView) findViewById(R.id.tvDescInvite);
        tvNumPersomInvite = (TextView) findViewById(R.id.tvNumPersomInvite);
        tvCodeInviteInvite = (TextView) findViewById(R.id.tvCodeInviteInvite);

        btnInviteUser = findViewById(R.id.btnInviteUser);
    }

    @Override
    protected void initDates() {
        super.initDates();
        TJHttpUtil.getInstance(this).getUserInviteInfo(UserUtils.getInstance().getUserId());
    }

    @Override
    protected void initListener() {
        super.initListener();

        btnInviteUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btnInviteUser:
                ShareDialog shareDialog = new ShareDialog(getContext(), "呼朋引伴  惊喜不断", "邀请1位好友可得1元红包，好友不限，优惠无限", urlShare + "?inviteCode=" + inviteCode, R.drawable.img_user_invite_share);
                shareDialog.show();
                break;
        }

    }

    // 初始化邀请信息数据
    private void initUserInviteData(InviteInfo inviteInfo) {
        if (inviteInfo != null) {
            tvDescInvite.setText(inviteInfo.getRed().replace("\\n", "\n"));
            tvNumPersomInvite.setText(inviteInfo.getInviteNumber() + "人");
            tvCodeInviteInvite.setText(inviteInfo.getInvitationCode());
        }
    }

    @Override
    public void onStart(int requestTag) {
        showLoading();
    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_INVITE:

                InviteInfo inviteInfo = (InviteInfo) response.getData();
                inviteCode = inviteInfo.getInvitationCode();
                initUserInviteData(inviteInfo);

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
}
