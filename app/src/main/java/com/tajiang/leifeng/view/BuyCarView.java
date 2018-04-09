package com.tajiang.leifeng.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.utils.DensityUtils;

/**
 * Created by 12154 on 2015/11/6.
 */
public class BuyCarView extends FrameLayout implements View.OnClickListener{

    private boolean isFirst = true;

    private float startY_mess;
    private float endY_mess;

    private float startX_takeAway;
    private float endX_takeAway;
    private float startY_takeAway;
    private float endY_takeAway;

    private float startX_passMe;
    private float endX_passMe;

    private boolean isExpand = false;

    private View contentView;

    private View rect_messBuyCar;
    private View rect_takeAwayBuyCar;
    private View rect_passMeBuyCar;

    private TextView tv_messBuyCar;
    private TextView tv_takeAwayBuyCar;
    private TextView tv_passMeBuyCar;

    private ImageView iv_buyCar;




    private OnBuyCarItemClickListener onBuyCarItemClickListener;

    public BuyCarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        contentView = LayoutInflater.from(context).inflate(R.layout.layout_buy_car, null);

        findView();
        setListener();

        rect_messBuyCar.setVisibility(View.INVISIBLE);
        rect_passMeBuyCar.setVisibility(View.INVISIBLE);
        rect_takeAwayBuyCar.setVisibility(View.INVISIBLE);

