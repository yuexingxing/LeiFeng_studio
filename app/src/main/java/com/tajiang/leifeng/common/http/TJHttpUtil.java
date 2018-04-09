package com.tajiang.leifeng.common.http;

import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.model.GoodSet;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Admins on 2016/12/28.
 */
public class TJHttpUtil {

    public final static int CODE_RESPONSE_NEED_LOGIN = 10001;
    public final static int CODE_RESPONSE_PERFORMED_FAILED = 12001;
    public final static int CODE_RESPONSE_NEED_AUTH = 14001;
    public final static int CODE_RESPONSE_IN_CHECKING = 14002;
    public final static int CODE_RESPONSE_AUTH_FAIL = 14003;

    private static HttpResponseListener listener;
    private static TJHttpUtil tjHttpUtil;
    private static final int pageSize = 10;

    public static TJHttpUtil getInstance(HttpResponseListener listener) {
        TJHttpUtil.listener = listener;
        if (tjHttpUtil == null) {
            synchronized (TJHttpUtil.class) {
                tjHttpUtil = new TJHttpUtil();
            }
        }
        return tjHttpUtil;
    }

    /**
     * 用户登录
     * @param userName
     * @param psw
     */
     public void doLogin(String userName, String psw, String clientId, String imei) {
        new HttpHandler(listener).doLogin(userName, psw, clientId, imei, TJRequestTag.TAG_USER_LOGIN);
    }

    /**
     * 用户退出
     */
    public void doLogout() {
        new HttpHandler(listener).doLogout(TJRequestTag.TAG_USER_LOGOUT);
    }

    //TODO.......Created By QiLi.........

    /**
     * 订单-获取订单
     * @param page       页码
     */
    public void getOrderList(int page) {
        new HttpHandler(listener).getOrderList(page, pageSize, TJRequestTag.TAG_GET_ORDER);
    }

    /**
     * 订单-获取订单详情
     */
    public void getOrderDetail(String orderId) {
        new HttpHandler(listener).getOrderDetail(orderId, TJRequestTag.TAG_GET_ORDERDETAIL);
    }

    /**
     * 用户 - 登陆 - QQ登陆
     *
     * @param accessToken
     * @param uid
     */
    public void doLoginByQQ(String accessToken, int authType, String clientId, String imei, String uid) {
        new HttpHandler(listener).doLoginByQQ(accessToken, authType, clientId, imei, uid, TJRequestTag.TAG_USER_LOGIN_OTHER);
    }

    /**
     * 用户 - 登陆 - 微信登陆
     *
     * @param accessToken
     * @param uid
     */
    public void doLoginByWeiChat(String accessToken, String uid) {
        new HttpHandler(listener).doLoginByWeiChat(accessToken, uid, TJRequestTag.TAG_USER_LOGIN_OTHER);
    }

    /**
     * 用户 - 登陆 - 微博登陆
     *
     * @param accessToken
     * @param uid
     */
    public void doLoginByWeiBo(String accessToken, String uid) {
        new HttpHandler(listener).doLoginByWeiBo(accessToken, uid, TJRequestTag.TAG_USER_LOGIN_OTHER);
    }

    /**
     * 获取广告Banner图片
     *
     * @param apId 广告位id（app首页轮播图列表id:9）
     */
    public void getAdvList(int schoolId, int apId) {
        new HttpHandler(listener).getAdvList(schoolId, apId, TJRequestTag.TAG_GET_ADV_LIST);
    }

    /**
     * 获取食堂配送楼栋
     */
    public void getStalls(int apartmentId, int page, int pageSize, String rangeCode, int recomTag) {
        new HttpHandler(listener).getStalls(apartmentId, page, pageSize, rangeCode, recomTag, TJRequestTag.TAG_GET_STORE_STALLS);
    }

    /**
     * 获取头条跑马灯
     */
    public void getMarquee(int schoolId) {
        new HttpHandler(listener).getMarquee(schoolId, TJRequestTag.TAG_GET_MARQUEE);
    }

