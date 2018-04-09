package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.constant.ApiCst;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.ToastUtils;

/**
 * Created by Admins on 2016/10/24.
 */
public class ChooseHostDialog extends Dialog implements View.OnClickListener{

    private View contentView;
    Button btnHost;
    Button btnHostTest;
    Button btnHosCuit;

    public ChooseHostDialog(Context context ) {
        super(context, R.style.dialog_operate);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        contentView = getLayoutInflater().inflate(R.layout.dialog_choose_host, null);

        btnHost = (Button) contentView.findViewById(R.id.btn_host);
        btnHostTest = (Button)contentView.findViewById(R.id.btn_host_test);
        btnHosCuit = (Button)contentView.findViewById(R.id.btn_host_cui);

        btnHost.setOnClickListener(this);
        btnHostTest.setOnClickListener(this);
        btnHosCuit.setOnClickListener(this);

        setContentView(contentView);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_host:
                SharedPreferencesUtils.put(TJApp.getIns().getApplicationContext(), SharedPreferencesUtils.HOST_TEST_ROOT_URL, ApiCst.HOST );
                break;
            case R.id.btn_host_test:
                SharedPreferencesUtils.put(TJApp.getIns().getApplicationContext(), SharedPreferencesUtils.HOST_TEST_ROOT_URL, ApiCst.HOST_TEST );
                break;
            case R.id.btn_host_cui:
                SharedPreferencesUtils.put(TJApp.getIns().getApplicationContext(), SharedPreferencesUtils.HOST_TEST_ROOT_URL, ApiCst.BIG_BROTHER );
                break;
        }
        /**
         * 清楚用户信息
         */
        SharedPreferencesUtils.remove(TJApp.getContext(), SharedPreferencesUtils.USER_LOGIN_INFOR);
        ToastUtils.showShort("重启应用生效");
        dismiss();
    }

}
