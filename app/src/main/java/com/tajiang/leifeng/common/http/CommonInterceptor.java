package com.tajiang.leifeng.common.http;

import android.util.Log;

import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.constant.ApiCst;
import com.tajiang.leifeng.utils.MD5;
import com.tajiang.leifeng.utils.UserUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 封装公共参数
 * hexiuhui
 */
public class CommonInterceptor implements Interceptor {
    private static String mAppId;
    private static String mVersion;
    private static String mTimestamp;

    public CommonInterceptor(String appid, String version, String timestamp) {
        this.mAppId = appid;
        this.mVersion = version;
        this.mTimestamp = timestamp;
    }

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request oldRequest = chain.request();
        long t1 = System.nanoTime();//请求发起的时间

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("appid", mAppId)
                .addQueryParameter("version", mVersion)
                .addQueryParameter("timestamp", mTimestamp);

        if (UserUtils.getInstance().isLogin()) {
            authorizedUrlBuilder.addQueryParameter("token", TJApp.token);
        }

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

//        return chain.proceed(newRequest);

        Response response = chain.proceed(newRequest);
        long t2 = System.nanoTime();//收到响应的时间
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.d("CSDN_LQR",
                String.format("接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
                        response.request().url(),
                        responseBody.string(),
                        (t2 - t1) / 1e6d,
                        response.headers() + newRequest.method()
                ));
        return response;

//        Request oldRequest = chain.request();
//        long t1 = System.nanoTime();//请求发起的时间
//
//        Log.i("hexiuhui----", "method==" + oldRequest.method());
//
//        if (oldRequest.method().equals("GET")) {
//            oldRequest = addGetParams(oldRequest);
//            Log.d("CSDN_LQR",String.format("发送请求 %s on %s%n%s",
//                    oldRequest.url(), chain.connection(), oldRequest.headers()));
//        } else if (oldRequest.method().equals("POST")) {
//            oldRequest = addPostParams(oldRequest);
//
//            StringBuilder sb = new StringBuilder();
//            if (oldRequest.body() instanceof FormBody) {
//                FormBody body = (FormBody) oldRequest.body();
//                for (int i = 0; i < body.size(); i++) {
//                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
//                }
//                sb.delete(sb.length() - 1, sb.length());
//                Log.d("CSDN_LQR",String.format("发送请求 %s on %s %n%s %nRequestParams:{%s}",
//                        oldRequest.url(), chain.connection(), oldRequest.headers(), sb.toString()));
//            }
//        }
//
//        Response response = chain.proceed(oldRequest);
//        long t2 = System.nanoTime();//收到响应的时间
//        //这里不能直接使用response.body().string()的方式输出日志
//        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
//        //个新的response给应用层处理
//        ResponseBody responseBody = response.peekBody(1024 * 1024);
//        Log.d("CSDN_LQR",
//                String.format("接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
//                        response.request().url(),
//                        responseBody.string(),
//                        (t2 - t1) / 1e6d,
//                        response.headers()
//                ));
//        return response;
    }

    //get请求 添加公共参数 签名
    private Request addGetParams(Request request) {
        //添加公共参数
        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("appid", mAppId)
                .addQueryParameter("version", mVersion)
                .addQueryParameter("timestamp", mTimestamp)
                .build();

        //添加签名
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        Collections.sort(nameList);

        Map<String, String> bodyMap = new HashMap<>();

//        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < nameList.size(); i++) {
//            buffer.append("&").append(nameList.get(i)).append("=").append(httpUrl.queryParameterValues(nameList.get(i)) != null &&
//                    httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ? httpUrl.queryParameterValues(nameList.get(i)).get(0) : "");

            bodyMap.put(nameList.get(i), httpUrl.queryParameterValues(nameList.get(i)).get(i));
        }

        httpUrl = httpUrl.newBuilder()
                .addQueryParameter("sign", MD5.getMd5Sign(bodyMap, ApiCst.DEV_AppKey))
                .build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    //post 添加签名和公共参数
    private static Request addPostParams(Request request) throws UnsupportedEncodingException {
        Log.i("hexiuhui========", "2222222222" + request.body());
        if (request.body() instanceof FormBody) {
            Log.i("hexiuhui========", "11111111111111");
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }

            formBody = bodyBuilder
                    .addEncoded("appid", mAppId)
                    .addEncoded("version", mVersion)
                    .addEncoded("timestamp", mTimestamp)
                    .build();

            Map<String, String> bodyMap = new HashMap<>();

            for (int i = 0; i < formBody.size(); i++) {
                bodyMap.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
            }

            bodyBuilder.addEncoded("sign", MD5.getMd5Sign(bodyMap, ApiCst.DEV_AppKey));
            bodyBuilder.build();
            request = request.newBuilder().post(formBody).build();
        }
        return request;
    }

//    /**
//     * 为MultipartBody类型请求体添加参数
//     *
//     * @param body
//     * @param paramsBuilder
//     * @return
//     */
//    private MultipartBody addParamsToMultipartBody(MultipartBody body) {
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//        for (int i = 0; i<body.size(); i ++) {
//            builder.addFormDataPart(body.)
//        }
//
//        //添加appcode
//        String appcode = context.getString(R.string.appkey);
//        builder.addFormDataPart("appcode", appcode);
//
//        //添加id，city参数
//        User user = BaseRepository.getUser();
//        if (user != null) {
//            String id = user.id() + "";
//            UserType userType = user.userType();
//
//            if (userType == UserType.BEAUTICIAN) {
//                builder.addFormDataPart("beautician_id", id);
//
//            } else  if (userType == UserType.BEAUTYSHOP){
//                builder.addFormDataPart("bp_id", id);
//
//            }
//
//            //城市
//            String city = user.city();
//
//            builder.addFormDataPart("city", city);
//        }
//
//        //添加原请求体
//        for (int i = 0; i < body.size(); i++) {
//            builder.addPart(body.part(i));
//        }
//
//        return builder.build();
//    }
}
