package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.UserAdressActivity;
import com.tajiang.leifeng.activity.UserAdressEditActivity;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.UserAddress;

import java.util.List;

public class UserAdressListAdapter extends CommonAdapter<UserAddress> implements OnClickListener {

    private UserAdressActivity userAdressActivity;

    private OnSetDefaultAdressOnClickListener mOnSetDefaultAdressOnClickListener;

    public UserAdressListAdapter(Context context, List<UserAddress> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        userAdressActivity = (UserAdressActivity) context;
    }

    @Override
    public void convert(final ViewHolder helper, final UserAddress item) {

        helper.setText(R.id.tv_nameUserAdress, item.getName()
                + "    "
                + item.getSex()
                + "神        "
                + item.getPhone());
        helper.setText(R.id.tv_schoolUserAdress, item.getDetail() + " " + item.getAddress());
//        helper.setText(R.id.tv_apartmentUserAdress, item.getAddress());
//        helper.setText(R.id.tvSexUserAdress, item.getSex() + "神");
//        helper.setText(R.id.tv_phoneUserAdress, item.getMobPhone());

        if (item.getIsDefault()) {
            helper.setImageResource(R.id.ivSelectDefaultAdress, R.drawable.icon_radio_selected);
            ((TextView) helper.getView(R.id.tvSelectDefaultAdress)).setTextColor(getContext().getResources().getColor(R.color.com_green));
            ((TextView) helper.getView(R.id.tvSelectDefaultAdress)).setText("默认地址");
        } else {
            helper.setImageResource(R.id.ivSelectDefaultAdress, R.drawable.icon_radio_normal);
            ((TextView) helper.getView(R.id.tvSelectDefaultAdress)).setTextColor(getContext().getResources().getColor(R.color.text_black_1));
            ((TextView) helper.getView(R.id.tvSelectDefaultAdress)).setText("设为默认");
        }

        if (userAdressActivity.getIntent().getStringExtra("ChooseAdress") != null) {
            helper.getView(R.id.rect_chooseUserAdress).setTag(item);
            helper.getView(R.id.rect_chooseUserAdress).setOnClickListener(this);
            helper.getView(R.id.rectSetAdressDefault).setVisibility(View.GONE);

            if(item.getIsDefault()){
                helper.getView(R.id.ivIsDefalutAdress).setVisibility(View.VISIBLE);
            }else {
                helper.getView(R.id.ivIsDefalutAdress).setVisibility(View.GONE);
            }
        }else {
            helper.getView(R.id.rectSetAdressDefault).setVisibility(View.VISIBLE);
        }

        helper.getView(R.id.edit_address_layout).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserAdressEditActivity.class);
                intent.putExtra("USER_ADRESS", item);
                getContext().startActivity(intent);
            }
        });

        // 点击设置默认地址
        helper.getView(R.id.rectSetAdressDefault).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSetDefaultAdressOnClickListener != null) {
                    mOnSetDefaultAdressOnClickListener.onSetDefaultAdressOnClick(helper.getPosition());
                }
            }
        });

    }

    public OnSetDefaultAdressOnClickListener getOnSetDefaultAdressOnClickListener() {
        return mOnSetDefaultAdressOnClickListener;
    }

    public void setOnSetDefaultAdressOnClickListener(OnSetDefaultAdressOnClickListener onSetDefaultAdressOnClickListener) {
        mOnSetDefaultAdressOnClickListener = onSetDefaultAdressOnClickListener;
    }

    @Override
    public void onClick(View v) {

        UserAddress item = (UserAddress) v.getTag();

        switch (v.getId()) {
            case R.id.rect_chooseUserAdress:

                Intent intent2 = new Intent();
                intent2.putExtra("ADRESS", item);
                userAdressActivity.setResult(2, intent2);
                userAdressActivity.onBackPressed();

                break;
        }
    }

    public interface OnSetDefaultAdressOnClickListener {
        void onSetDefaultAdressOnClick(int postion);
    }

}
