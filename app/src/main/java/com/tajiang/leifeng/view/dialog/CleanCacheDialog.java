package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.utils.DataCleanManager;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;

/**
 * Created by Admins on 2016/10/9.
 */
public class CleanCacheDialog extends Dialog implements View.OnClickListener {


    private View contentView;

    private View view_cancel;
    private View view_clean;

    private Context mContex;
    private TextView tv_cache_size;

    public CleanCacheDialog(Context context, TextView tv_cache_size) {
        super(context, R.style.dialog_operate);

        this.mContex = context;
        this.tv_cache_size = tv_cache_size;
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        contentView = getLayoutInflater().inflate(R.layout.dialog_clean_cache, null);

        view_cancel = contentView.findViewById(R.id.view_cancel);
        view_clean = contentView.findViewById(R.id.view_clean);

        view_cancel.setOnClickListener(this);
        view_clean.setOnClickListener(this);

        setContentView(contentView);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.view_cancel:
                break;
            case R.id.view_clean:
                try {
                    DataCleanManager.clearAppCache(mContex);
                    if (tv_cache_size != null) {
                        tv_cache_size.setText("0KB");
                    }
                } catch (Exception e) {
                    ToastUtils.showShort("缓存清楚异常，请重试！");
                    LogUtils.e(e.getMessage());
                }
                break;
        }

        dismiss();

    }

}
