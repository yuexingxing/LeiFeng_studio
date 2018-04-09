package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.SchoolsSelectListAdapter;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.AreaCity;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.service.LocationService;
import com.tajiang.leifeng.view.sortlistview.SortModelSchool;
import com.tajiang.leifeng.view.sortlistview.SortSchoolAdapter;

import java.util.ArrayList;
import java.util.List;

public class SchoolSelectAreaActivity extends BaseActivity implements BDLocationListener, HttpResponseListener {

    private EditText actv_schoolSearch;

    private LocationService locationService;

    private View viewWaitLocal;
    private TextView tvOneSchool;
    private TextView tvTwoSchool;
    private List<School> schoolListResult = new ArrayList<>();
    private List<School> schoolListSearch = new ArrayList<>();

    private SchoolsSelectListAdapter schoolsSelectListAdapter;

    private ListView lvSchoolSearchList;

    private List<AreaCity> mAreaCityList = new ArrayList<>();

    private ListView lvSchoolListLocal;

    private SortSchoolAdapter mSortSchoolAdapter;

    private List<SortModelSchool> mModelSchoolList = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        locationService = ((TJApp) getApplication()).locationService;

        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(this);

        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }

        // 定位SDK
        locationService.start();
    }


    @Override
    protected void initTopBar() {
        setTitle("选择学校");
        enableNavLeftImage();
        enableNavRightText("定位中");
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_school_area_select);
    }

    @Override
    protected void initView() {
        actv_schoolSearch = (EditText) findViewById(R.id.actv_schoolSearch);
        viewWaitLocal = findViewById(R.id.viewWaitLocal);

        tvOneSchool = (TextView) findViewById(R.id.tvOneSchool);
        tvTwoSchool = (TextView) findViewById(R.id.tvTwoSchool);

        lvSchoolSearchList = (ListView) findViewById(R.id.lvSchoolSearchList);
        lvSchoolListLocal = (ListView) findViewById(R.id.lvSchoolListLocal);

        actv_schoolSearch.addTextChangedListener(textWatcher);

    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        schoolsSelectListAdapter = new SchoolsSelectListAdapter(this, schoolListSearch, R.layout.item_list_school_select);
        lvSchoolSearchList.setAdapter(schoolsSelectListAdapter);
    }

    @Override
    protected void initDates() {
        super.initDates();

        lvSchoolSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                School school = (School) parent.getAdapter().getItem(position);
                intent2HomeActivity(school);

            }
        });

        lvSchoolListLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SortModelSchool sortModelSchool = (SortModelSchool) parent.getItemAtPosition(position);

                School school = sortModelSchool.getSchool();
                intent2HomeActivity(school);

            }
        });
    }

    private void intent2HomeActivity(School school) {
        //跳转到选择食堂和配送楼栋页面
//        if (SharedPreferencesUtils.contains(getContext(),"SCHOOL") && TJApp.getIns().getOldSchool() != null) {
//            TJApp.getIns().setOldSchool(TJApp.getIns().getSchool());
//        }
        TJApp.getIns().setOldSchool(school);
        Intent intent1 = new Intent(SchoolSelectAreaActivity.this, SchoolSelectStoreAndBuildingActivity.class);
        startActivity(intent1);
        finish();

        //跳转到HomeActivity
//        TJApp.getIns().setSchool(school);
//        Intent intent1 = new Intent(SchoolSelectAreaActivity.this, HomeActivity.class);
//        startActivity(intent1);
//        finish();
    }

    @Override
    protected void initListener() {
        super.initListener();
        tvOneSchool.setOnClickListener(this);
        tvTwoSchool.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.tvOneSchool:

                School school = schoolListResult.get(0);
                school.setSchoolId(school.getSchoolId());
                school.setSchoolName(school.getSchoolName());
                intent2HomeActivity(school);
                break;
            case R.id.tvTwoSchool:

                School school1 = schoolListResult.get(1);
                school1.setSchoolId(school1.getSchoolId());
                school1.setSchoolName(school1.getSchoolName());
                intent2HomeActivity(school1);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        locationService.unregisterListener(this); //注销掉监听
        locationService.stop(); //停止定位服务

    }


    // 定位成功回调
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {

            enableNavRightText(bdLocation.getCity());
            TJHttpUtil.getInstance(this).getSchoolOfLocalCity(bdLocation.getCity());

            TJHttpUtil.getInstance(this).getNearSchools(bdLocation.getLatitude(), bdLocation.getLongitude(), 50000);

            locationService.unregisterListener(this); //注销掉监听
            locationService.stop(); //停止定位服务
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s.toString())) {
                TJHttpUtil.getInstance(SchoolSelectAreaActivity.this).getSchoolSearchList(s.toString());
            } else {
                lvSchoolSearchList.setVisibility(View.GONE);
            }

        }
    };

    @Override
    protected void onClickNavRight() {
        intent2Activity(SchoolSelectCityActivity.class);
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_GET_SCHOOL_NEARS:
                viewWaitLocal.setVisibility(View.GONE);

                schoolListResult = (List<School>) response.getData();

                for (int i = 0; i < schoolListResult.size(); i++) {
                    if (i == 0) {
                        tvOneSchool.setVisibility(View.VISIBLE);
                        tvOneSchool.setText(schoolListResult.get(i).getSchoolName());
                    }

                    if (i == 1) {
                        tvTwoSchool.setVisibility(View.VISIBLE);
                        tvTwoSchool.setText(schoolListResult.get(i).getSchoolName());
                    }
                }

                break;

            case TJRequestTag.TAG_GET_SCHOOL_SEARCH:
                List<School> schoolList = (List<School>) response.getData();

                if (schoolList != null && schoolList.size() > 0) {
                    schoolListSearch.clear();
                    schoolListSearch.addAll(schoolList);
                    schoolsSelectListAdapter.notifyDataSetChanged();
                    lvSchoolSearchList.setVisibility(View.VISIBLE);
                } else {
                    lvSchoolSearchList.setVisibility(View.GONE);
                }
                break;

            case TJRequestTag.TAG_GET_SCHOOL_OF_LOCAL_CITY:
                List<AreaCity> areaCityList = (List<AreaCity>) response.getData();

                if (areaCityList != null && areaCityList.size() > 0) {
//                    mAreaCityList.clear();
//                    mAreaCityList.addAll(areaCityList);

                    for (int i = 0; i < areaCityList.size(); i++) {

                        List<School> schools = areaCityList.get(i).getListSchool();

                        for (School school : schools) {
                            SortModelSchool sortModelSchool = new SortModelSchool();
                            sortModelSchool.setName(school.getSchoolName());
                            sortModelSchool.setSortLetters(areaCityList.get(i).getAreaName());
                            sortModelSchool.setSchool(school);
                            mModelSchoolList.add(sortModelSchool);
                        }

                    }

                    mSortSchoolAdapter = new SortSchoolAdapter(this, mModelSchoolList);
                    lvSchoolListLocal.setAdapter(mSortSchoolAdapter);
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
