package com.tajiang.leifeng.common.retrofit;

import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.common.http.CommonInterceptor;
import com.tajiang.leifeng.common.mock.MockRetrofitService;
import com.tajiang.leifeng.common.retrofit.cookieJar.ClearableCookieJar;
import com.tajiang.leifeng.common.retrofit.cookieJar.PersistentCookieJar;
import com.tajiang.leifeng.common.retrofit.cookieJar.cache.SetCookieCache;
import com.tajiang.leifeng.common.retrofit.cookieJar.persistence.SharedPrefsCookiePersistor;
import com.tajiang.leifeng.constant.ApiCst;
import com.tajiang.leifeng.utils.DateUtils;
import com.tajiang.leifeng.utils.MD5;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by Admins on 2017/2/9.
 */

public class TJMockRetrofit {

    private static MockRetrofitService serviceMock;

    /**
     * 实现Mock数据的RetrofitService
     * @return
     */
    @SuppressWarnings("unchecked")
    public static MockRetrofitService createMockRetrofitService() {
        if(serviceMock == null) {
            synchronized (TJRetrofit.class) {
                ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(TJApp.getIns()));
//                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                CommonInterceptor interceptor = new CommonInterceptor(MD5.appId, "1.0", DateUtils.initDataTime());
//                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).cookieJar(cookieJar).build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiCst.ROOT_URL)
                        .client(client)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                serviceMock = new MockRetrofitService(getBehaviorDelegate(retrofit));
            }
        }
        return serviceMock;
    }

    /**
     * BehaviorDelegate - Retrofit Service 的代理，用于产生 Mock 数据
     * @param retrofit
     */
    private static BehaviorDelegate getBehaviorDelegate(Retrofit retrofit) {
        BehaviorDelegate<MockRetrofitService> delegate = getMockRetrofit(retrofit)
                .create(MockRetrofitService.class);
        return delegate;
    }

    /**
     * 创建MockRetrofit - 为 Retrofit 添加 Mock 数据（NetworkBehavior 等）
     * @return
     * @param retrofit
     */
    private static MockRetrofit getMockRetrofit(Retrofit retrofit) {
        MockRetrofit mockRetrofit = new MockRetrofit
                .Builder(retrofit)
                .networkBehavior(getNetworkBehavior())
                .build();
        return mockRetrofit;
    }

    /**
     * NetworkBehavior - 网络环境模拟
     * @return
     */
    public static NetworkBehavior getNetworkBehavior() {
        NetworkBehavior networkBehavior = NetworkBehavior.create();
        networkBehavior.setDelay(0, TimeUnit.MILLISECONDS);
        networkBehavior.setVariancePercent(0);
        networkBehavior.setFailurePercent(0);
        return networkBehavior;
    }
}
