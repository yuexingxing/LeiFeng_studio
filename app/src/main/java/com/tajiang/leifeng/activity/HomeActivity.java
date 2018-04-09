package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.base.BaseFragment;
import com.tajiang.leifeng.base.FragmentController;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.fragment.HomeFragment;
import com.tajiang.leifeng.fragment.OrderFragment;
import com.tajiang.leifeng.fragment.UserFragment;
import com.tajiang.leifeng.model.AppInfo;
import com.tajiang.leifeng.model.AppInfoResult;
import com.tajiang.leifeng.model.Notify;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.DateUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.StatusBarUtil;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UpdateManagerUtil;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.BottomBarView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity implements BottomBarView.OnBottomBarClickListener, HttpResponseListener {
    public static final int FIRST_PAGE = 0;      //全民雷锋
    public static final int ORDER_PAGE = 1;      //订单
    public static final int USER_INFO_PAGE = 2;  //个人中心


    // 配置底部菜单栏内容
    private String[] labelArr = {"全民雷锋", "订单", "我的"};

    private int[] imgNormalResIdArr = {
            R.drawable.icon_home_leifeng_normal,
            R.drawable.com_btm_icon_order_normal,
            R.drawable.icon_home_user_normal
    };

    private int[] imgPressedResIdArr = {
            R.drawable.icon_home_leifeng_pressed,
            R.drawable.com_btm_icon_order_selected,
            R.drawable.icon_home_user_pressed
    };

    List<Fragment> fragmentList = new ArrayList<>();

    private FragmentController fragmentController;

    private BottomBarView bbvHome;

    @Override
    protected void initTopBar() {
        unableNav();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initView() {
        bbvHome = (BottomBarView) findViewById(R.id.bbvHome);
    }

    @Override
    protected void initDates() {
        bbvHome.initData(labelArr, imgNormalResIdArr, imgPressedResIdArr);

        uploadUserCID();//上传CID收集信息
        initFragment();
//        TJHttpUtil.getInstance(this).getAppInfo();   //暂时
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        pushResponse();

        String codeOrderPay = intent.getStringExtra("CODE_ORDER_PAY");

        if (!TextUtils.isEmpty(codeOrderPay)) {
            fragmentController.showFragment(ORDER_PAGE);
            OrderFragment orderFragment = (OrderFragment) fragmentController.getFragment(ORDER_PAGE);
            orderFragment.refreshDate();
            bbvHome.doPressedItem(1);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // app打开时
        pushResponse();
//        /**
//         * 点击推送通知打开优惠卷页面
//         */
//        if (getIntent().hasExtra("intent_from_push_type")) {
//            switch (getIntent().getIntExtra("intent_from_push_type", Notify.TYPE_RED_PACKAGE)) {
//                case Notify.TYPE_RED_PACKAGE:
//                    intent2Activity(UserCouponActivity.class);
//                    break;
//                default:
//                    break;
//            }
//        }
    }

    private void uploadUserCID() {
        boolean isCollectCID = (Boolean) SharedPreferencesUtils.get(TJApp.getContext(), SharedPreferencesUtils.BOOLEAN_CID_COLLECTED, false);
        if (isCollectCID) { //已经上传过的，T周期内上传一次
            long currentTimeMills = System.currentTimeMillis();
            long lastTimeMills = (long) SharedPreferencesUtils.get(TJApp.getContext(), SharedPreferencesUtils.LAST_CID_UPLOAD_TIME, currentTimeMills);
            if ( DateUtils.getDayBetween2DateInMills(currentTimeMills, lastTimeMills ) >= 7 ) {
                uplaodMsg();
            }
        } else {   //未上传过的，上传CID以及其他信息
            uplaodMsg();
        }
    }

    private void uplaodMsg() {
        User user = UserUtils.getInstance().getUser();
        // 获取个推ClientId
        String clientId = (String) SharedPreferencesUtils.get(this, SharedPreferencesUtils.CHANNEL_ID, "");
        //收集用户手机信息
//        UserUtils.getInstance().uplaodMsg(user.getId(), clientId);
    }

    /**
     * 推送点击页面反馈
     */
    private void pushResponse() {
        if (getApp().getTypePush() != null) {
            switch (getApp().getTypePush()) {
                case 4: // 退款通知
                    Intent intent1 = new Intent(this, UserWalletTransactionDetailActivity.class);
                    startActivity(intent1);
                    getApp().setTypePush(null);
                    break;
            }
        }
    }

    private void initFragment() {

//        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new OrderFragment());
        fragmentList.add(new UserFragment());

        fragmentController = new FragmentController(this, R.id.flContentHome, fragmentList);
    }

    @Override
    protected void initListener() {
        bbvHome.setOnBottomBarClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            ToastUtils.showShort("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            MobclickAgent.onKillProcess(this);
            TJApp.getIns().exit();
        }
    }

    @Override
    public void onBottomBarClick(int position) {
        /**
         * 页面切换
         */
        switch (position) {
            case FIRST_PAGE:
                fragmentController.showFragment(position);
                break;
            case ORDER_PAGE:
                if (UserUtils.getInstance().isLoginWithLogin(this)) {
                    ((BaseFragment)(fragmentList.get(position))).reFreshCurrentPageData();
                    fragmentController.showFragment(position);
                } else {
                    bbvHome.doPressedItem(0);
                }
                break;
            case USER_INFO_PAGE:
                if (UserUtils.getInstance().isLoginWithLogin(this)) {
                    ((BaseFragment)(fragmentList.get(position))).reFreshCurrentPageData();
                    fragmentController.showFragment(position);
                } else {
                    bbvHome.doPressedItem(0);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle arg0) {
        StatusBarUtil.setTranslucentCustom(this);
        super.onCreate(arg0);
    }

    @Override
    protected void initStateBar() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //回调方法,（OrderFragment --> onActivityResult）
        fragmentController.getFragment(1).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_APP_INFO:
                // 请求版本信息成功
                AppInfoResult appInfoJson = (AppInfoResult) response.getData();
                AppInfo appInfo = new Gson().fromJson(appInfoJson.getResult(), AppInfo.class);
                // 检查更新
                UpdateManagerUtil manager = new UpdateManagerUtil(this, appInfo);
                manager.checkUpdate();
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
