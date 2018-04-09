package com.tajiang.leifeng.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

import com.tajiang.leifeng.R;

public class CountDownButton extends Button  {

	/** 倒计时总时间 */
	private int sumTime = 60;

	/** 字体大小 */
	private int textSize = 10;
	/** 字体颜色 */
	private int textColor = Color.WHITE;
	/** 等待时字体颜色 */
	private int textColorWaitting = Color.GRAY;
	/** 背景颜色 */
	private int backgroundResId = R.drawable.shape_rect_round_green;
	/** 等待时背景颜色 */
	private int backgroundResIdWaitting = R.drawable.shape_rect_round_gray;

	private Timer timer;
	private TimerTask timerTask;

	/** 当前显示的时间 */
	private int currentTime = sumTime;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (currentTime != 0) {
				setText(currentTime-- + "s");
			} else {
				setText("重新获取");
				setTextColor(textColor);
				setBackgroundResource(backgroundResId);
				timer.cancel();
				setClickable(true);
			}
		}
	};

	public CountDownButton(Context context) {
		this(context, null, 0);
	}

	public CountDownButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CountDownButton(Context context, AttributeSet attrs, int defStyle) {
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

		setText("获取验证码");
		setTextSize(textSize);
		setTextColor(textColor);
		setBackgroundResource(backgroundResId);
		setGravity(Gravity.CENTER);
//		setOnClickListener(this);

	}

	public void setBackground(int idRes){
		setBackgroundResource(idRes);
	}

	/**
	 * 开始计时
	 */
	public void startCount() {

		// 点击的时候重置时间，文字颜色，背景，开始计时
		currentTime = sumTime;
		setTextColor(textColorWaitting);
		setBackgroundResource(backgroundResIdWaitting);

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
		setClickable(false);
	}
	
}
