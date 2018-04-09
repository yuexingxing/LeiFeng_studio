package com.tajiang.leifeng.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tajiang.leifeng.R;


/**
 * 
 *@Title:自定义进度框
 *@Description:
 *@Author:admin
 *@Since:2015-7-29
 *@Version:1.1.0
 */
public class ProgressDialog extends AlertDialog {

	private TextView mMessageView;
	private CharSequence mMessage;

	public static ProgressDialog show(Context context, CharSequence title, CharSequence message) {
		return show(context, title, message, false);
	}

	public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean cancelable) {
		return show(context, title, message, cancelable, null);
	}

	public static ProgressDialog show(Context context, CharSequence title, CharSequence message, boolean cancelable,
			OnCancelListener cancelListener) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setCancelable(cancelable);
		dialog.setOnCancelListener(cancelListener);
		dialog.show();
		return dialog;
	}

	public ProgressDialog(Context context) {
//		super(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
		super(context, R.style.dialog_loading);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_progress, null);
		mMessageView = (TextView) view.findViewById(android.R.id.message);
		setView(view);
		if (mMessage != null) {
			setMessage(mMessage);
		}
		super.onCreate(savedInstanceState);
	}

	public ProgressDialog setmMessageText(String msg) {
		mMessageView.setText(msg);
		return this;
	}

	@Override
	public void setMessage(CharSequence message) {
		if (mMessageView != null) {
			mMessageView.setText(message);
		} else {
			mMessage = message;
		}
	}
}
