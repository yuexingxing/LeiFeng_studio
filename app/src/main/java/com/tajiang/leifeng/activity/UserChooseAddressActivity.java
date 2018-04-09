package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.UserChooseAdressListAdapter;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.model.UserAddress;
import com.tajiang.leifeng.model.UserAddressListEntity;
import com.tajiang.leifeng.utils.DensityUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.swipemenulistview.SwipeMenu;
import com.tajiang.leifeng.view.swipemenulistview.SwipeMenuCreator;
import com.tajiang.leifeng.view.swipemenulistview.SwipeMenuItem;
import com.tajiang.leifeng.view.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admins on 2017/3/27.
 */

public class UserChooseAddressActivity extends BaseActivity implements HttpResponseListener, SwipeMenuListView.OnMenuItemClickListener, AdapterView.OnItemClickListener, UserChooseAdressListAdapter.OnSetDefaultAdressOnClickListener {
    private View rect_addressEmpty;

    private SwipeMenuListView lv_adressUser;

    private UserChooseAdressListAdapter userAdressListAdapter;

    private View view_addUserAdress;

    private List<UserAddress> userAddressList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        TJHttpUtil.getInstance(this).getAdressList(1, 100);
    }

    @Override
    protected void initTopBar() {
        setTitle("我的地址");
        enableNavLeftImage();
        enableNavRightText("管理");
    }

    @Override
    protected void onClickNavRight() {
        super.onClickNavRight();
        intent2Activity(UserAdressActivity.class);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_adress);
    }

    @Override
    protected void initView() {
        rect_addressEmpty = findViewById(R.id.rect_addressEmpty);

        view_addUserAdress = findViewById(R.id.view_addUserAdress);
        lv_adressUser = (SwipeMenuListView) findViewById(R.id.lv_adressUser);
        lv_adressUser.setEmptyView(rect_addressEmpty);
        lv_adressUser.setOnMenuItemClickListener(this);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(DensityUtils.dp2px(UserChooseAddressActivity.this, 90));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };

        lv_adressUser.setMenuCreator(creator);

    }

    @Override
    protected void initAdapter() {
        userAdressListAdapter = new UserChooseAdressListAdapter(this, userAddressList, R.layout.item_list_user_adress);
        lv_adressUser.setAdapter(userAdressListAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();

        view_addUserAdress.setOnClickListener(this);
        lv_adressUser.setOnItemClickListener(this);

    }

    @Override
    protected void initDates() {
        if (getIntent().getStringExtra("ChooseAdress") != null) {
            view_addUserAdress.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.view_addUserAdress:
                Intent intent = new Intent(this, UserAdressAddActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
        TJHttpUtil.getInstance(this).userDoAdressDelete(userAddressList.get(position).getAddressId());
        userAddressList.remove(position);
        userAdressListAdapter.notifyDataSetChanged();
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        UserAddress userAddress = (UserAddress) parent.getItemAtPosition(position);

        Intent intent = new Intent(getContext(), UserAdressEditActivity.class);
        intent.putExtra("USER_ADRESS", userAddress);
        getContext().startActivity(intent);

    }

    @Override
    public void onSetDefaultAdressOnClick(int postion) {
        UserAddress userAddress = userAddressList.get(postion);
        TJHttpUtil.getInstance(this).doModifyAdress(userAddress.getAddress(), userAddress.getAddressId(), userAddress.getApartmentId(), 1, userAddress.getName(), userAddress.getPhone(), userAddress.getSex().equals("男") ? User.MAN : User.WOMAN);
    }

    @Override
    public void onStart(int requestTag) {
        showLoading();
    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_USER_ADRESS_DELETE:

                if ((Boolean) response.getData()) {
                    ToastUtils.showShort("删除成功");
                    UserUtils.getInstance().doUserRefreshNetUserInfor();
                } else {
                    ToastUtils.showShort("删除失败");
                }

                break;

            case TJRequestTag.TAG_USER_ADRESS:

                UserAddressListEntity userAddressListResult = (UserAddressListEntity) response.getData();
                userAddressList.clear();
                userAddressList.addAll(userAddressListResult.getList());
                userAdressListAdapter = new UserChooseAdressListAdapter(this, userAddressList, R.layout.item_list_user_adress);
                userAdressListAdapter.setOnSetDefaultAdressOnClickListener(this);
                lv_adressUser.setAdapter(userAdressListAdapter);

                break;

            case TJRequestTag.TAG_USER_ADDRESS_MODIFY:
                boolean isTrue = (Boolean) response.getData();

                if (isTrue) {
                    ToastUtils.showShort("修改地址成功");
                } else {
                    ToastUtils.showShort("修改地址失败");
                }

                UserUtils.getInstance().doUserRefreshNetUserInfor();
                TJHttpUtil.getInstance(this).getAdressList(1, 100);

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