    /**
     * 获取主营业务列表
     */
    public void getRangeList() {
        new HttpHandler(listener).getRangelist(TJRequestTag.TAG_GET_RANGELIST);
    }

    /**
     * 获取食堂配送楼栋
     * @param schoolId 配送学校Id
     */
    public void getStoreDistributionRange(int schoolId) {
        new HttpHandler(listener).getStoreDistributionRange(schoolId, TJRequestTag.TAG_GET_STORE_DELIVERED_APARTMENT);
    }

    /**
     * 选择学校 - 获取定位城市的学校
     */
    public void getSchoolOfLocalCity(String areaName) {
        new HttpHandler(listener).getSchoolOfLocalCity(areaName, TJRequestTag.TAG_GET_SCHOOL_OF_LOCAL_CITY);
    }

    /**
     * 学校 - LBS:附近学校
     */
    public void getNearSchools(Double lat, Double lng, Integer distance) {
        new HttpHandler(listener).getNearSchools(lat, lng, distance, TJRequestTag.TAG_GET_SCHOOL_NEARS);
    }

    /**
     * 学校 - 检索学校
     */
    public void getSchoolSearchList(String schoolName) {
        new HttpHandler(listener).getSchoolSearchList(schoolName, TJRequestTag.TAG_GET_SCHOOL_SEARCH);
    }

    /**
     * 选择学校 - 获取热门城市列表
     */
    public void getCityHotList() {
        new HttpHandler(listener).getCityHotList(TJRequestTag.TAG_GET_SCHOOL_CITY_HOT);
//        TJHttpClient.getInstance(context).get(new GetCityHotSchoolReq(), listener, TAG_GET_SCHOOL_CITY_HOT);
    }

    /**
     * 选择学校 - 获取所有城市列表
     */
    public void getCityList() {
        new HttpHandler(listener).getCityList(TJRequestTag.TAG_GET_SCHOOL_CITY_LIST);
    }

    public void getAppInfo() {
        new HttpHandler(listener).getAppInfo(TJRequestTag.TAG_APP_INFO);
    }

    /**
     * 用户-收集手机信息
     * @param userId
     * @param  clientId
     * @param  os
     * @param  osVersion
     * @param  deviceModel
     * @param  appVersion
     */
    public void collectClientMsg(String userId, String clientId, int os, String osVersion, String deviceModel, String appVersion) {
        new HttpHandler(listener).collectClientMsg(userId, clientId,os, osVersion,deviceModel, appVersion, TJRequestTag.TAG_COLLECT_CLIENT);
    }

    /**
     * 用户 - 信息
     */
    public void userDoGetInfor() {
        new HttpHandler(listener).userDoGetInfor(TJRequestTag.TAG_USER_INFO);
    }

    /**
     * 根据档口获取菜品分类
     * @param stallsId 配送学校Id
     */
    public void getStallsgoodsClass(String stallsId) {
        new HttpHandler(listener).getStallsgoodsClass(stallsId, TJRequestTag.TAG_GET_STORE_STALLS_FOOD_CLASS);
    }

    /**
     * 获取档口菜品信息
     * @param shopId 档口Id
     * @param goodsClassId
     * @param page
     * @param pageSize
     */
    public void getStallsGoods(int apartmentId, String shopId, String goodsClassId, int page, int pageSize, int hotTag) {
        new HttpHandler(listener).getStallsGoods(apartmentId, shopId,goodsClassId, page, pageSize, hotTag, TJRequestTag.TAG_GET_STORE_STALLS_GOODS);
    }

    /**
     * 用户-下单获取可用红包
     */
    public void getUserCoupon() {
        new HttpHandler(listener).getUserCoupon(TJRequestTag.TAG_GET_COUPON);
    }

    /**
     * 点餐 - 预计到达时间
     */
    public void getEstimatedTime(String shopId) {
        new HttpHandler(listener).getEstimatedTime(shopId,TJRequestTag.TAG_ORDER_GET_ESTIMATED_TIME);
    }

