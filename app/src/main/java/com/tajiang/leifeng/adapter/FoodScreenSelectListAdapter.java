package com.tajiang.leifeng.adapter;

import android.content.Context;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Order;

import java.util.List;

/**
 * Created by 12154 on 2016/3/2.
 */
public class FoodScreenSelectListAdapter extends CommonAdapter<Order> {

    public FoodScreenSelectListAdapter(Context context, List<Order> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, Order item) {

        switch (helper.getPosition()) {
            case 0:
                helper.setImageResource(R.id.ivIconScreen, R.drawable.icon_screen_price);
                helper.setText(R.id.ivDecsScreen, "按价格最低");
                break;
            case 1:
                helper.setImageResource(R.id.ivIconScreen, R.drawable.icon_screen_hot);
                helper.setText(R.id.ivDecsScreen, "按销量最高");
                break;
        }
    }
}
