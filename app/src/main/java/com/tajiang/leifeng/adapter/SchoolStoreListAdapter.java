package com.tajiang.leifeng.adapter;

import android.content.Context;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Store;

import java.util.List;

/**
 * Created by 12154 on 2016/1/13.
 */
public class SchoolStoreListAdapter extends CommonAdapter<Store> {

    public SchoolStoreListAdapter(Context context, List<Store> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, Store item) {
        helper.setImage(R.id.ivImgStore, item.getLogo());
        helper.setText(R.id.tvNameStore, item.getName());
    }
}
