package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.SchoolStoreListAdapter;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.model.Store;

import java.util.ArrayList;
import java.util.List;

public class SchoolStoreActivity extends BaseActivity implements AdapterView.OnItemClickListener , HttpResponseListener{

    private ListView lv_storeList;

    private List<Store> storeList = new ArrayList<>();

    private School school;

    @Override
    protected void initTopBar() {
        setTitle("选择餐厅");
        enableNavLeftImage();

        school = (School) getIntent().getSerializableExtra("school");
//        storeList.addAll(school.getStoreList());
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_school_store);
    }

    @Override
    protected void initView() {
        lv_storeList = (ListView) findViewById(R.id.lv_storeList);
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();

    }

    @Override
    protected void initDates() {
        super.initDates();

        TJHttpUtil.getInstance(this).getSchoolStorList(school.getSchoolId());

    }

    @Override
    protected void initListener() {
        super.initListener();
        lv_storeList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Store storePre = TJApp.getIns().getStore();
        Store storeNew = storeList.get(position);

        // 判断是否已经清空过
        if (storePre != null) {
            Intent intent = new Intent(this, FoodActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("school", school);

            // 切换餐厅如果是原餐厅
            if (!storePre.getId().equals(storeNew.getId())) {
                intent.putExtra("Store", storeNew);
                TJApp.getIns().setStore(storeNew);
            } else {
                intent.putExtra("Store", storePre);
            }

            startActivity(intent);
            finish();
        } else {

            Intent intent = new Intent(this, FoodActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("school", school);
            intent.putExtra("Store", storeNew);
            TJApp.getIns().setStore(storeNew);
            startActivity(intent);
            finish();

        }


    }

    @Override
    public void onStart(int requestTag) {
        showLoading();
    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag){
            case TJRequestTag.TAG_GET_SCHOOL_STORE_LIST:

                List<Store> storeListResult = (List<Store>) response.getData();
                storeList.clear();
                storeList.addAll(storeListResult);

                SchoolStoreListAdapter schoolStoreListAdapter = new SchoolStoreListAdapter(this, storeList, R.layout.item_list_school_store);
                lv_storeList.setAdapter(schoolStoreListAdapter);

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
