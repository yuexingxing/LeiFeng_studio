package com.tajiang.leifeng.common.mock.service;


import com.tajiang.leifeng.common.http.BaseResponse;

/**
 * Created by Admins on 2017/2/9.
 */

public abstract class MockService {
    /**
     * 每一个子类实现此抽象方法 用于返回手动生成的Mock数据
     * @param flag
     * @return json字符串
     */
    public abstract String getJsonData(boolean flag );

    /**
     * 每一个子类实现此抽象方法 用于返回手动生成的Mock数据
     * @param flag
     * @return BaseResponse
     */
    public abstract BaseResponse getResponseData(boolean flag, Object data);
}
