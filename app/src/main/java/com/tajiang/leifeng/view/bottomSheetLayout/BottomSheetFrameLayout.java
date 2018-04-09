package com.tajiang.leifeng.view.bottomSheetLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 自定义底部弹出Layout,
 * Created by Admins on 2016/10/25.
 */
public class BottomSheetFrameLayout extends FrameLayout {

    private static final double MAX_HEIGHT_RATIO = 0.6;

    private View BottomBarView;

    public BottomSheetFrameLayout(Context context) {
        this(context, null);
    }

    public BottomSheetFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomSheetFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



}
