package com.tajiang.leifeng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.tajiang.leifeng.utils.LogUtils;

/**
 * Created by Admins on 2017/2/22.
 */

public class SmoothScrollLayout extends LinearLayout {

    private static final int DURATION_TIME = 600;  //粘性滚动持续时间毫秒
    private static final int MIN_VELOCITY = 400;

    private Context mContext;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private View mHeaderRootView;
    private ViewGroup.LayoutParams params;

    private int mPointerId;
    private int mMaxScrollHeight;
    private int mMinScrollY;  //可滑动范围的Y轴上边界
    private int mMaxScrollY; //可滑动范围的Y轴下边界
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mTouchSlop;
    private int mLimitedY;
    private int mCurrentTouchY;
    private int mLastTouchY;

    private boolean isLayoutSizeChanged = false;


    public void setmHeaderRootView(View mHeaderRootView) {
        this.mHeaderRootView = mHeaderRootView;
    }

    public SmoothScrollLayout(Context context) {
        super(context);
        this.mContext = context;
        initdata();
    }

    public SmoothScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initdata();
    }

    public SmoothScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initdata();
    }


    private void initdata() {
        mScroller = new Scroller(mContext);
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity() + MIN_VELOCITY;
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    /**
     * 处理触摸滑动事件
     * @param event
     * @return super
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (!isLayoutSizeChanged) {
            params = getLayoutParams();
            mMaxScrollHeight = mHeaderRootView.getHeight();
            params.height = getHeight() + mMaxScrollHeight;
            setLayoutParams(params);
            isLayoutSizeChanged = true;
            LogUtils.e("mMaxScrollHeight ->  " + mMaxScrollHeight);
        }

        obtainVelocityTracker(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mPointerId = event.getPointerId(0);
                mLastTouchY = mCurrentTouchY = (int) event.getY();
                LogUtils.e("ACTION_DOWN --> event.getRawY() " + event.getY() + "getScrollY() " + getScrollY());
                break;

            case MotionEvent.ACTION_MOVE:
                mCurrentTouchY = (int) event.getY();
                int deltaY = mLastTouchY - mCurrentTouchY;
                mLastTouchY = mCurrentTouchY;
                //滑动到目标位置,Y轴滑动范围限制在 0, mMaxScrollHeight)之间
                //手指手势上滑，并且当前位置为上边界 mMaxScrollHeight 时不需要继续上滑，否则将布局上滑
                if (deltaY > 0 && getScrollY() < mMaxScrollHeight) {
                    //如果上滑的目标像素距离会超过边界，需要去除多余的像素
                    if (getScrollY() + deltaY > mMaxScrollHeight) {
                        deltaY = Math.abs(mMaxScrollHeight - getScrollY());
                    }
                    LogUtils.e("ACTION_MOVE --> 上滑 deltaY= " + deltaY + ", getScrollY()" + getScrollY());
                    scrollBy(0, deltaY);
                }
                //手指手势下滑，并且当前位置为下边界 0 时不需要继续下滑，否则将布局下滑
                if (deltaY < 0 && getScrollY() > 0) {
                    //如果下滑的目标像素距离会超过边界，需要去除多余的像素
                    if (getScrollY() + deltaY < 0) {
                        deltaY = getScrollY() * (deltaY < 0 ? -1 : 1);
                    }
                    LogUtils.e("ACTION_MOVE --> 下滑 deltaY= " + deltaY + ", getScrollY()" + getScrollY());
                    scrollBy(0, deltaY);
                }
                break;

            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000,mMaximumVelocity);  //速度单位：500毫秒内移动的像素
                fling(getScrollX(), getScrollY(),
                        0, (int) (mVelocityTracker.getYVelocity(mPointerId) * -1),
                        0, 0, 0, mMaxScrollHeight);
                recycleVelocityTracker();
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();//必须要调用
        } else {
            //结束惯性滑动，触发回弹效果
//            if (getScrollY() < 0) {
//                mScroller.startScroll(getScrollX(), getScrollY(), getScrollX(), -getScrollY(), DURATION_TIME);
//            }
        }
    }

    /**
     *
     * @param startX  X轴起始位置
     * @param startY  Y轴起始位置
     * @param velocityX ACTION_UP时候X轴速度
     * @param velocityY ACTION_UP时候Y轴速度
     * @param minX
     * @param maxX 可惯性滑动X轴滑动范围  minX <= X <= MaxX 坐标（相对于父容器坐标）
     * @param minY
     * @param maxY 可惯性滑动X轴滑动范围  minY <= Y <= MaxY 坐标（相对于父容器坐标）
     */
    private void fling(int startX, int startY, int velocityX, int velocityY,
                      int minX, int maxX, int minY, int maxY) {
        mScroller.fling(startX, startY, velocityX, velocityY,
                minX, maxX, minY, maxY);
        postInvalidate();
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

    private void obtainVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

}
