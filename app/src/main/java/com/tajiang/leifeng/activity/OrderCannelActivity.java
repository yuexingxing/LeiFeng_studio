package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.CancelReasonAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.constant.TJCst;
import com.tajiang.leifeng.model.CancelReason;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.utils.SoftKeyboardUtil;
import com.tajiang.leifeng.view.dialog.ActionSheetDialog;
import com.tajiang.leifeng.view.dialog.CallDialog;

import java.util.ArrayList;
import java.util.List;

public class OrderCannelActivity extends BaseActivity {

    private Button btnMessPhoneOrderCannel;
    private Button btnLeiFengPhoneOrderCannel;
    private Order order;

    private LinearLayout layout_cancel;
    private TextView tvNameStoreOrder_cancel;
    private TextView tvStateOrder_cancel;
    private TextView tv_store_name_cancel;
    private SimpleDraweeView tv_picOrder_cancel;
    private TextView tvNameGoodOrder_cancel;
    private TextView tvTimeOrder_cancel;
    private TextView tvPriceOrder_cancel;

    private RecyclerView reason_cancel_recycler;
    private CancelReasonAdapter cancelReasonAdapter;
    private EditText cancel_reason_edit;//退款具体说明
    private TextView tvNegativeOrderDetail_cancel;//取消申请
    private TextView tvPositiveOrderDetail_cancel;//提交申请
    private LinearLayout choose_reason_layout;
    private LinearLayout recycler_cancel_layout;
    private LinearLayout cancel_edit_layout;
    private ImageView cancel_choose_iv;
    private TextView introduce_text;
    private TextView tv_moneycause_cancel;
    private List<CancelReason> list;

    private boolean isVisible = false;

