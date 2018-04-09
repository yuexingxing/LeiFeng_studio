package com.tajiang.leifeng.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tajiang.leifeng.R;

public class BadgeView extends TextView {

	private int num = 0;
	
	private final int textColor = R.color.white;
	private final int bgColor = R.drawable.shape_circle_black;

	private OnNumChangeListener mOnNumChangeListener;

	public BadgeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initConfig();
	}

	public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
		this.mOnNumChangeListener = onNumChangeListener;
	}

	public interface OnNumChangeListener{
		public void onNumChange(int num);
	}

	private void initConfig() {
		setTextSize(12);
		setTextColor(getResources().getColor(textColor));
		setGravity(Gravity.CENTER);
		setBackgroundResource(bgColor);
		setText("0");
		setVisibility(View.INVISIBLE);
	}

	public int getNum() {
		return num;
	}

	public void setNumAnim(int numGood) {
		
		if (num == 0) {
			setVisibility(View.VISIBLE);
			PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX",0, 1f);
			PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY",0, 1f);
			ObjectAnimator.ofPropertyValuesHolder(this, pvhY, pvhZ).setDuration(300).start();
		}

		setNum(numGood);
	}

	public void setNum(int numGood) {

		/**
		 * 监听购物车的数量变化
		 */
		if (mOnNumChangeListener != null) {
			mOnNumChangeListener.onNumChange(numGood);
		}

		if(numGood == 0){
			setVisibility(View.INVISIBLE);
		}else {
			setVisibility(View.VISIBLE);
		}

		num = numGood;

		setText(numGood + "");
	}

	/** 添加1 */
	public void addNum() {

		if (num == 0) {
			setVisibility(View.VISIBLE);
			PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX",0, 1f);
			PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY",0, 1f);
			ObjectAnimator.ofPropertyValuesHolder(this, pvhY, pvhZ).setDuration(300).start();
		}

		num++;
		/**
		 * 监听购物车的数量变化
		 */
		if (mOnNumChangeListener != null) {
			mOnNumChangeListener.onNumChange(num);
		}
		setText(num + "");
	}

	/** 减1 */
	public void cutNum() {

		if (num == 0) {
			
		} else if (num >= 1) {

			if (num == 1) {

				num--;

				setText(num + "");

				PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0);
				PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0);
				ObjectAnimator.ofPropertyValuesHolder(this, pvhY, pvhZ).setDuration(300).start();
			}

			else {
				num--;
				setText(num + "");
			}

		}
		/**
		 * 监听购物车的数量变化
		 */
		if (mOnNumChangeListener != null) {
			mOnNumChangeListener.onNumChange(num);
		}
	}

}
