package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jungly.gridpasswordview.GridPasswordView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.UserWalletResetPassWordPayActivity;

public class PswPayDialog extends Dialog implements View.OnClickListener, DialogInterface.OnShowListener {

    private View contentView;
    private View rectForgetPsw;

    private View rectPsdPayBg;

    private GridPasswordView gpvNewPswPay;

    private Context context;

    public PswPayDialog(Context context) {
        super(context, R.style.dialog_psw_pay);

        this.context = context;
        setOnShowListener(this);

        contentView = getLayoutInflater().inflate(R.layout.dialog_psw_pay, null);

        rectPsdPayBg = contentView.findViewById(R.id.rectPsdPayBg);
        rectForgetPsw = contentView.findViewById(R.id.rectForgetPsw);

        gpvNewPswPay = (GridPasswordView) contentView.findViewById(R.id.gpvNewPswPay);

        rectPsdPayBg.setOnClickListener(this);
        rectForgetPsw.setOnClickListener(this);

        setContentView(contentView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rectPsdPayBg:
                dismiss();
                break;
            case R.id.rectForgetPsw:
                Intent intent = new Intent(getContext() , UserWalletResetPassWordPayActivity.class);
                getContext().startActivity(intent);
                break;
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        showInput();
    }

    /**
     * 弹出输入法
     */
    public void showInput() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    public void setOnPasswordChangedListener(GridPasswordView.OnPasswordChangedListener listener){
        gpvNewPswPay.setOnPasswordChangedListener(listener);
    }

    public void clearPassword(){
        gpvNewPswPay.clearPassword();
    }

}
