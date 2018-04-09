package com.tajiang.leifeng.common.mock;

import android.support.annotation.Nullable;

import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.mock.service.MockService;
import com.tajiang.leifeng.utils.LogUtils;

/**
 * Created by Admins on 2017/2/9.
 */

public class MockUtil {
    private static final String MOCK_CLASS_NAME = "com.tajiang.leifeng.common.mock.MockDataInfo";
    private static MockUtil mockUtil;

    private MockUtil() {}

    public synchronized static MockUtil getInstance() {
        if (mockUtil == null) {
            mockUtil = new MockUtil();
        }
        return mockUtil;
    }

    /**
     *
     * @param flag
     * @param data
     * @return
     */
    public BaseResponse getData(boolean flag, Object data, @Nullable String className) {
        BaseResponse response = null;
        MockService mockService = null;
        String strResponse = null;
        try {
            mockService = (MockService) Class.forName(className == null ? MOCK_CLASS_NAME : className).newInstance();
            strResponse = mockService.getJsonData(flag);
            response = mockService.getResponseData(flag, data);

        } catch (InstantiationException e) {
            e.printStackTrace();
            strResponse = "";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            strResponse = "";
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            strResponse = "";
        }
        LogUtils.i("strResponse", strResponse);
        return response;
    }

}
