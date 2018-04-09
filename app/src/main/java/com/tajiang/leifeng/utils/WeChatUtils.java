package com.tajiang.leifeng.utils;

import android.content.Context;

import com.tajiang.leifeng.constant.TJCst;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by 12154 on 2015/12/10.
 */
public class WeChatUtils {

    public static boolean isWXAppInstalledAndSupported(Context context) {

        IWXAPI api = WXAPIFactory.createWXAPI(context, TJCst.WEIXIN_APP_ID, false);

        boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled() && api.isWXAppSupportAPI();
        if (!sIsWXAppInstalledAndSupported) {
            ToastUtils.showShort("微信客户端未安装");
        }
        return sIsWXAppInstalledAndSupported;
    }
    
}
