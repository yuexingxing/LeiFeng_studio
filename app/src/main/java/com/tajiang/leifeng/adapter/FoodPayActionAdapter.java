package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.model.OrderActivity;
import com.tajiang.leifeng.model.StallsFullcut;
import com.tajiang.leifeng.utils.GlideRoundTransform;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.NumberUtils;

import java.util.List;

/**
 * Created by Admins on 2016/11/15.
 */

public class FoodPayActionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    private List<StallsFullcut> mData;

    public FoodPayActionAdapter(Context context, List<StallsFullcut> list) {
        this.mContext = context;
        this.mData = list;
    }

    public void updateData(List<StallsFullcut> list) {
        this.mData.clear();
        this.mData.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_action_discount, null);
        RecyclerView.ViewHolder myViewHolder = new ItemViewHolder(mItemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        StallsFullcut orderActivity = mData.get(position);
        viewHolder.tv_action_name.setText(orderActivity.getActivityName());

        viewHolder.tv_action_discount.setText("-￥" + orderActivity.getDiscountedPrice());
        viewHolder.tv_action_discount.setTextColor(mContext.getResources().getColor(R.color.red_money));
        viewHolder.tv_action_discount.setTextSize(12);
//        if (orderActivity.getState() == OrderActivity.STATE_NONE_HAVE) {
//            viewHolder.tv_action_name.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
//            viewHolder.tv_action_discount.setText("已享受最优惠活动");
//            viewHolder.tv_action_name.setTextColor(mContext.getResources().getColor(R.color.text_black_3));
//            viewHolder.tv_action_discount.setTextColor(mContext.getResources().getColor(R.color.text_black_1));
//            viewHolder.tv_action_discount.setTextSize(11);
//        } else {
//            viewHolder.tv_action_discount.setText("-￥" + orderActivity.getPrice());
//            viewHolder.tv_action_name.setTextColor(mContext.getResources().getColor(R.color.text_black_1));
//            viewHolder.tv_action_discount.setTextColor(mContext.getResources().getColor(R.color.red_money));
//            viewHolder.tv_action_discount.setTextSize(12);
//        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_action_name;
        private TextView tv_action_discount;

        public ItemViewHolder(View itemView) {
            super(itemView);
            initItemView();
        }

        private void initItemView() {
            this.tv_action_name = (TextView) itemView.findViewById(R.id.tv_action_name);
            this.tv_action_discount = (TextView) itemView.findViewById(R.id.tv_action_discount);
        }
    }
}