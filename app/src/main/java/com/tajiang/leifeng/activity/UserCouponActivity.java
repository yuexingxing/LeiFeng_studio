package com.tajiang.leifeng.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.UserChooseCouponListAdapter;
import com.tajiang.leifeng.adapter.UserCouponListAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.Voucher;
import com.tajiang.leifeng.model.VoucherList;
import com.tajiang.leifeng.utils.DateUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;
import com.tajiang.leifeng.view.dialog.CDialog;

public class UserCouponActivity extends BaseActivity implements  HttpResponseListener{
	
	private View rect_couponEmpty;
	
	private TextView tv_numCashUserCoupon;
	
	private Button btn_cashUserCoupon;
	
	private ListView lv_coupon;
	
	private UserCouponListAdapter usableCouponListAdapter; //可用红包
	private UserCouponListAdapter usedCouponListAdapter;   //不可用红包
	
	private AnimLoadDialog animLoadDialog;
	
	private CDialog cDialog;
	
	private Button btn_sureCashUserCoupon;

	private LinearLayout ll_coupon_category;

	private TextView tv_coupon_usable;
	private TextView tv_coupon_disable;

	private ArrayList<Voucher> UsableVoucherList = new ArrayList<Voucher>();
	private ArrayList<Voucher> UsedVoucherList = new ArrayList<Voucher>();
	@Override
	protected void initTopBar() {
		setTitle("优惠券");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_coupon);
	}

	@Override
	protected void initView() {
		rect_couponEmpty = findViewById(R.id.rect_couponEmpty);

		btn_cashUserCoupon = (Button) findViewById(R.id.btn_cashUserCoupon);

		ll_coupon_category = (LinearLayout) findViewById(R.id.ll_coupon_category);

		tv_coupon_usable = (TextView) findViewById(R.id.tv_coupon_usable);

		tv_coupon_disable = (TextView) findViewById(R.id.tv_coupon_disable);

		lv_coupon = (ListView) findViewById(R.id.lv_coupon);
		lv_coupon.setEmptyView(rect_couponEmpty);

		tv_coupon_usable.setEnabled(false);
		
	}
	
	@Override
	protected void initAdapter() {
		super.initAdapter();

//		/**
//		 * 分离可用和已经使用的红包
//		 */
//		for (Voucher voucher : voucherList) {
//			if (voucher.getState() == Voucher.STATE_USABLE) {
//				UsableVoucherList.add(voucher);
//			} else {
//				UsedVoucherList.add(voucher);
//			}
//		}
//		sortCouponList(voucherList);

		usableCouponListAdapter = new UserCouponListAdapter(this, UsableVoucherList, R.layout.item_list_order_coupon);
		usedCouponListAdapter = new UserCouponListAdapter(this, UsedVoucherList, R.layout.item_list_order_coupon);
	}

	/**
	 * 日期升序排序
	 * @param list
     */
	private void sortCouponList(List<Voucher> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 1; j < list.size() - i; j++) {
				Voucher a;
				if (DateUtils.stringToLong((list.get(j - 1)).getExpireTime(), "yyyy-MM-dd HH:mm:ss") > DateUtils.stringToLong((list.get(j)).getExpireTime(), "yyyy-MM-dd HH:mm:ss")) { // 比较两个整数的大小

					a = list.get(j - 1);
					list.set((j - 1), list.get(j));
					list.set(j, a);
				}
			}
		}
	}

	@Override
	protected void initDates() {
		super.initDates();

		lv_coupon.setAdapter(usableCouponListAdapter);
//		TJHttpUtils.getInstance(this).getCouponList(1, null,null);
//		TJHttpUtils.getInstance(this).getUserCoupon();
		TJHttpUtil.getInstance(this).getUserCoupon();

	}
	
	@Override
	protected void initListener() {
		super.initListener();

		tv_coupon_usable.setOnClickListener(this);
		tv_coupon_disable.setOnClickListener(this);
		btn_cashUserCoupon.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		
		switch (view.getId()) {
		case R.id.btn_cashUserCoupon:  //优惠卷提现
			TJHttpUtil.getInstance(this).rapGetUserCashCount();
			break;
		case R.id.btn_sureCashUserCoupon:  //确认优惠卷提现
			TJHttpUtil.getInstance(this).rapDoUserCash();
			break;
		case R.id.tv_coupon_usable:  //可用红包
			showUsableCoupon(true);
			break;
		case R.id.tv_coupon_disable: //历史红包
			showUsableCoupon(false);
			break;
		}
		
	}

	/**
	 * true 显示可用红包
	 * false 显示历史红包
	 * @param enable
     */
	private void showUsableCoupon(boolean enable) {
		tv_coupon_usable.setEnabled(!enable);
		tv_coupon_disable.setEnabled(enable);
		if (enable == true) {
			ll_coupon_category.setBackgroundResource(R.drawable.img_coupon_usable);
			tv_coupon_usable.setTextColor(this.getResources().getColor(R.color.white));
			tv_coupon_disable.setTextColor(this.getResources().getColor(R.color.com_green));
			lv_coupon.setAdapter(usableCouponListAdapter);
			lv_coupon.startLayoutAnimation();
		} else {
			ll_coupon_category.setBackgroundResource(R.drawable.img_coupon_disable);
			tv_coupon_usable.setTextColor(this.getResources().getColor(R.color.com_green));
			tv_coupon_disable.setTextColor(this.getResources().getColor(R.color.white));
			lv_coupon.setAdapter(usedCouponListAdapter);
			lv_coupon.startLayoutAnimation();
		}
	}

	@Override
	public void onStart(int requestTag) {

	}

	@Override
	public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
		switch (requestTag) {
			case TJRequestTag.TAG_GET_COUPON:
				VoucherList voucherPager = (VoucherList) response.getData();
				List<Voucher> unableList = voucherPager.getUnableList();

				List<Voucher> useableList = voucherPager.getUseableList();

				/**
				 * 分离可用和已经使用的红包
				 */
				UsableVoucherList.clear();
				UsedVoucherList.clear();
				UsableVoucherList.addAll(unableList);
				UsedVoucherList.addAll(useableList);

				usableCouponListAdapter.notifyDataSetChanged();
				usedCouponListAdapter.notifyDataSetChanged();
//			sortCouponList(voucherList);

//			 if(voucherPager.getList().size() > 0){
//				 btn_cashUserCoupon.setVisibility(View.VISIBLE);
//			 }

				break;
			case TJRequestTag.TAG_ORDER_RAP_USER_CASH_COUNT:
				int numCash = (Integer) response.getData();
				if(cDialog ==null){
					cDialog = new CDialog(this, R.layout.dialog_user_coupon_cash);
				}
				tv_numCashUserCoupon = (TextView) cDialog.findChildViewById(R.id.tv_numCashUserCoupon);
				btn_sureCashUserCoupon = (Button) cDialog.findChildViewById(R.id.btn_sureCashUserCoupon);

				btn_sureCashUserCoupon.setOnClickListener(this);
				tv_numCashUserCoupon.setText(numCash + "");
				cDialog.show();
				break;

			case TJRequestTag.TAG_ORDER_RAP_USER_CASH:

				if((Boolean) response.getData()){
					ToastUtils.showShort("体现成功");
				}

				break;
		}
	}

	@Override
	public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

	}

	@Override
	public void onFinish(int requestTag) {

	}
}
