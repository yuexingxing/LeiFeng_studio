package com.tajiang.leifeng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.utils.DensityUtils;
import com.tajiang.leifeng.view.StartView.OnStarChooseListener;

public class EvaluateView extends LinearLayout implements OnStarChooseListener {
	
	private int startX = 0;
	
	private boolean left = false;

	private int scrollDistance = 0;

	private Scroller scroller;

	private TextView tv_startNum;
	private TextView tv_text;
	private TextView btn_sure;
	
	private LinearLayout ll_content;
	
	private OnEvaluateListener onEvaluateListener;

	
	private StartView startView;

	public EvaluateView(Context context, AttributeSet attrs) {
		super(context, attrs);

		scroller = new Scroller(context);

		initLayout();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		scrollDistance = tv_text.getWidth() - DensityUtils.dp2px(getContext(), 5);
		
	}

	private void initLayout() {

		LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_evaluate, null);
		
		ll_content = (LinearLayout) linearLayout.findViewById(R.id.ll_content);
		startX = (int) ll_content.getX();

		 startView = (StartView) linearLayout.findViewById(R.id.startView);
		startView.setStarChooseListener(this);
		startView.enableClick(true);

		tv_text = (TextView) linearLayout.findViewById(R.id.tv_text);
		tv_startNum = (TextView) linearLayout.findViewById(R.id.tv_startNum);
		 btn_sure = (TextView) linearLayout.findViewById(R.id.btn_sure);
		btn_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				tv_text.setText("已评价");
				tv_text.setWidth(DensityUtils.dp2px(getContext(), 60));
				scrollDistance = tv_text.getWidth() + DensityUtils.dp2px(getContext(), 10);
				
				scroller.startScroll((int) ll_content.getX() + scrollDistance, 0, - scrollDistance  , 0, DensityUtils.dp2px(getContext(), 116));
				left = false;
				postInvalidate();
				startView.enableClick(false);
				btn_sure.setVisibility(View.GONE);
				
				onEvaluateListener.onEvaluate(EvaluateView.this, startView.getStartNum());

			}
		});

		addView(linearLayout);

	}
	
	@Override
	public void computeScroll() {

		if (scroller.computeScrollOffset()) {
			ll_content.scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
		}

	}

	@Override
	public void onStarChoose(int num) {

		if (!left) {
			scroller.startScroll((int) ll_content.getX(), 0, scrollDistance, 0, DensityUtils.dp2px(getContext(), 166));
			left = true;
		}

		tv_startNum.setText(num + "星");
		postInvalidate();

	}
	
	public void setEvaluateStart(int num) {
		tv_text.setText("已评价");
		startView.enableClick(false);
		startView.setStartNum(num);
		btn_sure.setVisibility(View.GONE);
	}
	
	public void onReSet() {
		ll_content.setX(startX);
	}
	
	public interface OnEvaluateListener{
		public void onEvaluate(EvaluateView evaluateView, int num);
	}

	public OnEvaluateListener getOnEvaluateListener() {
		return onEvaluateListener;
	}

	public void setOnEvaluateListener(OnEvaluateListener onEvaluateListener) {
		this.onEvaluateListener = onEvaluateListener;
	};

}
