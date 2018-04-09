package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.SchoolsSelectListAdapter;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.AreaCity;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.view.sortlistview.SortModelSchool;
import com.tajiang.leifeng.view.sortlistview.SortSchoolAdapter;

import java.util.ArrayList;
import java.util.List;

public class SchoolSelectActivity extends BaseActivity implements HttpResponseListener {

    private EditText actv_schoolSearch;

    private ListView lv_schoolList;

    private SchoolsSelectListAdapter schoolsSelectListAdapter;

    private List<School> schoolList = new ArrayList<>();

    private int areaId;
    private String areaName;

    private SortSchoolAdapter mSortSchoolAdapter;
    private List<SortModelSchool> mModelSchoolList = new ArrayList<>();

    @Override
    protected void initTopBar() {
        setTitle("选择学校");
        enableNavLeftImage();

        areaId = getIntent().getIntExtra("AreaId", 0);
        areaName = getIntent().getStringExtra("AreaName");
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_school_select);
    }

    @Override
    protected void initView() {

        actv_schoolSearch = (EditText) findViewById(R.id.actv_schoolSearch);
        lv_schoolList = (ListView) findViewById(R.id.lv_schoolList);

        actv_schoolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(s.toString());
            }
        });
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();

        schoolsSelectListAdapter = new SchoolsSelectListAdapter(this, schoolList, R.layout.item_list_school_select);
        lv_schoolList.setAdapter(schoolsSelectListAdapter);


        lv_schoolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SortModelSchool sortModelSchool = (SortModelSchool) parent.getItemAtPosition(position);
                School school = sortModelSchool.getSchool();
//                if (SharedPreferencesUtils.contains(getContext(),"SCHOOL") && TJApp.getIns().getOldSchool() != null) {
//                    TJApp.getIns().setOldSchool(TJApp.getIns().getSchool());
//                }
                TJApp.getIns().setOldSchool(school);
//                Intent intent = new Intent(SchoolSelectActivity.this, HomeActivity.class);
                Intent intent = new Intent(SchoolSelectActivity.this, SchoolSelectStoreAndBuildingActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void initDates() {
        super.initDates();
        TJHttpUtil.getInstance(this).getSchoolByAreaId(areaId, areaName);

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModelSchool> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mModelSchoolList;
        } else {
            filterDateList.clear();
            for (SortModelSchool sortModel : mModelSchoolList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1) {
                    filterDateList.add(sortModel);
                }
            }
        }

        mSortSchoolAdapter.updateListView(filterDateList);
    }

    @Override
    public void onStart(int requestTag) {
        showLoading();
    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_GET_SCHOOL_BY_AREA_ID:

                List<AreaCity> areaCityList = (List<AreaCity>) response.getData();

                for (int i = 0; i < areaCityList.size(); i++) {

                    AreaCity areaCity = areaCityList.get(i);

                    for (School school : areaCity.getListSchool()) {

                        SortModelSchool sortModelSchool = new SortModelSchool();
                        sortModelSchool.setName(school.getSchoolName());
                        sortModelSchool.setSortLetters(areaCity.getAreaName());
                        sortModelSchool.setSchool(school);
                        mModelSchoolList.add(sortModelSchool);
                    }

                    mSortSchoolAdapter = new SortSchoolAdapter(this, mModelSchoolList);
                    lv_schoolList.setAdapter(mSortSchoolAdapter);

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
