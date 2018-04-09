package com.tajiang.leifeng.commonadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class ViewHolder {
	
	private final SparseArray<View> mViews;
	private int mPosition;
	
	private Context mContext;
	
	private View mConvertView;

	private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mContext = context;
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder(context, parent, layoutId, position);
		} else {
			holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
		}
		return holder;
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, Object text) {
		TextView view = getView(viewId);
		if(text instanceof String){
			view.setText((String)text);
		}

		else{
			view.setText(String.valueOf(text));
		}

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}
	/**
	 * 为ImageView设置图片
	 *
	 * @param viewId
	 * @return
	 */
	public ViewHolder setImage(int viewId, String url) {
//		PicassoUtils.loadImage(mContext, url , R.drawable.pic_img_place_holder, (ImageView) this.getView(viewId));

		Uri uri = Uri.parse(url+"?imageView/2/w/180");
		SimpleDraweeView simpleDraweeView = getView(viewId);
		simpleDraweeView.setImageURI(uri);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 */
	public ViewHolder setFoodImageByUrl(int viewId, String url) {

		Uri uri = Uri.parse(url+"?imageView/2/w/180");
		SimpleDraweeView simpleDraweeView = getView(viewId);
		simpleDraweeView.setImageURI(uri);

		return this;
	}

	public int getPosition() {
		return mPosition;
	}
	
}
