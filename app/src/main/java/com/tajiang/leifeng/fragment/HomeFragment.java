package com.tajiang.leifeng.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.SchoolSelectAreaActivity;
import com.tajiang.leifeng.activity.SchoolSelectStoreAndBuildingActivity;
import com.tajiang.leifeng.activity.SupperActivity;
import com.tajiang.leifeng.activity.WebActivity;
import com.tajiang.leifeng.adapter.HeaderChannelAdapter;
import com.tajiang.leifeng.adapter.HomeStoreChooseAdapter;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseFragment;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Adv;
import com.tajiang.leifeng.model.Apartment;
import com.tajiang.leifeng.model.ChannelEntity;
import com.tajiang.leifeng.model.Goods;
import com.tajiang.leifeng.model.Marquee;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.model.StoreDelivered;
import com.tajiang.leifeng.model.StoreStalls;
import com.tajiang.leifeng.model.StoreStallsList;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.AutoTextView;
import com.tajiang.leifeng.view.FixedGridView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 新版本用户端首页
 * Created by Admins on 2016/10/28.V
 */
public class HomeFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, HttpResponseListener {

    private View headView;
    private LinearLayout rect_change_school;
    private SwipeToLoadLayout swipeToLoadLayout;
    private RelativeLayout mRecommendBusinessLayout; //推荐商家
    private RelativeLayout mHotDishesLayout;          //热门菜品
    private View mListViewLine1;
    private View mListViewLine2;

    private AutoTextView push_assgned_message;
    private TextView tv_nameStoreSelectFood;

    private RecyclerView rv_store;
    private ConvenientBanner mConvenientBanner;

    private ArrayList<String> imgList = new ArrayList<>();
    private List<Adv> advList = new ArrayList<>();
    private List<String> networkImages = new ArrayList<>();
    private HomeStoreChooseAdapter mHomeStoreChooseAdapter;

    private  List<String> mStoreList;
    private School mSchool;
    private StoreDelivered mStoreDelivered;
    private String selectedApartment;
    private Apartment userApartment;

    private static int sCount = 0; //跑马灯数量
    private final Handler handler = new Handler();
    private List<Marquee> marqueeList;

    @Override
    protected void initLayout() {
        setContentView(R.layout.fragment_home_2);
    }

