package com.tajiang.leifeng.adapter;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Apartment;

public class UserAdressAddApartmentAdapter extends CommonAdapter<Apartment>{

	public UserAdressAddApartmentAdapter(Context context, List<Apartment> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Apartment item) {
		
		TextView name = helper.getView(R.id.tv_nameApartmentAdressAdd);
		name.setText(item.getApartmentName());
		
	}

}
