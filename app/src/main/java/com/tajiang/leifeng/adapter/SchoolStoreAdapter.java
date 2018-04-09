package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.model.Store;
import com.tajiang.leifeng.model.StoreDelivered;
import com.tajiang.leifeng.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admins on 2016/10/27.
 */
public class SchoolStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int selectedItem = 0;
    private Context mContext;
    private List<StoreDelivered> mDatas;

    private TextView itemTextView;

    private OnItemStoreClickListener onItemClickListener;

    public interface OnItemStoreClickListener {
        public void OnItemStoreClick(StoreDelivered selectedStore, int selectedItemPosition);
    }

    public void setOnItemStoreClickListener(OnItemStoreClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SchoolStoreAdapter(Context context, List<StoreDelivered> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layou_title_content_selected_store, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StoreDelivered Item = mDatas.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.content_school_store_name.setText(Item.getZonesName());
        if (position == selectedItem) {
            itemTextView = myViewHolder.content_school_store_name;
            myViewHolder.content_school_store_name.setTextColor(mContext.getResources().getColor(R.color.com_green));
            myViewHolder.content_school_store_name.setBackgroundColor(mContext.getResources().getColor(R.color.gray_selected));
        } else {
            myViewHolder.content_school_store_name.setTextColor(mContext.getResources().getColor(R.color.text_black_1));
            myViewHolder.content_school_store_name.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void updateAllDataSetChanged(List<StoreDelivered> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void addAllAndUpdateDataSetChanged(List<StoreDelivered> mDatas) {
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setItemTextView(TextView itemTextView) {
        this.itemTextView = itemTextView;
    }

    public TextView getItemTextView() {
        return itemTextView;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView content_school_store_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            initView();
            initListener();
        }

        private void initView() {
            content_school_store_name = (TextView) itemView.findViewById(R.id.content_school_store_name);
        }

        private void initListener() {
            content_school_store_name.setOnClickListener(this);
        }

        //更换被点击Item的布局样式
        private void updateItemView(View itemView) {
            TextView textView = ((TextView)itemView);
            textView.setTextColor(mContext.getResources().getColor(R.color.com_green));
            textView.setBackgroundColor(mContext.getResources().getColor(R.color.gray_selected));
            if (getItemTextView() != null) {
                getItemTextView().setTextColor(mContext.getResources().getColor(R.color.text_black_1));
                getItemTextView().setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            setItemTextView(textView);
            setSelectedItem(getLayoutPosition());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.content_school_store_name:
                    if (getLayoutPosition() != selectedItem) {
                        //回调接口，响应外部点击事件更换宿舍区对于楼栋
                        if (onItemClickListener != null) {
                            onItemClickListener.OnItemStoreClick(mDatas.get(getLayoutPosition()), getLayoutPosition());
                        }
                        updateItemView(v);
                    }
                    break;
            }
        }
    }
}
