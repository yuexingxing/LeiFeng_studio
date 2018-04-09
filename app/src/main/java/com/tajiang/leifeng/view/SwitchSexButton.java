package com.tajiang.leifeng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.model.User;

public class SwitchSexButton extends FrameLayout implements OnClickListener{
	
	private OnSelectListener onSelectListener;
	
	private View view;
	
	private TextView text1;
	private TextView text2;
	
	private int widthView;

	public SwitchSexButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setBackgroundResource(R.drawable.shape_rect_round_gray_2);
		
		view = new View(getContext());
		
		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setWeightSum(2);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		layoutParams.weight = 1;
		
		text1 = new TextView(getContext());
		text2 = new TextView(getContext());
		
		text1.setText("男");
		text2.setText("女");	
		
		text1.setTextColor(getResources().getColor(R.color.white));
		text2.setTextColor(getResources().getColor(R.color.text_black_1));
		
		text1.setBackgroundResource(R.drawable.shape_rect_round_green);
		
		text1.setTextSize(12);
		text2.setTextSize(12);
		
		text1.setLayoutParams(layoutParams);
		text2.setLayoutParams(layoutParams);
		
		text1.setGravity(Gravity.CENTER);
		text2.setGravity(Gravity.CENTER);
		
		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		
		linearLayout.addView(text1);
		linearLayout.addView(text2);
		
		addView(view);
		addView(linearLayout , LayoutParams.MATCH_PARENT ,LayoutParams.MATCH_PARENT);
		
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		widthView = MeasureSpec.getSize(widthMeasureSpec);
		view.setLayoutParams(new LayoutParams(widthView/2, LayoutParams.MATCH_PARENT));
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public void onClick(View v) {
		if(v == text1){
			
			text2.setTextColor(getResources().getColor(R.color.text_black_1));
			text1.setTextColor(getResources().getColor(R.color.white));
		
			text1.setBackgroundResource(R.drawable.shape_rect_round_green);
			text2.setBackgroundColor(getResources().getColor(R.color.transparent));
			
			onSelectListener.onSelect(User.MAN);
			
		} else {
			text1.setTextColor(getResources().getColor(R.color.text_black_1));
			text2.setTextColor(getResources().getColor(R.color.white));
			
			text2.setBackgroundResource(R.drawable.shape_rect_round_green);
			text1.setBackgroundColor(getResources().getColor(R.color.transparent));
			
			onSelectListener.onSelect(User.WOMAN);
		}
	}
	
	public OnSelectListener getOnSelectListener() {
		return onSelectListener;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		this.onSelectListener = onSelectListener;
	}

	public interface OnSelectListener{
		public void onSelect(int position);
	}

}
