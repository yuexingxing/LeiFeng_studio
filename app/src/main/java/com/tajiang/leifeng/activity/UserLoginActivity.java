package com.tajiang.leifeng.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.base.BaseActivity.OnClickNavRightListener;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.HttpHandler;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.constant.ApiCst;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.model.TokenInfo;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.CommonUtils;
import com.tajiang.leifeng.utils.GsonObjUtil;
import com.tajiang.leifeng.utils.KeyBoardUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.MD5Utils;
import com.tajiang.leifeng.utils.QQLoginUtils;
import com.tajiang.leifeng.utils.QQLoginUtils.QQLoginListener;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.SoftKeyboardUtil;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.utils.Utils;
import com.tajiang.leifeng.utils.WeChatUtils;
import com.tajiang.leifeng.utils.WeiBoLoginUtils;
import com.tajiang.leifeng.utils.WeiBoLoginUtils.OnWeiBoLoginListener;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;
import com.tajiang.leifeng.view.dialog.ChooseHostDialog;
import com.tajiang.leifeng.view.dialog.ProgressDialog;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginActivity extends BaseActivity implements OnClickNavRightListener
        , QQLoginListener
        , OnWeiBoLoginListener
        , HttpResponseListener{

    private int[] location = {-1, -1};  //记录账号密码输入布局初始位置,用于软键盘弹出时界面滑动到指定位置，避免软键盘遮挡住输入框

    private final String TAG = this.getClass().getSimpleName();

    private View ll_root;
    private View ll_qqLogin;
    private View ll_weixinLogin;
    private View ll_weiboLogin;

    private View view_pswSeeAble;

    private TextView tv_forgetLogin;

    private EditText et_phoneLogin;
    private EditText et_pswLogin;

    private Button btn_loginUser;

    private ImageView iv_pswSeeAble;

    private Tencent tencent;
    private QQLoginUtils qqLogin;

    private SsoHandler mSsoHandler;

    private boolean isPassWordVisiable = false;

    private String phoneName;
    private String passWord;

    private String accessToken;
    private String openId;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    private ProgressDialog progressDialog;

    // 获取个推ClientId
    private String mClientId;

    @Override
    protected void initTopBar() {
        setTitle("登录");
        enableNavRightText("注册");
        setOnClickNavRightListener(this);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_login);
    }

    @Override
    protected void initView() {
        ll_root = findViewById(R.id.ll_root);
        view_pswSeeAble = findViewById(R.id.view_pswSeeAble);

        ll_qqLogin = findViewById(R.id.ll_qqLogin);
        ll_weixinLogin = findViewById(R.id.ll_weixinLogin);
        ll_weiboLogin = findViewById(R.id.ll_weiboLogin);

        tv_forgetLogin = (TextView) findViewById(R.id.tv_forgetLogin);

        et_phoneLogin = (EditText) findViewById(R.id.et_phoneLogin);
        et_pswLogin = (EditText) findViewById(R.id.et_pswLogin);

        iv_pswSeeAble = (ImageView) findViewById(R.id.iv_pswSeeAble);

        btn_loginUser = (Button) findViewById(R.id.btn_loginUser);

        mClientId = (String) SharedPreferencesUtils.get(this, SharedPreferencesUtils.CHANNEL_ID, "");
    }

    @Override
    protected void initListener() {

        et_phoneLogin.addTextChangedListener(new PhoneEditWatcher(et_phoneLogin));
        et_pswLogin.addTextChangedListener(new PasswordEditWatcher());

        view_pswSeeAble.setOnClickListener(this);

        ll_qqLogin.setOnClickListener(this);
        ll_weixinLogin.setOnClickListener(this);
        ll_weiboLogin.setOnClickListener(this);

        tv_forgetLogin.setOnClickListener(this);

        btn_loginUser.setOnClickListener(this);
        btn_loginUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (TJApp.isDebug) {
                    new ChooseHostDialog(UserLoginActivity.this).show();
                    return true;
                } else {
                    return false;
                }
            }
        });

        //捕捉用户在软键盘上的回车操作事件
        et_pswLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                SoftKeyboardUtil.hideSoftKeyboard(UserLoginActivity.this);
                return true;
            }
        });

        //监听键盘的显示和隐藏
        SoftKeyboardUtil.observeSoftKeyboard(this, new SoftKeyboardUtil.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(int softKeybardHeight, int screenHeight, boolean visible) {
                if (visible) {
                    if (location[1] == -1) {
                        et_pswLogin.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                    }
                    //密码输入框 edtPwd 距离底部的距离， 用来计算和键盘高度差值 deltaY
                    int bottomDistance = screenHeight - location[1] - et_pswLogin.getHeight();
                    int deltaY = bottomDistance - softKeybardHeight;
                    ObjectAnimator.ofFloat(ll_root, "translationY", deltaY)
                            .setDuration(300)
                            .start();
                } else {
                    ObjectAnimator.ofFloat(ll_root, "translationY", 0)
                            .setDuration(300)
                            .start();
                }
            }
        });
    }

    /**
     * 判断是否需要隐藏键盘
     * @param view  当前被点击的view.
     * @param event 点击事件
     * @return
     */
    private boolean isShouldHideInput(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            float currentX = event.getX();
            float currentY = event.getY();
            // 判断点击的点是否处在输入框区域(保留点击EditText的事件)
            if (currentX > left && currentX < right
                    && currentY > top && currentY < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 点击EditText文本框之外任何地方则隐藏键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                SoftKeyboardUtil.hideSoftKeyboard(UserLoginActivity.this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void initDates() {
        super.initDates();
        progressDialog = new ProgressDialog(this);
        // 登录按钮默认不可点击
//        checkFromContent(et_phoneLogin, et_pswLogin, btn_loginUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 腾讯登录成功回调
        if (tencent != null) {
            tencent.onActivityResult(requestCode, resultCode, data);
        }

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.ll_qqLogin:
                qqLogin = new QQLoginUtils(this);
                tencent = qqLogin.onClickQQLogin();
                qqLogin.setQQLoginListener(this);
                break;
            case R.id.ll_weixinLogin:

                if(WeChatUtils.isWXAppInstalledAndSupported(this)){
                    api = WXAPIFactory.createWXAPI(this, TJCst.WEIXIN_APP_ID);
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "tajiang_leifeng";
                    api.sendReq(req);
                }

                break;

            case R.id.btn_loginUser:
                if (checkLoginInfor(et_phoneLogin, et_pswLogin)) {
                    //----Async    Http
//                    TJHttpUtils.getInstance(this).doLogin(phoneName, MD5Utils.StringMD5(passWord));
                    //----Retrofit Http
//                    TJHttpUtil.getInstance(this).doLogin(phoneName, MD5Utils.StringMD5(passWord), mClientId, Utils.getMIME(UserLoginActivity.this));
                    TJHttpUtil.getInstance(this).doLogin(phoneName, passWord, mClientId, Utils.getMIME(UserLoginActivity.this));
                }
                break;
            case R.id.ll_weiboLogin:
                WeiBoLoginUtils weiBoLoginUtils = new WeiBoLoginUtils(this);
                weiBoLoginUtils.setOnWeiBoLoginListener(this);
                mSsoHandler = weiBoLoginUtils.login();
                break;
            case R.id.tv_forgetLogin:
                intent2Activity(UserFindPassWordActivity.class);
                break;
            case R.id.view_pswSeeAble:
                // 切换密码的可见状态
                switchPswVisiable(et_pswLogin, iv_pswSeeAble);
                break;
        }

    }

    @Override
    public void onClickNavRight() {
        Intent intent = new Intent(this, UserRegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginInQQSuccess(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                this.openId = jsonObject.getString("openid");
                this.accessToken = jsonObject.getString("access_token");
                TJHttpUtil.getInstance(this).doLoginByQQ(accessToken, TJCst.LOGIN_TYLE_QQ, mClientId, Utils.getMIME(UserLoginActivity.this), openId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    @Override
    public void onLoginSuccessInWeiBo(String accessToken, String uId) {
        this.openId = uId;
        this.accessToken = accessToken;
        TJHttpUtil.getInstance(this).doLoginByWeiBo(accessToken, openId);
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

    private void checkFromContent(EditText et_phoneLogin, EditText et_pswLogin, Button btn_loginUser) {

        if (!TextUtils.isEmpty(et_phoneLogin.getText().toString()) && !TextUtils.isEmpty(et_pswLogin.getText().toString())) {
            btn_loginUser.setEnabled(true);
            btn_loginUser.setBackgroundResource(R.drawable.slt_btn_rect_green);
        } else {
            btn_loginUser.setEnabled(false);
            btn_loginUser.setBackgroundResource(R.drawable.shape_rect_round_gray);
        }

    }

    /**
     * 验证登录信息
     */
    private boolean checkLoginInfor(EditText et_phoneLogin, EditText et_pswLogin) {

        phoneName = et_phoneLogin.getText().toString().replaceAll(" ", "").trim();
        passWord = et_pswLogin.getText().toString().trim();

        if (!TextUtils.isEmpty(phoneName) && !TextUtils.isEmpty(passWord)) {

            if (phoneName.length() < 11) {
                ToastUtils.showShort("您输入的号码有误");
                return false;
            } else if (passWord.length() < 6) {
                ToastUtils.showShort("您输入的密码不足6位");
                return false;
            } else {
                return true;
            }

        } else {

            if (TextUtils.isEmpty(phoneName)) {
                ToastUtils.showShort("请填写手机号码");
            } else {
                ToastUtils.showShort("请填写密码");
            }

            return false;
        }

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
            // 判断账号密码都符合要求
            checkFromContent(et_phoneLogin, et_pswLogin, btn_loginUser);

        }

    }


    private class PasswordEditWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFromContent(et_phoneLogin, et_pswLogin, btn_loginUser);
        }

    }

    /**
     * 集中处理该页面的接口返回结果
     */
    @Override
    public void onStart(int requestTag ) {
        progressDialog.show();
    }

    @Override
    public void onSuccess(BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_LOGIN: // 登录请求
                dealUserLogin(response);
                break;
            case TJRequestTag.TAG_USER_INFO:
                toHomeAcitivity(response);
//                User user = (User) response.getData();
                break;
            case TJRequestTag.TAG_USER_LOGIN_OTHER:
                dealUserLoginOther(response);
                break;

            case TJRequestTag.TAG_PUSH_CHANNEL_ID:
                LogUtils.d(TAG, "case TJRequestTag.TAG_PUSH_CHANNEL_ID");
                finish();
                break;

        }
        progressDialog.dismiss();
        finish();
    }

    private void dealUserLoginOther(BaseResponse response) {
        LogUtils.d(TAG, "case TJRequestTag.TAG_USER_LOGIN_OTHER");
        if ((Boolean) response.getData()) {
//            TJHttpUtil.getInstance(this).doLogin(openId, accessToken);
        } else {
            ToastUtils.showShort("服务器注册用户失败");
        }
    }

    private void dealUserLogin(BaseResponse response) {
        LogUtils.d(TAG, "case TJRequestTag.TAG_USER_LOGIN");
        TokenInfo info = (TokenInfo) response.getData();
        TJApp.token = info.getToken();
        TJApp.timeout = info.getTimeout();

        //保存用户名跟密码
        SharedPreferencesUtils.put(this, SharedPreferencesUtils.LOGIN_USER_NAME, phoneName);
        SharedPreferencesUtils.put(this, SharedPreferencesUtils.LOGIN_USER_PSW, passWord);

        SharedPreferencesUtils.put(UserLoginActivity.this, SharedPreferencesUtils.USER_TOKENINFO, GsonObjUtil.getGsonInstance().toJson(info));

        //登录完成获取用户信息
        TJHttpUtil.getInstance(this).userDoGetInfor();
    }

    private void toHomeAcitivity(BaseResponse response) {
        User user = (User) response.getData();
        UserUtils.getInstance().saveUserInfor(GsonObjUtil.getGsonInstance().toJson(user));
        UserUtils.getInstance().initUser();
        Intent intent1 = new Intent(this, HomeActivity.class);
        startActivity(intent1);

        //收集用户手机信息
//        UserUtils.getInstance().uplaodMsg(user.getId(), mClientId);
    }

    @Override
    public void onFailed(BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
