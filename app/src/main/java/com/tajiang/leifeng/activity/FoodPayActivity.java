package com.tajiang.leifeng.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.FoodPayActionAdapter;
import com.tajiang.leifeng.adapter.FoodPayListAdapter;
import com.tajiang.leifeng.adapter.FoodPayListAdapter.OnGoodNumChangeListener;
import com.tajiang.leifeng.adapter.UserCouponListAdapter;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.GoodSet;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.OrderActivity;
import com.tajiang.leifeng.model.OrderPre;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.StallsFullcut;
import com.tajiang.leifeng.model.Store;
import com.tajiang.leifeng.model.StoreStalls;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.model.UserAddress;
import com.tajiang.leifeng.model.Voucher;
import com.tajiang.leifeng.model.VoucherList;
import com.tajiang.leifeng.model.WorkTime;
import com.tajiang.leifeng.model.WorkTimeList;
import com.tajiang.leifeng.pay.TJPayUtils;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.BuyCarUtils;
import com.tajiang.leifeng.utils.DateUtils;
import com.tajiang.leifeng.utils.GsonObjUtil;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.NumberUtils;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.utils.WeChatUtils;
import com.tajiang.leifeng.view.ClickButton;
import com.tajiang.leifeng.view.PullListView;
import com.tajiang.leifeng.view.dialog.CDialog;
import com.tajiang.leifeng.view.dialog.LoadingDialog;
import com.tajiang.leifeng.view.dialog.MarkerDialog;
import com.tajiang.leifeng.view.dialog.OrderReminderDialog;
import com.tajiang.leifeng.view.dialog.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodPayActivity extends BaseActivity implements  OnGoodNumChangeListener, HttpResponseListener, OnItemClickListener {

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private final int CODE_ADD_ADRESS = 1;
    private final int CODE_CHOOSE_ADRESS = 2;
    private final int CODE_CHOOSE_COUPON = 3;

    private List<String> labelList;
    private String stallsId;  //商家id
    private int voucherCount = 0; //筛选可用的红包（可用满减红包）

    // 设置默认订单类型：送给我
    private int typeOrder = Order.TYPE_PASS_ME;

    private View rect_couponFoodPay;
    private View rect_adressFoodPay;
    private View rect_addMarkerFoodPay;

    private View view_couponFoodPay;

    private View rectFirstEatFoodPay;

    private View rect_BoxFeeFoodPay; //打包服务费

    private View rect_shoppingFeeFoodPay; //配送费

    private TextView tv_BoxFeeFoodPay;   //打包服务费TextView
    private TextView tv_coupon_count;
    private TextView tv_serviceFeeFoodPay;
    private TextView tv_sumPriceFoodPay;
    private TextView tv_discountCutFoodPay;
    private TextView tv_markerOrder;
    private TextView tvHasPerssiomFoodPay;

    private TextView tv_userFoodPay;
    private TextView tv_adressFoodPay;
    private TextView tv_phoneFoodPay;
    private TextView tv_priceCouponFoodPay;

    private TextView tv_action_description;

    private TextView tvNameSchoolFoodPay;

    private TextView tv_sumPriceFoodPayDetail;

    private TextView tvNumFoodDetail;

    private ClickButton btn_payOrder;

    private PullListView plv_orderDetailFoodPay;

    private ArrayList<Voucher> voucherList = new ArrayList<>();

    private List<String> arriveTime = new ArrayList<>();

    /**
     * 当前选中的代金券
     */
    private Voucher voucher;
    private UserAddress mUserAddress;

    /**
     * 后台结算返回的Order
     */
    Order orderPay;
    /**
     * 支付方式默认走支付宝
     */
    private int currentPayType = TJPayUtils.PAY_TAPE_ALIPAY;

    private EditText et_phoneAddDialog;

    private CDialog coupon;
    private CDialog addPhoneDialog;
    private MarkerDialog markerDialog;

    private OptionsPickerView pvOptions;

    private boolean hasShowAddPhoneDialog = false;
    private boolean have_limit_action = false;

    private double serverCash = 0;

    private RecyclerView rv_order_action;
    private FoodPayActionAdapter foodPayActionAdapter;
    private String estimatedTimev2 = null;

    private StoreStalls storeStalls;

    private ArrayList<String> options1Items = new ArrayList<>();

//    private LoadingDialog loadingDialog;
    private ProgressDialog loadingDialog;

    @Override
    protected void initTopBar() {
        storeStalls = getIntent().getParcelableExtra("storeStalls");
        have_limit_action = getIntent().getBooleanExtra("have_limit_action", false);
        stallsId = getIntent().getStringExtra("stalls_id");
        labelList = getIntent().getStringArrayListExtra("labelList");
        setTitle("送给我");
        enableNavLeftImage();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_food_pay);
    }

    @Override
    protected void initView() {

        view_couponFoodPay = findViewByID(R.id.view_couponFoodPay);
        rect_BoxFeeFoodPay = findViewByID(R.id.rect_BoxFeeFoodPay);

        rv_order_action = findViewByID(R.id.rv_order_action);
        rect_addMarkerFoodPay = findViewByID(R.id.rect_addMarkerFoodPay);
        rect_couponFoodPay = findViewByID(R.id.rect_couponFoodPay);
        rect_adressFoodPay = findViewByID(R.id.rect_adressFoodPay);
        rect_shoppingFeeFoodPay = findViewByID(R.id.rect_shoppingFeeFoodPay);
        rectFirstEatFoodPay = findViewByID(R.id.rectFirstEatFoodPay);

        tv_BoxFeeFoodPay = findViewByID(R.id.tv_BoxFeeFoodPay);
        tv_action_description = findViewByID(R.id.tv_action_description);
        tv_coupon_count = findViewByID(R.id.tv_coupon_count);
        tv_serviceFeeFoodPay = findViewByID(R.id.tv_serviceFeeFoodPay);
        tv_sumPriceFoodPay = findViewByID(R.id.tv_sumPriceFoodPay);
        tv_discountCutFoodPay = findViewByID(R.id.tv_discountCutFoodPay);
        tv_adressFoodPay = findViewByID(R.id.tv_adressFoodPay);
        tv_phoneFoodPay = findViewByID(R.id.tv_phoneFoodPay);
        tv_priceCouponFoodPay = findViewByID(R.id.tv_priceCouponFoodPay);
        tv_markerOrder = findViewByID(R.id.tv_markerOrder);
        tv_sumPriceFoodPayDetail = findViewByID(R.id.tv_sumPriceFoodPayDetail);
        tv_userFoodPay = findViewByID(R.id.tv_userFoodPay);

        tvNameSchoolFoodPay = findViewByID(R.id.tvNameSchoolFoodPay);
        tvHasPerssiomFoodPay = findViewByID(R.id.tvHasPerssiomFoodPay);

        tvNumFoodDetail = findViewByID(R.id.tvNumFoodDetail);

        btn_payOrder = findViewByID(R.id.btn_payOrder);

        plv_orderDetailFoodPay = findViewByID(R.id.plv_orderDetailFoodPay);

        btn_payOrder.setButtonEnable(false);

//        //是否有特价菜
//        if (!BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().haveSpecialOffer()) {
//            rect_action_discount.setVisibility(View.GONE);
//        }

        //是否有满减活动
//        if (have_limit_action == false) {
//            rect_limit_discount.setVisibility(View.GONE);
//        }

    }

    @Override
    protected void initListener() {
        view_couponFoodPay.setOnClickListener(this);

        rect_adressFoodPay.setOnClickListener(this);
        rect_addMarkerFoodPay.setOnClickListener(this);
        rectFirstEatFoodPay.setOnClickListener(this);

        btn_payOrder.setOnClickListener(this);

        //TODO.........
//        if (BuyCarUtils.getIns().getBuyCarGoodSumCount() == 0) {
//        if (BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() == 0) {
//            btn_payOrder.setClickable(false);
//        }
    }

    @Override
    protected void initAdapter() {
        foodPayActionAdapter = new FoodPayActionAdapter(getContext(), new ArrayList<StallsFullcut>());
        rv_order_action.setLayoutManager(new LinearLayoutManager(getContext()));

        FoodPayListAdapter foodPayListAdapter = new FoodPayListAdapter(this, BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList(), R.layout.item_list_food_pay_mess);
        foodPayListAdapter.setCountNumberUnit("份");
        foodPayListAdapter.setOnGoodNumChangeListener(this);
        rv_order_action.setAdapter(foodPayActionAdapter);
        plv_orderDetailFoodPay.setAdapter(foodPayListAdapter);
    }

    @Override
    protected void initDates() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
