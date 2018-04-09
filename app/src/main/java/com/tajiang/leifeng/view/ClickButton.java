package com.tajiang.leifeng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.tajiang.leifeng.R;

public class ClickButton extends Button{
	
	private final int textColorAbleCilck = R.color.white;
	private final int textColorUnableClick = R.color.white;
	
	private final int bgAbleClick = R.color.common_title_bg_root;
	private final int bgUnableClick = R.drawable.shape_rect_round_gray_3;
	
	public ClickButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundResource(bgAbleClick);
	}

	@Override
	public void setClickable(boolean clickable) {
		super.setClickable(clickable);
		
		setTextColor(getResources().getColor( clickable ? textColorAbleCilck : textColorUnableClick));
		setBackgroundResource(clickable ? bgAbleClick : bgUnableClick);
		
	}

	public void setButtonEnable(boolean clickable) {
		this.setEnabled(clickable);
		setTextColor(getResources().getColor( clickable ? textColorAbleCilck : textColorUnableClick));
		setBackgroundResource(clickable ? bgAbleClick : bgUnableClick);
	}
	
}
