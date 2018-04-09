package com.tajiang.leifeng.view.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.tajiang.leifeng.R;

/**
 * Created by Admins on 2016/10/26.
 */
public class RefreshHeadView extends LayoutHeaderView implements SwipeRefreshTrigger, SwipeTrigger {

    public RefreshHeadView(Context context) {
        this(context, null);
    }

    public RefreshHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onPrepare() {
        gifImageView.setImageResource(R.drawable.load_start);

    }

    @Override
    public void onMove(int i, boolean b, boolean b1) {

    }

    @Override
    public void onRelease() {
        gifImageView.setImageResource(R.drawable.load_ing);

    }

    @Override
    public void onComplete() {
        gifImageView.setImageResource(R.drawable.load_finish);

    }

    @Override
    public void onReset() {

    }
}