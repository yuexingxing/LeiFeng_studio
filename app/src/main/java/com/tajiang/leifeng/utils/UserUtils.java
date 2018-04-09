package com.tajiang.leifeng.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.tajiang.leifeng.activity.UserLoginActivity;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.Store;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.model.UserAddress;

public class UserUtils implements HttpResponseListener {

    public static final int LOGIN_TYPE_PHONE = 0;
    public static final int LOGIN_TYPE_QQ = 1;
    public static final int LOGIN_TYPE_WECHAT = 2;
    public static final int LOGIN_TYPE_WEIBO = 3;

    private User mUser = new User();

    private static UserUtils userUtils;

    private UserUtils() {
    }

    ;

    public static UserUtils getInstance() {
        if (userUtils == null) {
            synchronized (UserUtils.class) {
                userUtils = new UserUtils();
            }
        }
        return userUtils;
    }

    public void initUser() {
        if (isLogin()) {
            UserUtils.getInstance();
            String userInfor = UserUtils.getInstance().getUserInfor();
            mUser = GsonObjUtil.getGsonInstance().fromJson(userInfor, User.class);
        }
    }


    /**
     * 上传手机信息到服务器
     */
    public void uplaodMsg(String userId, String clientId) {
        String appVersion = CommonUtils.getVersion(TJApp.getContext());
        String osVersion = Build.VERSION.RELEASE;
        String deviceModel = Build.MODEL;
        int os = 1;//代表android

        if (userId != null && userId.equals("") == false) {
            TJHttpUtil.getInstance(this).collectClientMsg(
                    userId, clientId, os, osVersion, deviceModel, appVersion);
        }
    }

    /**
     * 判断是否登录过了，没有登录则跳转到登录界面
     *
     * @return
     */
    public boolean isLoginWithLogin(Context context) {
        if (SharedPreferencesUtils.contains(context, SharedPreferencesUtils.USER_LOGIN_INFOR)) {
            return true;
        } else {
            // 1. 先清用户数据
            // 2. 启动登录界面
            clearUserInfor();
            Intent intent = new Intent(context, UserLoginActivity.class);
            context.startActivity(intent);
            return false;
        }
    }


    /**
     * 判断是否登录过了
     *
     * @return
     */
    public boolean isLogin() {
        if (SharedPreferencesUtils.contains(TJApp.getContext(), SharedPreferencesUtils.USER_TOKENINFO)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 储存用户信息
     *
     * @param userInfor
     */
    public void saveUserInfor(String userInfor) {
        SharedPreferencesUtils.put(TJApp.getContext(), SharedPreferencesUtils.USER_LOGIN_INFOR, userInfor);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public String getUserInfor() {
        return (String) SharedPreferencesUtils.get(TJApp.getContext(), SharedPreferencesUtils.USER_LOGIN_INFOR, "没有用户信息");
    }

    public void modifySex(int sex) {

        String userInfor = getUserInfor();

        User user = GsonObjUtil.getGsonInstance().fromJson(userInfor, User.class);
        user.setSex(sex);

        refreshUserInfor(user);

    }

    public void modifyNickName(String nikeName) {
        String userInfor = getUserInfor();

        User user = GsonObjUtil.getGsonInstance().fromJson(userInfor, User.class);
        user.setNikeName(nikeName);

        refreshUserInfor(user);
    }

    public void modifyAvatar(String avatarUrl) {
        String userInfor = getUserInfor();

        User user = GsonObjUtil.getGsonInstance().fromJson(userInfor, User.class);
        user.setAvatar(avatarUrl);

        refreshUserInfor(user);
    }

    /**
     * 刷新本地用户信息
     *
     * @param user
     */
    public void refreshUserInfor(User user) {
        // 更新Application的User对象
        mUser = user;
        // 更新本地缓存的用户信息
        saveUserInfor(GsonObjUtil.getGsonInstance().toJson(user));
    }

    /**
     * 清除用户信息
     */
    public void clearUserInfor() {
        // 2. 清缓存的用户信息
        mUser = new User();
        SharedPreferencesUtils.remove(TJApp.getContext(), SharedPreferencesUtils.LOGIN_USER_NAME);
        SharedPreferencesUtils.remove(TJApp.getContext(), SharedPreferencesUtils.LOGIN_USER_PSW);
        SharedPreferencesUtils.remove(TJApp.getContext(), SharedPreferencesUtils.USER_TOKENINFO);
        SharedPreferencesUtils.remove(TJApp.getContext(), SharedPreferencesUtils.USER_LOGIN_INFOR);
    }

    /**
     * 取食堂
     */
    public Store getStoreInfor() {
        String storeInfor = (String) SharedPreferencesUtils.get(TJApp.getContext(), SharedPreferencesUtils.USER_STORE, "");
        return GsonObjUtil.getGsonInstance().fromJson(storeInfor, Store.class);
    }

    /**
     * 存储食堂
     */
    public void saveStoreInfor(Store store) {
        SharedPreferencesUtils.put(TJApp.getContext(), SharedPreferencesUtils.USER_STORE, GsonObjUtil.getGsonInstance().toJson(store));
    }

    public boolean isApproveUser() {

        if (mUser.isLeifeng() || mUser.getCertified() == 4) {
            return true;
        } else {
            return false;
        }

    }

    public String getUserName() {
        return mUser.getNikeName();
    }

    public String getUserIconUrl() {
        return mUser.getAvatar();
    }

    public String getUserId() {
        return mUser.getId();
    }

    public String getUserSex() {
        if (mUser.getSex() == User.MAN) {
            return "男";
        } else if (mUser.getSex() == User.WOMAN) {
            return "女";
        }
        return "未设置";
    }

    public UserAddress getUserAddress() {
        return mUser.getUserAddress();
    }

    public User getUser() {
        return mUser;
    }

    public void doUserRefreshNetUserInfor() {
        TJHttpUtil.getInstance(this).userDoGetInfor();
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_INFO:
                User userResult = (User) response.getData();
                refreshUserInfor(userResult);
                break;
            case TJRequestTag.TAG_COLLECT_CLIENT:
                LogUtils.d("case TJRequestTag.TAG_COLLECT_CLIENT");
                if ((Boolean) response.getData()) {
                    SharedPreferencesUtils.put(TJApp.getContext(), SharedPreferencesUtils.BOOLEAN_CID_COLLECTED, true );
                    SharedPreferencesUtils.put(TJApp.getContext(), SharedPreferencesUtils.LAST_CID_UPLOAD_TIME, System.currentTimeMillis());
                }
                break;
            default:
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
