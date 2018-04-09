package com.tajiang.leifeng.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.utils.DateUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimingButton extends Button {

    /**
     * 倒计时总时间
     */
    private int sumTime = 60;

    /**
     * 字体大小
     */
    private int textSize = 14;
    /**
     * 字体颜色
     */
    private int textColor = Color.WHITE;
    /**
     * 等待时字体颜色
     */
    private int textColorWaitting = Color.GRAY;
    /**
     * 背景颜色
     */
//    private int backgroundResId = R.drawable.shape_rect_round_green;
    /**
     * 等待时背景颜色
     */
    private int backgroundResIdWaitting = R.drawable.shape_rect_round_gray;

    private Timer timer;

    private String desText = "";

    /**
     * 当前显示的时间
     */
    private long currentTime;
    private String timeForm;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            Date date = new Date();
            long residueTime = 15 * 60 * 1000 - (date.getTime() - currentTime);

            if (residueTime >= 1000) {
                setText(desText + DateUtils.getDate(residueTime, timeForm));
            } else {
                timer.cancel();
                setText("订单超时");
            }
        }
    };

    public TimingButton(Context context) {
        this(context, null, 0);
    }

    public TimingButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimingButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initView();
    }

    /**
     * 初始化TextView
     */
    private void initView() {
        setTextSize(textSize);
        setTextColor(textColor);
        setGravity(Gravity.CENTER);
    }

    /**
     * 开始计时
     */
    public void startCount(long createTime) {

        desText = "去付款 | ";
        timeForm = "mm:ss";
        currentTime = createTime;

        // 启动计时任务
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);

    }

    /**
     * 开始计时
     */
    public void startCount(long createTime, String timeForm) {

        this.timeForm = timeForm;
        this.currentTime = createTime;

        // 启动计时任务
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);

    }

}
