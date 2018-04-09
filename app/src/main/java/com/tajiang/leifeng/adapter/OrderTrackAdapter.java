package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.view.View;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.TraceInfo;

import java.util.List;

/**
 * Created by hexiuhui on 2017/8/2.
 */
public class OrderTrackAdapter extends CommonAdapter<TraceInfo> {
    private List<TraceInfo> mTrackList;

    public OrderTrackAdapter(Context context, List<TraceInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        mTrackList = mDatas;
    }

    @Override
    public void convert(ViewHolder helper, TraceInfo item) {
        helper.setText(R.id.track_time, item.getCreateDate());
        helper.setText(R.id.track_content, item.getEventContent());

        int position = helper.getPosition();
        if (position == 0) {
            // 设置图片
            helper.getView(R.id.item_track_icon).setBackgroundResource(R.drawable.order_completed);
            helper.getView(R.id.item_track_shangxian).setVisibility(View.INVISIBLE);
            helper.getView(R.id.item_track_xiaxian).setVisibility(View.VISIBLE);
            if (mTrackList.size() == 1) {
                helper.getView(R.id.item_track_xiaxian).setVisibility(View.INVISIBLE);
            }
        } else if (position == mTrackList.size() - 1) {
            // 设置图片
            helper.getView(R.id.item_track_icon).setBackgroundResource(R.drawable.order_round);

            helper.getView(R.id.item_track_shangxian).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_track_xiaxian).setVisibility(View.INVISIBLE);

        } else {
            helper.getView(R.id.item_track_shangxian).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_track_xiaxian).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_track_icon).setBackgroundResource(R.drawable.order_round);
        }
    }
}