    /**
     * 订单-获取备注标签
     */
    public void showexplainlabelReq() {
        new HttpHandler(listener).showexplainlabelReq(TJRequestTag.TAG_ORDER_SHOW_EXPLAIN_LABEL);
    }

    /**
     * 订单-确认下单
     */
    public void placeorder(Integer activityId, String buyerRemark, String cartIds, String channel, String delyEndDate, int delyFast, String delyStartDate, String shopId, int userAddressId, Integer voucherId) {
        new HttpHandler(listener).placeorder(activityId, buyerRemark, cartIds, channel, delyEndDate, delyFast, delyStartDate, shopId, userAddressId, voucherId, TJRequestTag.TAG_ORDER_PLACEORDER);
    }

    /**
     * 点餐 - 带走 - 绑定手机
     */
    public void doOrderBindPhone(String phone) {
        new HttpHandler(listener).doOrderBindPhone(phone, TJRequestTag.TAG_PAY_BIND_PHONE);
    }

    /**
     * 获取下单价格 - 获取实际优惠后价格
     *
     * @param voucherId     优惠券id
     * @param stallsId      档口id
     */
    public void doPayOrderPrice(List<GoodSet> goodSetList, String voucherId, String stallsId) {
        //		(格式：商品id&商品数量;商品id&商品数量)
        String cart_ids = "";
        for (GoodSet goodSet : goodSetList) {
            cart_ids += goodSet.getGoods().getGoodsId() + "&" + goodSet.getNum() + ";";
        }
        new HttpHandler(listener).doPayOrderPrice(cart_ids, voucherId, stallsId, TJRequestTag.TAG_PAY_PRICE);
    }

    /**
     * 用户 - 钱包 - 支付密码 - 是否认证
     */
    public void userWalletPayPswIsAuthReq() {
        new HttpHandler(listener).userWalletPayPswIsAuthReq(TJRequestTag.TAG_USER_WELLAT_IS_AUTH);
    }

    /**
     * 点餐 - 下单直接下单
     *
     */
    public void doPayOrder(String bizId, int bizType, String payPwd, int thdType) {
        if (thdType == 0) {
            new HttpHandler(listener).doPayOrder(bizId, bizType, payPwd, thdType, TJRequestTag.TAG_PAY);
        } else {
            new HttpHandler(listener).doPayOrder(bizId, bizType, thdType, TJRequestTag.TAG_PAY);
        }
    }

    /**
     * 用户 - 钱包 - 支付密码 - 认证
     */
    public void userWalletPayPswAuthReq(String pdPassword) {
        new HttpHandler(listener).userWalletPayPswAuthReq(pdPassword, TJRequestTag.TAG_USER_WELLAT_AUTH);
    }

    /**
     * 七牛 - 请求TOKEN
     */
    public void getQiNiuTokenRequest(){
        new  HttpHandler(listener).getQiNiuTokenRequest("hztajiang", TJRequestTag.TAG_QINIU_TOKEN_REQUEST);
    }

    /*
    * 修改头像
    * */
    public void upLoadUserIcon(RequestBody Body) {
        new HttpHandler(listener).upLoadUserIcon(Body, TJRequestTag.TAG_USER_MODIFY_AVATAR);
    }

    /*
   * 用户- 消息列表
   * */
    public void getMessageList(int page, int pageSize) {
        new HttpHandler(listener).getMessageList(page, pageSize, TJRequestTag.TAG_USER_MESSAGE);
    }

    /**
     * 用户 - 修改 - 性别
     *
     * @param sex 性别(0男1女)
     */
    public void doModifySex(String sex) {
        new HttpHandler(listener).doModifySex(sex,TJRequestTag.TAG_USER_MODIFY_SEX);
    }

    /**
     * 用户 - 修改 - 昵称
     *
     * @param nikeName
     */
    public void doModifyNickName(String nikeName) {
        new HttpHandler(listener).doModifyNickName(nikeName, TJRequestTag.TAG_USER_MODIFY_NICK_NAME);
    }


    /**
     * 用户 - 钱包 - 充值活动
     */
    public void userPDActivity() {
        new  HttpHandler(listener).userPDActivity(TJRequestTag.TAG_USER_PD_Activity);
    }

