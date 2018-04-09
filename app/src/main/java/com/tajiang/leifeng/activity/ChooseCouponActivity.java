package com.tajiang.leifeng.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.UserChooseCouponListAdapter;
import com.tajiang.leifeng.adapter.UserCouponListAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.Voucher;
import com.tajiang.leifeng.utils.DateUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;
import com.tajiang.leifeng.view.dialog.CDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ChooseCouponActivity extends BaseActivity implements HttpResponseListener {

    private View rect_couponEmpty;

    private ListView lv_coupon;

    private UserChooseCouponListAdapter usableCouponListAdapter;
    private UserChooseCouponListAdapter usedCouponListAdapter;

    private ArrayList<Voucher> voucherList = new ArrayList<Voucher>();
    private ArrayList<Voucher> UsableVoucherList = new ArrayList<Voucher>();
    private ArrayList<Voucher> UsedVoucherList = new ArrayList<Voucher>();

    private LinearLayout ll_coupon_category;

    private TextView tv_coupon_usable;
    private TextView tv_coupon_disable;

    private BigDecimal sumPrice;
    private Voucher defaultVoucher;

    @Override
    protected void initTopBar() {
        setTitle("优惠券");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_choose_user_coupon);
    }

    @Override
    protected void initView() {

        rect_couponEmpty = findViewById(R.id.rect_couponEmpty);

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
    }

    @Override
    protected void initDates() {
        super.initDates();

        voucherList = getIntent().getParcelableArrayListExtra("voucher_list");
        sumPrice = (BigDecimal) getIntent().getSerializableExtra("sum_price");
        defaultVoucher = getIntent().getParcelableExtra("default_voucher");

        /**
         * 分离可用和已经使用的红包
         */
        for (Voucher voucher : voucherList) {
            if (voucher.getState() == Voucher.STATE_USABLE) {
                UsableVoucherList.add(voucher);
            } else {
                UsedVoucherList.add(voucher);
            }
        }

        /**
         * 排序：
         * 可用红包列表：
         * 排序：按照过期顺序，快过期的在顶部；
         * 不可用红包为灰色，不可点击
         */
        UsableVoucherList = sortVoucherList(UsableVoucherList, sumPrice);

        //可用红包
        usableCouponListAdapter = new UserChooseCouponListAdapter(this, UsableVoucherList, defaultVoucher, sumPrice,  R.layout.item_list_order_coupon);
        usableCouponListAdapter.setOnItemClick(new UserChooseCouponListAdapter.OnItemClick() {
            @Override
            public void onItemClicked(Voucher voucher) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("voucher", voucher);
                ChooseCouponActivity.this.setResult(Activity.RESULT_OK, ChooseCouponActivity.this.getIntent().putExtras(bundle));
                ChooseCouponActivity.this.finish();
            }
        });
        //历史过期红包
        usedCouponListAdapter = new UserChooseCouponListAdapter(this, UsedVoucherList, null, null, R.layout.item_list_order_coupon);
        lv_coupon.setAdapter(usableCouponListAdapter);
    }

    private ArrayList<Voucher> sortVoucherList(ArrayList<Voucher> originalVoucherList, BigDecimal sumPrice) {
        ArrayList<Voucher> Temp = new ArrayList<>();
        ArrayList<Voucher> TempList = new ArrayList<>();  //满足条件的可用的红包
        ArrayList<Voucher> TempList2 = new ArrayList<>();  //不满足条件的可用的红包

        for (Voucher voucher : originalVoucherList) {
            if (new BigDecimal(0.00).compareTo(voucher.getLimitPrice()) == 0
                    || sumPrice.compareTo(voucher.getLimitPrice()) >= 0) {
                TempList.add(voucher);
            } else {
                TempList2.add(voucher);
            }
        }

        //日期升序排序排序
        sortList(TempList);

        Temp.addAll(TempList);
        Temp.addAll(TempList2);
        return Temp;
    }


    /**
     * 日期升序排序
     * @param list
     */
    private void sortList(List<Voucher> list) {
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
    protected void initListener() {
        super.initListener();

        tv_coupon_usable.setOnClickListener(this);
        tv_coupon_disable.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
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
        } else {
            ll_coupon_category.setBackgroundResource(R.drawable.img_coupon_disable);
            tv_coupon_usable.setTextColor(this.getResources().getColor(R.color.com_green));
            tv_coupon_disable.setTextColor(this.getResources().getColor(R.color.white));
            lv_coupon.setAdapter(usedCouponListAdapter);
        }
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(BaseResponse response, int requestTag) {

    }

    @Override
    public void onFailed(BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {

    }
}
