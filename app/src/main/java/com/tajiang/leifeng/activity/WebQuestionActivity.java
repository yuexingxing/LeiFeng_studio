package com.tajiang.leifeng.activity;

import android.webkit.WebView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;

/**
 * Created by 12154 on 2016/2/26.
 */
public class WebQuestionActivity extends BaseActivity {

    private WebView questionWeb;

    @Override
    protected void initTopBar() {
        setTitle("常见问题");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_web_question);
    }

    @Override
    protected void initView() {
        questionWeb = (WebView) findViewById(R.id.questionWeb);
        questionWeb.getSettings().setJavaScriptEnabled(true);
        questionWeb.loadUrl("http://www.itajiang.com/tajweb/helpcenter");
    }

}
