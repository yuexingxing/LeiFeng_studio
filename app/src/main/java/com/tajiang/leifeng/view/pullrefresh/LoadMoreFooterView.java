package com.tajiang.leifeng.view.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by Admins on 2016/10/26.
 */

public class LoadMoreFooterView extends LayoutFooterView implements SwipeTrigger, SwipeLoadMoreTrigger {
    public LoadMoreFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onLoadMore() {

    }

    @Override
    public void onPrepare() {
        textView.setText("上拉加载更多");
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {

    }

    @Override
    public void onRelease() {
        textView.setText("加载中...");
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onComplete() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onReset() {

    }

    public void setFootTextView(String msg) {
        textView.setText(msg);
    }
}
