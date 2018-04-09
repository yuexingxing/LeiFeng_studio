package com.tajiang.leifeng.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tajiang.leifeng.activity.OrderPayActivity;
import com.tajiang.leifeng.common.http.CommonInterceptor;
import com.tajiang.leifeng.common.retrofit.cookieJar.ClearableCookieJar;
import com.tajiang.leifeng.common.retrofit.cookieJar.PersistentCookieJar;
import com.tajiang.leifeng.common.retrofit.cookieJar.cache.SetCookieCache;
import com.tajiang.leifeng.common.retrofit.cookieJar.persistence.SharedPrefsCookiePersistor;
import com.tajiang.leifeng.common.retrofit.ssl.TJSSLUtil;
import com.tajiang.leifeng.model.Apartment;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.model.Store;
import com.tajiang.leifeng.model.StoreDelivered;
import com.tajiang.leifeng.service.LocationService;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.DateUtils;
import com.tajiang.leifeng.utils.MD5;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class TJApp extends Application {

    public static final String MY_PKG_NAME = "com.tajiang.leifeng";

    private static TJApp tjApp;
    private static Context context;

    public LocationService locationService;

    public static final boolean isDebug = true;

    public String sendTime = "35916884898255966"; // 加密time

    // 推送
    private Integer typePush;
    private boolean isRun;

    public static String token = "";
    public static int timeout = 0;  //登录过期时间

    private String mStallsId = "";

    private School oldSchool = null;

    private School school;
    private Store store;
    private StoreDelivered storeDelivered;
    private Apartment userApartment;
    private List<Activity> activities = new ArrayList<>();
    /**
     * 是否已经显示登陆界面
     */
    private volatile boolean isShowLogin;
    private OkHttpClient okHttpClient;

    public boolean isShowLogin() {
        return isShowLogin;
    }

    public void setShowLogin(boolean showLogin) {
        isShowLogin = showLogin;
    }

    // 微信 - 钱包充值
    private static int typeWeChatPay = 0;

    // 微信 - 订单支付 - 类型
    private static int typeOrder = OrderPayActivity.ORDER_TYPE_PAY_NOW;

    public static int getTypeWeChatPay() {
        return typeWeChatPay;
    }

    public static void setTypeWeChatPay(int typeWeChatPay) {
        TJApp.typeWeChatPay = typeWeChatPay;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tjApp = this;
        context = getApplicationContext();
        initData();
        UserUtils.getInstance().initUser();

//      日志初始化
        Logger.init("Ciko")
                .methodCount(2)
                .hideThreadInfo()
                .logLevel(isDebug ? LogLevel.FULL : LogLevel.NONE)
                .methodOffset(0);
        initPushManager();
        // 加载网络图片
        Fresco.initialize(context);

        locationService = new LocationService(getApplicationContext());

        school = getSchool();
        store = getStore();
    }

    private void initData() {
        //初始化是否已上传用户ClientID及相关数据  false --> 未上传
        if (! SharedPreferencesUtils.contains(tjApp, SharedPreferencesUtils.BOOLEAN_CID_COLLECTED)) {
            SharedPreferencesUtils.put(tjApp, SharedPreferencesUtils.BOOLEAN_CID_COLLECTED, false);
        }
        //初始化OkHttpClient
        initHttpClient();
    }


    /**
     * 初始化OkHttpClient
     */
    private void initHttpClient() {
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(TJApp.getIns()));
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        CommonInterceptor interceptor = new CommonInterceptor(MD5.appId, "1.0", DateUtils.initDataTime());
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(TJSSLUtil.getSSLSocketFactory())
                .addInterceptor(interceptor)
                .cookieJar(cookieJar)
                .build();
    }

    private void initPushManager() {
        PushManager.getInstance().initialize(this.getApplicationContext());
    }

    public static int getTypeOrder() {
        return typeOrder;
    }

    public static void setTypeOrder(int typeOrder) {
        TJApp.typeOrder = typeOrder;
    }

    public School getSchool() {
        String schoolJson = (String) SharedPreferencesUtils.get(getContext(), "SCHOOL", "");
        school = new Gson().fromJson(schoolJson, School.class);
        return school;
    }

    public School getOldSchool() {
        return oldSchool;
    }

    public void setOldSchool(School oldSchool) {
        this.oldSchool = oldSchool;
    }

    public void setSchool(School school) {
        this.school = school;
        SharedPreferencesUtils.put(getContext(), "SCHOOL", new Gson().toJson(school));
        clearStore();
//        BuyCarUtils.getIns().clearBuyCar();
    }

    public void setStore(Store store) {
        this.store = store;
        SharedPreferencesUtils.put(getContext(), "Store", new Gson().toJson(store));
        BuyCarMapUtils.getIns().clearAllBuyCar();
//        BuyCarUtils.getIns().clearBuyCar();
    }

    public Store getStore() {
        String schoolJson = (String) SharedPreferencesUtils.get(getContext(), "Store", "");
        store = new Gson().fromJson(schoolJson, Store.class);
        return store;
    }


    public void setUserApartment(Apartment userApartment) {
        this.userApartment = userApartment;
        SharedPreferencesUtils.put(getContext(), SharedPreferencesUtils.USER_APARTMENT, new Gson().toJson(userApartment));
    }

    public Apartment getUserApartment() {
        String schoolJson = (String) SharedPreferencesUtils.get(getContext(), SharedPreferencesUtils.USER_APARTMENT, "");
        userApartment = new Gson().fromJson(schoolJson, Apartment.class);
        return userApartment;
    }


    public void setStoreDelivered(StoreDelivered storeDelivered) {
        this.storeDelivered = storeDelivered;
        SharedPreferencesUtils.put(getContext(), "StoreDelivered", new Gson().toJson(storeDelivered));
        BuyCarMapUtils.getIns().clearAllBuyCar();
//        BuyCarUtils.getIns().clearBuyCar();
    }

    public StoreDelivered getStoreDelivered() {
        String schoolJson = (String) SharedPreferencesUtils.get(getContext(), "StoreDelivered", "");
        storeDelivered = new Gson().fromJson(schoolJson, StoreDelivered.class);
        return storeDelivered;
    }

    /**
     * 新建一个activity
     * @param activity
     */
    public void addActivity(Activity activity) {
        if(activity != null) {
            activities.add(activity);
        }
    }
    /**
     *  结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity!=null) {
            this.activities.remove(activity);
            activity.finish();
        }
    }
    /**
     * 结束指定的Activity(通过类名)
     */
    public void finishActivity(Class clazz) {
        for (Activity activity : activities) {
            if (activity != null && activity.getClass().equals(clazz)) {
                this.activities.remove(activity);
                activity.finish();
            }
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit(){
        for (Activity activity : activities) {
            if (activity!=null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    public synchronized OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            initHttpClient();
        }
        return okHttpClient;
    }

    public void clearStore() {
        SharedPreferencesUtils.remove(getContext(), "Store");
    }

    public static Context getContext() {
        return context;
    }

    public static TJApp getIns() {
        return tjApp;
    }

    public Integer getTypePush() {
        return typePush;
    }

    public void setTypePush(Integer typePush) {
        this.typePush = typePush;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean isRun) {
        this.isRun = isRun;
    }

    public void setStallsId(String stallsId) {
        this.mStallsId = stallsId;
    }

    public String getStallsId() {
        return mStallsId;
    }
}
