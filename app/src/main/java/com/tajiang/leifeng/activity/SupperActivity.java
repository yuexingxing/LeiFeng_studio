package com.tajiang.leifeng.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.OrderPassMeListAdapter;
import com.tajiang.leifeng.adapter.SupperAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.ChannelEntity;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.StoreStalls;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshBase;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshListView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SupperActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener<ListView>, HttpResponseListener {

	private PullToRefreshListView supper_view;
	private ListView mSupperListView;

	private SupperAdapter mSupperAdapter;
	private View ll_orderEmpty;
	private ChannelEntity mChannelEntity;

	private int pager = 1;
	private static final int pageSize = 10;

	private List<StoreStalls> supperList = new ArrayList<>();

	@Override
	protected void initTopBar() {
		mChannelEntity = (ChannelEntity) getIntent().getSerializableExtra("ChannelEntity");
		setTitle(mChannelEntity.getName());
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_supper);
	}

	@Override
	protected void initView() {
		ll_orderEmpty   = findViewById(R.id.ll_orderEmpty);
		supper_view = (PullToRefreshListView) findViewById(R.id.supper_listview);

		mSupperListView = supper_view.getRefreshableView();
		supper_view.setPullLoadEnabled(false);
		supper_view.setScrollLoadEnabled(true);
		mSupperListView.setEmptyView(ll_orderEmpty);
	}

	@Override
	protected void initAdapter() {
		super.initAdapter();
		mSupperAdapter = new SupperAdapter(this, supperList, R.layout.item_supper_listview);
	}

	@Override
	protected void initDates() {
		super.initDates();
		mSupperListView.setAdapter(mSupperAdapter);

		TJHttpUtil.getInstance(this).getStalls(7, pager, pageSize, mChannelEntity.getCode(), 0);
	}

	@Override
	protected void initListener() {
		super.initListener();
		supper_view.setOnRefreshListener(this);
	}

	@Override
	public void onSuccess(BaseResponse response, int requestTag) {
		super.onSuccess(response, requestTag);
		switch (requestTag) {
			case TJRequestTag.TAG_GET_STORE_STALLS: //获取上架档口
				Pager<StoreStalls> storeStallsList = (Pager<StoreStalls>) response.getData();
				List<StoreStalls> list = storeStallsList.getList();

				if(list != null && list.size() != 0){

					pager = storeStallsList.getPage();

					if(pager == 1){
						supperList.clear();
					}

					supperList.addAll(list);
					mSupperAdapter.notifyDataSetChanged();

				}

				supper_view.onPullDownRefreshComplete();
				supper_view.onPullUpRefreshComplete();

				if(pager == storeStallsList.getLastPage()){
					supper_view.setHasMoreData(false);
				}

//				if (list.size() == 0) {
//					ToastUtils.showShort("食堂营业筹备中！");
//				}
				break;
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		pager =1 ;
		TJHttpUtil.getInstance(this).getStalls(7, pager, pageSize, mChannelEntity.getCode(), 0);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		TJHttpUtil.getInstance(this).getStalls(7, pager + 1, pageSize, mChannelEntity.getCode(), 0);
	}
}
