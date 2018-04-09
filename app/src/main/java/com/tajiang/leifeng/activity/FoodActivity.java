package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.FoodScreenSelectListAdapter;
import com.tajiang.leifeng.adapter.FoodTypeSelectListAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.base.FragmentController;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.fragment.FoodSelectFragment;
import com.tajiang.leifeng.model.AppInfo;
import com.tajiang.leifeng.model.AppInfoResult;
import com.tajiang.leifeng.model.FoodClass;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.Store;
import com.tajiang.leifeng.model.StoreStalls;
import com.tajiang.leifeng.utils.UpdateManagerUtil;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.BottomBarView;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        BottomBarView.OnBottomBarClickListener , HttpResponseListener{

    private final int SCREEN_CLASS = 1;
    private final int SCREEN_FOOD = 2;

    // 配置底部菜单栏内容
    private String[] labelArr = {"点饭"};

    private int[] imgNormalResIdArr = {
            R.drawable.com_btm_icon_select_food_normal,
    };

    private int[] imgPressedResIdArr = {
            R.drawable.com_btm_icon_select_food_selected,
    };

    // 选餐界面的筛选功能
    private View rectScreenFood;
    private View rectBgTypeFoodSelect;

    private ListView lvTypeFoodSelect;

    private BottomBarView bbvFood;

    private FoodSelectFragment foodSelectFragment;

    private FragmentController fragmentController;

    private List<Fragment> fragmentList = new ArrayList<>();

    private int current = 0;

    private Store store;

    private List<Order> orderList = new ArrayList<>();
    private List<FoodClass> foodClassList = new ArrayList<>();

    private FoodTypeSelectListAdapter foodTypeSelectListAdapter;   //菜品的类型列表
    private FoodScreenSelectListAdapter foodScreenSelectListAdapter;  //筛选下拉列表


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        store = (Store) getIntent().getSerializableExtra("Store");

        super.onCreate(savedInstanceState);
        initFragment();
        if (getApp().getTypePush() != null) {
            switch (getApp().getTypePush()) {
                case 1:
                    Intent intent = new Intent(this, UserCouponActivity.class);
                    startActivity(intent);
                    getApp().setTypePush(null);
                    break;
                case 2:
                    selectFragment(1);
                    break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getApp().getTypePush() != null) {
            switch (getApp().getTypePush()) {
                case 1: // 优惠券推送
                    Intent intent1 = new Intent(this, UserCouponActivity.class);
                    startActivity(intent1);
                    getApp().setTypePush(null);
                    break;
                case 2:
                    selectFragment(1);
                    break;
            }
        }

    }

    @Override
    protected void initTopBar() {
        unableNav();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_food);
    }

    @Override
    protected void initView() {

        bbvFood = (BottomBarView) findViewById(R.id.bbvFood);

        rectScreenFood = findViewById(R.id.rectScreenFood);
        rectBgTypeFoodSelect = findViewById(R.id.rectBgTypeFoodSelect);
    }

    @Override
    protected void initListener() {
        bbvFood.setOnBottomBarClickListener(this);
        rectBgTypeFoodSelect.setOnClickListener(this);
        lvTypeFoodSelect.setOnItemClickListener(this);
    }

    @Override
    protected void initDates() {

        bbvFood.initData(labelArr , imgNormalResIdArr, imgPressedResIdArr);
//
//        TJHttpUtil.getInstance(this).getAppInfo();
//        TJHttpUtils.getInstance(this).userPDActivity();

        lvTypeFoodSelect = (ListView) findViewById(R.id.lvTypeFoodSelect);

        foodTypeSelectListAdapter = new FoodTypeSelectListAdapter(this, foodClassList, R.layout.item_list_food_select_type);
        lvTypeFoodSelect.setAdapter(foodTypeSelectListAdapter);


        orderList.add(new Order());
        orderList.add(new Order());
        foodScreenSelectListAdapter = new FoodScreenSelectListAdapter(this, orderList, R.layout.item_list_food_select_screen);

        /**
         * 获取菜品类型列表
         */
//        TJHttpUtils.getInstance(this).getFoodClass(store.getId());

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rectBgTypeFoodSelect:
                rectScreenFood.setVisibility(View.GONE);
                foodSelectFragment.resetDownMore();
                break;
        }
    }

    private void initFragment() {

        foodSelectFragment = new FoodSelectFragment();

        StoreStalls storeStalls = getIntent().getParcelableExtra("store_stalls");
        foodSelectFragment.setStoreStalls(storeStalls);
        foodSelectFragment.setmStallsName(storeStalls.getShopName());
        foodSelectFragment.setmDeliverAmount(storeStalls.getMinMoney());

        Log.i("hexiuhui---", storeStalls.getDelyType() + "---");

//        foodSelectFragment.setmStallsId(getIntent().getStringExtra("stalls_id"));
//        foodSelectFragment.setmStallsName(getIntent().getStringExtra("stalls_name"));
//        foodSelectFragment.setmDeliverAmount(getIntent().getStringExtra("stalls_deliver_amount"));

        fragmentList.add(foodSelectFragment);
        fragmentController = new FragmentController(this, R.id.fl_content, fragmentList);
        // 默认显示
        selectFragment(0);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 1 && arg1 == 1) {
            selectFragment(1);
        }
    }

    private void selectFragment(int position) {
        fragmentController.showFragment(position);
    }

    public void showTypeScreenFood() {

        if (current == SCREEN_CLASS && rectScreenFood.getVisibility() == View.VISIBLE) {
            rectScreenFood.setVisibility(View.INVISIBLE);
        } else {
            rectScreenFood.setVisibility(View.VISIBLE);
        }

        lvTypeFoodSelect.setAdapter(foodTypeSelectListAdapter);
        current = SCREEN_CLASS;

    }

    public void showScreenFood() {

        if (current == SCREEN_FOOD && rectScreenFood.getVisibility() == View.VISIBLE) {
            rectScreenFood.setVisibility(View.INVISIBLE);
        } else {
            rectScreenFood.setVisibility(View.VISIBLE);
        }

        lvTypeFoodSelect.setAdapter(foodScreenSelectListAdapter);
        current = SCREEN_FOOD;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (current == SCREEN_CLASS) {
            FoodClass foodClass = (FoodClass) parent.getAdapter().getItem(position);

            foodSelectFragment.doReqTypeResult(foodClass);
        } else if (current == SCREEN_FOOD) {
            foodSelectFragment.doReqScreenResult(position);
        }

        rectScreenFood.setVisibility(View.INVISIBLE);
        foodSelectFragment.resetDownMore();

    }

    @Override
    public void onBottomBarClick(int position) {
        // 订单页面需要验证是否登录
        if (position == 1) {
            if (UserUtils.getInstance().isLoginWithLogin(this)) {
                selectFragment(1);
            } else {
                bbvFood.doPressedItem(0);
            }
            return;
        }
        fragmentController.showFragment(position);
    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_APP_INFO:
                // 请求版本信息成功
                AppInfoResult appInfoJson = (AppInfoResult) response.getData();
                AppInfo appInfo = new Gson().fromJson(appInfoJson.getResult(), AppInfo.class);
                // 检查更新
                UpdateManagerUtil manager = new UpdateManagerUtil(this, appInfo);
                manager.checkUpdate();
                break;
            case TJRequestTag.TAG_FOOD_SELECT_FOOF_CLASS:
                List<FoodClass> foodClassListResult = (List<FoodClass>) response.getData();
                foodClassList.addAll(foodClassListResult);
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
