package com.tajiang.leifeng.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.tajiang.leifeng.R;

@SuppressLint("InflateParams")
public class HintDialog extends Dialog implements OnClickListener{
	
	private View contentView;
	
	private TextView tv_messageHintDialog;
	
	private View view_negetiveDialog;
	private View view_positiveDialog;
	
	public HintDialog(Context context) {
		super(context, R.style.dialog_operate);
		
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		
		contentView = getLayoutInflater().inflate(R.layout.dialog_hint, null);
		
		tv_messageHintDialog = (TextView) contentView.findViewById(R.id.tv_messageHintDialog);
		view_negetiveDialog = contentView.findViewById(R.id.tv_negetiveHintDialog);
		view_positiveDialog = contentView.findViewById(R.id.tv_positiveHintDialog);
		
		view_negetiveDialog.setOnClickListener(this);

		setContentView(contentView);
	}
	
	public void setMessage(String message) {
		tv_messageHintDialog.setText(message);
	}
	
	public void setPositiveButtonListener(View.OnClickListener listener) {
		view_positiveDialog.setOnClickListener(listener);
	}
	
	public void setNegativeButtonListener(View.OnClickListener listener){
		view_negetiveDialog.setOnClickListener(listener);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_negetiveHintDialog:
			dismiss();
			break;
		}
	}
	
	

}
