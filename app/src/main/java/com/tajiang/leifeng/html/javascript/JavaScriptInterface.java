package com.tajiang.leifeng.html.javascript;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;

import java.math.BigDecimal;

/**
 * Created by Admins on 2016/12/22.
 */
public class JavaScriptInterface {
    Context mContext;
    private onJSIntent2Charge onJSIntent;

    public void setOnJSIntent(JavaScriptInterface.onJSIntent2Charge onJSIntent) {
        this.onJSIntent = onJSIntent;
    }

    public JavaScriptInterface(Context context) {
        mContext = context;
    }

    /**
     * 首页Banner的H5交互 （接收充值金额）
     * @param price
     */
    @JavascriptInterface
    public void callFromJSBasicDataType(double price) {
        LogUtils.e("----> " + price);
        onJSIntent.onJSIntent2Charge(price);
    }

    public interface onJSIntent2Charge {
        public void onJSIntent2Charge(double price);
    }

}
