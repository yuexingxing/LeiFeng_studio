package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Action;
import com.tajiang.leifeng.model.ActiveInfo;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.UserUtils;

import java.util.List;

/**
 * Created by 12154 on 2016/1/13.
 */
public class UserWalletActivity extends BaseActivity implements HttpResponseListener {

    private View btn_walletAdd;
    private View rect_transcationDetailWallet;
    private View rectPassWordPayWallet;
    private Button btn_walletShow;
    private TextView common_nav_left;

    private TextView tvNumWellat;

    protected void initTopBar() {
//        setTitle("我的钱包");
//        enableNavLeftImage();
        unableNav();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_wallet);
    }

    @Override
    protected void initView() {
        btn_walletShow = (Button) findViewById(R.id.btn_walletShow);
        btn_walletAdd = findViewById(R.id.btn_walletAdd);
        rectPassWordPayWallet = findViewById(R.id.rectPassWordPayWallet);
        tvNumWellat = (TextView) findViewById(R.id.tvNumWellat);
        common_nav_left = (TextView) findViewById(R.id.wallet_nav_left);

        rect_transcationDetailWallet = findViewById(R.id.rect_transcationDetailWallet);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_walletAdd.setOnClickListener(this);
        common_nav_left.setOnClickListener(this);
        rectPassWordPayWallet.setOnClickListener(this);
        rect_transcationDetailWallet.setOnClickListener(this);
    }

    @Override
    protected void initDates() {
        super.initDates();

        TJHttpUtil.getInstance(this).userPDActivity();

        tvNumWellat.setText(UserUtils.getInstance().getUser().getBalance() + "");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void onResume() {
        super.onResume();

        TJHttpUtil.getInstance(this).userDoGetInfor();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_walletAdd:
                intent2Activity(UserWalletAddActivity.class);
                break;
            case R.id.rect_transcationDetailWallet:
                intent2Activity(UserWalletTransactionDetailActivity.class);
                break;
            case R.id.rectPassWordPayWallet:
                TJHttpUtil.getInstance(this).userWalletPayPswIsAuthReq();
                break;
            case R.id.common_nav_left:
                finish();
                break;
            case R.id.btn_walletShow:
                break;
        }
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_INFO:
                User userResult = (User) response.getData();
                UserUtils.getInstance().refreshUserInfor(userResult);
                initDates();
                break;
            case TJRequestTag.TAG_USER_WELLAT_IS_AUTH:

                ActiveInfo activeInfo = (ActiveInfo) response.getData();

                if (activeInfo.getActiveState() == 1) {
                    intent2Activity(UserWalletPassWordPayActivity.class);
                } else {
                    intent2Activity(UserWalletNewPassWordPayActivity.class);
                }
                break;
            case TJRequestTag.TAG_USER_PD_Activity:

                List<Action> actionListResult = (List<Action>) response.getData();

                if (actionListResult != null && actionListResult.size() > 0) {
                    Action action = actionListResult.get(0);
                    if (!TextUtils.isEmpty(action.getImg())) {
                        Uri uri = Uri.parse(action.getImg());
                        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.iv_actionImgWallet);
                        draweeView.setImageURI(uri);
                    }
                }

                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {

    }
}