        addView( contentView,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);




    }

    private void findView() {
        rect_messBuyCar = contentView.findViewById(R.id.rect_messBuyCar);
        rect_takeAwayBuyCar = contentView.findViewById(R.id.rect_takeAwayBuyCar);
        rect_passMeBuyCar = contentView.findViewById(R.id.rect_passMeBuyCar);

        tv_messBuyCar = (TextView) contentView.findViewById(R.id.tv_messBuyCar);
        tv_takeAwayBuyCar = (TextView) contentView.findViewById(R.id.tv_takeAwayBuyCar);
        tv_passMeBuyCar = (TextView) contentView.findViewById(R.id.tv_passMeBuyCar);

        iv_buyCar = (ImageView) contentView.findViewById(R.id.iv_buyCar);
    }

    private void setListener() {
        rect_messBuyCar.setOnClickListener(this);
        rect_takeAwayBuyCar.setOnClickListener(this);
        rect_passMeBuyCar.setOnClickListener(this);
        iv_buyCar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        isFirstAnimation();


        switch (v.getId()){
            case R.id.iv_buyCar:


                switchItemState();



                break;
            case R.id.rect_messBuyCar:
                if(onBuyCarItemClickListener != null){
                    onBuyCarItemClickListener.onBuyCarItemClick(0);
                }
                closeItem();
                isExpand = !isExpand;
                break;
            case R.id.rect_takeAwayBuyCar:
                if(onBuyCarItemClickListener != null){
                    onBuyCarItemClickListener.onBuyCarItemClick(1);
                }
                closeItem();
                isExpand = !isExpand;
                break;
            case R.id.rect_passMeBuyCar:
                if(onBuyCarItemClickListener != null){
                    onBuyCarItemClickListener.onBuyCarItemClick(2);
                }
                closeItem();
                isExpand = !isExpand;
                break;
        }
    }

    private void isFirstAnimation() {
        if(isFirst){
            startY_mess = rect_messBuyCar.getY();
            endY_mess = startY_mess - DensityUtils.dp2px(getContext(), 100);

            startX_takeAway = rect_takeAwayBuyCar.getX();
            endX_takeAway = startX_takeAway + DensityUtils.dp2px(getContext(),60);

            startY_takeAway = rect_takeAwayBuyCar.getY();
            endY_takeAway = startY_takeAway - DensityUtils.dp2px(getContext(),60);

            startX_passMe = rect_passMeBuyCar.getX();
            endX_passMe = startX_passMe + DensityUtils.dp2px(getContext() , 90);

            isFirst = false;
        }
    }

    public void switchItemState() {

        isFirstAnimation();

        if(!isExpand){
            openItem();
        } else {
            closeItem();
        }

        isExpand = !isExpand;

    }

    public void closeItem() {

        PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat("rotation", 0, 360*4);

        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y",rect_messBuyCar.getY(), startY_mess);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(rect_messBuyCar, pvhY, pvhR).setDuration(300);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                iv_buyCar.setClickable(false);
                rect_messBuyCar.setClickable(false);
                rect_passMeBuyCar.setClickable(false);
                rect_takeAwayBuyCar.setClickable(false);


                if (isExpand) {
                    tv_messBuyCar.setVisibility(View.GONE);
                    tv_takeAwayBuyCar.setVisibility(View.GONE);
                    tv_passMeBuyCar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rect_messBuyCar.setVisibility(View.INVISIBLE);
                rect_passMeBuyCar.setVisibility(View.INVISIBLE);
                rect_takeAwayBuyCar.setVisibility(View.INVISIBLE);

                iv_buyCar.setClickable(true);
                rect_messBuyCar.setClickable(true);
                rect_passMeBuyCar.setClickable(true);
                rect_takeAwayBuyCar.setClickable(true);
                onBuyCarItemClickListener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();


        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", rect_passMeBuyCar.getX(), startX_passMe);
        ObjectAnimator.ofPropertyValuesHolder(rect_passMeBuyCar, pvhX,pvhR).setDuration(300).start();

        PropertyValuesHolder pvhXY1 = PropertyValuesHolder.ofFloat("y", rect_takeAwayBuyCar.getY(), startY_takeAway);
        PropertyValuesHolder pvhXY2 = PropertyValuesHolder.ofFloat("x", rect_takeAwayBuyCar.getX(), startX_takeAway);
        ObjectAnimator.ofPropertyValuesHolder(rect_takeAwayBuyCar, pvhXY1, pvhXY2, pvhR).setDuration(300).start();
    }

    private void openItem() {

        PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat("rotation", 0, 360*4);

        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", rect_messBuyCar.getY(), endY_mess);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(rect_messBuyCar, pvhY, pvhR).setDuration(300);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                iv_buyCar.setClickable(false);
                rect_messBuyCar.setClickable(false);
                rect_passMeBuyCar.setClickable(false);
                rect_takeAwayBuyCar.setClickable(false);

                rect_messBuyCar.setVisibility(View.VISIBLE);
                rect_passMeBuyCar.setVisibility(View.VISIBLE);
                rect_takeAwayBuyCar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                iv_buyCar.setClickable(true);

                rect_messBuyCar.setClickable(true);
                rect_passMeBuyCar.setClickable(true);
                rect_takeAwayBuyCar.setClickable(true);

                if (isExpand) {
                    tv_messBuyCar.setVisibility(View.VISIBLE);
                    tv_takeAwayBuyCar.setVisibility(View.VISIBLE);
                    tv_passMeBuyCar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", rect_passMeBuyCar.getX(), endX_passMe);
        ObjectAnimator.ofPropertyValuesHolder(rect_passMeBuyCar, pvhX ,pvhR).setDuration(300).start();


        PropertyValuesHolder pvhXY1 = PropertyValuesHolder.ofFloat("y", rect_takeAwayBuyCar.getY(), endY_takeAway);
        PropertyValuesHolder pvhXY2 = PropertyValuesHolder.ofFloat("x", rect_takeAwayBuyCar.getX(),endX_takeAway);
        ObjectAnimator.ofPropertyValuesHolder(rect_takeAwayBuyCar, pvhXY1, pvhXY2, pvhR).setDuration(300).start();
    }

    public interface OnBuyCarItemClickListener{
        public void onBuyCarItemClick(int position);
        public void onAnimationEnd();
    }

    public void setOnBuyCarItemClickListener(OnBuyCarItemClickListener onBuyCarItemClickListener) {
        this.onBuyCarItemClickListener = onBuyCarItemClickListener;
    }

    public View getBuyCarView(){
        return iv_buyCar;
    }

}
