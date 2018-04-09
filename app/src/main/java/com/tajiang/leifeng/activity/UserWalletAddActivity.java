package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.UserWalletPayActivityListAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Action;

import java.util.ArrayList;
import java.util.List;

public class UserWalletAddActivity extends BaseActivity implements OnItemClickListener, HttpResponseListener{

    private Button btn_sureAddWallet;

    private GridView gcPayActivity;

    private UserWalletPayActivityListAdapter userWalletPayActivityListAdapter;

    private List<Action> actionList = new ArrayList<>();

    private int selectPosition = 0;

    @Override
    protected void initTopBar() {
        setTitle("充值");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_wallet_add);
    }

    @Override
    protected void initView() {
        btn_sureAddWallet = (Button) findViewById(R.id.btn_sureAddWallet);

        gcPayActivity = (GridView) findViewById(R.id.gcPayActivity);

    }

    @Override
    protected void initAdapter() {
        super.initAdapter();

        userWalletPayActivityListAdapter = new UserWalletPayActivityListAdapter(this, actionList, R.layout.item_grid_pay_activity);
        gcPayActivity.setAdapter(userWalletPayActivityListAdapter);

    }

    @Override
    protected void initDates() {
        super.initDates();

        TJHttpUtil.getInstance(this).userPDActivity();
    }

    @Override
    protected void initListener() {
        super.initListener();

        btn_sureAddWallet.setOnClickListener(this);
        gcPayActivity.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_sureAddWallet:

                Intent intent = new Intent(this , UserWalletAddPayActivity.class);
                intent.putExtra("Action", actionList.get(selectPosition));
                startActivity(intent);

                break;
        }
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectPosition = position;
        userWalletPayActivityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart(int requestTag) {
        showLoading();
    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_PD_Activity:
                List<Action> actionListResult = (List<Action>) response.getData();

                if(actionListResult != null){
                    actionList.addAll(actionListResult);
                    userWalletPayActivityListAdapter.notifyDataSetChanged();
                }

                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        dismissLoading();
    }

    @Override
    public void onFinish(int requestTag) {
        dismissLoading();
    }
}
