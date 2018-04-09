package com.tajiang.leifeng.common.http;


/**
 * Created by Administrator on 2016/6/20.
 */
public interface HttpResponseListener {

     void onStart(int requestTag);

     void onSuccess(BaseResponse response, int requestTag);

     void onFailed(BaseResponse response, int requestTag);

     void onFinish(int requestTag);
}
