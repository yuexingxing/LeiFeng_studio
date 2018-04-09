package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.model.Apartment;
import com.tajiang.leifeng.model.ApartmentZone;
import com.tajiang.leifeng.model.SchoolApartment;
import com.tajiang.leifeng.utils.LogUtils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admins on 2016/10/27.
 */
public class SchoolStoreBuildingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<SchoolStoreBuildingAdapter.HeaderViewHolder> {

    private int selectedItem = -1;
    private Context mContext;
    private int mZonesId;  //分区id
    private TextView itemTextView;            //存储当前被点击的TextView
    private ImageView itemImageView;
    private Map<Integer, String> mSchoolZones;//存储学校宿舍区
    private List<Apartment> mDatas;           //存储学校楼栋

    private OnItemApartmentClickListener onItemClickListener;

    public void updateStoreDeliveredApartment(int zonesId, String zonesName, List<Apartment> list) {
        this.selectedItem = -1;
        this.mDatas.clear();
        this.mSchoolZones.clear();
        this.mZonesId = zonesId;
        if (getItemTextView() != null) {
            getItemTextView().setTextColor(mContext.getResources().getColor(R.color.text_black_1));
            getItemTextView().setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
//        for (ApartmentZone apartmentZone : list) {
        if (list != null) {
            addAll2DataSet(list);
        }
        for (int i = 0; i < list.size(); i++) {
            putZone2MapSchoolZones(zonesId, zonesName);
        }
//        }
        notifyDataSetChanged();
    }

    public interface OnItemApartmentClickListener {
        public void OnItemApartmentClick(String name, Apartment selectedApartment, int selectedItemPosition);
    }

    public void setOnItemApartmentClickListener(OnItemApartmentClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SchoolStoreBuildingAdapter(Context context, List<Apartment> mDatas) {
        this.mContext = context;
        if (mDatas == null) {
            this.mDatas = new ArrayList<Apartment>();
        } else {
            this.mDatas = mDatas;
        }
        initData();
    }

    private void initData() {
        this.mSchoolZones = new HashMap();
    }

    public void updateAllDataSetChanged(List<Apartment> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void addAllAndUpdateDataSetChanged(List<Apartment> mDatas) {
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void addAll2DataSet(List<Apartment> mDatas) {
        this.mDatas.addAll(mDatas);
    }

    public void putZone2MapSchoolZones(Integer ZoneId, String name) {
        this.mSchoolZones.put(ZoneId, name);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layou_title_content_selected_store, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.content_school_store_name.setText(mDatas.get(position).getApartmentName());
        if (TJApp.getIns().getUserApartment() != null && mDatas.get(position).getApartmentId() == TJApp.getIns().getUserApartment().getApartmentId()) {
            myViewHolder.content_school_store_name.setTextColor(mContext.getResources().getColor(R.color.com_green));
            myViewHolder.iv_hook.setVisibility(View.VISIBLE);
            itemTextView = myViewHolder.content_school_store_name;
            itemImageView = myViewHolder.iv_hook;
        } else {
            myViewHolder.content_school_store_name.setTextColor(mContext.getResources().getColor(R.color.text_black_1));
            myViewHolder.iv_hook.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    /**
     * 获取HeaderId, 只要HeaderId不相等就添加一个Header
     */
    @Override
    public long getHeaderId(int position) {
        return mZonesId;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layou__head_content_selected_store,parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {
        holder.tv_head_school_zone_name.setText(mSchoolZones.get(mZonesId));
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


    class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tv_head_school_zone_name;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tv_head_school_zone_name = (TextView) itemView.findViewById(R.id.tv_head_school_zone_name);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView content_school_store_name;
        ImageView iv_hook;
        View rootView;

        public MyViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
            initListener();
        }

        private void initView(View itemView) {
            rootView = itemView;
            content_school_store_name = (TextView) itemView.findViewById(R.id.content_school_store_name);
            iv_hook = (ImageView) itemView.findViewById(R.id.iv_hook);
        }

        private void initListener() {
            content_school_store_name.setOnClickListener(this);
        }

        //更换被点击Item的布局样式
        private void updateItemView(View itemView, View rootView) {
            TextView textView = ((TextView)itemView);
            textView.setTextColor(mContext.getResources().getColor(R.color.com_green));
//            textView.setBackgroundColor(mContext.getResources().getColor(R.color.gray_selected));
            if (itemTextView != null) {
                itemTextView.setTextColor(mContext.getResources().getColor(R.color.text_black_1));
                itemTextView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            if (itemImageView != null) {
                itemImageView.setVisibility(View.GONE);
            }
            itemTextView = textView;
            itemImageView = (ImageView) rootView.findViewById(R.id.iv_hook);
            itemImageView.setVisibility(View.VISIBLE);
            setSelectedItem(getLayoutPosition());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.content_school_store_name:
                    if (getLayoutPosition() != selectedItem) {
                        //回调接口，响应外部点击事件更换宿舍区对于楼栋
                        if (onItemClickListener != null) {
                            Apartment apartment = mDatas.get(getLayoutPosition());
                            onItemClickListener.OnItemApartmentClick(
                                    mSchoolZones.get(mZonesId) + " " + apartment.getApartmentName(),
                                    apartment,
                                    getLayoutPosition());
                        }
                        updateItemView(v, itemView);
                    }
                    break;
            }
        }
    }

}
