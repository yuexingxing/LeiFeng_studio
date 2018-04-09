package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.UserAdressListAdapter;
import com.tajiang.leifeng.adapter.UserMessageListAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Message;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.UserAddress;
import com.tajiang.leifeng.model.UserAddressListEntity;
import com.tajiang.leifeng.utils.DensityUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.swipemenulistview.SwipeMenu;
import com.tajiang.leifeng.view.swipemenulistview.SwipeMenuCreator;
import com.tajiang.leifeng.view.swipemenulistview.SwipeMenuItem;
import com.tajiang.leifeng.view.swipemenulistview.SwipeMenuListView;
import com.tajiang.leifeng.view.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends BaseActivity implements HttpResponseListener, OnMenuItemClickListener {

    private View rect_addressEmpty;

    private SwipeMenuListView lv_message;

    private UserMessageListAdapter userMessageListAdapter;

    private List<Message> userMessageList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        TJHttpUtil.getInstance(this).getMessageList(1, 100);
    }

    @Override
    protected void initTopBar() {
        setTitle("通知中心");
        enableNavLeftImage();
        enableNavRightText("一键已读");
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_message);
    }

    @Override
    protected void initView() {
        rect_addressEmpty = findViewById(R.id.rect_addressEmpty);

        lv_message = (SwipeMenuListView) findViewById(R.id.lv_message);
        lv_message.setEmptyView(rect_addressEmpty);
        lv_message.setOnMenuItemClickListener(this);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(DensityUtils.dp2px(MessageListActivity.this, 90));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };

        lv_message.setMenuCreator(creator);

    }

    @Override
    protected void initAdapter() {
        userMessageListAdapter = new UserMessageListAdapter(this, userMessageList, R.layout.item_list_user_message);
        lv_message.setAdapter(userMessageListAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected void initDates() {
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//        TJHttpUtil.getInstance(this).userDoAdressDelete(userAddressList.get(position).getAddressId());
//        userAddressList.remove(position);
//        userAdressListAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public void onStart(int requestTag) {
        showLoading();
    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_ADRESS_DELETE:
                    ToastUtils.showShort("删除成功");
                    UserUtils.getInstance().doUserRefreshNetUserInfor();

                break;

            case TJRequestTag.TAG_USER_MESSAGE:
                Pager<Message> pager = (Pager<Message>) response.getData();

                List<Message> messList = pager.getList();

                userMessageList.clear();
                userMessageList.addAll(messList);
                userMessageListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        dismissLoading();
    }

    @Override
    public void onFinish(int requestTag) {
        dismissLoading();
    }
}
