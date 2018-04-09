package com.tajiang.leifeng.adapter;

import android.content.Context;

import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Goods;

import java.util.List;

/**
 * 选餐 -  ListView的Adapter
 */
@SuppressWarnings("ALL")
public class StoreFoodSelectListAdapter extends CommonAdapter<Goods> {

	public StoreFoodSelectListAdapter(Context context, List<Goods> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, final Goods item) {

	}
}