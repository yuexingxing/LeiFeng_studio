package com.tajiang.leifeng.activity;

import android.widget.ListView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.UserWalletTranscationDetailListAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.PredepositLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12154 on 2016/1/15.
 */
public class UserWalletTransactionDetailActivity extends BaseActivity implements HttpResponseListener {

    private ListView lv_transactionDetailWallet;

    private UserWalletTranscationDetailListAdapter userWalletTranscationDetailListAdapter;
    private List<PredepositLog> predepositLogList = new ArrayList<PredepositLog>();

    @Override
    protected void initTopBar() {
        setTitle("余额明细");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_wallet_transaction_detail);
    }

    @Override
    protected void initView() {
        lv_transactionDetailWallet = (ListView) findViewById(R.id.lv_transactionDetailWallet);
    }

    @Override
    protected void initDates() {
        super.initDates();
        TJHttpUtil.getInstance(this).userPredepositLogsReq(null , 1);
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();

        userWalletTranscationDetailListAdapter = new UserWalletTranscationDetailListAdapter(this, predepositLogList, R.layout.item_list_wallet_transcation_detail);
        lv_transactionDetailWallet.setAdapter(userWalletTranscationDetailListAdapter);

    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_WELLAT_PREDEPOSIT_LOGS:
                Pager<PredepositLog> predepositLogPager = (Pager<PredepositLog>) response.getData();
                predepositLogList.addAll(predepositLogPager.getList());
                userWalletTranscationDetailListAdapter.notifyDataSetChanged();
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