    @Override
    protected void initView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment_head_banner, null);
        mConvenientBanner = (ConvenientBanner) headView.findViewById(R.id.banner_home);
        push_assgned_message = (AutoTextView) headView.findViewById(R.id.push_assgned_message);
        mRecommendBusinessLayout = (RelativeLayout) headView.findViewById(R.id.recommend_business_layout);
        mHotDishesLayout = (RelativeLayout) headView.findViewById(R.id.hot_dishes_layout);
        mListViewLine1 = headView.findViewById(R.id.listview_line1);
        mListViewLine2 = headView.findViewById(R.id.listview_line2);

        tv_nameStoreSelectFood = findViewById(R.id.tv_nameStoreSelectFood);
        rect_change_school = findViewById(R.id.rect_change_school);
        swipeToLoadLayout = findViewById(R.id.swipe_to_load_layout);
        rv_store = findViewById(R.id.swipe_target);

        mListViewLine1.setVisibility(View.VISIBLE);
        mListViewLine2.setVisibility(View.GONE);

    }

    @Override
    protected void initListener() {
        rect_change_school.setOnClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setLoadMoreEnabled(false);  //禁止上拉加载更多
        mRecommendBusinessLayout.setOnClickListener(this);
        mHotDishesLayout.setOnClickListener(this);
    }

    @Override
    protected void initAdapter() {
        mHomeStoreChooseAdapter = new HomeStoreChooseAdapter(getActivity(), headView);
    }

    @Override
    protected void initData() {
        this.mSchool = TJApp.getIns().getSchool();
        userApartment = TJApp.getIns().getUserApartment();
        rv_store.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_store.setAdapter(mHomeStoreChooseAdapter);
        rv_store.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).color(getResources().getColor(R.color.gray)).size(1).build());

        this.mStoreDelivered = TJApp.getIns().getStoreDelivered();
        this.selectedApartment = (String) SharedPreferencesUtils.get(getActivity(), SharedPreferencesUtils.USER_APARTMENT_NAME, "");

        if (mSchool == null || mStoreDelivered == null) {
            intent2Activity(SchoolSelectAreaActivity.class);
            getActivity().finish();
        } else {
            if (!TextUtils.isEmpty(mSchool.getSchoolName())) {
                tv_nameStoreSelectFood.setText(mSchool.getSchoolName() + "-" + selectedApartment);
            } else {
                tv_nameStoreSelectFood.setText(mSchool.getSchoolName() + "-" + selectedApartment);
            }
            //获取档口
            refreshStalls();
        }

        refreshBanner();

        TJHttpUtil.getInstance(this).getMarquee(mSchool.getSchoolId());  //获取雷锋头条
        TJHttpUtil.getInstance(this).getRangeList();  //获取主营业务列表
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.rect_change_school:  //选择学校
//                intent2Activity(SchoolSelectAreaActivity.class);
                intent2Activity(SchoolSelectStoreAndBuildingActivity.class);
                break;
            case R.id.recommend_business_layout:
                mListViewLine1.setVisibility(View.VISIBLE);
                mListViewLine2.setVisibility(View.GONE);
                //获取档口
                refreshStalls();
                break;
            case R.id.hot_dishes_layout:
                mListViewLine1.setVisibility(View.GONE);
                mListViewLine2.setVisibility(View.VISIBLE);
                TJHttpUtil.getInstance(HomeFragment.this).getStallsGoods(7, null, "", 1, 100, 1);
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        this.mConvenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mConvenientBanner.stopTurning();
    }

    private Adv adv;

    /**
     * Banner点击监听器
     */
    private OnItemClickListener bannerOnClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            adv = advList.get(position);
            if (UserUtils.getInstance().isLoginWithLogin(getActivity())) {
                if (checkUrlIsEmpty(adv)) {
//                    if (adv.getType() == 5) {
                        TJHttpUtil.getInstance(HomeFragment.this).userDoGetInfor();
//                    } else {
//                        Intent intent = new Intent(getContext(), WebActivity.class);
//                        intent.putExtra("ADV", adv);
//                        startActivity(intent);
//                    }
                }
            }
        }
    };

    private void refreshBanner() {
        TJHttpUtil.getInstance(this).getAdvList(mSchool.getSchoolId(), 1);
    }

    private void refreshStalls() {
        TJHttpUtil.getInstance(this).getStalls(7, 1, 100, "", 1);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refreshStalls();//获取档口
//        refreshBanner();//获取广告
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_GET_STORE_STALLS: //获取上架档口
                Pager<StoreStalls> storeStallsList = (Pager<StoreStalls>) response.getData();
                List<StoreStalls> list = storeStallsList.getList();
                if (list.size() == 0) {
                    ToastUtils.showShort("食堂营业筹备中！");
                }
                mHomeStoreChooseAdapter.updateAllDataSet(list, 0);
                break;
            case TJRequestTag.TAG_GET_ADV_LIST:
                imgList.clear();
                advList.clear();
                networkImages.clear();

                List<Adv> advListResult = (List<Adv>) response.getData();
                advList.addAll(advListResult);
                for (Adv adv : advList) {
                    imgList.add(adv.getImgUrl());
                }
                networkImages.addAll(imgList);
                mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, networkImages)
                        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                        //设置指示器的方向
                        //.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                        //.setOnPageChangeListener(this)//监听翻页事件
                        .setOnItemClickListener(bannerOnClickListener);
                mConvenientBanner.startTurning(5000);
                initAutoPagerAinm();
                break;
            case TJRequestTag.TAG_USER_INFO:
                User user = (User) response.getData();
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("ADV", adv);
                intent.putExtra("user", user);
                startActivity(intent);
                break;
            case TJRequestTag.TAG_GET_RANGELIST:

                setChannelData((List<ChannelEntity>) response.getData());
                break;
            case TJRequestTag.TAG_GET_MARQUEE:
                marqueeList = (List<Marquee>) response.getData();

                sCount = marqueeList.size();
                //启动计时器
                handler.postDelayed(runnable, 2000);
                break;
            case TJRequestTag.TAG_GET_STORE_STALLS_GOODS:
                // 获取热门菜品列表
                Pager<Goods> goodPager = (Pager<Goods>) response.getData();
                List<Goods> goodsList = goodPager.getList();

                mHomeStoreChooseAdapter.updateHotDateSet(goodsList, 1);

                LogUtils.e(response.getData().toString());

                break;
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 在此处添加执行的代码
            push_assgned_message.next();
            sCount++;
            if (sCount >= Integer.MAX_VALUE) {
                sCount = marqueeList.size();
            }
            push_assgned_message.setText(marqueeList.get(sCount % (marqueeList.size())).getShowName() + " " + marqueeList.get(sCount % (marqueeList.size())).getAction());
            if (marqueeList.size() > 1) {
                handler.postDelayed(this, 2000);// 50是延时时长
            }
        }
    };

    // 设置频道数据
    private void setChannelData(final List<ChannelEntity> channelList) {
        FixedGridView fixedGridView = (FixedGridView) headView.findViewById(R.id.gv_channel);
        HeaderChannelAdapter adapter = new HeaderChannelAdapter(getActivity(), channelList);
        fixedGridView.setAdapter(adapter);

        fixedGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent intent = new Intent(getActivity(), SupperActivity.class);
                    intent.putExtra("ChannelEntity", channelList.get(arg2));
                    startActivity(intent);
            }
        });
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }


    public class NetworkImageHolderView implements Holder<String> {
        private SimpleDraweeView simpleDraweeView;
        @Override
        public View createView(Context context) {

            View view = LayoutInflater.from(context).inflate(R.layout.layout_img, null);

            simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            simpleDraweeView.setImageURI(Uri.parse(data));
        }
    }

    private boolean checkUrlIsEmpty(Adv adv) {
        String url = "";
        try {
            JSONObject jsonObject = new JSONObject(adv.getContent());
            url = jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(url)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 初始化页面切换动画
     */
    private void initAutoPagerAinm() {

//        //各种翻页效果
//        transformerList.add(DefaultTransformer.class.getSimpleName());
//        transformerList.add(AccordionTransformer.class.getSimpleName());
//        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
//        transformerList.add(CubeInTransformer.class.getSimpleName());
//        transformerList.add(CubeOutTransformer.class.getSimpleName());
//        transformerList.add(DepthPageTransformer.class.getSimpleName());
//        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
//        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
//        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
//        transformerList.add(RotateDownTransformer.class.getSimpleName());
//        transformerList.add(RotateUpTransformer.class.getSimpleName());
//        transformerList.add(StackTransformer.class.getSimpleName());
//        transformerList.add(ZoomInTransformer.class.getSimpleName());
//        transformerList.add(ZoomOutTranformer.class.getSimpleName());

        String transforemerName = "ZoomOutTranformer";

        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            mConvenientBanner.getViewPager().setPageTransformer(true, transforemer);

            //部分3D特效需要调整滑动速度
            if (transforemerName.equals("StackTransformer")) {
                mConvenientBanner.setScrollDuration(1200);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
