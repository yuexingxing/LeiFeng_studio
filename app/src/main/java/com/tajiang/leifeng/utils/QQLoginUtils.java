package com.tajiang.leifeng.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tajiang.leifeng.constant.TJCst;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * QQ 登录工具类
 */
public class QQLoginUtils {

    private String qqAppid = TJCst.QQ_APP_ID;
    private Context context;
    private Tencent tencent;
    private QQLoginListener qqLoginListener;

    public QQLoginUtils(Context context) {
        this.context = context;
        this.tencent = Tencent.createInstance(qqAppid, context);
    }

    public Tencent onClickQQLogin() {
        if (!tencent.isSessionValid()) {
            tencent.login((Activity) context, "all", loginListener);
        } else {
            tencent.logout(context);
        }
        return tencent;
    }

    public void setQQLoginListener(QQLoginListener qqLoginListener) {
        this.qqLoginListener = qqLoginListener;
    }

    public interface QQLoginListener {
        public void onLoginInQQSuccess(JSONObject jsonObject);
// 		public void loginGetUserInforSuccess(JSONObject jsonObject);
    }

    /**
     * 分享到QQ
     */
    public void shareQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
//      params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
        tencent.shareToQQ((Activity) context, params, shareListener);
    }

    /**
     * 分享到QQ空间
     */
    public void shareToQzone(String imgUrl, String title, String description, String shareUrl) {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, description);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareUrl);//必填
//        ArrayList<String> imageUrls = new ArrayList<>();
//        imageUrls.add(imgUrl);
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        tencent.shareToQzone((Activity) context, params, shareListener);
    }

    private IUiListener loginListener = new IUiListener() {

        @Override
        public void onComplete(Object response) {

            if (null == response) {
                ToastUtils.showShort("登录失败");
                return;
            }

            JSONObject jsonResponse = (JSONObject) response;

            if (null != jsonResponse && jsonResponse.length() == 0) {
                ToastUtils.showShort("登录失败");
                return;
            }

            ToastUtils.showShort("登录成功");
            doComplete((JSONObject) response);
        }

        // 登录完成
        protected void doComplete(JSONObject values) {
            qqLoginListener.onLoginInQQSuccess(values);
        }

        // 错误
        @Override
        public void onError(UiError e) {
            ToastUtils.showShort("onError: " + e.errorDetail);
        }

        // 取消登录
        @Override
        public void onCancel() {
            ToastUtils.showShort("取消登录");
        }
    };


    private IUiListener shareListener = new IUiListener() {
        @Override
        public void onComplete(Object response) {

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    };

}