//        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        // 初始化地址信息
        initAdress();
        // 初始化订单价格（商品类别下边、底部）
        tv_sumPriceFoodPay.setText(decimalFormat.format(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumPrice()) + "");
        tv_sumPriceFoodPayDetail.setText("￥" + decimalFormat.format(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumPrice()) + "");
        // 优惠信息（商品类别下边、底部）
        tv_action_description.setVisibility(View.GONE);
        rect_couponFoodPay.setVisibility(View.GONE);
        //TODO......
//        rectCouponFoodPayDetail.setVisibility(View.GONE);

        // 设置学校名称
        tvNameSchoolFoodPay.setText(storeStalls.getShopName());

        // 初始化备注信息
        initMarkText(tv_markerOrder);

        // 食物份数
        tvNumFoodDetail.setText("共" + BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() + "份美食");

        // 请求-可用代金券
//        String json = GsonObjUtil.getGsonInstance().toJson(BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList());

        setTlementJson();
        // 请求-配送时间
        TJHttpUtil.getInstance(this).getEstimatedTime(stallsId);
    }

    /**
     * 初始化备注
     */
    private void initMarkText(TextView editText) {
        if (BuyCarMapUtils.getCurBuyCarUtils().getAllMark().size() != 0) {
            editText.setText(BuyCarMapUtils.getCurBuyCarUtils().getMarkerText());
        }
    }

    private void setTlementJson() {
        String goodsJson = toGoodsJson();

        try {
            TJHttpUtil.getInstance(this).setTlement(URLEncoder.encode(goodsJson, "UTF-8"), stallsId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private String toGoodsJson () {
        List<GoodSet> goodSets = BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray();
            JSONObject jsonObject = null;
            for (int i = 0; i < goodSets.size(); i++) {
                GoodSet goodSet = goodSets.get(i);
                jsonObject = new JSONObject();
                jsonObject.put("num", goodSet.getNum());
                jsonObject.put("cartId", goodSet.getGoods().getGoodsId());
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }

    //TODO....Go To Pay.....
    private void doPayAfterRequest() {

        int addressId = mUserAddress != null ? mUserAddress.getAddressId() : 0;

        if (addressId == 0) {                                 //送餐地址校验
            showToast("请添加送餐地址");
        } else {
            placeorder();

//            if (currentPayType == TJPayUtils.PAY_TAPE_WELLAT) {  // 钱包支付钱，判断余额够不够
//
//                double sumOrder = Double.valueOf(tv_sumPriceFoodPay.getText().toString());
//                double sumWallet = UserUtils.getInstance().getUser().getAvailablePredeposit();
//
//                if (sumWallet >= sumOrder) {
//                    TJHttpUtil.getInstance(FoodPayActivity.this).userWalletPayPswIsAuthReq();
//                } else {
//                    showToast("雷锋钱包余额不足");
//                }
//            } else {
//                doPay();//判断所选收货地址中的送餐楼栋是否开通配送}
//            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_payOrder:
                doPayAfterRequest();
                break;
            case R.id.rect_addMarkerFoodPay:
                /*把获取的所有标签先储存*/
                BuyCarMapUtils.getCurBuyCarUtils().setAllMark(labelList);

                markerDialog = new MarkerDialog(this, labelList, tv_markerOrder, BuyCarMapUtils.getCurBuyCarUtils().getMarkList(), BuyCarMapUtils.getCurBuyCarUtils().getMarkInput());
                markerDialog.show();
                break;
            case R.id.view_couponFoodPay:
                showCouponDialog();
                break;
            case R.id.rect_adressFoodPay:

//                if (UserUtils.getInstance().getUserAddress() == null) {
//                    Intent intent = new Intent(this, UserAdressAddActivity.class);
//                    startActivityForResult(intent, CODE_ADD_ADRESS);
//                } else {
//                    Intent intent = new Intent(this, UserAdressActivity.class);
                    Intent intent = new Intent(this, UserChooseAddressActivity.class);
                    intent.putExtra("ChooseAdress", "ChooseAdress");
                    startActivityForResult(intent, CODE_CHOOSE_ADRESS);
//                }

                break;
            case R.id.btn_submitPhoneAddDialog:

                String phone = et_phoneAddDialog.getText().toString().trim();

                if (checkPhone(phone)) {
                    TJHttpUtil.getInstance(this).doOrderBindPhone(phone);
                }

                break;
            case R.id.rectFirstEatFoodPay:
                showSelectTime();
                break;
        }

    }

    private boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            showToast("号码不能为空");
            return false;
        } else if (phone.length() != 11) {
            showToast("号码不足11位");
            return false;
        }

        return true;
    }

    /*
    * 用户下单，获取订单号
    * */
    private void placeorder () {
        // 获取优惠券和地址的Id，有则取值，无则置Null
        String voucherId = voucher != null ? voucher.getId() : null;
        int adressId = mUserAddress != null ? mUserAddress.getAddressId() : 0;

        if (!hasShowAddPhoneDialog) {
            if (typeOrder != Order.TYPE_PASS_ME) {
                if (TextUtils.isEmpty(UserUtils.getInstance().getUser().getPhone())) {
                    showAddPhoneDialog();
                    return;
                }
            }
        }

        if (currentPayType == TJPayUtils.PAY_TAPE_WEICHATPAY) {
            if (!WeChatUtils.isWXAppInstalledAndSupported(this)) {
                return;
            }
        }

        String goodsJson = toGoodsJson();  //菜品列表
        String buyerRemark = BuyCarMapUtils.getCurBuyCarUtils().getMarkerText();  //备注

        String[] estime = estimatedTimev2.split("-");
        String startTime = DateUtils.getCurrentDate() + " " + estime[0];
        String endTime = DateUtils.getCurrentDate() + " " + estime[1];

        try {
            TJHttpUtil.getInstance(this).placeorder(null, buyerRemark, URLEncoder.encode(goodsJson, "UTF-8"), null, endTime, 0, startTime, stallsId, adressId, voucherId != null?Integer.parseInt(voucherId): null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 进行支付操作
     */
    private void doPay(String orderId) {
//        OrderPre orderPre = new OrderPre(currentPayType,
//                typeOrder,
//                adressId,
//                voucherId,
//                BuyCarMapUtils.getCurBuyCarUtils().getMarkerText(),
//                estimatedTimev2,
//                tv_sumPriceFoodPay.getText().toString(),
//                BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList());

        Intent intent = new Intent(this, OrderPayActivity.class);
        intent.putExtra("ORDER_ID", orderId);
        intent.putExtra("sumPrice", tv_sumPriceFoodPay.getText().toString());
        intent.putExtra("ORDER_TYPE", OrderPayActivity.ORDER_TYPE_PAY_NOW);
        startActivity(intent);

    }

    private void initAdress() {

        rect_adressFoodPay.setVisibility(View.VISIBLE);
        rectFirstEatFoodPay.setVisibility(View.VISIBLE);

        UserAddress userAddress = UserUtils.getInstance().getUserAddress();

        // 有地址的情况
        if (userAddress != null) {
            int selectedApartId = (int) SharedPreferencesUtils.get(this, SharedPreferencesUtils.USER_APARTMENT_ID, -1);
            if (userAddress.getApartmentId() != selectedApartId) {
                ToastUtils.showShort("默认地址和食堂配送地址不一致！");
            }
            tv_adressFoodPay.setVisibility(View.VISIBLE);
            setAddress(userAddress);
            mUserAddress = userAddress;
        }

        // 没有地址的情况
        else {
            tv_adressFoodPay.setVisibility(View.GONE);
            tv_adressFoodPay.setText("请先添加一个地址");
        }

    }

    @Override
    public void onGoodNumChange(int postion, int num) {
//        btn_payOrder.setClickable(BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() != 0);
        setSumPriceNeedPay();
        tvNumFoodDetail.setText("共" + BuyCarMapUtils.getCurBuyCarUtils().getBuyCarGoodSumCount() + "份美食");
    }

    /**
     * 默认展示获取的第一个红包优惠卷
     * 筛选可用的红包（满减红包）
     */
    private Voucher filterVoucherList(List<Voucher> vouchers) {
        this.voucherCount = 0;
        int voucherSum = 0;
        Voucher defVoucher = null;

        BigDecimal  sumPrice  =  BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumPrice();//加上运费

        for (Voucher voucher : vouchers) {
            if (voucher.getState() == Voucher.STATE_USABLE) {
                this.voucherCount += 1;
                if (voucher.getLimitPrice().doubleValue() == 0 || sumPrice.compareTo(voucher.getLimitPrice()) >= 0) {
                    if (defVoucher == null) {
                        defVoucher = voucher;
                    }
                    voucherSum += 1;
                }
            }
        }

        if (voucherSum > 0) {
            tv_coupon_count.setText(voucherSum + "张优惠券可用");
        } else {
            tv_coupon_count.setText("优惠券");
        }
        return defVoucher;
    }

    /**
     * 过滤 返回的红包优惠券列表
     * 有特价菜的情况下只能使用抵用券，（过滤掉满减的红包）
     * @param voucherListResult2
     */
    private void filterUsableVoucherList(List<Voucher> voucherListResult2) {
        voucherList.clear();
        if (BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().haveSpecialOffer()) {
            for (Voucher voucher : voucherListResult2) {
                if (voucher.getLimitPrice().doubleValue() == 0) {
                    voucherList.add(voucher);
                }
            }
        } else {
            voucherList.addAll(voucherListResult2);
        }
    }

    /**
     * 初始化预计到达时间
     */
    private void initEstimatedTime(List<String> arriveTimeResult) {

        arriveTime.addAll(arriveTimeResult);

        // 有预计送达时间
        if (arriveTime.size() > 0) {
            estimatedTimev2 = arriveTime.get(0);
        }

        // 没有设置送达时间
        else {
            estimatedTimev2 = "尽快送达";
        }

        tvHasPerssiomFoodPay.setText(estimatedTimev2);
    }

    /**
     * 设置优惠券
     */
    private void setVoucher(Voucher voucher) {
        if (voucher == null) {
//            rect_couponFoodPay.setVisibility(View.GONE);
//            rectCouponFoodPayDetail.setVisibility(View.GONE);
            tv_priceCouponFoodPay.setText("暂无可用优惠券");

            if (this.voucherCount <= 0) {   //如果有可以使用的红包(未使用过但达不到满减要求的，灰色状态)，也可以点击查看
                view_couponFoodPay.setClickable(false);
            }
        } else {
//            rect_couponFoodPay.setVisibility(View.VISIBLE);
//            rectCouponFoodPayDetail.setVisibility(View.VISIBLE);
//            tv_couponCutFoodPay.setText(voucher.getPrice() + "");
//            tv_couponCutFoodPayDetail.setText("-￥" + voucher.getPrice() + "");
            tv_priceCouponFoodPay.setText(voucher.getFaceValue() + "元");
            view_couponFoodPay.setClickable(true);
            /**
             * 在有特价菜的情况下，优惠价不可使用
             */
//            if (BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().haveSpecialOffer()) {
//                view_couponFoodPay.setClickable(false);
//                tv_coupon_count.setText("优惠卷");
//                tv_priceCouponFoodPay.setText("暂无可用优惠券");
//            } else {
//                view_couponFoodPay.setClickable(true);
//            }
        }
    }

    /**
     * 选择可用优惠卷
     */
    public void showCouponDialog() {
        BigDecimal sumPrice = BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getSumPrice();

        Intent intent = new Intent(this, ChooseCouponActivity.class);
        intent.putParcelableArrayListExtra("voucher_list", voucherList);
        intent.putExtra("sum_price", sumPrice);
        intent.putExtra("default_voucher", voucher);
        this.startActivityForResult(intent, CODE_CHOOSE_COUPON);

//        if (coupon == null) {
//            coupon = new CDialog(this, R.layout.dialog_food_pay_coupon);
//            ListView lv_couponFoodPay = (ListView) coupon.getContentView().findViewById(R.id.lv_couponFoodPay);
//            lv_couponFoodPay.setOnItemClickListener(this);
//            UserCouponListAdapter userCouponListAdapter = new UserCouponListAdapter(this, voucherList, R.layout.item_list_order_coupon_food_pay);
//            lv_couponFoodPay.setAdapter(userCouponListAdapter);
//        }
//        coupon.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        voucher = (Voucher) parent.getItemAtPosition(position);
//        setVoucher(voucher);
//        setSumPriceNeedPay();
//        coupon.dismiss();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_no, R.anim.slide_out_buttom);
    }

    /**
     * 设置总价 调用接口后台计算价格
     */
    private void setSumPriceNeedPay() {
        loadingDialog.show();
        // 请求-获取结算价格
        TJHttpUtil.getInstance(this).doPayOrderPrice(
                BuyCarMapUtils.getCurBuyCarUtils().getBuyCar().getGoodSetList(),
                voucher == null ? null : voucher.getId(),
                stallsId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 添加地址回调
        if (requestCode == 1 && resultCode == 1) {
            mUserAddress = (UserAddress) data.getSerializableExtra("AdressAdd");
            setAddress(mUserAddress);
        }

        // 选择地址回调
        else if (requestCode == 2 && resultCode == 2) {
            mUserAddress = (UserAddress) data.getSerializableExtra("ADRESS");
            setAddress(mUserAddress);
        }

        //选择优惠卷回调
        else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            LogUtils.e("RESULT_OK = ");
            Bundle bundle =  data.getExtras();
            if (bundle != null) {
                this.voucher = bundle.getParcelable("voucher");
                setVoucher(voucher);
                setSumPriceNeedPay();
                LogUtils.e("position = " + voucher.toString());
            }
        }

    }

    private void setAddress(UserAddress userAddress) {
        tv_phoneFoodPay.setVisibility(View.VISIBLE);
        tv_adressFoodPay.setVisibility(View.VISIBLE);
        tv_userFoodPay.setText(userAddress.getName() + "   " + userAddress.getSex() + "神    ");
        tv_adressFoodPay.setText(userAddress.getDetail() + " " + userAddress.getAddress());
        tv_phoneFoodPay.setText(userAddress.getPhone());
    }

    /**
     * 弹出绑定手机号码弹窗
     */
    private void showAddPhoneDialog() {

        hasShowAddPhoneDialog = true;

        addPhoneDialog = new CDialog(this, R.layout.dialog_add_phone_num);
        et_phoneAddDialog = addPhoneDialog.findChildViewById(R.id.et_phoneAddDialog);
        Button btn_submitPhoneAddDialog = addPhoneDialog.findChildViewById(R.id.btn_submitPhoneAddDialog);
        btn_submitPhoneAddDialog.setOnClickListener(this);
        addPhoneDialog.show();

    }

    /**
     * 选择预计送达时间
     */
    private void showSelectTime() {

        if (pvOptions == null) {
            //选项选择器
            pvOptions = new OptionsPickerView(this);

            for (String str : arriveTime) {
                options1Items.add(str);
            }

            //三级联动效果
            pvOptions.setPicker(options1Items);
            pvOptions.setTitle("请选择送达时间");
            pvOptions.setCyclic(false, true, true);
            //设置默认选中的三级项目
            //监听确定选择按钮
            pvOptions.setSelectOptions(0, 0, 0);
            pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    estimatedTimev2 = options1Items.get(options1);
                    tvHasPerssiomFoodPay.setText(estimatedTimev2);
                }
            });
        }

        pvOptions.show();

    }

    @Override
    public void onStart(int requestTag) {

    }

    @Override
    public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        switch (requestTag) {
            case TJRequestTag.TAG_PAY_PRICE:   //获取结算价格
                try{
                    orderPay = (Order) response.getData();
//                    foodPayActionAdapter.updateData(orderPay.getOrderActivityList());

                }catch(NullPointerException e){
                    LogUtils.e(e.toString());
                }

                break;
            case TJRequestTag.TAG_PAY_BIND_PHONE:
                if ((Boolean) (response.getData())) {
                    showToast("绑定手机成功");
                    addPhoneDialog.dismiss();
                    UserUtils.getInstance().doUserRefreshNetUserInfor();
                } else {
                    showToast("绑定手机失败");
                }

                break;

            case TJRequestTag.TAG_ORDER_GET_ESTIMATED_TIME:
                WorkTimeList workTimeList = (WorkTimeList) response.getData();
                List<WorkTime> timeList = workTimeList.getList();
                List<String> listTime = new ArrayList<>();

                for (int i = 0; i < timeList.size(); i ++) {
                    WorkTime workTime = timeList.get(i);

                    listTime.add(workTime.getStartTime() + "-" + workTime.getEndTime());
                }

                // 初始化预计到达时间
                initEstimatedTime(listTime);

                break;

            case TJRequestTag.TAG_ORDER_PLACEORDER:

                String orderId = (String) response.getData();

                doPay(orderId);//判断所选收货地址中的送餐楼栋是否开通配送}
                break;
            case TJRequestTag.TAG_ORDER_SETTLEMENT:  //新增获取优惠价接口，使用此接口
                VoucherList list2 = (VoucherList) response.getData();

                int delyFee = list2.getTotalDelyFee();
                //餐盒费
                if ( delyFee > 0) {
                    rect_BoxFeeFoodPay.setVisibility(View.VISIBLE);
                    tv_BoxFeeFoodPay.setText("￥" + delyFee);
                } else {
                    rect_BoxFeeFoodPay.setVisibility(View.GONE);
                }

                int packFee = list2.getTotalPackFee();
                //配送费
                if (packFee > 0) {
                    tv_serviceFeeFoodPay.setText("￥" + decimalFormat.format(packFee));
                    rect_shoppingFeeFoodPay.setVisibility(View.VISIBLE);
                } else {
                    rect_shoppingFeeFoodPay.setVisibility(View.GONE);
                }

                BigDecimal  sum = new BigDecimal(BigInteger.ZERO);
                for (StallsFullcut orderActivity : list2.getActivityList()) {
                    sum = sum.add(orderActivity.getDiscountedPrice());
                }

                rect_couponFoodPay.setVisibility(sum.doubleValue() > 0 ? View.VISIBLE : View.GONE);
                tv_discountCutFoodPay.setText("" + sum);

                btn_payOrder.setButtonEnable(true);

                tv_action_description.setVisibility(list2.getActivityList().size() > 0 ? View.VISIBLE : View.GONE);
                foodPayActionAdapter.updateData(list2.getActivityList());

                List<Voucher> voucherList = list2.getVoucherList();
                if (list2 != null) {
                    filterUsableVoucherList(voucherList);//过滤！有特价菜的情况下只能使用抵用券，（过滤掉满减的红包）
                    if (voucherList.size() == 0) {
                        voucher = null;
                    } else {
                        /**
                         * 对红包进行筛选，筛选可用的红包（满减红包）
                         * 默认展示获取的第一个红包优惠卷
                         */
                        voucher = filterVoucherList(voucherList);
//                            voucher = voucherList.get(0);
                    }
                    setVoucher(voucher);
                }
//                setSumPriceNeedPay();
                break;
        }
    }

    @Override
    public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onFinish(int requestTag) {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
