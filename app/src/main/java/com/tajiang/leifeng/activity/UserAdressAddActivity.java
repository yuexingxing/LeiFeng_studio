package com.tajiang.leifeng.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.Apartment;
import com.tajiang.leifeng.model.ApartmentZone;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.model.SchoolApartment;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.model.UserAddress;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.dialog.AnimLoadDialog;
import com.tajiang.leifeng.view.dialog.SelectApartmentDialog;

public class UserAdressAddActivity extends BaseActivity implements HttpResponseListener, SelectApartmentDialog.OnSelectApartmentListener {

    private int sex = User.MAN;

    private int isdefault = 0;

    private View rectManSexAdress;
    private View rectWomanSexAdress;

    private ImageView ivManAdressAdd;
    private ImageView ivWomanAdressAdd;

    private Editable nameUser;
    private Editable phoneUser;
    private Editable roomUser;

    private Apartment userApartment;
    private School userSchool;

    private EditText et_nameUserAddressAdd;
    private EditText et_phoneUserAddressAdd;
    private TextView et_apartmentUserAddressAdd;
    private EditText et_roomUserAddressAdd;

    private Button btn_addAdressUser;

    private AnimLoadDialog animLoadDialog;

    private SelectApartmentDialog selectApartmentDialog;

    @Override
    protected void initTopBar() {
        setTitle("添加我的地址");
        enableNavLeftImage();

        userSchool = TJApp.getIns().getSchool();

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_adress_add);
    }

    @Override
    protected void initView() {

        rectManSexAdress = findViewById(R.id.rectManSexAdress);
        rectWomanSexAdress = findViewById(R.id.rectWomanSexAdress);

        ivManAdressAdd = (ImageView) findViewById(R.id.ivManAdressAdd);
        ivWomanAdressAdd = (ImageView) findViewById(R.id.ivWomanAdressAdd);

        et_nameUserAddressAdd = (EditText) findViewById(R.id.et_nameUserAddressAdd);
        et_phoneUserAddressAdd = (EditText) findViewById(R.id.et_phoneUserAddressAdd);
        et_apartmentUserAddressAdd = (TextView) findViewById(R.id.et_apartmentUserAddressAdd);
        et_roomUserAddressAdd = (EditText) findViewById(R.id.et_roomUserAddressAdd);

        btn_addAdressUser = (Button) findViewById(R.id.btn_addAdressUser);
    }


    @Override
    protected void initListener() {
        super.initListener();

        et_apartmentUserAddressAdd.setOnClickListener(this);
        btn_addAdressUser.setOnClickListener(this);

        rectManSexAdress.setOnClickListener(this);
        rectWomanSexAdress.setOnClickListener(this);

    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
    }

    @Override
    protected void initDates() {
        super.initDates();
        if (SharedPreferencesUtils.contains(this, SharedPreferencesUtils.USER_APARTMENT_NAME)
                && SharedPreferencesUtils.contains(this, SharedPreferencesUtils.USER_APARTMENT)) {
            userApartment = TJApp.getIns().getUserApartment();
            et_apartmentUserAddressAdd.setText(TJApp.getIns().getSchool().getSchoolName()
                    + " "
                    + SharedPreferencesUtils.get(this, SharedPreferencesUtils.USER_APARTMENT_NAME, ""));
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {

            case R.id.rectManSexAdress:
                setSex(User.MAN);
                break;

            case R.id.rectWomanSexAdress:
                setSex(User.WOMAN);
                break;
            case R.id.et_apartmentUserAddressAdd:
                // 学校：宿舍列表
                TJHttpUtil.getInstance(this).getApartmentBySchoolId(userSchool.getSchoolId());
                break;
            case R.id.btn_addAdressUser:
                // 点击了确定
                if (checkAdressInfor()) {
                    TJHttpUtil.getInstance(this).doAddAdress(nameUser.toString().trim(), sex, userApartment.getApartmentId(), roomUser.toString().trim(), phoneUser.toString().trim(), isdefault);
                }
                break;
        }

    }

    private void setSex(int sex) {

        switch (sex) {
            case User.MAN:
                ivManAdressAdd.setImageResource(R.drawable.icon_radio_selected);
                ivWomanAdressAdd.setImageResource(R.drawable.icon_radio_normal);
                break;
            case User.WOMAN:
                ivManAdressAdd.setImageResource(R.drawable.icon_radio_normal);
                ivWomanAdressAdd.setImageResource(R.drawable.icon_radio_selected);
                break;
        }

        this.sex = sex;

    }

    private boolean checkAdressInfor() {

        nameUser = et_nameUserAddressAdd.getText();
        phoneUser = et_phoneUserAddressAdd.getText();
        roomUser = et_roomUserAddressAdd.getText();


        if (TextUtils.isEmpty(nameUser) || TextUtils.isEmpty(nameUser.toString().trim())) {
            ToastUtils.showShort("用户昵称不能为空");
            return false;
        } else if (sex == User.NONE) {
            ToastUtils.showShort("请选择您的性别");
            return false;
        } else if (TextUtils.isEmpty(phoneUser) || TextUtils.isEmpty(phoneUser.toString().trim())) {
            ToastUtils.showShort("请填写手机号");
            return false;
        } else if (phoneUser.toString().trim().length() < 11) {
            ToastUtils.showShort("手机号不足11位");
            return false;
        } else if (userApartment == null || userSchool == null) {
            ToastUtils.showShort("请选择您的宿舍");
            return false;
        } else if (TextUtils.isEmpty(roomUser) || TextUtils.isEmpty(roomUser.toString().trim())) {
            ToastUtils.showShort("请填写详细的寝室号");
            return false;
        }

        return true;

    }

    @Override
    public void onSelectApartment(SchoolApartment schoolApartment, ApartmentZone apartmentZone, Apartment apartment) {
        userApartment = apartment;
        et_apartmentUserAddressAdd.setText(schoolApartment.getName() + " " + apartmentZone.getName() + " " + apartment.getApartmentName());
    }

    @Override
    public void onStart(int requestTag) {
        animLoadDialog = new AnimLoadDialog(this);
        animLoadDialog.show();
    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_GET_SCHOOL_APARTMENT_LIST:

                SchoolApartment schoolListResult = (SchoolApartment) response.getData();

                if (selectApartmentDialog == null) {
                    selectApartmentDialog = new SelectApartmentDialog(this, schoolListResult);
                    selectApartmentDialog.setOnSelectApartmentListener(this);
                }

                selectApartmentDialog.show();

                break;
            case TJRequestTag.TAG_USER_ADDRESS_ADD:

                UserAddress userAddressResult = (UserAddress) response.getData();

                ToastUtils.showShort("添加地址成功");
                UserUtils.getInstance().doUserRefreshNetUserInfor();

                Intent intent = new Intent();
                intent.putExtra("AdressAdd", userAddressResult);
                setResult(1, intent);
                onBackPressed();

                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        animLoadDialog.dismiss();
    }

    @Override
    public void onFinish(int requestTag) {
        animLoadDialog.dismiss();
    }
}
