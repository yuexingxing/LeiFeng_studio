package com.tajiang.leifeng.common.retrofit;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.HomeActivity;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.GlobalErrorCode;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.ToastUtils;

import rx.Subscriber;

/**
 * Created by Admins on 2016/12/27.
 */
public class TJSubscriber<T> extends Subscriber<BaseResponse<T>> {

    private HttpResponseListener listener;

    private int requestTag = TJRequestTag.TAG_DEFAULT;

    public TJSubscriber(HttpResponseListener listener, int tag) {
        this.listener = listener;
        this.requestTag = tag;
    }


    @Override
    public void onStart() {
        LogUtils.d("onStart");
        listener.onStart(requestTag);
    }

    @Override
    public void onCompleted() {
        LogUtils.d("onCompleted");
        listener.onFinish(requestTag);
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.d("onError");
        LogUtils.e("Throwable:" + e.toString());
        ToastUtils.showShort(R.string.msg_http_error);
        Log.i("hexiuhui----", "error=" + e.toString());
        listener.onFinish(requestTag);
    }

    @Override
    public void onNext(BaseResponse response) {
        LogUtils.d("onSuccess");
        if (response.isSuccess()) {
            listener.onSuccess(response, requestTag);
        } else {
            ToastUtils.showShort(response.getMessage());
//            handleOutLogin(response);
        }
    }

    /**
     * 统一处理未登录
     * @param response
     */
    private void handleOutLogin(BaseResponse response) {
        if(!TextUtils.isEmpty(response.getMoreInfo()) && response.getErrorCode() != GlobalErrorCode.UNAUTHORIZED) {
            com.tajiang.leifeng.utils.ToastUtils.showShort(response.getMoreInfo());
        }
        if(response.getErrorCode() == GlobalErrorCode.UNAUTHORIZED) {
            if(!TJApp.getIns().isShowLogin()) {
                TJApp.getIns().setShowLogin(true);
                Intent intent = new Intent("com.tajiang.leifeng.action.LOGIN");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                TJApp.getIns().startActivity(intent);
                /**
                 * 由于HomeActivity为singleInstance,结束HomeActivity对象（若存在）
                 * 清除已过时的登录信息，以免重用旧的实例。同时清空登录信息
                 */
                SharedPreferencesUtils.remove(TJApp.getIns().getApplicationContext(), SharedPreferencesUtils.USER_LOGIN_INFOR);
                TJApp.getIns().finishActivity(HomeActivity.class);
            }
        } else {
            listener.onFailed(response, requestTag);
        }
    }

}
