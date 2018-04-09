package com.tajiang.leifeng.activity;

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
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.dialog.SelectApartmentDialog;

public class UserAdressEditActivity extends BaseActivity implements HttpResponseListener, SelectApartmentDialog.OnSelectApartmentListener {

    private int sex = User.NONE;

    private String nameUser;
    private String phoneUser;
    private String roomUser;

    private int apartmentId = 0;

    private View rectManSexAdress;
    private View rectWomanSexAdress;

    private TextView tv_apartmentUserAddressAdd;

    private EditText et_nameUserAddressAdd;
    private EditText et_phoneUserAddressAdd;
    private EditText et_roomUserAddressEdit;

    private ImageView ivManAdressAdd;
    private ImageView ivWomanAdressAdd;

    private Button btn_addAdressUser;

    private UserAddress userAddress;

    private SelectApartmentDialog selectApartmentDialog;
    private School userSchool;

    @Override
    protected void initTopBar() {
        setTitle("编辑我的地址");
        enableNavLeftImage();

        userSchool = TJApp.getIns().getSchool();
        userAddress = (UserAddress) getIntent().getSerializableExtra("USER_ADRESS");


    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_user_adress_edit);
    }

    @Override
    protected void initView() {

        rectManSexAdress = findViewById(R.id.rectManSexAdress);
        rectWomanSexAdress = findViewById(R.id.rectWomanSexAdress);

        et_nameUserAddressAdd = (EditText) findViewById(R.id.et_nameUserAddressAdd);
        et_phoneUserAddressAdd = (EditText) findViewById(R.id.et_phoneUserAddressAdd);
        tv_apartmentUserAddressAdd = (TextView) findViewById(R.id.tv_apartmentUserAddressAdd);
        et_roomUserAddressEdit = (EditText) findViewById(R.id.et_roomUserAddressAdd);

        ivManAdressAdd = (ImageView) findViewById(R.id.ivManAdressAdd);
        ivWomanAdressAdd = (ImageView) findViewById(R.id.ivWomanAdressAdd);

        btn_addAdressUser = (Button) findViewById(R.id.btn_addAdressUser);
    }

    @Override
    protected void initAdapter() {

    }

    @Override
    protected void initListener() {
        super.initListener();

        btn_addAdressUser.setOnClickListener(this);
        tv_apartmentUserAddressAdd.setOnClickListener(this);

        rectManSexAdress.setOnClickListener(this);
        rectWomanSexAdress.setOnClickListener(this);

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

            case R.id.tv_apartmentUserAddressAdd:
//			 学校：宿舍列表
                // 学校：宿舍列表
                TJHttpUtil.getInstance(this).getApartmentBySchoolId(userSchool.getSchoolId());
                break;
            case R.id.btn_addAdressUser:
                // 点击了确定
                if (checkAdressInfor()) {
                    TJHttpUtil.getInstance(this).doModifyAdress(roomUser, userAddress.getAddressId(), apartmentId, userAddress.getIsDefault() ? 1 : 0, nameUser, phoneUser, sex);
                    UserUtils.getInstance().doUserRefreshNetUserInfor();
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

    @Override
    protected void initDates() {
        if (userAddress != null) {
            nameUser = userAddress.getName();
            sex = userAddress.getSex().equals("男") ? User.MAN : User.WOMAN;
            phoneUser = userAddress.getPhone();
            apartmentId = userAddress.getApartmentId();
            roomUser = userAddress.getAddress();

            et_nameUserAddressAdd.setText(nameUser);
            et_phoneUserAddressAdd.setText(phoneUser);
            tv_apartmentUserAddressAdd.setText(userAddress.getDetail());
            et_roomUserAddressEdit.setText(userAddress.getAddress());
            setSex(sex);

        }
    }

    private boolean checkAdressInfor() {

        nameUser = et_nameUserAddressAdd.getText().toString().trim();
        phoneUser = et_phoneUserAddressAdd.getText().toString().trim();
        roomUser = et_roomUserAddressEdit.getText().toString().trim();

        if (TextUtils.isEmpty(nameUser)) {
            ToastUtils.showShort("用户昵称不能为空");
            return false;
        } else if (sex == User.NONE) {
            ToastUtils.showShort("请选择您的性别");
            return false;
        } else if (apartmentId == 0) {
            ToastUtils.showShort("请选择您的宿舍");
            return false;
        } else if (TextUtils.isEmpty(roomUser)) {
            ToastUtils.showShort("请填写详细的寝室号");
            return false;
        }

        return true;

    }

    @Override
    public void onSelectApartment(SchoolApartment schoolApartment, ApartmentZone apartmentZone, Apartment apartment) {
        apartmentId = apartment.getApartmentId();
        tv_apartmentUserAddressAdd.setText(schoolApartment.getName() + " " + apartmentZone.getName() + " " + apartment.getApartmentName());
    }

    @Override
    public void onStart(int requestTag) {

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
            case TJRequestTag.TAG_USER_ADDRESS_MODIFY:
                ToastUtils.showShort("修改地址成功");
                finish();

                UserUtils.getInstance().doUserRefreshNetUserInfor();
                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

    }

    @Override
    public void onFinish(int requestTag) {

    }
}
