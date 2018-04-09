package com.tajiang.leifeng.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.UserOrderDetialActivity;
import com.tajiang.leifeng.adapter.OrderPassMeListAdapter;
import com.tajiang.leifeng.base.BaseFragment;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshBase;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class UserOrderPassMeFragment extends BaseFragment implements OnRefreshListener<ListView>, HttpResponseListener,OnItemClickListener{
	
	private int pager = 1;
	
	private View emptyView; 
	
	private PullToRefreshListView plv_passmeOrder;
	
	private ListView lv_passmeOrder;
	
	private OrderPassMeListAdapter orderPassMeListAdapter;
	
	private List<Order> orderList = new ArrayList<>();
	
	@Override
	protected void initLayout() {
		setContentView(R.layout.fragment_user_order_pass_me);
	}

	protected void initView( ) {
		emptyView = findViewById(R.id.ll_orderUserEmpty);
		plv_passmeOrder =  findViewById(R.id.plv_passmeOrder);
		lv_passmeOrder = plv_passmeOrder.getRefreshableView();
		
		plv_passmeOrder.setPullLoadEnabled(false);
		plv_passmeOrder.setScrollLoadEnabled(true);
		
		lv_passmeOrder.setEmptyView(emptyView);
	}
	
	protected void initAdapter() {
		orderPassMeListAdapter = new OrderPassMeListAdapter(getActivity(), orderList, R.layout.item_list_order_pass_me_copy);
	}
	
	
	protected void initListener() {
		plv_passmeOrder.setOnRefreshListener(this);
		lv_passmeOrder.setOnItemClickListener(this);
	}
	
	protected void initData() {
		lv_passmeOrder.setAdapter(orderPassMeListAdapter);
		// 加载订单数据
		TJHttpUtil.getInstance(this).getOrderList(1);
	}
	

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Order order = (Order) parent.getItemAtPosition(position);
		Intent intent = new Intent(getActivity() , UserOrderDetialActivity.class);
		intent.putExtra("UERR_ORDER_TYPE", 2);
		intent.putExtra("UERR_ORDER", order );
		startActivityForResult(intent, 3);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		pager =1 ;
		// 加载订单数据
		TJHttpUtil.getInstance(this).getOrderList(pager);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		TJHttpUtil.getInstance(this).getOrderList(pager +1 );
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 3 && resultCode == 3){
			plv_passmeOrder.doPullRefreshing(true, 500);
		}
		
	}

	@Override
	public void onStart(int requestTag) {

	}

	@Override
	public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
		Pager<Order> orderPager = (Pager<Order>) response.getData();

		List<Order> orderListResult = orderPager.getList();

		if(orderListResult != null && orderListResult.size() != 0){

			pager = orderPager.getPage();

			if(pager == 1){
				orderList.clear();
			}

			orderList.addAll(orderPager.getList());
			orderPassMeListAdapter.notifyDataSetChanged();

		}

		plv_passmeOrder.onPullDownRefreshComplete();
		plv_passmeOrder.onPullUpRefreshComplete();

		if(pager == orderPager.getLastPage()){
			plv_passmeOrder.setHasMoreData(false);
		}
	}

	@Override
	public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

	}

	@Override
	public void onFinish(int requestTag) {

	}
}
