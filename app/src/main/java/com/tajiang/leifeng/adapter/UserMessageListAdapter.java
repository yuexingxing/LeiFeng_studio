package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.UserAdressEditActivity;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Message;
import com.tajiang.leifeng.model.UserAddress;

import java.util.List;

public class UserMessageListAdapter extends CommonAdapter<Message> {

    private Context mContext;

    public UserMessageListAdapter(Context context, List<Message> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        mContext = context;
    }

    @Override
    public void convert(final ViewHolder helper, final Message item) {

        helper.setText(R.id.tv_message_content, item.getContent());

        helper.setText(R.id.tv_message_time, item.getCreateTime());
    }

}
