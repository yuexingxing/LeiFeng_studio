package com.tajiang.leifeng.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.utils.AccessTokenKeeper;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.WeChatUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by ciko on 16/4/22.
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    private Context mContext;

    private String mTitle;
    private String mContent;
    private String mUrlShare;

    private View rect_weiboShareWeb;
    private View rect_weChatShareWeb;
    private View rect_circleShareWeb;

    private View contentView;

    private Bitmap bitmapNet;

    public ShareDialog(Context context, String title, String content, String urlShare,Integer imgResId) {
        super(context, R.style.ActionSheetDialogStyle);

        init(context, title, content, urlShare);
        bitmapNet = BitmapFactory.decodeResource(context.getResources(), imgResId);

    }

    public ShareDialog(Context context, String title, String content, String urlShare) {
        super(context, R.style.ActionSheetDialogStyle);

        init(context, title, content, urlShare);
        bitmapNet = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_default);

    }

    private void init(Context context, String title, String content, String urlShare) {
        mContext = context;
        mTitle = title;
        mContent = content;
        mUrlShare = urlShare;


        contentView = getLayoutInflater().inflate(R.layout.dialog_order_share_buttom_web, null);

        getWindow().setGravity(Gravity.BOTTOM);

        rect_weiboShareWeb = contentView.findViewById(R.id.rect_weiboShareWeb);
        rect_weChatShareWeb = contentView.findViewById(R.id.rect_weChatShareWeb);
        rect_circleShareWeb = contentView.findViewById(R.id.rect_circleShareWeb);

        rect_weiboShareWeb.setOnClickListener(this);
        rect_weChatShareWeb.setOnClickListener(this);
        rect_circleShareWeb.setOnClickListener(this);

        setContentView(contentView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rect_weiboShareWeb:
                sendMultiMessage(bitmapNet, mTitle, mContent, mUrlShare );
                break;
            case R.id.rect_weChatShareWeb:
                wechatShare(0, bitmapNet, mTitle, mContent, mUrlShare);
                break;
            case R.id.rect_circleShareWeb:
                wechatShare(1, bitmapNet, mTitle, mContent, mUrlShare);
                break;
        }
        dismiss();
    }

    /**
     * 微博分享
     */
    private void sendMultiMessage(Bitmap imgBitmap, String title, String description, String shareUrl) {

        // 注册微博
        IWeiboShareAPI mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, TJCst.WEIBO_APP_KEY);
        mWeiboShareAPI.registerApp();

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.mediaObject = getWebpageObj(imgBitmap, title, description, shareUrl);
        weiboMessage.imageObject = getImageObj(imgBitmap);

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        AuthInfo authInfo = new AuthInfo(mContext, TJCst.WEIBO_APP_KEY, TJCst.WEIBO_REDIRECT_URL, TJCst.WEIBO_SCOPE);

        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mContext);

        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }

        mWeiboShareAPI.sendRequest((Activity) mContext, request, authInfo, token, new WeiboAuthListener() {
            @Override
            public void onWeiboException(WeiboException arg0) {}
            @Override
            public void onComplete(Bundle bundle) {
                ToastUtils.showShort("分享成功");
            }
            @Override
            public void onCancel() {
                ToastUtils.showShort("取消分享");
            }
        });
    }

    /**
     * 创建多媒体（网页）消息对象
     */
    private WebpageObject getWebpageObj(Bitmap imgBitmap, String title, String description, String shareUrl) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title; // 标题
        mediaObject.description = description; // 描述
        mediaObject.setThumbImage(imgBitmap); // 图标
        mediaObject.actionUrl = shareUrl; // 网页地址
        mediaObject.defaultText = ""; // Webpage 默认文案
        return mediaObject;
    }


    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 微信分享
     */
    private void wechatShare(int flag, Bitmap imgBitmap, String title, String description, String shareUrl) {

        if (WeChatUtils.isWXAppInstalledAndSupported(mContext)) {
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = shareUrl;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = title;
            msg.description = description;
            msg.setThumbImage(imgBitmap);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = msg;
            req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
            WXAPIFactory.createWXAPI(mContext, TJCst.WEIXIN_APP_ID).sendReq(req);
        }

    }

}
