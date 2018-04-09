package com.tajiang.leifeng.adapter;

import android.content.Context;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.City;

import java.util.List;

/**
 * Created by ciko on 16/5/5.
 */
public class SchoolHotCityGridAdapter extends CommonAdapter<City>{

    public SchoolHotCityGridAdapter(Context context, List<City> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, City item) {
        helper.setText(R.id.tvNameCityHotSchool, item.getAreaName());
    }
}
