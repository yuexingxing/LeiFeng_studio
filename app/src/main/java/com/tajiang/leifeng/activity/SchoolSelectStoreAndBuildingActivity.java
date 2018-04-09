package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.SchoolStoreAdapter;
import com.tajiang.leifeng.adapter.SchoolStoreBuildingAdapter;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Apartment;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.model.StoreDelivered;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.view.dialog.ProgressDialog;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admins on 2016/10/27.
 */
public class SchoolSelectStoreAndBuildingActivity extends BaseActivity implements
        SchoolStoreAdapter.OnItemStoreClickListener,
        SchoolStoreBuildingAdapter.OnItemApartmentClickListener,
        HttpResponseListener {

    private String ApartmentId;
    private StoreDelivered mStoreDelivered;
    private School school;

    private SchoolStoreAdapter schoolStoreAdapter;
    private SchoolStoreBuildingAdapter schoolApartmentAdapter;

    private RecyclerView rv_store;
    private RecyclerView rv_store_apartment;

//    private LoadingDialog loadingDialog;

    private ProgressDialog loadingDialog;

    @Override
    protected void initTopBar() {
        school = TJApp.getIns().getOldSchool() != null
                ? TJApp.getIns().getOldSchool()
                : TJApp.getIns().getSchool();
        if (!TextUtils.isEmpty(school.getSchoolName())) {
            setTitle(school.getSchoolName());
        } else {
            setTitle(school.getSchoolName());
        }
        enableNavRightText("切换学校");
    }

    /**
     * 点击右上角切换学校
     */
    @Override
    protected void onClickNavRight() {
        super.onClickNavRight();
        intent2Activity(SchoolSelectAreaActivity.class);
        SchoolSelectStoreAndBuildingActivity.this.finish();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_select_store_and_building);
    }

    @Override
    protected void initView() {
        rv_store = (RecyclerView) findViewById(R.id.rv_store);
        rv_store_apartment = (RecyclerView) findViewById(R.id.rv_store_apartment);
        loadingDialog = new ProgressDialog(this);
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        schoolStoreAdapter = new SchoolStoreAdapter(this, new ArrayList<StoreDelivered>());
        schoolApartmentAdapter = new SchoolStoreBuildingAdapter(this, new ArrayList<Apartment>());

        rv_store.setLayoutManager(new LinearLayoutManager(this));
        rv_store.setAdapter(schoolStoreAdapter);

        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(schoolApartmentAdapter);
        rv_store_apartment.setLayoutManager(new LinearLayoutManager(this));
        rv_store_apartment.addItemDecoration(headersDecor);
        rv_store_apartment.setAdapter(schoolApartmentAdapter);


    }

    @Override
    protected void initDates() {
        super.initDates();
        loadingDialog.show();
        //获取食堂
//        TJHttpUtils.getInstance(this).getSchoolStorList(school.getId());
        //获取食堂配送跟宿舍区和楼栋
        TJHttpUtil.getInstance(this).getStoreDistributionRange(school.getSchoolId());
    }

    @Override
    protected void initListener() {
        super.initListener();
        schoolStoreAdapter.setOnItemStoreClickListener(this);
        schoolApartmentAdapter.setOnItemApartmentClickListener(this);
    }

    /**
     * 刷新右侧食堂配送楼栋
     * @param selectedStore
     * @param selectedItemPosition
     */
    @Override
    public void OnItemStoreClick(StoreDelivered selectedStore, int selectedItemPosition) {
        schoolApartmentAdapter.updateStoreDeliveredApartment(selectedStore.getZonesId(), selectedStore.getZonesName(), selectedStore.getApartmentList());
        this.mStoreDelivered = selectedStore;
    }

    /**
     * 点击楼栋跳转到主页，更新档口
     * @param selectedApartment
     * @param selectedItemPosition
     */
    @Override
    public void OnItemApartmentClick(String name, Apartment selectedApartment, int selectedItemPosition) {
        //保存当前所选的学校
        if (TJApp.getIns().getOldSchool() != null) {
            TJApp.getIns().setSchool(TJApp.getIns().getOldSchool());
            TJApp.getIns().setOldSchool(null);
        }

        TJApp.getIns().setUserApartment(selectedApartment);
        TJApp.getIns().setStoreDelivered(this.mStoreDelivered);
        SharedPreferencesUtils.put(this, SharedPreferencesUtils.USER_APARTMENT_ID, selectedApartment.getApartmentId());
        SharedPreferencesUtils.put(this, SharedPreferencesUtils.USER_APARTMENT_NAME, name);
        intent2Activity(HomeActivity.class);
        this.finish();
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_GET_STORE_DELIVERED_APARTMENT:
                List<StoreDelivered> storeListResult = (List<StoreDelivered>) response.getData();
                this.mStoreDelivered = storeListResult.get(0);
                schoolStoreAdapter.updateAllDataSetChanged(storeListResult);
                schoolApartmentAdapter.updateStoreDeliveredApartment(mStoreDelivered.getZonesId(), mStoreDelivered.getZonesName(), storeListResult.get(0).getApartmentList());
                break;
            case TJRequestTag.TAG_GET_SCHOOL_STORE_LIST:
//                List<Store> storeListResult = (List<Store>) response.getData();
//                school.setStoreList(storeListResult);
//                if (storeListResult == null || storeListResult.size() == 0) {
//                    ToastUtils.showShort("食堂正在筹备开业中");
////                    intent2Activity(HomeActivity.class);
//                    SchoolSelectStoreAndBuildingActivity.this.finish();
//                } else {
//                    schoolStoreAdapter.updateAllDataSetChanged(storeListResult);
//                    // 学校：宿舍列表
//                    TJHttpUtils.getInstance(this).getApartmentBySchoolId(school.getId());
//                }
                break;
            case TJRequestTag.TAG_GET_SCHOOL_APARTMENT_LIST:
//                SchoolApartment schoolListResult = (SchoolApartment) response.getData();
//                for (ApartmentZone apartmentZone : schoolListResult.getZonesList()) {
//                    schoolApartmentAdapter.addAll2DataSet(apartmentZone.getApartmentList());
//                    schoolApartmentAdapter.putZone2MapSchoolZones(apartmentZone.getId(), apartmentZone.getName());
//                }
//                schoolApartmentAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if (TJApp.getIns().getOldSchool() != null) {
//            TJApp.getIns().setSchool(TJApp.getIns().getOldSchool());
//            TJApp.getIns().setOldSchool(null);
//        }
//    }
}
