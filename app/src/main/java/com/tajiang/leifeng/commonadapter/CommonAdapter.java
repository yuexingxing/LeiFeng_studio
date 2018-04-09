package com.tajiang.leifeng.commonadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.utils.ImageUtils;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
	
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;
	
	public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	public Context getContext() {
		return mContext;
	}
	
	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
		convert(viewHolder, getItem(position));
		return viewHolder.getConvertView();

	}

	public abstract void convert(ViewHolder helper, T item);

	private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,position);
	}

	protected void startActivity(Intent intent){
		getContext().startActivity(intent);
		((Activity) getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	protected void startActivityAndForResult(Intent intent, int requestCode){
		((Activity) getContext()).startActivityForResult(intent, requestCode);
		((Activity) getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	protected void loadImage(ImageView imageView, String imgUrl, int placeImgResId) {
		ImageUtils.loadImage(getContext() , imageView, imgUrl, placeImgResId);
	}

	protected void loadImage(ImageView imageView, String imgUrl) {
		ImageUtils.loadImage(getContext() , imageView, imgUrl, null);
	}

}
