package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.view.circularprogressview.CircularProgressView;

public class AnimLoadDialog extends Dialog{
	
	public AnimLoadDialog(Context context) {
		super(context, R.style.dialog_loading);
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		
		View contentView = getLayoutInflater().inflate(R.layout.dialog_loading_anim, null);
		
		CircularProgressView circularProgressView = (CircularProgressView) contentView.findViewById(R.id.cpv);
		circularProgressView.startAnimation();
		
		setContentView(contentView);
		
	}
	
}
