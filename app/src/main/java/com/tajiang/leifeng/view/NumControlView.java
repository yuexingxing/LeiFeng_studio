package com.tajiang.leifeng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tajiang.leifeng.R;

public class NumControlView extends LinearLayout implements OnClickListener {

	private String CountNumberUnit = "";    //分数单位（4份）

	private int num = 0;

	private int widthView;
	private int heightView;

	private ImageView iv_add;
	private ImageView iv_cut;

	private TextView tv_num;

	private OnNumChangeListener onNumChangeListener;
	
	public NumControlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initConfig();
		initLayout();
	}

	public void setCountNumberUnit(String countNumberUnit) {
		CountNumberUnit = countNumberUnit;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		widthView = MeasureSpec.getSize(widthMeasureSpec);
		heightView = MeasureSpec.getSize(heightMeasureSpec);

		initMeasure(widthView, heightView);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private void initConfig() {
		setOrientation(LinearLayout.HORIZONTAL);
	}

	private void initLayout() {

		iv_add = new ImageView(getContext());
		iv_cut = new ImageView(getContext());
		iv_cut.setVisibility(View.INVISIBLE);

		tv_num = new TextView(getContext());

		tv_num.setText(num + CountNumberUnit);
		tv_num.setTextSize(16);
		tv_num.setGravity(Gravity.CENTER);
		tv_num.setVisibility(View.INVISIBLE);
		tv_num.setTextColor(getResources().getColor(R.color.black));

		LayoutParams textLayoutParams = new LayoutParams(0,
				LayoutParams.MATCH_PARENT);
		textLayoutParams.weight = 1;

		tv_num.setLayoutParams(textLayoutParams);

		iv_add.setImageResource(R.drawable.icon_add_nornal);
		iv_cut.setImageResource(R.drawable.icon_cut_normal);

		iv_add.setOnClickListener(this);
		iv_cut.setOnClickListener(this);

		addView(iv_cut);
		addView(tv_num);
		addView(iv_add);

	}

	private void initMeasure(int widthView, int heightView) {

		LayoutParams imageLayoutParams = new LayoutParams(heightView,
				heightView);

		iv_add.setLayoutParams(imageLayoutParams);
		iv_cut.setLayoutParams(imageLayoutParams);

	}

	@Override
	public void onClick(View v) {

		if (v == iv_add) {
			addNum();
			if(onNumChangeListener != null){
				onNumChangeListener.OnAddButtonClick( iv_add,num);
			}
		}

		else if (v == iv_cut) {
			cutNum();
			if(onNumChangeListener != null){
				onNumChangeListener.OnCutButtonClick(iv_cut,num);
			}
		}

	}

	public void addNum() {

		num++;
		tv_num.setText(num + CountNumberUnit);

		if (num == 1) {
			tv_num.setVisibility(View.VISIBLE);
			iv_cut.setVisibility(View.VISIBLE);
		}

	}

	public void cutNum() {
		if (num == 0) {

		} else if (num >= 1) {

			num--;

			if (num == 0) {
				iv_cut.setVisibility(View.INVISIBLE);
				tv_num.setVisibility(View.INVISIBLE);
			}

			tv_num.setText(num + CountNumberUnit);

		}
	}
	
	public int getNum() {
		return num;
	}

	public void setAddAndCutImgVisibility(boolean visibility) {
		iv_cut.setVisibility(visibility == true ? View.VISIBLE :INVISIBLE);
		iv_add.setVisibility(visibility == true ? View.VISIBLE :INVISIBLE);
	}

	public void setNumGood(int num) {
		
		this.num = num;
		
		if(num == 0){
			iv_cut.setVisibility(View.INVISIBLE);
			tv_num.setVisibility(View.INVISIBLE);
		}else{
			tv_num.setText(num + CountNumberUnit);
			tv_num.setVisibility(View.VISIBLE);
			iv_cut.setVisibility(View.VISIBLE);
		}
	}
	
	public OnNumChangeListener getOnNumChangeListener() {
		return onNumChangeListener;
	}

	public void setOnNumChangeListener(OnNumChangeListener onNumChangeListener) {
		this.onNumChangeListener = onNumChangeListener;
	}

	public interface OnNumChangeListener{
		public void OnAddButtonClick(View view,int num);
		public void OnCutButtonClick(View view,int num);
	}

}
