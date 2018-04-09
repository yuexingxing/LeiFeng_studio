package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.UserWalletAddActivity;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Action;

import java.util.List;

/**
 * Created by 12154 on 2016/1/27.
 */
public class UserWalletPayActivityListAdapter extends CommonAdapter<Action> {

    private UserWalletAddActivity userWalletAddActivity;

    public UserWalletPayActivityListAdapter(Context context, List<Action> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);

        userWalletAddActivity = (UserWalletAddActivity) context;
    }

    @Override
    public void convert(ViewHolder helper, Action item) {

        TextView textView = helper.getView(R.id.tvDesPayActivity);
        textView.setText(item.getName());
        textView.setTextColor(getContext().getResources().getColor(R.color.text_black_1));

        helper.setImageResource(R.id.ivPayActivityBg, R.drawable.icon_pay_activity_normal);

        if (helper.getPosition() == userWalletAddActivity.getSelectPosition()) {
            helper.setImageResource(R.id.ivPayActivityBg, R.drawable.icon_pay_activity_pressed);

            textView.setTextColor(getContext().getResources().getColor(R.color.white));
        }

    }
}
