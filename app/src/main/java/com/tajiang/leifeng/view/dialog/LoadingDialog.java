package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.tajiang.leifeng.R;

/**
 * Created by Administrator on 2016/9/22.
 */
public class LoadingDialog extends Dialog {

    private CircularProgressView progressView;

    public LoadingDialog(Context context) {
        super(context, R.style.default_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_view, null);
        progressView = (CircularProgressView) view.findViewById(R.id.progress_view);
        setContentView(view);
        progressView.startAnimation();
    }

}