    /**
     *用户 - 钱包 - 预存款日志记录
     * @param type
     * @param page
     */
    public void userPredepositLogsReq(Integer type, Integer page) {
        new HttpHandler(listener).userPredepositLogsReq(type, page, 1000000, TJRequestTag.TAG_USER_WELLAT_PREDEPOSIT_LOGS);
    }

    public void userWalletPayPswModifly(String oldPwd, String psw) {
        new HttpHandler(listener).userWalletPayPswModifly(oldPwd, psw, TJRequestTag.TAG_USER_WELLAT_MODIFLY);
    }

    public void userWalletPayPswCreate(String psw) {
        new  HttpHandler(listener).userWalletPayPswCreate(psw, TJRequestTag.TAG_USER_WELLAT_CREATE);
    }

    public void userWalletPayPswCode(String phone, String code) {
        new HttpHandler(listener).userWalletPayPswCode(phone, code, TJRequestTag.TAG_USER_WELLAT_CODE);
    }

    public void getRegisterCode(String codeType, String phone) {
        new HttpHandler(listener).getRegisterCode(codeType, phone,TJRequestTag.TAG_GET_CODE);
    }




    //TODO.......Created By QiLi.........
    /**
     * 优惠券提现
     */
    public void rapGetUserCashCount() {
        new HttpHandler(listener).rapGetUserCashCount(TJRequestTag.TAG_ORDER_RAP_USER_CASH_COUNT);
    }
    /**
     * 抢单 - 提现
     */
    public void rapDoUserCash() {
        new HttpHandler(listener).rapDoUserCash(TJRequestTag.TAG_ORDER_RAP_USER_CASH);
    }

    /**
     * 用户地址列表
     * @return
     */
    public void getAdressList(int page, int pageSize) {
        new HttpHandler(listener).getAdressList(page, pageSize, TJRequestTag.TAG_USER_ADRESS);
    }

    /**
     * 删除地址
     * @param AddressId
     * @return
     */
    public void userDoAdressDelete(int AddressId) {
        new HttpHandler(listener).userDoAdressDelete(AddressId,TJRequestTag.TAG_USER_ADRESS_DELETE);
    }

    /**
     * 用户-修改地址
     * @param addressId
     * @param sex
     * @param apartmentId
     * @param address
     * @param isDefault
     * @return
     */
    public void doModifyAdress(String address, int addressId, int apartmentId,int isDefault, String name, String phone, int sex) {
        new HttpHandler(listener).doModifyAdress(address,addressId,apartmentId,isDefault,name,phone,sex,TJRequestTag.TAG_USER_ADDRESS_MODIFY);
    }

    /**
     * 用户-设置默认地址
     * @param addressId
     * @return
     */
    public void setAddressDefault (int addressId) {
        new HttpHandler(listener).setAddressDefault(addressId, TJRequestTag.TAG_USER_ADDRESS_DEFAULT);
    }

    /**
     * 点餐 - 获取学校列表和宿舍列表
     * @param schoolId
     * @return
     */
    public void getApartmentBySchoolId(int schoolId) {
        new HttpHandler(listener).getApartmentBySchoolId(schoolId,TJRequestTag.TAG_GET_SCHOOL_APARTMENT_LIST);
    }
    /**
     * 用户-添加地址
     * @param trueName
     * @param sex
     * @param apartmentId
     * @param address
     * @param mobPhone
     * @param isDefault
     * @return
     */
    public void doAddAdress(String trueName, int sex, int apartmentId,String address, String mobPhone, int isDefault) {
        new HttpHandler(listener).doAddAdress(trueName,sex,apartmentId,address,mobPhone,isDefault,TJRequestTag.TAG_USER_ADDRESS_ADD);

    }

    /**
     * 用户 - 邀请好友
     * @param userId
     * @return
     */
    public void getUserInviteInfo(String userId) {
        new HttpHandler(listener).getUserInviteInfo(userId,TJRequestTag.TAG_USER_INVITE);
    }

