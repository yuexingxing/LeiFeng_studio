package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Apartment;
import com.tajiang.leifeng.model.ApartmentZone;

import java.util.List;

/**
 * Created by ciko on 16/3/24.
 */
public class ApartmentZoneListAdapter extends CommonAdapter<ApartmentZone>{

    private int selectedItem = 0 ;

    public ApartmentZoneListAdapter(Context context, List<ApartmentZone> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, ApartmentZone item) {

        TextView textView = helper.getView(R.id.content);
        textView.setText(item.getName());

        if(helper.getPosition() == selectedItem){
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        }else {
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
        }

    }

    public void setSelected(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }

}