    @Override
    protected void initTopBar() {
        setTitle("申请退单");
        enableNavLeftImage();
        order = (Order) getIntent().getSerializableExtra("ORDER");
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_order_cannel);
    }

    @Override
    protected void initView() {
        btnMessPhoneOrderCannel = (Button) findViewById(R.id.btnMessPhoneOrderCannel);
        btnLeiFengPhoneOrderCannel = (Button) findViewById(R.id.btnLeiFengPhoneOrderCannel);

        //TODO....新版本......
//        layout_cancel = (LinearLayout) findViewById(R.id.layout_cancel);
//        tvNameStoreOrder_cancel = (TextView) findViewById(R.id.tvNameStoreOrder_cancel);
//        tvStateOrder_cancel = (TextView) findViewById(R.id.tvStateOrder_cancel);
//        tv_store_name_cancel = (TextView) findViewById(R.id.tv_store_name_cancel);
//        tv_picOrder_cancel = (SimpleDraweeView) findViewById(R.id.tv_picOrder_cancel);
//        tvNameGoodOrder_cancel = (TextView) findViewById(R.id.tvNameGoodOrder_cancel);
//        tvTimeOrder_cancel = (TextView) findViewById(R.id.tvTimeOrder_cancel);
//        tvPriceOrder_cancel = (TextView) findViewById(R.id.tvPriceOrder_cancel);
//
//        reason_cancel_recycler = (RecyclerView) findViewById(R.id.reason_cancel_recycler);
//        cancel_reason_edit = (EditText) findViewById(R.id.cancel_reason_edit);
//        tvNegativeOrderDetail_cancel = (TextView) findViewById(R.id.tvNegativeOrderDetail_cancel);
//        tvPositiveOrderDetail_cancel = (TextView) findViewById(R.id.tvPositiveOrderDetail_cancel);
//        choose_reason_layout = (LinearLayout) findViewById(R.id.choose_reason_layout);
//        recycler_cancel_layout = (LinearLayout) findViewById(R.id.recycler_cancel_layout);
//        cancel_choose_iv = (ImageView) findViewById(R.id.cancel_choose_iv);
//        cancel_edit_layout = (LinearLayout) findViewById(R.id.cancel_edit_layout);
//        introduce_text = (TextView) findViewById(R.id.introduce_text);
//        tv_moneycause_cancel = (TextView) findViewById(R.id.tv_moneycause_cancel);
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        list = new ArrayList<CancelReason>();
        for (int i = 0; i <= 6; i++) {
            list.add(new CancelReason("A、送餐太慢了，等的花都谢了" + i));
        }
//        updateCancelReason(list);
    }

    @Override
    protected void initListener() {
        super.initListener();

        btnMessPhoneOrderCannel.setOnClickListener(this);
        btnLeiFengPhoneOrderCannel.setOnClickListener(this);
//        initKeyListener();
//        tvNegativeOrderDetail_cancel.setOnClickListener(this);
//        tvPositiveOrderDetail_cancel.setOnClickListener(this);
//        choose_reason_layout.setOnClickListener(this);
//        cancelReasonAdapter.setOnItemClickLitener(new CancelReasonAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(int position) {
//                tv_moneycause_cancel.setText(list.get(position).getReasonText());
//                cancelReasonAdapter.notifyDataSetChanged();
//                showRecycler(false);//隐藏RecyclerView控件
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
//            case R.id.tvNegativeOrderDetail_cancel:
//                finish();
//                break;
//            case R.id.tvPositiveOrderDetail_cancel:
//                intent2Activity(ApplySuccessActivity.class);
//                break;
//            case R.id.choose_reason_layout:
//                if (!isVisible) {
//                    showRecycler(true);
//                } else if (isVisible) {
//                    showRecycler(false);
//                }

            case R.id.btnMessPhoneOrderCannel:

                showContactDialog(order);

                break;
            case R.id.btnLeiFengPhoneOrderCannel:

                CallDialog callDialog = new CallDialog(this, TJCst.CUSTOMER_SERVICES_PHONE);
                callDialog.show();

                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(BaseResponse response, int requestTag) {
        super.onSuccess(response, requestTag);
//        updateCancelReason((List<CancelReason>) response.getData());
    }

//    private void updateCancelReason(List<CancelReason> data) {
//        cancelReasonAdapter = new CancelReasonAdapter(getContext(), data);
//        reason_cancel_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        reason_cancel_recycler.setAdapter(cancelReasonAdapter);
//    }

    private void showRecycler(boolean b) {
        if (b) {
            recycler_cancel_layout.setVisibility(View.VISIBLE);
            cancel_choose_iv.setBackgroundResource(R.drawable.icon_expand_tv_down);
            isVisible = true;
            cancel_edit_layout.setVisibility(View.GONE);
            introduce_text.setVisibility(View.GONE);
        } else {
            recycler_cancel_layout.setVisibility(View.GONE);
            cancel_choose_iv.setBackgroundResource(R.drawable.icon_expand_tv_up);
            isVisible = false;
            cancel_edit_layout.setVisibility(View.VISIBLE);
            introduce_text.setVisibility(View.VISIBLE);
        }
    }

    private void initKeyListener() {
        //监听键盘的显示和隐藏
        SoftKeyboardUtil.observeSoftKeyboard(this, new SoftKeyboardUtil.OnSoftKeyboardChangeListener() {
            @Override
            public void onSoftKeyBoardChange(int softKeybardHeight,int screenHeight, boolean visible) {
                if (visible == true) {
                    if (layout_cancel.getVisibility() == View.VISIBLE) {
                        layout_cancel.setVisibility(View.GONE);
                    }
                } else {
                    layout_cancel.setVisibility(View.VISIBLE);
                }
            }
        });

        //添加搜索框清空时候事件
        cancel_reason_edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void showContactDialog(Order order) {

        if (order.getTakeInfo() != null) {
            String phoneInfo = order.getTakeInfo();
            ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this)
                    .builder()
                    .setCanceledOnTouchOutside(true)
                    .setTitle("联系号码")
                    .setCanceledOnTouchOutside(false);

            String[] phoneArr = phoneInfo.split(",");

            if (phoneArr.length == 0) {
                showToast("没有联系人的号码");
                return;
            }

            for (final String phone : phoneArr) {
                actionSheetDialog.addSheetItem(phone, ActionSheetDialog.SheetItemColor.Green,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onSheetItemClick(int which) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.CALL");
                                intent.setData(Uri.parse("tel:" + phone)); // num为电话号码
                                startActivity(intent);
                            }
                        });
            }
            actionSheetDialog.show();
        } else {
            showToast("没有联系人的号码");
        }
    }

}
