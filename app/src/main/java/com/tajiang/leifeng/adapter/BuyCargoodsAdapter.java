package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.model.GoodSet;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.BuyCarUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.BadgeView;
import com.tajiang.leifeng.view.NumControlView;

import java.util.ArrayList;
import java.util.List;

/**
 *  购物车列表弹框
 * Created by Admins on 2016/10/31.
 */
public class BuyCargoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<GoodSet> goodSets;
    private BadgeView badgeView;
    private BottomSheetLayout bottomSheetLayout;

    private OnCutAndAddButtonClick onCutAndAddButtonClick;
    private OnCutButtonClickListener onCutButtonClickListener;
    private OnAddButtonClickListener onAddButtonClickListener;


    public void setOnCutAndAddButtonClick(OnCutAndAddButtonClick onCutAndAddButtonClick) {
        this.onCutAndAddButtonClick = onCutAndAddButtonClick;
    }

    public interface OnCutAndAddButtonClick {
        public void OnCutAndAddButtonClick();
    }

    public void setOnCutButtonClickListener(OnCutButtonClickListener onCutButtonClickListener) {
        this.onCutButtonClickListener = onCutButtonClickListener;
    }

    public void setOnAddButtonClickListener(OnAddButtonClickListener onAddButtonClickListener) {
        this.onAddButtonClickListener = onAddButtonClickListener;
    }

    public interface OnCutButtonClickListener {
        public void OnCutButton();
    }

    public interface OnAddButtonClickListener {
        public void OnAddButton();
    }

    public BuyCargoodsAdapter(Context context, BadgeView badgeView, BottomSheetLayout bottomSheetLayout) {
        this.mContext = context;
        this.badgeView = badgeView;
        this.bottomSheetLayout = bottomSheetLayout;
        this.goodSets = new ArrayList<>();
    }

    public void updateAllDataSet(List<GoodSet> goodSets) {
        this.goodSets.clear();
        this.goodSets.addAll(goodSets);
        notifyDataSetChanged();
    }

    public void addAllToDataSet(List<GoodSet> goodSets) {
        this.goodSets.addAll(goodSets);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_food_buy_car, parent, false);
        return new MyViewHoldere(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GoodSet goodSet = goodSets.get(position);
        final MyViewHoldere myViewHoldere = (MyViewHoldere) holder;

        myViewHoldere.tv_good_name.setText(goodSet.getGoods().getGoodsName());
        myViewHoldere.tv_priceFoodSelect.setText("" + goodSet.getSumPrice());
        myViewHoldere.ncv_numFoodSelect.setNumGood(goodSet.getNum());

        myViewHoldere.ncv_numFoodSelect.setOnNumChangeListener(new NumControlView.OnNumChangeListener() {
            @Override
            public void OnCutButtonClick(View view, int num) {
                if (goodSet.getNum() == 1) {
                    removeItem(myViewHoldere.getLayoutPosition());
                    updaBottomSheetLayout();
                }
                //TODO..........
//                BuyCarUtils.getIns().getBuyCar().cutGood(goodSet.getGoods());
//                badgeView.setNum(BuyCarUtils.getIns().getBuyCar().getSumCount());
                BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().cutGood(goodSet.getGoods());
                badgeView.setNum(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumCount());

                myViewHoldere.tv_priceFoodSelect.setText("" + goodSet.getSumPrice());
                if (onCutAndAddButtonClick != null) {
                    onCutAndAddButtonClick.OnCutAndAddButtonClick();
                }
            }

            @Override
            public void OnAddButtonClick(View view, int num) {
                //TODO..........
//                BuyCarUtils.getIns().getBuyCar().addGood(goodSet.getGoods());
//                badgeView.setNum(BuyCarUtils.getIns().getBuyCar().getSumCount());
                BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().addGood(goodSet.getGoods(), 1);
                badgeView.setNum(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumCount());

                myViewHoldere.tv_priceFoodSelect.setText("" + goodSet.getSumPrice());
                if (onCutAndAddButtonClick != null) {
                    onCutAndAddButtonClick.OnCutAndAddButtonClick();
                }
            }
        });
    }

    private void updaBottomSheetLayout() {
        if (goodSets.size() == 0 && bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        }
    }

    public void removeItem(int position) {
        this.goodSets.remove(position);
        notifyItemRemoved(position);//Attention!
    }

    @Override
    public int getItemCount() {
        return goodSets == null ? 0 : goodSets.size();
    }

    public void clearAllData() {
        this.goodSets.clear();
    }

    class MyViewHoldere extends RecyclerView.ViewHolder {

        TextView tv_good_name;
        TextView tv_priceFoodSelect;
        NumControlView ncv_numFoodSelect;

        public MyViewHoldere(View itemView) {
            super(itemView);
            initView();
            initListener();
        }

        private void initListener() {

        }

        private void initView() {
            tv_good_name = (TextView) itemView.findViewById(R.id.tv_good_name);
            tv_priceFoodSelect = (TextView) itemView.findViewById(R.id.tv_priceFoodSelect);
            ncv_numFoodSelect = (NumControlView) itemView.findViewById(R.id.ncv_numFoodSelect);
        }

    }
}
