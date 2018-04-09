package com.tajiang.leifeng.view.dialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class AdvDialog extends Dialog implements View.OnClickListener, Animator.AnimatorListener {

    private View v_lineAdvDialog;
    private View iv_closeAdvDialog;
    private FrameLayout rect_ADDialog;

    private View rootView;

    private ViewPager vp_advDialog;

    private List<View> viewList = new ArrayList<>();
    private List<String> mImgList = new ArrayList<>();

    private ViewPagerAdapter viewPagerAdapter;


    public AdvDialog(Context context) {
        super(context, R.style.dialog_full_screen);

        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_ad, null);
        setContentView(rootView);
        getWindow().setGravity(Gravity.TOP);

        v_lineAdvDialog = findViewById(R.id.v_lineAdvDialog);
        vp_advDialog = (ViewPager) findViewById(R.id.vp_advDialog);

        iv_closeAdvDialog =  findViewById(R.id.iv_closeAdvDialog);
        rect_ADDialog = (FrameLayout) findViewById(R.id.rect_ADDialog);

        rect_ADDialog.setAlpha(0);
        v_lineAdvDialog.setScaleY(0);

        iv_closeAdvDialog.setOnClickListener(this);

        v_lineAdvDialog.setPivotX(0);
        v_lineAdvDialog.setPivotY(0);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v_lineAdvDialog, View.SCALE_Y, 0F, 1F).setDuration(400);
        objectAnimator.addListener(this);
        objectAnimator.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_closeAdvDialog:
                dismiss();
                break;
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        ObjectAnimator.ofFloat(rect_ADDialog, "alpha", 0F, 1F).setDuration(600).start();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public void setImageList(List<String> imgList) {
        this.mImgList = imgList;

        for (int i = 0; i < mImgList.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_image_round, null);
            SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.iv_testPic);
            imageView.setImageURI(Uri.parse(mImgList.get(i)));
            viewList.add(view);
        }

        viewPagerAdapter = new ViewPagerAdapter(viewList);
        vp_advDialog.setAdapter(viewPagerAdapter);

    }
}
