package com.tajiang.leifeng.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.FoodPayActivity;
import com.tajiang.leifeng.activity.FoodActivity;
import com.tajiang.leifeng.adapter.BuyCargoodsAdapter;
import com.tajiang.leifeng.adapter.FoodSelectListAdapter;
import com.tajiang.leifeng.adapter.FoodTypeSelectListAdapter;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseFragment;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.Adv;
import com.tajiang.leifeng.model.Apartment;
import com.tajiang.leifeng.model.FoodClass;
import com.tajiang.leifeng.model.FoodClassList;
import com.tajiang.leifeng.model.GoodSet;
import com.tajiang.leifeng.model.Goods;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.model.StallsFullcut;
import com.tajiang.leifeng.model.Store;
import com.tajiang.leifeng.model.StoreStalls;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.GlideRoundTransform;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.NumberUtils;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.TJWindowManager;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.BadgeView;
import com.tajiang.leifeng.view.SmoothScrollLayout;
import com.tajiang.leifeng.view.dialog.AdvDialog;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;
import com.tajiang.leifeng.view.dialog.CDialog;
import com.tajiang.leifeng.view.dialog.HintDialog;
import com.tajiang.leifeng.view.dialog.NotifyDialog;
import com.tajiang.leifeng.view.expandablelist.ActionSlideExpandableListView;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshBase;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.tajiang.leifeng.view.pullrefresh.PullToRefreshListView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FoodSelectFragment extends BaseFragment implements OnClickListener, HttpResponseListener,
        OnRefreshListener<ListView>, OnItemClickListener,
         PullToRefreshListView.OnScrollNoteListener,
        BadgeView.OnNumChangeListener, View.OnTouchListener{

    private final int pageSize = 10;

    private SmoothScrollLayout layout_food_select_root;
    private View header_banner_root;
    private View ll_foodEmpty;
    private View bottomSheet;
    private View bottomSheetRootView;  //底部弹框购物车布局
    private View ll_price_lunch_fee;   //底部弹框购物车底部餐盒费布局

    private View rect_noteMsg;
    private View rect_showAD;
    private View rect_noteMsgHome;

    private View fl_imgNavLeft;
    private View ivSelectStoreFoodSelect;

    private View rectTypefoodSelect; // 食物分类
    private View rectScreenfoodSelect; // 食物筛选

    private View ivMoreClassFoodSelect;
    private View ivMoreScreenFoodSelect;

    private View ll_goods_price_to_pay; //底部显示的价格

    private RelativeLayout rl_food_select;
    private RelativeLayout rl_food_type;
    //底部数据
    private BottomSheetLayout bottomSheetLayout;

    private TextView tv_price_box_fee;  //餐盒费
    private TextView tv_noteMsg;
    private TextView tv_noteMsgMsgHome;
    private TextView tvIconClassFoodSelect;
    private TextView tvIconScreenFoodSelect;
    private TextView tv_goods_to_pay;  //底部去结算按钮
    private TextView tv_goods_pay_price;
    private TextView tv_clear_buy_car;  //清空底部弹出的购物车
    private TextView tv_deliver_amount; //顶部满起送
    private TextView tv_deliver_store_name; //食堂名称
    private ImageView iv_dely_type;   //配送类型

    private LinearLayout ll_action_1;
    private ImageView iv_action_1;
    private TextView tv_action_1;

    private LinearLayout ll_action_2;
    private ImageView iv_action_2;
    private TextView tv_action_2;

    private ImageView iv_pic_store_banner;
    private ImageView iv_show_food_type;
    private ImageView iv_foodSelect;

    private ListView lv_selectFood;   //菜单列表
    private ListView lv_type_food_select;  //菜单分类列表
    private RecyclerView lv_buy_var_goods;

    private PullToRefreshListView lv_selectFoodPull;

    private BadgeView tv_numFood;

    private NotifyDialog notifyDialog;
    private CDialog selectedStoreDialog;
    private AnimLoadDialog loadDialog;
    private HintDialog alertDialog;

    private BuyCargoodsAdapter buyCargoodsAdapter;
    private FoodSelectListAdapter foodSelectListAdapter;
    private SchoolAdapter schoolAdapter;
    private List<Adv> advListResult;

    private FoodActivity homeActivity;

    private int pager = 1;
    private int header_banner_root_height = 0;

    private boolean isShowFoodType = true;  //默认是否展示菜单分类列表

    private String goodClassId = null;
    private String goodScreen = null;
    private String mStallsId = null;  //档口ID
    private String mStallsName = null;//档口名字

    public void setStoreStalls(StoreStalls storeStalls) {
        this.storeStalls = storeStalls;
        this.mStallsId = storeStalls.getShopId();
    }

    private StoreStalls storeStalls;
    private BigDecimal mDeliverAmount = new BigDecimal(BigInteger.ZERO);   //起送价

    private Store store;
    private Apartment userApartment;
//    private School school;

    private List<Goods> goodList = new ArrayList<>();
    private List<School> schoolList = new ArrayList<>();
    private List<FoodClass> foodClassList = new ArrayList<>();   //菜品的类型列表数据

    private List<String> labelList = new ArrayList<>();   //备注标签

    private FoodTypeSelectListAdapter foodTypeSelectListAdapter;   //菜品的类型列表

    public void setmStallsName(String mStallsName) {
        this.mStallsName = mStallsName;
    }
    public void setmDeliverAmount(BigDecimal mDeliverAmount) {
        if (mDeliverAmount != null) {
            this.mDeliverAmount = mDeliverAmount;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        homeActivity = (FoodActivity) getActivity();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_food_select);
        //购物车列表框
        bottomSheetRootView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_buttom_sheet, null);

        store = (Store) getActivity().getIntent().getSerializableExtra("Store");
//        school = (School) getActivity().getIntent().getSerializableExtra("school");

    }

    @Override
    protected void initView() {
        lv_buy_var_goods = (RecyclerView) bottomSheetRootView.findViewById(R.id.rv_goods_selected);
        tv_clear_buy_car = (TextView) bottomSheetRootView.findViewById(R.id.tv_clear_buy_car);
        //餐盒费区
        ll_price_lunch_fee = bottomSheetRootView.findViewById(R.id.ll_price_lunch_fee);
        tv_price_box_fee = (TextView) ll_price_lunch_fee.findViewById(R.id.tv_price_box_fee);
        //餐盒费区
        header_banner_root = findViewById(R.id.header_banner_root);

        iv_dely_type = findViewById(R.id.iv_dely_type);
        tv_deliver_store_name = findViewById(R.id.tv_deliver_store_name);

        layout_food_select_root = findViewById(R.id.layout_food_select_root);
        ll_foodEmpty = findViewById(R.id.ll_FoodEmpty);
        rect_noteMsgHome = findViewById(R.id.rect_noteMsgHome);
        rect_showAD = findViewById(R.id.rect_showAD);
        rectTypefoodSelect = findViewById(R.id.rectTypefoodSelect);
        rectTypefoodSelect = findViewById(R.id.rectTypefoodSelect);
        rectScreenfoodSelect = findViewById(R.id.rectScreenfoodSelect);

        tv_deliver_amount = findViewById(R.id.tv_deliver_amount);
        tvIconScreenFoodSelect = findViewById(R.id.tvIconScreenFoodSelect);
        tv_noteMsgMsgHome = findViewById(R.id.tv_noteMsgMsgHome);
        tv_numFood = findViewById(R.id.tv_numFood);
        tvIconClassFoodSelect = findViewById(R.id.tvIconClassFoodSelect);

        rl_food_select = findViewById(R.id.rl_food_select);
        rl_food_type = findViewById(R.id.rl_food_type);

        iv_pic_store_banner = findViewById(R.id.iv_pic_store_banner);
        iv_show_food_type = findViewById(R.id.iv_show_food_type);
        iv_foodSelect = findViewById(R.id.iv_foodSelect);

        ll_action_1 = findViewById(R.id.ll_action_1);
        ll_action_2 = findViewById(R.id.ll_action_2);
        iv_action_1 = findViewById(R.id.iv_action_1);
        iv_action_2 = findViewById(R.id.iv_action_2);
        tv_action_1 = findViewById(R.id.tv_action_1);
        tv_action_2 = findViewById(R.id.tv_action_2);

//        ivSelectStoreFoodSelect = findViewById(R.id.ivSelectStoreFoodSelect);

        ivMoreClassFoodSelect = findViewById(R.id.ivMoreClassFoodSelect);
        ivMoreScreenFoodSelect = findViewById(R.id.ivMoreScreenFoodSelect);

        lv_type_food_select = findViewById(R.id.lv_type_food_select);   //选择菜品列表

        foodTypeSelectListAdapter = new FoodTypeSelectListAdapter(getActivity(), foodClassList, R.layout.item_list_food_select_type);
        foodTypeSelectListAdapter.setShowFoodTypeCount(false);  //是否显示每个菜品分类的数量
        foodTypeSelectListAdapter.setShowFoodTypeImg(false);  //是否显示每个菜品分类的缩略图
        lv_type_food_select.setAdapter(foodTypeSelectListAdapter);
        lv_type_food_select.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodTypeSelectListAdapter adapter = (FoodTypeSelectListAdapter) parent.getAdapter();
                doReqTypeResult(adapter.getItem(position));
                resetDownMore();
                /**
                 * 重置被点击的位置position,并更新UI
                 */
                adapter.setClickedPosition(position);
                adapter.updateItemUI(view);
            }
        });

        lv_selectFoodPull = findViewById(R.id.lv_selectFood);
        lv_selectFoodPull.setScrollLoadEnabled(true);
        lv_selectFood = lv_selectFoodPull.getRefreshableView();
        lv_selectFood.setEmptyView(ll_foodEmpty);

        fl_imgNavLeft = findViewById(R.id.fl_imgNavLeft);
        ll_goods_price_to_pay = findViewById(R.id.ll_goods_price_to_pay);
        tv_goods_to_pay = findViewById(R.id.tv_goods_to_pay);
        tv_goods_pay_price = findViewById(R.id.tv_goods_pay_price);

        bottomSheetLayout = findViewById(R.id.bottomSheetLayout);

        initBannerPager();
    }

    @Override
    protected void initListener() {
        header_banner_root.setOnClickListener(this);
        rect_showAD.setOnClickListener(this);
        rectTypefoodSelect.setOnClickListener(this);
        rectScreenfoodSelect.setOnClickListener(this);
        ll_goods_price_to_pay.setOnClickListener(this);
//        iv_show_food_type.setOnClickListener(this);//点击展开（收起）左侧菜品分类列表
        iv_foodSelect.setOnClickListener(this);
        lv_selectFoodPull.setOnRefreshListener(this);
        lv_selectFoodPull.setOnScrollNoteListener(this);

        fl_imgNavLeft.setOnClickListener(this);
        tv_numFood.setOnNumChangeListener(this);
        tv_goods_to_pay.setOnClickListener(this);
        tv_clear_buy_car.setOnClickListener(this);

    }

    @Override
    protected void initAdapter() {
        userApartment = TJApp.getIns().getUserApartment();
        buyCargoodsAdapter = new BuyCargoodsAdapter(getActivity(), tv_numFood, bottomSheetLayout);
        buyCargoodsAdapter.setOnCutAndAddButtonClick(new BuyCargoodsAdapter.OnCutAndAddButtonClick() {
            @Override
            public void OnCutAndAddButtonClick() {
                foodSelectListAdapter.notifyDataSetChanged();
            }
        });
        foodSelectListAdapter = new FoodSelectListAdapter(getActivity(), goodList, R.layout.item_list_food_select, getActivity(), iv_foodSelect, tv_numFood, 1, storeStalls.getPackFee());
        lv_selectFood.setAdapter(foodSelectListAdapter);

        lv_buy_var_goods.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv_buy_var_goods.setAdapter(buyCargoodsAdapter);
    }

    @Override
    protected void initData() {
        //将header_banner_root 传入进以待获取其高度进行重置layout_food_select_root高度
        layout_food_select_root.setmHeaderRootView(header_banner_root);
        List<StallsFullcut> list =  storeStalls.getActivityList();
        setNoteMsg();
        addNewActionTextView(list);
//        switch (list.size()) {
//            case 0:
//                tv_head_action_1.setCompoundDrawables(null, null, null, null);
//                tv_head_action_1.setText("暂无商家活动。");
//                tv_head_action_1.setVisibility(View.VISIBLE);
//                tv_head_action_2.setVisibility(View.GONE);
//                break;
//            case 1:
//                tv_head_action_1.setVisibility(View.VISIBLE);
//                tv_head_action_2.setVisibility(View.GONE);
//                tv_head_action_1.setText("满"
//                        + NumberUtils.clearTailZero(list.get(0).getPrice()) + "减"
//                        + NumberUtils.clearTailZero(list.get(0).getLimitPrice()) + "  ");
//                break;
//            case 2:
//                tv_head_action_1.setVisibility(View.VISIBLE);
//                tv_head_action_2.setVisibility(View.VISIBLE);
//                tv_head_action_1.setText("满"
//                        + NumberUtils.clearTailZero(list.get(0).getPrice()) + "减"
//                        + NumberUtils.clearTailZero(list.get(0).getLimitPrice()) + "  ");
//                tv_head_action_2.setText("满"
//                        + NumberUtils.clearTailZero(list.get(1).getPrice()) + "减"
//                        + NumberUtils.clearTailZero(list.get(1).getLimitPrice()) + "  ");
//                break;
//            default:
//                tv_head_action_1.setVisibility(View.VISIBLE);
//                tv_head_action_2.setVisibility(View.VISIBLE);
//                tv_head_action_1.setText("满"
//                        + NumberUtils.clearTailZero(list.get(0).getPrice()) + "减"
//                        + NumberUtils.clearTailZero(list.get(0).getLimitPrice()) + "  ");
//                tv_head_action_2.setText("满"
//                        + NumberUtils.clearTailZero(list.get(1).getPrice()) + "减"
//                        + NumberUtils.clearTailZero(list.get(1).getLimitPrice()) + "  ");
//                break;
//        }

        tv_deliver_amount.setText("满" + NumberUtils.clearTailZero((mDeliverAmount)) + "元起送 | 配送费￥" + storeStalls.getDelyFee());
        tv_deliver_store_name.setText(mStallsName);
        if (storeStalls.getDelyType().equals(StoreStalls.STORE_DELY_TYPE_PT)) {
            iv_dely_type.setVisibility(View.VISIBLE);
        } else {
            iv_dely_type.setVisibility(View.GONE);
        }
//        // 获取Banner
//        TJHttpUtils.getInstance(new HttpResponseListener() {
//            @Override
//            public void onRequestStart(int requestTag) {
//            }
//
//            @Override
//            public void onResponseFail(BaseResponse<?> response, int requestTag) {
//            }
//
//            @Override
//            public void onResponseSuccess(BaseResponse<?> response, int requestTag) {
//                /*判断首页广告是否展示*/
//                doCheckAdShowOrNot(response);
//            }
//
//            @Override
//            public void onResponseFinish(int requestTag) {
//            }
//        }).getAdvList(8);
        //获取菜单分
        TJHttpUtil.getInstance(this).getStallsgoodsClass(mStallsId);
        //初始化头部档口展示图片
        Glide.with(getActivity())
                .load(storeStalls.getShopImage())
                .transform(new CenterCrop(getActivity())   //此方式解决CenterCrop与Transformer的共存问题
                        ,new GlideRoundTransform(getActivity(), 4))
                .placeholder(R.drawable.icon_empty_good)
                .error(R.drawable.icon_empty_good)
                .crossFade()
                .into(iv_pic_store_banner);
    }

    /**
     * 新增优惠活动 View (重用)
     * @param list
     */
    public void addNewActionTextView(List<StallsFullcut> list) {
        ll_action_1.setVisibility(View.VISIBLE);
        ll_action_2.setVisibility(View.GONE);
        if (list == null || list.size() == 0) {
            iv_action_1.setVisibility(View.GONE);
            tv_action_1.setText("暂无商家活动");
        } else if (list.size() == 1) {
            iv_action_1.setVisibility(View.VISIBLE);
            //初始化档口展示图片
            Glide.with(getContext())
                    .load(list.get(0).getUrl())
                    .transform(new CenterCrop(getContext())   //此方式解决CenterCrop与Transformer的共存问题
                            ,new GlideRoundTransform(getContext(), 2))
                    .placeholder(R.drawable.icon_order_action_empty)
                    .error(R.drawable.icon_order_action_empty)
                    .crossFade()
                    .into(iv_action_1);
            tv_action_1.setText(list.get(0).getActivityName());
        } else {
            ll_action_1.setVisibility(View.VISIBLE);
            ll_action_2.setVisibility(View.VISIBLE);
            iv_action_1.setVisibility(View.VISIBLE);

            //初始化档口展示图片
            Glide.with(getContext())
                    .load(list.get(0).getUrl())
                    .transform(new CenterCrop(getContext())   //此方式解决CenterCrop与Transformer的共存问题
                            ,new GlideRoundTransform(getContext(), 2))
                    .placeholder(R.drawable.icon_order_action_empty)
                    .error(R.drawable.icon_order_action_empty)
                    .crossFade()
                    .into(iv_action_1);
            //初始化档口展示图片
            Glide.with(getContext())
                    .load(list.get(1).getUrl())
                    .transform(new CenterCrop(getContext())   //此方式解决CenterCrop与Transformer的共存问题
                            ,new GlideRoundTransform(getContext(), 2))
                    .placeholder(R.drawable.icon_order_action_empty)
                    .error(R.drawable.icon_order_action_empty)
                    .crossFade()
                    .into(iv_action_2);
            tv_action_1.setText(list.get(0).getActivityName());
            tv_action_2.setText(list.get(1).getActivityName());
        }
    }

    private void doCheckAdShowOrNot(BaseResponse response) {

        advListResult = (List<Adv>) response.getData();

        if (advListResult != null) {
            if (advListResult.size() > 0) {
                rect_showAD.setVisibility(View.VISIBLE);

                Long updatedAt = (Long) SharedPreferencesUtils.get(getContext(), "AdvShow", 0L); // 本地缓存的时间
                Long updateAtNet = advListResult.get(0).getUpdatedAt();

                if (updatedAt == 0) {
                    showADDialog(advListResult);
                    SharedPreferencesUtils.put(getContext(), "AdvShow", updateAtNet);
                } else {
                    if (!updatedAt.equals(updateAtNet)) {
                        showADDialog(advListResult);
                        SharedPreferencesUtils.put(getContext(), "AdvShow", updateAtNet);
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_numFood.setNumAnim(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumCount());
        foodSelectListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_goods_to_pay:
                if (UserUtils.getInstance().isLoginWithLogin(getActivity())) {
                    if (BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() != 0) {
                        Intent intent = new Intent(getActivity(), FoodPayActivity.class);
                        intent.putExtra("stalls_id", mStallsId);
                        intent.putExtra("have_limit_action", storeStalls.getActivityList().size() == 0 ? false : true);
                        intent.putExtra("storeStalls", (Parcelable) storeStalls);
                        intent.putStringArrayListExtra("labelList", (ArrayList<String>) labelList);
                        getActivity().startActivityForResult(intent, 1);
//                        getActivity().overridePendingTransition(R.anim.slide_in_buttom, R.anim.anim_no);
                    } else {
                        showToast("请先添加套餐");
                    }
                }
                break;

            case R.id.header_banner_root:
                if (notifyDialog == null) {
                    notifyDialog = new NotifyDialog(getActivity(), storeStalls);
                }
                notifyDialog.show();
                break;
//            case R.id.rect_selectStoreFoodSelect:
//
////                Intent intent1 = new Intent(getContext(), SchoolStoreActivity.class);
////                intent1.putExtra("school", school);
////                startActivity(intent1);
////                getActivity().finish();
//
//                break;
            case R.id.ll_goods_price_to_pay:
//                if (UserUtils.getInstance().isLoginWithLogin(getActivity())) {
//                    if (BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() != 0) {
//                        showBottomSheet();
//                    } else {
//                        showToast("请先添加套餐");
//                    }
//                }
//                break;
            case R.id.iv_foodSelect:
                if (UserUtils.getInstance().isLoginWithLogin(getActivity())) {
                    if (BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() != 0) {
                        showBottomSheet();
                    } else {
                        showToast("请先添加套餐");
                    }
                }
                break;
            case R.id.rect_showAD:
                showADDialog(advListResult);
                break;
            case R.id.fl_imgNavLeft:
                getActivity().finish();
                break;
            case R.id.iv_show_food_type:
                if (isShowFoodType == true) {
                    hideMenuFoodType();
                    iv_show_food_type.setImageResource(R.drawable.icon_food_select_right);
                    isShowFoodType = false;
                } else {
                    showMenuFoodType();
                    iv_show_food_type.setImageResource(R.drawable.icon_food_select_left);
                    isShowFoodType = true;
                }
                break;
            case R.id.rectTypefoodSelect:
                homeActivity.showTypeScreenFood();

                if (ivMoreClassFoodSelect.getRotation() == 0) {
                    ivMoreClassFoodSelect.animate().rotation(180f);
                    ivMoreScreenFoodSelect.animate().rotation(0f);
                } else {
                    ivMoreClassFoodSelect.animate().rotation(0f);
                    ivMoreScreenFoodSelect.animate().rotation(0f);
                }

                break;
            case R.id.rectScreenfoodSelect:
                homeActivity.showScreenFood();

                if (ivMoreScreenFoodSelect.getRotation() == 0) {
                    ivMoreScreenFoodSelect.animate().rotation(180f);
                    ivMoreClassFoodSelect.animate().rotation(0f);
                } else {
                    ivMoreScreenFoodSelect.animate().rotation(0f);
                    ivMoreClassFoodSelect.animate().rotation(0f);
                }

                break;
            case R.id.tv_clear_buy_car:  //清空底部购物车
                clearBottomSheetBuyCar();
                break;
        }

    }

    /**
     * 清空底部购物车view
     */
    private void clearBottomSheetBuyCar(){
        BuyCarMapUtils.getCurBuyCarUtils().clearBuyCar();
        tv_numFood.setNum(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumCount());
        foodSelectListAdapter.notifyDataSetChanged();
        buyCargoodsAdapter.clearAllData();
        if(bottomSheetLayout.isSheetShowing()){
            bottomSheetLayout.dismissSheet();
        }
    }

    /**
     * 创建底部购物车view
     */
    private void showBottomSheet(){
        if(bottomSheetLayout.isSheetShowing()){
            bottomSheetLayout.dismissSheet();
        }else {
            if(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList().size() != 0){
                buyCargoodsAdapter.updateAllDataSet(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList());
                bottomSheetLayout.showWithSheetView(bottomSheetRootView);
            }
        }
    }

    /**
     * (横向)展开菜品分类列表
     */
    private void showMenuFoodType() {
        FrameLayout.LayoutParams params_food_select = (FrameLayout.LayoutParams) rl_food_select.getLayoutParams();
        params_food_select.height = rl_food_select.getHeight();
        params_food_select.width = TJWindowManager.getWindowWidth() - getResources().getDimensionPixelSize(R.dimen.common_food_select_margin_left);
        params_food_select.setMargins(0, 0, 0, 0);
        rl_food_select.setLayoutParams(params_food_select);

        ObjectAnimator.ofFloat(rl_food_select, "translationX", 0, getResources().getDimensionPixelSize(R.dimen.common_food_select_margin_left))
                .setDuration(360)
                .start();
        ObjectAnimator.ofFloat(rl_food_type, "translationX", -getResources().getDimensionPixelSize(R.dimen.common_food_type_width), 0)
                .setDuration(500)
                .start();

    }

    /**
     * (横向)收起菜品分类列表
     */
    private void hideMenuFoodType() {
        final FrameLayout.LayoutParams params_food_select = (FrameLayout.LayoutParams) rl_food_select.getLayoutParams();
        params_food_select.height = rl_food_select.getHeight();
        params_food_select.width = TJWindowManager.getWindowWidth();
        params_food_select.setMargins(getResources().getDimensionPixelSize(R.dimen.common_food_select_margin_left), 0, 0, 0);
        rl_food_select.setLayoutParams(params_food_select);

        ObjectAnimator.ofFloat(rl_food_select, "translationX", 0, -getResources().getDimensionPixelSize(R.dimen.common_food_select_margin_left))
                .setDuration(360)
                .start();
        ObjectAnimator.ofFloat(rl_food_type, "translationX", 0, -getResources().getDimensionPixelSize(R.dimen.common_food_type_width))
                .setDuration(500)
                .start();

    }

    public void resetDownMore() {
        ivMoreClassFoodSelect.animate().rotation(0f);
        ivMoreScreenFoodSelect.animate().rotation(0f);
    }

    private void showADDialog(List<Adv> advListResult) {
        // 弹出广告
        List<String> imgList = new ArrayList<>();
        for (Adv adv : advListResult) {
            imgList.add(adv.getImgUrl());
        }
        AdvDialog advDialog = new AdvDialog(getContext());
        advDialog.show();
        advDialog.setImageList(imgList);
    }


//    @Override
//    public void onItemClickListener(int position) {
//
//        int payType = Order.TYPE_MESS;
//
//        switch (position) {
//            case 0:
//                payType = Order.TYPE_MESS;
//                break;
//            case 1:
//                payType = Order.TYPE_TAKE_AWAY;
//                break;
//            case 2:
//                payType = Order.TYPE_PASS_ME;
//                break;
//        }
//
//        if (UserUtils.getInstance().isLoginWithLogin(getActivity())) {
//            if (BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() != 0) {
//                Intent intent = new Intent(getActivity(), FoodPayActivity.class);
//                intent.putExtra("ORDER_TYPE", payType);
//                getActivity().startActivityForResult(intent, 1);
//                getActivity().overridePendingTransition(R.anim.slide_in_buttom, R.anim.anim_no);
//            } else {
//                showToast("请先添加套餐");
//            }
//        }
//    }

    @Override
    public void onScrollNote(int x) {

    }

    /**
     * 购物车数量变化监听
     * @param foodsNum 购物车菜品数量
     */
    @Override
    public void onNumChange(int foodsNum) {

        BigDecimal CurMoneyWithBox = BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumWithBoxFee();
        BigDecimal remainMoney = mDeliverAmount.subtract(CurMoneyWithBox);
        int compare = remainMoney.compareTo(BigDecimal.ZERO);
        tv_goods_pay_price.setText(NumberUtils.clearTailZero(CurMoneyWithBox) + "元");

        ll_goods_price_to_pay.setVisibility(foodsNum > 0 ? View.VISIBLE : View.INVISIBLE);
        tv_goods_to_pay.setEnabled(foodsNum > 0 && compare <= 0 ? true : false);
        tv_goods_to_pay.setBackgroundColor(getActivity().getResources().getColor(foodsNum > 0 && compare <= 0 ?
                R.color.common_title_bg_root :
                R.color.gray_3));
        tv_goods_to_pay.setText(
                foodsNum > 0 && compare <= 0 ? "结算享优惠":
                "还差" + NumberUtils.clearTailZero(remainMoney)  + "元");
        //更新餐盒费，
        updateBoxFee(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList());
    }

    /**
     * 更新餐盒费，餐盒费没有则不需要显示
     */
    private void updateBoxFee(List<GoodSet> goodSetList) {
        int boxFee = storeStalls.getPackFee();
//        BigDecimal boxFee = new BigDecimal(BigInteger.ZERO);
//        for (GoodSet goodSet : goodSetList) {
//            if (goodSet.getGoods().getMealFee() != null) {
//                ;
//                boxFee = boxFee.add(goodSet.getGoods().getMealFee().multiply(new BigDecimal(goodSet.getCount())));
//            }
//        }
        tv_price_box_fee.setText("" + boxFee);
        ll_price_lunch_fee.setVisibility(boxFee > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onStart(int requestTag) {
        if (loadDialog == null) {
            loadDialog = new AnimLoadDialog(getActivity());
        }

        switch (requestTag) {
            case TJRequestTag.TAG_GET_SCHOOL_STORE_LIST:
                loadDialog.show();
                break;
        }
    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_GET_STORE_STALLS_GOODS:
                // 获取套餐列表
                Pager<Goods> goodPager = (Pager<Goods>) response.getData();
                pager = goodPager.getPage();
                List<Goods> goodsList = goodPager.getList();

                if (goodPager != null) {
                    /**
                     *  如果是刷新，清理全部数据
                     */
                    if (pager == 1) {
//                        goodList.clear();
                        foodSelectListAdapter.clearAllData();
                    }

                    for (int i = 0; i < goodsList.size(); i++) {
                        Goods goods = goodsList.get(i);
                        if (goods.getSpecialPrice().doubleValue() != 0) {
                            BigDecimal priceCache = goods.getSellPrice();
                            goods.setSellPrice(goods.getSpecialPrice());
                            goods.setSpecialPrice(priceCache);
                            goodsList.set(i, goods);
                        }
                    }

                    goodList.addAll(goodPager.getList());
                    foodSelectListAdapter.notifyDataSetChanged();
                }

                lv_selectFoodPull.onPullDownRefreshComplete();
                lv_selectFoodPull.onPullUpRefreshComplete();

                if (pager == goodPager.getLastPage()) {
                    lv_selectFoodPull.setHasMoreData(false);
                }
                LogUtils.e(response.getData().toString());
                break;
            case TJRequestTag.TAG_GET_SCHOOL_STORE_LIST:

                List<School> schoolListResult = (List<School>) response.getData();
                schoolList.clear();
                schoolList.addAll(schoolListResult);
                schoolAdapter = new SchoolAdapter(schoolList);

                showSelectStoreDialog();

                break;
            case TJRequestTag.TAG_GET_STORE_STALLS_FOOD_CLASS:
                FoodClassList food = (FoodClassList) response.getData();
                List<FoodClass> foodClassListResult = food.getGoodsClassList();

                labelList = food.getLabelList();

                foodClassList.addAll(foodClassListResult);

                foodTypeSelectListAdapter.notifyDataSetChanged();
                //获取完菜品列表数据后，获取第一个菜品的数据
                if (foodClassList.size() > 0) {
                    goodClassId = foodClassList.get(0).getClassId();
//                    TJHttpUtils.getInstance(this).getGoodLstByStore(store.getId(), pager, goodClassId, goodScreen);
//                    TJHttpUtil.getInstance(this).getStallsGoods(mStallsId, goodClassId, pager, pageSize);

                    TJHttpUtil.getInstance(this).getStallsGoods(userApartment.getApartmentId(), storeStalls.getShopId(), goodClassId, pager, pageSize, 0);

                }
                break;
        }

        loadDialog.dismiss();
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {
        lv_selectFoodPull.onPullDownRefreshComplete();
        lv_selectFoodPull.onPullUpRefreshComplete();
    }

    /**
     * 触摸滑动事件
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        LogUtils.e("header_banner_root -> height = " + header_banner_root.getHeight());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("food_select --> MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("food_select --> MotionEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("food_select --> MotionEvent.ACTION_UP");
                break;
        }
        return false;
    }

    private class SchoolAdapter extends BaseAdapter {

        private List<School> schoolList;

        public SchoolAdapter(List<School> schoolList) {
            this.schoolList = schoolList;
        }

        @Override
        public View getView(int arg0, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.item_list_expandable_select_school, null);
            }

            TextView tv_schoolSelect = (TextView) view.findViewById(R.id.tv_schoolSelect);
            tv_schoolSelect.setText(schoolList.get(arg0).getSchoolName());

            int size = schoolList.get(arg0).getStoreList().size();

            int length = 100;

            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            float density = dm.density;
            int gridviewWidth = (int) (size * (length + 4) * density);
            int itemWidth = (int) (length * density);

            GridView gridView = (GridView) view.findViewById(R.id.grid);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);

            gridView.setLayoutParams(params); // 重点
            gridView.setColumnWidth(itemWidth); // 重点
            gridView.setHorizontalSpacing(5); // 间距
            gridView.setStretchMode(GridView.NO_STRETCH);
            gridView.setNumColumns(size); // 重点

            GridViewAdapter adapter = new GridViewAdapter(getActivity(), schoolList.get(arg0).getStoreList());
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(FoodSelectFragment.this);

            return view;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public Object getItem(int arg0) {
            return schoolList.get(arg0);
        }

        @Override
        public int getCount() {
            return schoolList.size();
        }

    }

    private class GridViewAdapter extends BaseAdapter {

        Context context;
        List<Store> list;

        public GridViewAdapter(Context _context, List<Store> _list) {
            this.list = _list;
            this.context = _context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_school_select, null);
            }

            TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);

            ImageView ItemImage = (ImageView) convertView.findViewById(R.id.ItemImage);

            final Store storeCache = list.get(position);

            tvCity.setText(storeCache.getName());

            loadImage(ItemImage, storeCache.getLogo(), R.drawable.icon_hander_defaut);

            return convertView;
        }
    }

    private void initBannerPager() {
        View viewPagerContent = LayoutInflater.from(getActivity()).inflate(R.layout.layout_banner_pager, null);
        tv_noteMsg = (TextView) viewPagerContent.findViewById(R.id.tv_noteMsg);
        rect_noteMsg = viewPagerContent.findViewById(R.id.rect_noteMsg);
        lv_selectFood.addHeaderView(viewPagerContent);
        rect_noteMsg.setVisibility(View.GONE);
    }

    // 下拉刷新
    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

        pager = 1;
//            TJHttpUtils.getInstance(this).getGoodListByStore(store.getId(), pager, goodClassId, goodScreen);

        TJHttpUtil.getInstance(this).getStallsGoods(userApartment.getApartmentId(), mStallsId, goodClassId, pager, pageSize, 0);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//            TJHttpUtils.getInstance(this).getGoodListByStore(store.getId(), pager + 1, goodClassId, goodScreen);
        TJHttpUtil.getInstance(this).getStallsGoods(userApartment.getApartmentId(), mStallsId, goodClassId, pager + 1, pageSize, 0);
    }

    private void showSelectStoreDialog() {

        if (selectedStoreDialog == null) {
            selectedStoreDialog = new CDialog(getActivity(), R.layout.dialog_select_restrant);
        }

        if (!SharedPreferencesUtils.contains(getActivity(), SharedPreferencesUtils.USER_STORE)) {
            selectedStoreDialog.setCanceledOnTouchOutside(false);
            selectedStoreDialog.setCancelable(false);
        } else {
            selectedStoreDialog.setCanceledOnTouchOutside(true);
            selectedStoreDialog.setCancelable(true);
        }

        ActionSlideExpandableListView list = (ActionSlideExpandableListView) selectedStoreDialog.getContentView().findViewById(R.id.list);
        list.setAdapter(schoolAdapter);

        selectedStoreDialog.show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        store = UserUtils.getInstance().getStoreInfor();

        if (store != null) {

            final Store storeCache = (Store) parent.getItemAtPosition(position);

            if (!storeCache.getId().equals(store.getId())) {

                if (BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() > 0) {

                    if (alertDialog == null) {
                        alertDialog = new HintDialog(getActivity());
                        alertDialog.setMessage("修改食堂将会情况您的购物车，确定要修改吗？");
                    }

                    alertDialog.setPositiveButtonListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BuyCarMapUtils.getCurBuyCarUtils().clearBuyCar();

                            store = storeCache;

                            pager = 1;
                            UserUtils.getInstance().saveStoreInfor(store);
//                            tv_nameStoreSelectFood.setText(store.getName());
//                            TJHttpUtils.getInstance(FoodSelectFragment.this).getGoodListByStore(store.getId(), pager, goodClassId, goodScreen);
//                            TJHttpUtil.getInstance(FoodSelectFragment.this).getStallsGoods(mStallsId, goodClassId, pager, pageSize);
                            TJHttpUtil.getInstance(FoodSelectFragment.this).getStallsGoods(userApartment.getApartmentId(), mStallsId, goodClassId, pager, pageSize, 0);

                            selectedStoreDialog.dismiss();

                            onResume();

                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.setNegativeButtonListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();
                } else {
                    changeStore(parent, position);
                }

            } else {
                selectedStoreDialog.dismiss();
            }

        } else {
            changeStore(parent, position);
        }
    }

    /**
     * 改变食堂
     */
    private void changeStore(AdapterView<?> parent, int position) {
        store = (Store) parent.getItemAtPosition(position);
        // 先保存食堂信息
        UserUtils.getInstance().saveStoreInfor(store);
//        tv_nameStoreSelectFood.setText(store.getName());
        selectedStoreDialog.dismiss();
        pager = 1;

//        TJHttpUtils.getInstance(this).getGoodListByStore(store.getId(), pager, goodClassId, goodScreen);
//        TJHttpUtil.getInstance(this).getStallsGoods(mStallsId, goodClassId, pager, pageSize);
        TJHttpUtil.getInstance(FoodSelectFragment.this).getStallsGoods(userApartment.getApartmentId(), mStallsId, goodClassId, pager, pageSize, 0);
    }

    /**
     * 档口公告
     */
    private void setNoteMsg() {
        if (TextUtils.isEmpty(storeStalls.getNotice())) {
            tv_noteMsgMsgHome.setText("暂无商家公告。");
        } else {
            tv_noteMsgMsgHome.setText("公告:" + storeStalls.getNotice());
        }
    }

    public void doReqTypeResult(FoodClass foodClass) {
        goodClassId = foodClass.getClassId();
        tvIconClassFoodSelect.setText(foodClass.getClassName());

        lv_selectFoodPull.doPullRefreshing(true, 0);
        lv_selectFood.setSelection(0);

        // 将筛选置为默认
        goodScreen = null;
        tvIconScreenFoodSelect.setText("筛选");

    }

    public void doReqScreenResult(int position) {
        switch (position) {
            case 0:
                goodScreen = "price";
                tvIconScreenFoodSelect.setText("按价格最低");
                break;
            case 1:
                goodScreen = "hot";
                tvIconScreenFoodSelect.setText("按销量最高");
                break;
        }
        lv_selectFoodPull.doPullRefreshing(true, 0);
        lv_selectFood.setSelection(0);
    }

}
