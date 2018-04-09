package com.tajiang.leifeng.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tajiang.leifeng.R;

/**
 * Created by 12154 on 2015/11/6.
 */
public class BaseDialog extends Dialog implements View.OnClickListener {

    private int layoutResId;

    private Context mContext;

    private View rootView;
    private View contentView;
    private View rect_chooseBaseDialog;

    private View tv_negetiveBaseDialog;
    private View tv_positiveHintDialog;

    private FrameLayout rect_contectBaseDialog;

    public BaseDialog(Context context, int layoutResId) {
        super(context, R.style.dialog_operate);

        this.layoutResId = layoutResId;
        mContext = context;

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_base, null);
        rect_contectBaseDialog = (FrameLayout) rootView.findViewById(R.id.rect_contectBaseDialog);
        rect_chooseBaseDialog = rootView.findViewById(R.id.rect_chooseBaseDialog);
        tv_negetiveBaseDialog = rootView.findViewById(R.id.tv_negetiveBaseDialog);
        tv_positiveHintDialog = rootView.findViewById(R.id.tv_positiveHintDialog);

        tv_negetiveBaseDialog.setOnClickListener(this);

        setContentView(rootView);
        setContentLayout(layoutResId);

    }

    public void setContentLayout(int layoutResId) {
        contentView = LayoutInflater.from(mContext).inflate(layoutResId, null);
        rect_contectBaseDialog.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public <T extends View> T findViewByID(int id) {
        return (T) contentView.findViewById(id);
    }

    public BaseDialog unableSelectButton() {
        rect_chooseBaseDialog.setVisibility(View.GONE);
        return this;
    }

    public void setPositiveOnClickListener(View.OnClickListener onClickListener) {
        tv_positiveHintDialog.setOnClickListener(onClickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_negetiveBaseDialog:
                dismiss();
                break;
        }
    }
}
