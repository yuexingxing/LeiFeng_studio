package com.tajiang.leifeng.adapter;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.School;

public class UserAdressAddSchoolAdapter extends CommonAdapter<School>{

	public UserAdressAddSchoolAdapter(Context context, List<School> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, School item) {
		
		TextView name = helper.getView(R.id.tv_nameSchoolAdressAdd);
		name.setText(item.getSchoolName());
		
	}

}
