package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Apartment;

import java.util.List;

/**
 * Created by ciko on 16/3/24.
 */
public class ApartmentListAdapter extends CommonAdapter<Apartment>{

    private int selectPosition = -1;

    public ApartmentListAdapter(Context context, List<Apartment> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);

    }

    @Override
    public void convert(ViewHolder helper, Apartment item) {

        TextView textView = helper.getView(R.id.content);
        textView.setText(item.getApartmentName());

        if (helper.getPosition() % 2 == 0) {
            textView.setBackgroundColor(Color.parseColor("#fbfbfb"));
        } else {
            textView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        if(helper.getPosition() == selectPosition){
            textView.setBackgroundColor(Color.parseColor("#e9e9e9"));
        }

    }

    public void setSelect(int position) {
        selectPosition = position;
        notifyDataSetChanged();
    }

    public  void cannelSelect() {
        selectPosition = -1;
        notifyDataSetChanged();
    }

}
