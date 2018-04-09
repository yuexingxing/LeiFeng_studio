package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.FoodActivity;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.StallsFullcut;
import com.tajiang.leifeng.model.StoreDelivered;
import com.tajiang.leifeng.model.StoreStalls;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.GlideRoundTransform;
import com.tajiang.leifeng.utils.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admins on 2016/10/25.
 */
public class SupperAdapter extends CommonAdapter<StoreStalls> {

    private List<StoreStalls> mDataList;

    public SupperAdapter(Context context, List<StoreStalls> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.mDataList = mDatas;
    }

    @Override
    public void convert(ViewHolder helper, StoreStalls item) {
        LinearLayout ll_action_1 = helper.getView(R.id.ll_action_1);
        ImageView iv_action_1 = helper.getView(R.id.iv_action_1);
        TextView tv_action_1 = helper.getView(R.id.tv_action_1);

        LinearLayout rl_item_root = helper.getView(R.id.rl_item_root);    //整个Item的点击View
        ImageView iv_pic_supper = helper.getView(R.id.iv_pic_supper);

        TextView tv_supper_name = helper.getView(R.id.tv_supper_name);
        TextView tv_supper_status = helper.getView(R.id.tv_supper_status);     //食堂状态（营业，休息）
        TextView tv_supper_notice = helper.getView(R.id.tv_supper_notice);   //食堂公告
        TextView tv_supper_goods_count = helper.getView(R.id.tv_supper_goods_count); //脚标（已选商品数量）

        ExpandableListView supper_activity_listview = helper.getView(R.id.supper_activity_listview);

        updateActionRootView(helper, item);
        //初始化档口展示图片
        Glide.with(mContext)
                .load(item.getShopImage())
                .transform(new CenterCrop(mContext)   //此方式解决CenterCrop与Transformer的共存问题
                        , new GlideRoundTransform(mContext, 4))
                .placeholder(R.drawable.img_store_stalls)
                .error(R.drawable.img_store_stalls)
                .crossFade()
                .into(iv_pic_supper);
    }

    private void updateActionRootView(ViewHolder helper, final StoreStalls item) {
        LinearLayout rl_item_root = helper.getView(R.id.rl_item_root);    //整个Item的点击View

        TextView tv_supper_name = helper.getView(R.id.tv_supper_name);
        TextView tv_supper_status = helper.getView(R.id.tv_supper_status);     //食堂状态（营业，休息）
        TextView tv_supper_notice = helper.getView(R.id.tv_supper_notice);   //食堂公告
        TextView tv_supper_goods_count = helper.getView(R.id.tv_supper_goods_count); //脚标（已选商品数量）

        int currentSumCount = BuyCarMapUtils.getSpecCurBuyCarUtils(item.getShopId()).getBuyCar().getSumCount();

        //添加活动
        addNewActionTextView(helper, item.getActivityList());

        tv_supper_goods_count.setVisibility(currentSumCount == 0 ? View.GONE : View.VISIBLE);
        tv_supper_goods_count.setText("" + currentSumCount);
        tv_supper_name.setText(item.getShopName());
        tv_supper_notice.setText((item.getMinMoney() == null ? "" : "满" + NumberUtils.clearTailZero(item.getMinMoney()) + "元起送 | 配送费￥") + item.getDelyFee());

        if (item.getBussState() == StoreStalls.STORE_STALLS_CLOSED) {
            tv_supper_status.setText("休息中");
            tv_supper_status.setVisibility(View.VISIBLE);
            rl_item_root.setEnabled(false);
        } else {
            tv_supper_status.setVisibility(View.GONE);
            rl_item_root.setEnabled(true);
        }

        rl_item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TJApp.getIns().setStallsId(item.getShopId());
                Intent intent = new Intent(mContext, FoodActivity.class);
                intent.putExtra("store_stalls", item);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 新增优惠活动 View (重用)
     * @param list
     */
    public void addNewActionTextView(ViewHolder helper, List<StallsFullcut> list) {
        LinearLayout ll_action_1 = helper.getView(R.id.ll_action_1);
        ImageView iv_action_1 = helper.getView(R.id.iv_action_1);
        TextView tv_action_1 = helper.getView(R.id.tv_action_1);

        final ExpandableListView supper_activity_listview = helper.getView(R.id.supper_activity_listview);

        ll_action_1.setVisibility(View.GONE);
        tv_action_1.setTextColor(mContext.getResources().getColor(R.color.text_black_1));
        if (list == null || list.size() == 0) {
            supper_activity_listview.setVisibility(View.GONE);
            ll_action_1.setVisibility(View.VISIBLE);
            iv_action_1.setVisibility(View.GONE);
            tv_action_1.setText("暂无商家活动");
            tv_action_1.setTextColor(mContext.getResources().getColor(R.color.text_black_3));
        } else {
            ll_action_1.setVisibility(View.GONE);
            supper_activity_listview.setVisibility(View.VISIBLE);
            BuddyAdapter adapter = new BuddyAdapter(list.get(0).getActivityName(), list, mContext);
            supper_activity_listview.setAdapter(adapter);

            // 分组展开
            supper_activity_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    setListViewHeightBasedOnChildren(supper_activity_listview, 1);
                    return false;
                }
            });
            // 分组关闭
            supper_activity_listview.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    setListViewHeightBasedOnChildren(supper_activity_listview, 0);
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

}
