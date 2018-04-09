package com.tajiang.leifeng.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.utils.DensityUtils;

public class StartView extends LinearLayout implements OnClickListener {

	private Context mContext;

	private LinearLayout linearLayout_1;
	private LinearLayout linearLayout_2;
	private LinearLayout linearLayout_3;
	private LinearLayout linearLayout_4;
	private LinearLayout linearLayout_5;
	
	private List<ImageView> startList = new ArrayList<ImageView>();
	private List<LinearLayout> linearLayoutList = new ArrayList<LinearLayout>();

	private OnStarChooseListener starChooseListener;

	private int startNum = 0;

	public StartView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;

		initLayout();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void initLayout() {

		int startWidth = DensityUtils.dp2px(getContext(), 20);

		LayoutParams layoutParams = new LayoutParams(startWidth, startWidth);
		layoutParams.gravity = Gravity.CENTER;
		
		ImageView start_1 = new ImageView(mContext);
		ImageView start_2 = new ImageView(mContext);
		ImageView start_3 = new ImageView(mContext);
		ImageView start_4 = new ImageView(mContext);
		ImageView start_5 = new ImageView(mContext);
		
		start_1.setScaleType(ScaleType.FIT_XY);
		start_2.setScaleType(ScaleType.FIT_XY);
		start_1.setScaleType(ScaleType.FIT_XY);
		start_1.setScaleType(ScaleType.FIT_XY);
		start_1.setScaleType(ScaleType.FIT_XY);

		start_1.setLayoutParams(layoutParams);
		start_2.setLayoutParams(layoutParams);
		start_3.setLayoutParams(layoutParams);
		start_4.setLayoutParams(layoutParams);
		start_5.setLayoutParams(layoutParams);

		start_1.setImageResource(R.drawable.icon_start_normal);
		start_2.setImageResource(R.drawable.icon_start_normal);
		start_3.setImageResource(R.drawable.icon_start_normal);
		start_4.setImageResource(R.drawable.icon_start_normal);
		start_5.setImageResource(R.drawable.icon_start_normal);
		
		startList.add(start_1);
		startList.add(start_2);
		startList.add(start_3);
		startList.add(start_4);
		startList.add(start_5);
		
		LayoutParams linearLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, 1);
		linearLayoutParams.gravity = Gravity.CENTER;
		
		linearLayout_1 = new LinearLayout(getContext()); 
		linearLayout_2 = new LinearLayout(getContext()); 
		linearLayout_3 = new LinearLayout(getContext()); 
		linearLayout_4 = new LinearLayout(getContext()); 
		linearLayout_5 = new LinearLayout(getContext()); 
		
		linearLayout_1.setLayoutParams(linearLayoutParams);
		linearLayout_2.setLayoutParams(linearLayoutParams);
		linearLayout_3.setLayoutParams(linearLayoutParams);
		linearLayout_4.setLayoutParams(linearLayoutParams);
		linearLayout_5.setLayoutParams(linearLayoutParams);

		linearLayout_1.addView(start_1);
		linearLayout_2.addView(start_2);
		linearLayout_3.addView(start_3);
		linearLayout_4.addView(start_4);
		linearLayout_5.addView(start_5);
		
		linearLayout_1.setOnClickListener(this);
		linearLayout_2.setOnClickListener(this);
		linearLayout_3.setOnClickListener(this);
		linearLayout_4.setOnClickListener(this);
		linearLayout_5.setOnClickListener(this);

		linearLayoutList.add(linearLayout_1);
		linearLayoutList.add(linearLayout_2);
		linearLayoutList.add(linearLayout_3);
		linearLayoutList.add(linearLayout_4);
		linearLayoutList.add(linearLayout_5);
		
		addView(linearLayout_1);
		addView(linearLayout_2);
		addView(linearLayout_3);
		addView(linearLayout_4);
		addView(linearLayout_5);

	}

	protected void enableClick(boolean canClick) {
		
		if(canClick){
			for (LinearLayout linearLayout : linearLayoutList) {
				linearLayout.setOnClickListener(this);
			}
		}else{
			for (LinearLayout linearLayout : linearLayoutList) {
				linearLayout.setOnClickListener(null);
			}
		}
		
	}


	private void initAllStart(List<ImageView> startList) {

		for (ImageView imageView : startList) {
			imageView.setImageResource(R.drawable.icon_start_normal);
		}

	}

	@Override
	public void onClick(View v) {
		
		if(v == linearLayout_1){
			initAllStart(startList);
			selectStart(1, startList);
			starChooseListener.onStarChoose(1);
			startNum = 1;
		}
		
		else if(v == linearLayout_2){
			initAllStart(startList);
			selectStart(2, startList);
			starChooseListener.onStarChoose(2);
			startNum = 2;
		}
		
		else if(v == linearLayout_3){
			initAllStart(startList);
			selectStart(3, startList);
			starChooseListener.onStarChoose(3);
			startNum = 3;
		}
		
		else if(v == linearLayout_4){
			initAllStart(startList);
			selectStart(4, startList);
			starChooseListener.onStarChoose(4);
			startNum = 4;
		}
		
		else if(v == linearLayout_5){
			initAllStart(startList);
			selectStart(5, startList);
			starChooseListener.onStarChoose(5);
			startNum = 5;
		}
		
	}

	private void selectStart(int num, List<ImageView> startList) {
		for (int i = 0; i < num; i++) {
			startList.get(i).setImageResource(R.drawable.icon_start_selsect);
		}
	}

	public void setStarChooseListener(OnStarChooseListener starChooseListener) {
		this.starChooseListener = starChooseListener;
	}

	public interface OnStarChooseListener {
		public void onStarChoose(int num);
	}

	public int getStartNum() {
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
		selectStart(startNum, startList);
	}

}
