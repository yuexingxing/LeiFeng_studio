package com.tajiang.leifeng.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.utils.DateUtils;
import com.tajiang.leifeng.utils.ToastUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ciko on 16/5/23.
 */
public class OrderStateBtnView extends Button {

    public static final int TRANSFER_MODE = 1;  //配送倒计时
    public static final int PAY_MODE = 2;     //支付倒计时

    private int mode = PAY_MODE;
    private long currentTime = 0;

    private TimerTask timerTask;
    private Timer timer;
    private OnTimeOverListener timeOverListener;

    public void setTimeOverListener(OnTimeOverListener timeOverListener) {
        this.timeOverListener = timeOverListener;
    }

    public interface OnTimeOverListener{
        public void onTimeOver();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            long residueTime = Order.COUNT_TIME_TRANSFER - (new Date().getTime() - currentTime);//距下单15分钟剩余倒计时时间

            if (residueTime >= 1000) {
                if (OrderStateBtnView.this.mode == PAY_MODE) {
                    setText("去支付(" + DateUtils.getDate(residueTime, "mm:ss") + ")");
                }
//                else {
//                    setText("配送倒计时(" + DateUtils.getDate(residueTime, "mm:ss") + ")");
//                }
            } else {
                timer.cancel();
                if (OrderStateBtnView.this.mode == PAY_MODE) {
                    setText("订单超时");
                    // 订单超时以后不可点击付款
                    setEnabled(false);
                } else {
                    OrderStateBtnView.this.timeOverListener.onTimeOver();
                    setText("联系送餐人");
                }
            }
        }
    };


    public OrderStateBtnView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setState(Order order, int mode) {
        this.mode = mode;
        cannelTimer();

        switch (order.getOrderState()) {
            case Order.STATE_NO_PAY:
                setVisibility(VISIBLE);
                long createDate = DateUtils.stringToLong(order.getCreateDate(), "yyyy-MM-dd HH:mm");
                startCount(createDate);
                break;
            case Order.STATE_PAY:
                /**
                 * 订单已送达后不需要进入此操作，否则倒计时在付款15分钟后显示配送中
                 */
                if (order.getOrderState() != Order.STATE_TRANSFERED) {
                    startCount(order.getUpdatedAt());
                }
                break;
            case Order.STATE_TAKE:
            case Order.STATE_COMPLETE:
                break;
            case Order.STATE_CANNEL_1:
            case Order.STATE_CANNEL_2:
            case Order.STATE_CANNEL_3:
            case Order.STATE_CANNEL_4:
                setVisibility(GONE);
                break;
        }
    }

    /**
     * 开始计时
     */
    public void startCount(long time) {
        // 点击的时候重置时间，文字颜色，背景，开始计时
        currentTime = time;

        // 启动计时任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);

        // 计时期间不可点击
//        setClickable(false);
    }

    public void cannelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
