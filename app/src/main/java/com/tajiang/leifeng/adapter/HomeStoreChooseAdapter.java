package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.FoodActivity;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.model.Goods;
import com.tajiang.leifeng.model.StallsFullcut;
import com.tajiang.leifeng.model.StoreDelivered;
import com.tajiang.leifeng.model.StoreStalls;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.DensityUtils;
import com.tajiang.leifeng.utils.GlideRoundTransform;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.NumberUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.TJExpandableTextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admins on 2016/10/25.
 */
public class HomeStoreChooseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_HEAD = 0;
    private static final int ITEM_TYPE_NORMAL = 1;
    private static final int MAX_ACTION_COUNT = 2;  //每个档口优惠活动列的最大展示数量

    private Context mContext;
    private List<StoreStalls> mDataList;
    private List<Goods> mHotDataList;
    private View mHeadView;
    private int homeType = 0;

    public HomeStoreChooseAdapter(Context context, View headView) {
        this.mContext = context;
        this.mDataList = new ArrayList<>();
        this.mHotDataList = new ArrayList<>();
        this.mHeadView = headView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder myViewHolder = null;
        switch (viewType) {
            case ITEM_TYPE_HEAD: //RecyclerView头部
                myViewHolder =  new HeaderHolder(mHeadView);
                break;
            case ITEM_TYPE_NORMAL: //RecyclerView列表项, 单个Itemt，单行展示
                View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_home_fragment, null);
                myViewHolder = new ItemViewHolder(mItemView);
                break;
        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) {
            return;
        } else {
            if (homeType == 0) {
                StoreStalls item = mDataList.get(position - 1);  //去掉头部Item
                ItemViewHolder ItemHolder = (ItemViewHolder) holder;

                ItemHolder.rl_item_root.setVisibility(View.VISIBLE);
                ItemHolder.rl_hot_item_root.setVisibility(View.GONE);
                updateActionRootView(ItemHolder, item);
                //初始化档口展示图片
                Glide.with(mContext)
                        .load(item.getShopImage())
                        .transform(new CenterCrop(mContext)   //此方式解决CenterCrop与Transformer的共存问题
                                , new GlideRoundTransform(mContext, 4))
                        .placeholder(R.drawable.img_store_stalls)
                        .error(R.drawable.img_store_stalls)
                        .crossFade()
                        .into(ItemHolder.iv_pic_store);
            } else {
                Goods goods = mHotDataList.get(position - 1);
                ItemViewHolder ItemHolder = (ItemViewHolder) holder;
                ItemHolder.rl_item_root.setVisibility(View.GONE);
                ItemHolder.rl_hot_item_root.setVisibility(View.VISIBLE);

                updateHotRootView(ItemHolder, goods);
            }
        }
    }

    private void updateHotRootView(ItemViewHolder holder, Goods item) {
        //初始化档口展示图片
        Glide.with(mContext)
                .load(item.getGoodsImage())
                .transform(new CenterCrop(mContext)   //此方式解决CenterCrop与Transformer的共存问题
                        , new GlideRoundTransform(mContext, 4))
                .placeholder(R.drawable.img_store_stalls)
                .error(R.drawable.img_store_stalls)
                .crossFade()
                .into(holder.iv_hot_pic_goods);

//        holder.tv_hot_goods_count.setText();
        holder.tv_hot_goods_name.setText(item.getGoodsName());
        holder.tv_hot_month_sellqty.setText("月销量 " + item.getMonthSellQty());
        holder.tv_hot_goods_money.setText("￥ " + item.getSellPrice());
        holder.btnSnapUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void updateActionRootView(ItemViewHolder holder, StoreStalls item) {
        int currentSumCount = BuyCarMapUtils.getSpecCurBuyCarUtils(item.getShopId()).getBuyCar().getSumCount();

        holder.addNewActionTextView(item.getActivityList());

        holder.tv_store_goods_count.setVisibility(currentSumCount == 0 ? View.GONE : View.VISIBLE);
        holder.tv_store_goods_count.setText("" + currentSumCount);
        holder.tv_store_name.setText(item.getShopName());
        holder.tv_store_notice.setText((item.getMinMoney() == null ? "" : "满" + NumberUtils.clearTailZero(item.getMinMoney()) + "元起送 | 配送费￥") + item.getDelyFee());

        if (item.getBussState() == StoreStalls.STORE_STALLS_CLOSED) {
            holder.tv_store_status.setText("休息中");
            holder.tv_store_status.setVisibility(View.VISIBLE);
            holder.ll_transparent_layout.setVisibility(View.VISIBLE);
            holder.rl_item_root.setEnabled(false);
        } else {
            holder.tv_store_status.setVisibility(View.GONE);
            holder.ll_transparent_layout.setVisibility(View.GONE);
            holder.rl_item_root.setEnabled(true);
        }
        initTransparentLayout(holder);
    }

    private void initTransparentLayout(ItemViewHolder holder) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        holder.rl_item_root.measure(w, h);
        int height = holder.rl_item_root.getMeasuredHeight();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.ll_transparent_layout.getLayoutParams();
        params.height = height;
        holder.ll_transparent_layout.setLayoutParams(params);
    }

    //设置商家列表
    public void updateAllDataSet(List<StoreStalls> data, int type) {
        this.homeType = type;
        this.mDataList.clear();
        this.mDataList.addAll(data);
        this.notifyDataSetChanged();
    }

    //设置热门菜品
    public void updateHotDateSet(List<Goods> goodsList, int type) {
        this.homeType = type;
        this.mHotDataList.clear();;
        this.mHotDataList.addAll(goodsList);

        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (homeType == 0) {
            if (mDataList == null) {
                return 1;
            } else if (mDataList.size() == 0) {
                return 1;
            } else {
                return mDataList.size() + 1;  //加上头部的Item
            }
        } else {
            if (mHotDataList == null) {
                return 1;
            } else if (mHotDataList.size() == 0) {
                return 1;
            } else {
                return mHotDataList.size() + 1;  //加上头部的Item
            }
        }
    }

    /**
     * 决定元素的布局使用哪种类型
     *
     * @param position 数据源的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数 */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEAD;
        } else
            return ITEM_TYPE_NORMAL;
    }

    /**
     * 判断当前position是否处于第一个
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return position == 0;
    }

    /*头部Item*/
    class HeaderHolder extends RecyclerView.ViewHolder {
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ExpandableListView store_activity_listview;
        private LinearLayout ll_action_1;
        private ImageView iv_action_1;
        private TextView tv_action_1;

        private LinearLayout ll_action_root;//食堂优惠活动RootView
        private LinearLayout ll_transparent_layout; //暂停营业的蒙版
        private LinearLayout rl_item_root;    //整个Item的点击View
        private ImageView iv_pic_store;

        private TextView tv_store_name;
        private TextView tv_store_status;     //食堂状态（营业，休息）
        private TextView tv_store_notice;   //食堂公告
        private TextView tv_store_goods_count; //脚标（已选商品数量）

        /*----------------------------------------------*/

        private LinearLayout rl_hot_item_root;
        private ImageView iv_hot_pic_goods;
        private TextView tv_hot_goods_count;
        private TextView tv_hot_goods_name;
        private TextView tv_hot_month_sellqty;
        private TextView tv_hot_goods_money;
        private Button btnSnapUp;

        public ItemViewHolder(View itemView) {
            super(itemView);
            initView();
            initListener();
        }

        private void initView() {
            store_activity_listview = (ExpandableListView) itemView.findViewById(R.id.store_activity_listview);
            this.ll_action_1 = (LinearLayout) itemView.findViewById(R.id.ll_action_1);
            this.iv_action_1 = (ImageView) itemView.findViewById(R.id.iv_action_1);
            this.tv_action_1 = (TextView) itemView.findViewById(R.id.tv_action_1);

            this.ll_action_root = (LinearLayout) itemView.findViewById(R.id.ll_action_root);
            this.ll_transparent_layout = (LinearLayout) itemView.findViewById(R.id.ll_transparent_layout);
            this.rl_item_root = (LinearLayout) itemView.findViewById(R.id.rl_item_root);
            this.iv_pic_store = (ImageView) itemView.findViewById(R.id.iv_pic_store);

            this.tv_store_name = (TextView) itemView.findViewById(R.id.tv_store_name);
            this.tv_store_status = (TextView) itemView.findViewById(R.id.tv_store_status);
            this.tv_store_notice = (TextView) itemView.findViewById(R.id.tv_store_notice);
            this.tv_store_goods_count = (TextView) itemView.findViewById(R.id.tv_store_goods_count);


            rl_hot_item_root = (LinearLayout) itemView.findViewById(R.id.rl_hot_item_root);
            iv_hot_pic_goods = (ImageView) itemView.findViewById(R.id.iv_hot_pic_goods);
            tv_hot_goods_count = (TextView) itemView.findViewById(R.id.tv_hot_goods_count);
            tv_hot_goods_name = (TextView) itemView.findViewById(R.id.tv_hot_goods_name);
            tv_hot_month_sellqty = (TextView) itemView.findViewById(R.id.tv_hot_month_sellqty);
            tv_hot_goods_money = (TextView) itemView.findViewById(R.id.tv_hot_goods_money);
            btnSnapUp = (Button) itemView.findViewById(R.id.btnSnapUp);
        }

        private void initListener() {
            this.rl_item_root.setOnClickListener(this);
        }

        /**
         * 新增优惠活动 View (重用)
         * @param list
         */
        public void addNewActionTextView(List<StallsFullcut> list) {
            ll_action_1.setVisibility(View.GONE);
            tv_action_1.setTextColor(mContext.getResources().getColor(R.color.text_black_1));
            if (list == null || list.size() == 0) {
                store_activity_listview.setVisibility(View.GONE);
                ll_action_1.setVisibility(View.VISIBLE);
                iv_action_1.setVisibility(View.GONE);
                tv_action_1.setText("暂无商家活动");
                tv_action_1.setTextColor(mContext.getResources().getColor(R.color.text_black_3));
            } else {
                ll_action_1.setVisibility(View.GONE);
                store_activity_listview.setVisibility(View.VISIBLE);
                BuddyAdapter adapter = new BuddyAdapter(list.get(0).getActivityName(), list, mContext);
                store_activity_listview.setAdapter(adapter);

                // 分组展开
                store_activity_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        setListViewHeightBasedOnChildren(store_activity_listview, 1);
                        return false;
                    }
                });
                // 分组关闭
                store_activity_listview.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                    @Override
                    public void onGroupCollapse(int groupPosition) {
                        setListViewHeightBasedOnChildren(store_activity_listview, 0);
                    }
                });
            }
        }

        /**
         * 重新计算ListView的高度
         * @param listView
         */
        public void setListViewHeightBasedOnChildren(ExpandableListView listView, int res) {
            // 获取ListView对应的Adapter
            ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
            if (listAdapter == null) {
                // pre -condition
                return;
            }

            int totalHeight = 0;
            if (res == 1) {
                for (int i = 0; i < listAdapter.getGroupCount(); i++) { // listAdapter.getCount()返回数据项的数目
                    View listgroupItem = listAdapter.getGroupView(i, true, null, listView);
                    listgroupItem.measure(0, 0); // 计算子项View 的宽高
                    totalHeight += listgroupItem.getMeasuredHeight(); // 统计所有子项的总高度
                    System.out.println("height : group" + i + "次" + totalHeight);
                    for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                        View listchildItem = listAdapter.getChildView(i, j, false, null, listView);
                        listchildItem.measure(0, 0); // 计算子项View 的宽高
                        totalHeight += listchildItem.getMeasuredHeight(); // 统计所有子项的总高度
                        System.out.println("height :" + "group:" + i + " child:" + j + "次" + totalHeight);
                    }
                }
            } else {
                for (int i = 0; i < listAdapter.getGroupCount(); i++) { // listAdapter.getCount()返回数据项的数目
                    View listgroupItem = listAdapter.getGroupView(i, true, null, listView);
                    listgroupItem.measure(0, 0); // 计算子项View 的宽高
                    totalHeight += listgroupItem.getMeasuredHeight(); // 统计所有子项的总高度
                    System.out.println("height : group" + i + "次" + totalHeight);
                }
            }

            ViewGroup.LayoutParams params = listView .getLayoutParams();
            params.height = totalHeight + ( listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
            // listView.getDividerHeight()获取子项间分隔符占用的高度
            // params.height最后得到整个ListView完整显示需要的高度
            listView.setLayoutParams(params );
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_item_root:
                    StoreStalls storeStalls = mDataList.get(getLayoutPosition() - 1);
                    TJApp.getIns().setStallsId(storeStalls.getShopId());
                    Intent intent = new Intent(mContext, FoodActivity.class);
                    intent.putExtra("store_stalls", storeStalls);
//                    intent.putExtra("stalls_id", storeStalls.getStallsId());
//                    intent.putExtra("stalls_name", storeStalls.getName());
//                    intent.putExtra("stalls_deliver_amount", storeStalls.getDeliveryAmount() == null ? "0" : storeStalls.getDeliveryAmount().toString());
                    mContext.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
