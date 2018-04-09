package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tajiang.leifeng.R;

/**
 * 用户下单支付前，温馨提醒对话框
 * Created by Administrator on 2016/9/21.
 */
public class OrderReminderDialog extends Dialog implements View.OnClickListener {

    public static int MODE_SHOW_AFTERNOON = 0;
    public static int MODE_SHOW_LAUNCH = 1;

    private LinearLayout tv_reminder_content_after_launch;
    private TextView tv_reminder_content_launch;
    private View contentView;
    private LinearLayout llRootView;
    private OnDoNextListener onDoNextListener;

    public interface OnDoNextListener{
        public void onDoNextClick();
    }

    public void setOnDoNextListener(OnDoNextListener onDoNextListener) {
        this.onDoNextListener = onDoNextListener;
    }

    public OrderReminderDialog(Context context) {
        super(context, R.style.default_dialog);

        contentView = getLayoutInflater().inflate(R.layout.dialog_reminder, null);
        llRootView = (LinearLayout) contentView.findViewById(R.id.ll_root_view);
        tv_reminder_content_launch = (TextView) contentView.findViewById(R.id.tv_reminder_content_launch);
        tv_reminder_content_after_launch = (LinearLayout) contentView.findViewById(R.id.tv_reminder_content_after_launch);

        contentView.findViewById(R.id.view_cancel).setOnClickListener(this);
        contentView.findViewById(R.id.view_ensure).setOnClickListener(this);
        setContentView(contentView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_cancel:

                break;
            case R.id.view_ensure:
                if (onDoNextListener != null) {
                    onDoNextListener.onDoNextClick();
                }
                break;
        }
        dismiss();
    }

    public void setDialogMode(int mode) {
        tv_reminder_content_launch.setVisibility(mode == MODE_SHOW_LAUNCH ? View.VISIBLE : View.GONE);
        tv_reminder_content_after_launch.setVisibility(mode == MODE_SHOW_LAUNCH ? View.GONE : View.VISIBLE);

        llRootView.setBackgroundResource(mode == MODE_SHOW_LAUNCH ? R.drawable.dialog_reminder_bg_01 : R.drawable.dialog_reminder_bg_02);
    }
}
