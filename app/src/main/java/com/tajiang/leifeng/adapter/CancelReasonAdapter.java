package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.model.CancelReason;

import java.util.List;

/**
 * Created by wushu on 2017-02-04.
 */

public class CancelReasonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ImageView itemImageView;           //存储当前被点击的ImageView
    private List<CancelReason> mData;
    private int selectedItem = -1;

    public CancelReasonAdapter(Context context, List list) {
        this.mContext = context;
        this.mData = list;
    }
    public void updateData(List list) {
        this.mData.clear();
        this.mData.addAll(list);
        this.notifyDataSetChanged();
    }

    public interface OnItemClickLitener {
        void onItemClick(int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_reason_cancel, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.reason_cancel_text.setText(mData.get(position).getReasonText());
        itemImageView = viewHolder.choose_icon_cancel_iv;
        if (position != selectedItem) {
            itemImageView.setBackgroundResource(R.drawable.unchoose_icon);
        } else {
            itemImageView.setBackgroundResource(R.drawable.choose_icon);
        }
        viewHolder.cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != selectedItem) {
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.onItemClick(position);
                    }
                    updateItemView(view);
                    CancelReasonAdapter.this.selectedItem = position;
                }
            }

            private void updateItemView(View view) {
                if (itemImageView != null) {
                    itemImageView.setBackgroundResource(R.drawable.unchoose_icon);
                }
                itemImageView = (ImageView) view.findViewById(R.id.choose_icon_cancel_iv);
                itemImageView.setBackgroundResource(R.drawable.choose_icon);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {
        private RelativeLayout cancel_layout;
        private TextView reason_cancel_text;
        private ImageView choose_icon_cancel_iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            initItemView(itemView);
        }
        private void initItemView(View itemView) {
            this.cancel_layout = (RelativeLayout) itemView.findViewById(R.id.cancel_layout);
            this.reason_cancel_text = (TextView) itemView.findViewById(R.id.reason_cancel_text);
            this.choose_icon_cancel_iv = (ImageView) itemView.findViewById(R.id.choose_icon_cancel_iv);
        }
    }
}
