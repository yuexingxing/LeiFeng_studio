package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.FoodClass;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;

import java.util.List;

public class FoodTypeSelectListAdapter extends CommonAdapter<FoodClass> {

    private int clickedPosition = 0;

    private TextView textView = null;
    private View iewDriverV = null;
    private View rl_root_selected;
    private ImageView imageView = null;

    private boolean isShowFoodTypeCount = true;  //是否显示每个菜品分类的数量
    private boolean isShowFoodTypeImg = true;  //是否显示每个菜品分类的缩略图

    public FoodTypeSelectListAdapter(Context context, List<FoodClass> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, FoodClass item) {
        View currentDriverV = helper.getView(R.id.v_driver_v);
        TextView currentTextView = helper.getView(R.id.tvDecsTypeScreen);
//        ImageView currentImageView = helper.getView(R.id.iv_point);
        View current_rl_root_selected = helper.getView(R.id.rl_root_selected);

        if (isShowFoodTypeCount == true) {
            helper.getView(R.id.ll_count_food_type).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.ll_count_food_type).setVisibility(View.GONE);
        }

//        if (TextUtils.isEmpty(item.getImage())) {
//            helper.getView(R.id.ivIconTypeScreen).setVisibility(View.GONE);
//        } else {
//            helper.setImage(R.id.ivIconTypeScreen, item.getImage());
//            if (isShowFoodTypeImg == true) {
//                helper.getView(R.id.ivIconTypeScreen).setVisibility(View.VISIBLE);
//            } else {
//                helper.getView(R.id.ivIconTypeScreen).setVisibility(View.GONE);
//            }
//        }
//        helper.getView(R.id.iv_point).setVisibility(View.INVISIBLE);
        helper.setText(R.id.tvDecsTypeScreen, item.getClassName());
        helper.setText(R.id.tvSumTypeScreen, item.getNum());

//        if (clickedPosition == helper.getPosition()) {
//            LogUtils.e("--->clickedPosition");
//            this.iewDriverV = currentDriverV;
//            this.textView = currentTextView;
//            this.imageView = currentImageView;
//        }
//        if (clickedPosition == helper.getPosition() && this.iewDriverV != null && this.textView != null && this.imageView != null) {
//            LogUtils.e("--->imageView.setVisibility(View.VISIBLE-->" + "clickedPosition = " + clickedPosition);
//            this.iewDriverV.setVisibility(View.GONE);
//            this.textView.setTextColor(getContext().getResources().getColor(R.color.text_black_1));
//            this.imageView.setVisibility(View.VISIBLE);
//        }

        if (clickedPosition == helper.getPosition()) {
            currentDriverV.setVisibility(View.GONE);
            currentTextView.setTextColor(getContext().getResources().getColor(R.color.text_black_1));
//            currentImageView.setVisibility(View.VISIBLE);
            current_rl_root_selected.setVisibility(View.VISIBLE);

            this.iewDriverV = currentDriverV;
            this.textView = currentTextView;
//            this.imageView = currentImageView;
            this.rl_root_selected = current_rl_root_selected;
        } else {
            currentDriverV.setVisibility(View.VISIBLE);
            currentTextView.setTextColor(getContext().getResources().getColor(R.color.text_black_2));
//            currentImageView.setVisibility(View.INVISIBLE);
            current_rl_root_selected.setVisibility(View.INVISIBLE);
        }
    }

    public void setShowFoodTypeCount(boolean showFoodTypeCount) {
        isShowFoodTypeCount = showFoodTypeCount;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public View getIewDriverV() {
        return iewDriverV;
    }

    public void setIewDriverV(View iewDriverV) {
        this.iewDriverV = iewDriverV;
    }

    public void setShowFoodTypeImg(boolean showFoodTypeImg) {
        isShowFoodTypeImg = showFoodTypeImg;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setClickedPosition(int clickedPosition) {
        this.clickedPosition = clickedPosition;
    }

    public void updateItemUI(View view) {
        if (view != null) {
            View currentDriverV = view.findViewById(R.id.v_driver_v);
            TextView currentTextView = (TextView) view.findViewById(R.id.tvDecsTypeScreen);
//            ImageView currentImageView = (ImageView) view.findViewById(R.id.iv_point);
            View current_rl_root_selected = view.findViewById(R.id.rl_root_selected);
            /**
             * 清楚原先被选中的View状态
             */
            this.iewDriverV.setVisibility(View.VISIBLE);
            this.textView.setTextColor(getContext().getResources().getColor(R.color.text_black_2));
//            this.imageView.setVisibility(View.INVISIBLE);
            this.rl_root_selected.setVisibility(View.INVISIBLE);
            /**
             * 更新目前选中的Item的状态
             */
            currentDriverV.setVisibility(View.GONE);
            currentTextView.setTextColor(getContext().getResources().getColor(R.color.text_black_1));
//            currentImageView.setVisibility(View.VISIBLE);
            current_rl_root_selected.setVisibility(View.VISIBLE);
            /**
             * 重置目前选中的View
             */
            this.iewDriverV = currentDriverV;
            this.textView = currentTextView;
//            this.imageView = currentImageView;
            this.rl_root_selected = current_rl_root_selected;
        }
    }

}
