package com.tajiang.leifeng.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tajiang.leifeng.activity.HomeActivity;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.TokenInfo;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.GsonObjUtil;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONObject;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler, HttpResponseListener {

    private String accessToken;
    private String unionid;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, TJCst.WEIXIN_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq arg0) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {

        String result = "";
        String code = "";

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:

                try {
                    result = "操作成功";
                    code = ((SendAuth.Resp) resp).code;
                    if (resp instanceof SendAuth.Resp) {
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + TJCst.WEIXIN_APP_ID + "&secret=" + TJCst.WEIXIN_APP_SECRET + "&code=" + code + "&grant_type=authorization_code",
                                new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        String response = new String(responseBody);

                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            if (jsonObject != null) {
                                                String mAccessToken = jsonObject.getString("access_token");
                                                String unionid = jsonObject.getString("unionid");

                                                WXEntryActivity.this.accessToken = mAccessToken;
                                                WXEntryActivity.this.unionid = unionid;

                                                TJHttpUtil.getInstance(WXEntryActivity.this).doLoginByWeiChat(accessToken, WXEntryActivity.this.unionid);

                                            }
                                        } catch (Exception e) {

                                        }
                                        LogUtil.i("WXEntryActivity", "get wx access_token response:" + response);
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        if (responseBody != null)
                                            LogUtil.i("WXEntryActivity", "get wx onFailure:" + new String(responseBody));
                                        return;
                                    }

                                });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }

        finish();

        if (TextUtils.isEmpty(result)) {
            ToastUtils.showShort(result);
        }

    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {

            case TJRequestTag.TAG_USER_LOGIN: // 登录请求
                TokenInfo info = (TokenInfo) response.getData();
                TJApp.token = info.getToken();
                TJApp.timeout = info.getTimeout();

                SharedPreferencesUtils.put(this, SharedPreferencesUtils.USER_TOKENINFO, GsonObjUtil.getGsonInstance().toJson(info));

                //登录完成获取用户信息
                TJHttpUtil.getInstance(this).userDoGetInfor();
                break;
            case TJRequestTag.TAG_USER_INFO:
                User user = (User) response.getData();
                UserUtils.getInstance().saveUserInfor(GsonObjUtil.getGsonInstance().toJson(user));
                UserUtils.getInstance().initUser();

                Intent intent1 = new Intent(this, HomeActivity.class);
                startActivity(intent1);
                break;

            case TJRequestTag.TAG_USER_LOGIN_OTHER:

                if ((Boolean) response.getData()) {
//                    TJHttpUtil.getInstance(this).doLogin(WXEntryActivity.this.unionid, accessToken);  //暂时注释
                } else {
                    ToastUtils.showShort("服务器注册用户失败");
                }
                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        Toast.makeText(WXEntryActivity.this, response.getMoreInfo(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish(int requestTag) {

    }
}
