package com.tajiang.leifeng.adapter;

import android.content.Context;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.PredepositLog;
import com.tajiang.leifeng.utils.DateUtils;

import java.util.List;

/**
 * Created by 12154 on 2016/1/15.
 */
public class UserWalletTranscationDetailListAdapter extends CommonAdapter<PredepositLog> {

    public UserWalletTranscationDetailListAdapter(Context context, List<PredepositLog> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, PredepositLog item) {

        switch (item.getType()) {
            case 1:
                helper.setText(R.id.tvTypePayLog, "支出");
                helper.setText(R.id.tvNumPayLog, "-" + item.getAvAmount());
                break;
            case 2:
                helper.setText(R.id.tvTypePayLog, "充值");
                helper.setText(R.id.tvNumPayLog, "+" + item.getAvAmount());
                break;
            case 3:
                helper.setText(R.id.tvTypePayLog, "退款");
                helper.setText(R.id.tvNumPayLog, "+" + item.getAvAmount());
                break;
        }

        helper.setText(R.id.tvTimePayLog, DateUtils.getDate(item.getAddTime(), "yyyy-MM-dd HH:mm"));

    }
}
