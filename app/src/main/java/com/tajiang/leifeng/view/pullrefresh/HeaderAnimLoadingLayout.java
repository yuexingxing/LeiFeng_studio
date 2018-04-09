package com.tajiang.leifeng.view.pullrefresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tajiang.leifeng.R;

import pl.droidsonroids.gif.GifImageView;

@SuppressLint("InflateParams")
public class HeaderAnimLoadingLayout extends LoadingLayout{

	  /**Header的容器*/
    private RelativeLayout mHeaderContainer;
    
//    private ImageView animImage;

	private GifImageView gifImage;
    
	public HeaderAnimLoadingLayout(Context context) {
		super(context);
		init(context);
	}

	public HeaderAnimLoadingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public HeaderAnimLoadingLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	 /**
     * 初始化
     * 
     * @param context context
     */
    private void init(Context context) {
    	mHeaderContainer = (RelativeLayout) findViewById(R.id.pull_to_refresh_header_content);
//    	从xml中得到GifView的句柄
		gifImage = (GifImageView) findViewById(R.id.gif);
//    	AnimationDrawable animationDrawable = (AnimationDrawable) animImage.getBackground();
//		animationDrawable.start();
    }

	@Override
	public int getContentSize() {
		if (null != mHeaderContainer) {
			return mHeaderContainer.getHeight();
		}
		return (int) (getResources().getDisplayMetrics().density * 90);
	}

	@Override
	protected View createLoadingView(Context context, AttributeSet attrs) {
		View container = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header3, null);
        return container;
	}

	@Override
	protected void init(Context context, AttributeSet attrs) {
		super.init(context, attrs);
	}

	@Override
	public void show(boolean show) {
		super.show(show);
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
		super.setLastUpdatedLabel(label);
	}

	@Override
	public void setLoadingDrawable(Drawable drawable) {
		super.setLoadingDrawable(drawable);
		
	}

	@Override
	public void setPullLabel(CharSequence pullLabel) {
		super.setPullLabel(pullLabel);
	}

	@Override
	public void setRefreshingLabel(CharSequence refreshingLabel) {
		super.setRefreshingLabel(refreshingLabel);
	}

	@Override
	public void setReleaseLabel(CharSequence releaseLabel) {
		super.setReleaseLabel(releaseLabel);
	}

	@Override
	public void setState(State state) {
		super.setState(state);
		
	}

	@Override
	public State getState() {
		return super.getState();
	}

	@Override
	public void onPull(float scale) {
		super.onPull(scale);
//		gf1.showFrame(scale , isRun);
	}

	@Override
	protected State getPreState() {
		return super.getPreState();
	}

	@Override
	protected void onStateChanged(State curState, State oldState) {
		super.onStateChanged(curState, oldState);
		
//		if(curState  == State.REFRESHING){
//			gf1.restartGif();
//			isRun = true;
//		}else{
//			isRun = false;
//		}
		
	}

	@Override
	protected void onReset() {
		super.onReset();
	}

	@Override
	protected void onPullToRefresh() {
		super.onPullToRefresh();
		gifImage.setImageResource(R.drawable.load_start);
	}

	@Override
	protected void onReleaseToRefresh() {
		super.onReleaseToRefresh();
	}

	@Override
	protected void onRefreshing() {
		super.onRefreshing();
		gifImage.setImageResource(R.drawable.load_ing);
	}

	@Override
	protected void onNoMoreData() {
		super.onNoMoreData();
	}
	
	

}
