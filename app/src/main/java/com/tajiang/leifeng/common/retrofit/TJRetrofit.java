package com.tajiang.leifeng.common.retrofit;

import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.common.retrofit.cookieJar.ClearableCookieJar;
import com.tajiang.leifeng.common.retrofit.cookieJar.PersistentCookieJar;
import com.tajiang.leifeng.common.retrofit.cookieJar.cache.SetCookieCache;
import com.tajiang.leifeng.common.retrofit.cookieJar.persistence.SharedPrefsCookiePersistor;
import com.tajiang.leifeng.constant.ApiCst;
import com.tajiang.leifeng.constant.TJCst;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admins on 2016/12/27.
 */
public class TJRetrofit {

    private static RetrofitService service;

    public static RetrofitService createRetrofitService() {
        if(service == null) {
            synchronized (TJRetrofit.class) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiCst.ROOT_URL)
                        .client(TJApp.getIns().getOkHttpClient())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(RetrofitService.class);
            }
        }
        return service;
    }

}