    /**
     * 用户 - 提交反馈信息
     * @param content
     * @return
     */
    public void doUserFeedBack(String content) {
        new HttpHandler(listener).doUserFeedBack(content, TJRequestTag.TAG_USER_FEED_BACK);
    }
    /**
     * 用户 - 修改密码
     * @param oldpassword
     * @param password
     * @return
     */
    public void doUserModifyPassword(String oldpassword, String password) {
        new HttpHandler(listener).doUserModifyPassword(oldpassword,password,TJRequestTag.TAG_MODIFY_PASSWORD);
    }

    /**
     * 用户 - 钱包 -  充值
     */
    public void doWellatPredeposit(int paymentId, double predeposit) {
        new HttpHandler(listener).doWellatPredeposit(paymentId, predeposit, TJRequestTag.TAG_WELLAT_PREDEPOSIT);
    }



    /**
     * 用户-修改密码
     */
    public void userDoModifyPsd(int schoolId, String mobilePhone, String regcode, String password) {
        new HttpHandler(listener).SignUpReq(schoolId, mobilePhone, regcode, password, 2, "" , TJRequestTag.TAG_USER_RESET_PASSWORD);
    }

    /**
     * 用户-注册
     */
    public void doRegister(String invitationCode, String password, String phone, String regcode) {
        new HttpHandler(listener).doRegister(invitationCode, password, phone, regcode, TJRequestTag.TAG_USER_REGISTER);
    }

    /**
     * 取消订单
     */
    public void orderDoCancal(String orderId) {
        new HttpHandler(listener).orderDoCancal(orderId, TJRequestTag.TAG_ORDER_CANCEL);
    }

    /**
     * 订单 - 确认收获
     *
     * @param orderId
     */
    public void userOrderComplete(String orderId) {
        new HttpHandler(listener).userOrderComplete(orderId, TJRequestTag.TAG_ORDER_COMPLETE);
    }

    /**
     * 订单 - 催单
     *
     * @param orderId
     */
    public void userOrderHurry(String orderId) {
        new HttpHandler(listener).userOrderHurry(orderId, TJRequestTag.TAG_ORDER_HURRY);
    }

    /**
     * 订单 - 获取结算订单详情
     *
     */
    public void setTlement(String cartIds, String shopId) {
        new HttpHandler(listener).setTlement(cartIds, shopId, TJRequestTag.TAG_ORDER_SETTLEMENT);
    }

    /**
     * 订单 - 商品 - 评价
     */
    public void userOrderGoodEvaluate(String goodId, int numStar) {
        new HttpHandler(listener).userOrderGoodEvaluate(goodId, numStar, TJRequestTag.TAG_GOOD_EVALUATE);
    }

    /*
    * 评价
    * */
    public void evaluate(String content, String orderId, int service, int speed, int taste) {
        new HttpHandler(listener).evaluate(content, orderId, service, speed, taste, TJRequestTag.TAG_GOOD_EVALUATE);
    }

    /**
     * 选择学校 - 获取学校通过城市Id
     */
    public void getSchoolByAreaId(int areaId, String areaName) {
        new HttpHandler(listener).getSchoolByAreaId(areaId, areaName, TJRequestTag.TAG_GET_SCHOOL_BY_AREA_ID);
    }

    /**
     * 点餐 - 指定学校的餐厅列表
     * <p/>
     * 学校id
     */
    public void getSchoolStorList(int schoolId) {
        new HttpHandler(listener).getSchoolStorList(schoolId, TJRequestTag.TAG_GET_SCHOOL_STORE_LIST);
    }

    /**
     * 抢单 - 用户 - 认证
     *
     * @param phone       电话
     * @param certificate 证件照片地址
     * @param apartmentId 宿舍id
     * @param address     详细地址
     */
    public void rapDoUserJoin(String name, String phone, String certificate, Integer apartmentId, String address) {
        new HttpHandler(listener).rapDoUserJoin(name, phone, certificate, apartmentId, address, TJRequestTag.TAG_ORDER_RAP_USER_JOIN);
    }

}
