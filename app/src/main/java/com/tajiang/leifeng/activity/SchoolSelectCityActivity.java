package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.SchoolHotCityGridAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.City;
import com.tajiang.leifeng.model.CityData;
import com.tajiang.leifeng.view.PullGridView;
import com.tajiang.leifeng.view.sortlistview.CharacterParser;
import com.tajiang.leifeng.view.sortlistview.PinyinComparator;
import com.tajiang.leifeng.view.sortlistview.SideBar;
import com.tajiang.leifeng.view.sortlistview.SortAdapter;
import com.tajiang.leifeng.view.sortlistview.SortModelCity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SchoolSelectCityActivity extends BaseActivity implements HttpResponseListener {

    private TextView dialog;

    private ListView sortListView;

    private SideBar sideBar;
    private SortAdapter adapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModelCity> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private List<City> mCityList = new ArrayList<>();

    private SchoolHotCityGridAdapter mSchoolHotCityGridAdapter;

    private PullGridView gvCityHotSchool;

    @Override
    protected void initTopBar() {
        setTitle("选择城市");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_school_select_city);
    }

    @Override
    protected void initView() {

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SortModelCity sortModelCity = (SortModelCity) parent.getItemAtPosition(position);
                intent2SelectSchoolActivity(sortModelCity.getCity().getAreaId(), sortModelCity.getCity().getAreaName());
            }
        });

        View hotCityView = LayoutInflater.from(this).inflate(R.layout.layout_school_city_hot, null);
        gvCityHotSchool = (PullGridView) hotCityView.findViewById(R.id.gvCityHotSchool);
        sortListView.addHeaderView(hotCityView);
        gvCityHotSchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) parent.getItemAtPosition(position);
                intent2SelectSchoolActivity(city.getAreaId(), city.getAreaName());
            }
        });

    }

    private void intent2SelectSchoolActivity(Integer areaId, String areaName) {
        Intent intent = new Intent(SchoolSelectCityActivity.this, SchoolSelectActivity.class);
        intent.putExtra("AreaId", areaId);
        intent.putExtra("AreaName", areaName);
        startActivity(intent);
    }

    @Override
    protected void initDates() {
        super.initDates();

        TJHttpUtil.getInstance(this).getCityList();
//        TJHttpUtil.getInstance(this).getCityHotList();

    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
    }

    /**
     * 为ListView填充数据
     */
    private List<SortModelCity> filledData(List<City> cityList) {

        List<SortModelCity> mSortList = new ArrayList<>();

        for (int i = 0; i < cityList.size(); i++) {

            City city = cityList.get(i);

            SortModelCity sortModel = new SortModelCity();
            sortModel.setCity(city);
            sortModel.setName(city.getAreaName());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(city.getAreaName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    @Override
    public void onStart(int requestTag) {
        showLoading();
    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_GET_SCHOOL_CITY_LIST:
                  Log.i("hexiuhui---", "re = " + response.getData());
                  CityData cityData = (CityData) response.getData();
                  List<City> cityList = cityData.getCityList();
//                List<CityData> cityList = (List<CityData>) response.getData();

                if (cityList != null && cityList.size() > 0) {
                    mCityList.addAll(cityList);
                    SourceDateList = filledData(mCityList);

                    // 根据a-z进行排序源数据
                    Collections.sort(SourceDateList, pinyinComparator);
                    adapter = new SortAdapter(this, SourceDateList);
                    sortListView.setAdapter(adapter);


                    ArrayList<String> typeArr = new ArrayList<>();
                    String tag = SourceDateList.get(0).getSortLetters();
                    typeArr.add(tag);
                    for (int i = 1; i < SourceDateList.size(); i++) {
                        if (!tag.equals(SourceDateList.get(i).getSortLetters())) {
                            tag = SourceDateList.get(i).getSortLetters();
                            typeArr.add(tag);
                        }
                    }

                    sideBar.setTypeArr(typeArr.toArray(new String[0]));

                    List<City> cityList1 = cityData.getHotCityList();

                    if (cityList1 != null && cityList1.size() > 0) {
                        mSchoolHotCityGridAdapter = new SchoolHotCityGridAdapter(this, cityList1, R.layout.item_grid_school_city_hot);
                        gvCityHotSchool.setAdapter(mSchoolHotCityGridAdapter);
                    }
                }

                break;
            case TJRequestTag.TAG_GET_SCHOOL_CITY_HOT:

                List<City> cityList1 = (List<City>) response.getData();

                if (cityList1 != null && cityList1.size() > 0) {
                    mSchoolHotCityGridAdapter = new SchoolHotCityGridAdapter(this, cityList1, R.layout.item_grid_school_city_hot);
                    gvCityHotSchool.setAdapter(mSchoolHotCityGridAdapter);
                }

                break;
        }

    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        Log.i("hexiuhui----", "55555555555");
    }

    @Override
    public void onFinish(int requestTag) {
        Log.i("hexiuhui----", "666666666666");
        dismissLoading();
    }
}
