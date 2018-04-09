package com.tajiang.leifeng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 12154 on 2016/1/13.
 */
public class SquareTextView extends TextView {
    public SquareTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), (getDefaultSize(0, widthMeasureSpec)));
    }
}
