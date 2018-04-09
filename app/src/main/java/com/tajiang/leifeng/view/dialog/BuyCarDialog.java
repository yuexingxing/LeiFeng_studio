package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.BuyCarUtils;
import com.tajiang.leifeng.view.BadgeView;
import com.tajiang.leifeng.view.BuyCarView;

/**
 * Created by 12154 on 2015/11/13.
 */
public class BuyCarDialog extends Dialog implements View.OnClickListener,BuyCarView.OnBuyCarItemClickListener,DialogInterface.OnShowListener {

    private View contentView;
    private View rect_bgBuyCar;

    private BadgeView bv_buyCar;

    private BuyCarView bcv_dialog;

    private OnItemClickListener onItemClickListener;

    public BuyCarDialog(Context context) {
        super(context, R.style.dialog_full_screen);
        init(R.layout.dialog_buycar);
        findView();

//        if(BuyCarUtils.getIns().getBuyCarGoodSumCount() != 0 ){
//            bv_buyCar.setNum(BuyCarUtils.getIns().getBuyCarGoodSumCount());
//        }
        if(BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() != 0 ){
            bv_buyCar.setNum(BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount());
        }
    }

    private void init(int layoutId) {
        setOnShowListener(this);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        contentView = getLayoutInflater().inflate(layoutId, null);
        setContentView(contentView);
    }

    private void findView() {

        bv_buyCar = (BadgeView) findViewById(R.id.bv_buyCar);
        bcv_dialog = (BuyCarView) findViewById(R.id.bcv_dialog);
        bcv_dialog.setOnBuyCarItemClickListener(this);

        rect_bgBuyCar = findViewById(R.id.rect_bgBuyCar);
        rect_bgBuyCar.setOnClickListener(this);

    }


    @Override
    public void onBuyCarItemClick(int position) {
        if(onItemClickListener != null){
            onItemClickListener.onItemClickListener(position);
        }

    }

    @Override
    public void onAnimationEnd() {
        dismiss();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rect_bgBuyCar:
                bcv_dialog.closeItem();
                break;
        }

    }

    @Override
    public void onShow(DialogInterface dialog) {
        bcv_dialog.onClick(bcv_dialog.getBuyCarView());
    }

    public interface OnItemClickListener{
       public void onItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
