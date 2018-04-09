package com.tajiang.leifeng.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.MessageListActivity;
import com.tajiang.leifeng.activity.UserAdressActivity;
import com.tajiang.leifeng.activity.UserAdviseActivity;
import com.tajiang.leifeng.activity.UserCouponActivity;
import com.tajiang.leifeng.activity.UserInforActivity;
import com.tajiang.leifeng.activity.UserInviteActivity;
import com.tajiang.leifeng.activity.UserServiceCenterActivity;
import com.tajiang.leifeng.activity.UserSetActivity;
import com.tajiang.leifeng.activity.UserWalletActivity;
import com.tajiang.leifeng.activity.WebQuestionActivity;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseFragment;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.model.Voucher;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.dialog.CDialog;

import java.math.BigDecimal;
import java.util.List;

public class UserFragment extends BaseFragment implements OnClickListener, HttpResponseListener {

    private boolean isDataLoaded = false;

    private View view_inforUser;
    private View view_setUser;
    private View view_couponUser;
    private View view_adressUser;
    private View view_walletUser;
    private View rect_adviceServiceCenter;
    private View rect_questionServiceCenter;

    private View viewInviteUser;
    private View viewAttestateUser;

    private TextView tv_nameUser;
    private TextView tvMoneyOverUser;  //用户钱包余额
    private TextView tvNumCouponUser;  //用户优惠卷数量

    private ImageView user_message_icon;  //消息

    private SimpleDraweeView iv_iconUser;

    public void onResume() {
        super.onResume();
        // 其它页面回来就刷新界面
        initData();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_user);
    }

    protected void initView() {
        view_setUser = findViewById(R.id.view_setUser);
        view_couponUser = findViewById(R.id.view_couponUser);
        view_adressUser = findViewById(R.id.view_adressUser);
        view_inforUser = findViewById(R.id.view_inforUser);
        view_walletUser = findViewById(R.id.view_walletUser);
        user_message_icon = findViewById(R.id.user_message_icon);
        rect_adviceServiceCenter = findViewById(R.id.rect_adviceServiceCenter);
        rect_questionServiceCenter = findViewById(R.id.rect_questionServiceCenter);

        viewInviteUser = findViewById(R.id.viewInviteUser);
        viewAttestateUser = findViewById(R.id.viewAttestateUser);

        tv_nameUser = findViewById(R.id.tv_nameUser);
        iv_iconUser =  findViewById(R.id.iv_iconUser);
        tvMoneyOverUser =  findViewById(R.id.tvMoneyOverUser);
        tvNumCouponUser = findViewById(R.id.tvNumCouponUser);

    }

    protected void initListener() {
        view_setUser.setOnClickListener(this);
        view_couponUser.setOnClickListener(this);
        view_adressUser.setOnClickListener(this);
        view_inforUser.setOnClickListener(this);
        view_walletUser.setOnClickListener(this);
        rect_adviceServiceCenter.setOnClickListener(this);
        rect_questionServiceCenter.setOnClickListener(this);
        viewInviteUser.setOnClickListener(this);
        viewAttestateUser.setOnClickListener(this);
        user_message_icon.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // 需要先登录
        if (UserUtils.getInstance().isLoginWithLogin(getActivity())) {
            switch (v.getId()) {
                case R.id.view_setUser:
                    intent2Activity(UserSetActivity.class);
                    break;
                case R.id.view_couponUser:
                    intent2Activity(UserCouponActivity.class);
                    break;
                case R.id.view_adressUser:
                    intent2Activity(UserAdressActivity.class);
                    break;
                case R.id.view_inforUser: //
                    intent2Activity(UserInforActivity.class);
                    break;
                case R.id.view_walletUser: // 钱包
                    intent2Activity(UserWalletActivity.class);
                    break;
                case R.id.viewInviteUser: // 用户邀请
                    intent2Activity(UserInviteActivity.class);
                    break;
                case R.id.rect_adviceServiceCenter: //意见反馈
                    intent2Activity(UserAdviseActivity.class);
                    break;
                case R.id.rect_questionServiceCenter: //常见问题
                    intent2Activity(WebQuestionActivity.class);
                    break;
                case R.id.user_message_icon:  //消息
                    intent2Activity(MessageListActivity.class);
                    break;
            }
        }
    }

    @Override
    protected void initAdapter() {

    }

    @Override
    protected void initData() {
        if (isDataLoaded) doRefreshCouponAndWallet();
        // 设置用户昵称
        tv_nameUser.setText(UserUtils.getInstance().getUserName());  //暂时注释
        loadImage(iv_iconUser,  UserUtils.getInstance().getUserIconUrl(), R.drawable.icon_hander_defaut );  //暂时注释
        tvMoneyOverUser.setText("￥ " +UserUtils.getInstance().getUser().getBalance() + "");
        tvNumCouponUser.setText("" + UserUtils.getInstance().getUser().getVoucherNum());
    }

    @Override
    public void reFreshCurrentPageData() {
        super.reFreshCurrentPageData();
        doRefreshCouponAndWallet();
    }

    /**
     * 刷新用户钱包和优惠卷
     */
    private void doRefreshCouponAndWallet() {
//*        TJHttpUtils.getInstance(this).userDoGetInfor();
////        TJHttpUtils.getInstance(this).getCouponList(1, 1,null);//获取可用优惠卷数量
//*        TJHttpUtils.getInstance(this).getUserCoupon();//获取可用优惠卷数量
        TJHttpUtil.getInstance(this).userDoGetInfor();
//        TJHttpUtil.getInstance(this).getUserCoupon();

        this.isDataLoaded = true;
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_INFO:

                User user = (User) response.getData();
                tvMoneyOverUser.setText("￥ " +user.getBalance() + "");
                break;
            case TJRequestTag.TAG_GET_COUPON:
                List<Voucher> voucherList = (List<Voucher>) response.getData();
                int count = 0;
                for (Voucher voucher : voucherList) {
                    if (voucher.getState() == Voucher.STATE_USABLE) {
                        count += 1;
                    }
                }
                tvNumCouponUser.setText("" + count);
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
