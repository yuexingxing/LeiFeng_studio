package com.tajiang.leifeng.common.mock;

import android.util.Log;

import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.mock.service.MockService;
import com.tajiang.leifeng.utils.GsonObjUtil;
import com.tajiang.leifeng.utils.LogUtils;

/**
 * 在具体调用接口的地方  使用反射 找到该类代用相关方法获取数据
 * Created by Admins on 2017/2/9.
 *  @param <T>  BaseResponse中获取的对象的泛型
 */
public class MockDataInfo<T> extends MockService {
    private BaseResponse response = new BaseResponse();

    @Override
    public String getJsonData(boolean flag) {
        return null;
    }

    /**
     * @param flag true 获取成功， false获取失败
     * @return BaseResponse
     */
    @Override
    public BaseResponse getResponseData(boolean flag, Object data) {
        response.setData(data);
        if (flag) {  //获取成功
            response.setError("获取成功");
            response.setMoreInfo("获取成功");
            response.setErrorCode(10000);

        } else { //信息获取失败
            response.setError("获取失败");
            response.setMoreInfo("获取失败");
            response.setErrorCode(12000);
        }
        LogUtils.i("error", response.getError() + "");
        LogUtils.i("response", response.toString());
        return response;
    }

}