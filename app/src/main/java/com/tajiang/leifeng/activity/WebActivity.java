package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.html.javascript.JavaScriptInterface;
import com.tajiang.leifeng.model.Action;
import com.tajiang.leifeng.model.Adv;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.model.Voucher;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.dialog.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WebActivity extends BaseActivity implements BaseActivity.OnClickNavRightListener {

    private String url;
    private String titleHtml;
    private String contentHtml;

    private Adv adv;

    private WebView webview;

    private JavaScriptInterface javaScriptInterface;

    // webViMain.setWebViewClient(new WebViewClient() {});
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            logE(title);
            WebActivity.this.titleHtml = title;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        adv = (Adv) getIntent().getSerializableExtra("ADV");
        titleHtml = contentHtml = adv.getTitle();

        try {
            JSONObject jsonObject = new JSONObject(adv.getContent());
            url = jsonObject.getString("url");
            //Log.d("type=5",url);
            if (url.indexOf("?type=5") != -1) {
                User user = (User) getIntent().getSerializableExtra("user");
                url = url + "&partnerCode=QMLF&partnerUserId=" + user.getId() + "&tel=" + user.getPhone() + "&source=app";
                // ToastUtils.showLong("人肉拼接：：："+url);
                //Log.d("type=5","人肉拼接：：："+url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);

    }

    protected void initTopBar() {
        setTitle(titleHtml + "");
        enableNavLeftImage();
        if (adv.getType() != 5) {
            enableNavRightImage(R.drawable.icon_share);
            setOnClickNavRightListener(this);
        }
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_web);
    }

    protected void initView() {
        webview = (WebView) findViewById(R.id.webview);
        webViewSetting();
    }

    private void webViewSetting() {

        webview.setWebViewClient(new WebViewClient() {});

        webview.getSettings().setJavaScriptEnabled(true);  //设置javascript 可用
//        webview.loadDataWithBaseURL(url,getNewContent(htmltext2), "text/html", "utf-8", null);
        webview.loadUrl(url);
        Log.i("hexiuhui---", url);
        webview.setWebChromeClient(webChromeClient);
        //设置加载进来的页面自适应手机屏幕
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        //设置充值卡H5的交互接口
        webview.addJavascriptInterface(getJavaScriptInterface() , "android_charge");
        //html的图片就会以单列显示就不会变形占了别的位置
//        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置支持缩放
//        webview.getSettings().setSupportZoom(true);
        // 设置缩放工具的显示
//        webview.getSettings().setBuiltInZoomControls(true);
    }

    private JavaScriptInterface getJavaScriptInterface() {
        javaScriptInterface = new JavaScriptInterface(TJApp.getContext());
        javaScriptInterface.setOnJSIntent(new JavaScriptInterface.onJSIntent2Charge() {
            @Override
            public void onJSIntent2Charge(double price) {
                //是否登录
                if (UserUtils.getInstance().isLoginWithLogin(WebActivity.this)) {
                    //充值跳转
                    Action action = new Action();
                    action.setName(price + "元");
                    action.setPreAmountStart(price);
                    action.setAwardAmount(0);

                    Intent intent = new Intent(WebActivity.this , UserWalletAddPayActivity.class);
                    intent.putExtra("Action", action);
                    startActivity(intent);
                }
            }
        });
        return javaScriptInterface;
    }

    public void onClickNavRight() {
        ShareDialog shareDialog = new ShareDialog(this,titleHtml,contentHtml,url);
        shareDialog.show();
    }

}
