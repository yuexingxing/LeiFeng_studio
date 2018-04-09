package com.tajiang.leifeng.adapter;

import android.content.Context;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.School;

import java.util.List;

/**
 * Created by 12154 on 2016/1/13.
 */
public class SchoolsSelectListAdapter extends CommonAdapter<School>{

    public SchoolsSelectListAdapter(Context context, List<School> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, School item) {
        helper.setText(R.id.tvNameSchool, item.getSchoolName());
    }
}
