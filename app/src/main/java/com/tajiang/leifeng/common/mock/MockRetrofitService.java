package com.tajiang.leifeng.common.mock;

import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.retrofit.RetrofitService;
import com.tajiang.leifeng.model.Action;
import com.tajiang.leifeng.model.ActiveInfo;
import com.tajiang.leifeng.model.Adv;
import com.tajiang.leifeng.model.AppInfoResult;
import com.tajiang.leifeng.model.AreaCity;
import com.tajiang.leifeng.model.ChannelEntity;
import com.tajiang.leifeng.model.City;
import com.tajiang.leifeng.model.CityData;
import com.tajiang.leifeng.model.FoodClass;
import com.tajiang.leifeng.model.FoodClassList;
import com.tajiang.leifeng.model.Goods;
import com.tajiang.leifeng.model.InviteInfo;
import com.tajiang.leifeng.model.Marquee;
import com.tajiang.leifeng.model.Message;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.Pager;
import com.tajiang.leifeng.model.PredepositLog;
import com.tajiang.leifeng.model.PredepositOrder;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.model.SchoolApartment;
import com.tajiang.leifeng.model.Store;
import com.tajiang.leifeng.model.StoreDelivered;
import com.tajiang.leifeng.model.StoreStalls;
import com.tajiang.leifeng.model.StoreStallsList;
import com.tajiang.leifeng.model.TokenInfo;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.model.UserAddress;
import com.tajiang.leifeng.model.UserAddressListEntity;
import com.tajiang.leifeng.model.Voucher;
import com.tajiang.leifeng.model.VoucherList;
import com.tajiang.leifeng.model.WorkTimeList;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;
import rx.Observable;

/**
 * RetrofitServiceMock类的实现用于产生 Mock 数据
 * Created by Admins on 2017/2/9.
 */

public class MockRetrofitService implements RetrofitService{
    //Retrofit Service 的代理，产生 Mock 数据
    private final BehaviorDelegate<RetrofitService> mDelegate;

    public MockRetrofitService(BehaviorDelegate<RetrofitService> delegate) {
        mDelegate = delegate;
    }


    @Override
    public Observable<BaseResponse<TokenInfo>> doLogin(@Query("phone") String userName, @Query("password") String psw, @Query("clientId") String clientId, @Query("imei") String imei) {
        return mDelegate.returningResponse("replace with your fake json data.").doLogin(userName, psw, clientId, imei);
    }

    @Override
    public Observable<BaseResponse<Boolean>> doLogout() {
        return null;
    }

    @Override
    public Observable<BaseResponse<Pager<Order>>> getOrderList(@Query("page") int page, @Query("pagesize") int pageSize) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Order>> getOrderDetail(@Query("orderId") String orderId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doLoginByQQ(@Query("accessToken") String accessToken, @Query("authType") int authType, @Query("clientId") String clientId, @Query("imei") String imei, @Query("uid") String uid) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doLoginByWeiChat(@Query("accessToken") String accessToken, @Query("uid") String uid) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doLoginByWeiBo(@Query("accessToken") String accessToken, @Query("uid") String uid) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<Adv>>> getAdvList(@Query("schoolId") int schoolId, @Query("adType") int apId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Pager<StoreStalls>>> getStalls(@Query("apartmentId") int apartmentId, @Query("page") int page, @Query("pageSize") int pageSize, @Query("rangeCode") String rangeCode, @Query("recomTag") int recomTag) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<Marquee>>> getMarquee(@Query("schoolId") int schoolId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<ChannelEntity>>> getRangelist() {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<StoreDelivered>>> getStoreDistributionRange(@Query("schoolId") int schoolId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<AreaCity>>> getSchoolOfLocalCity(@Query("areaName") String areaName) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<School>>> getNearSchools(@Query("lat") double lat, @Query("lng") double lng, @Query("distance") int distance) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<School>>> getSchoolSearchList(@Query("schoolName") String schoolName) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<City>>> getCityHotList() {
        return null;
    }

    @Override
    public Observable<BaseResponse<CityData>> getCityList() {
        return null;
    }

    @Override
    public Observable<BaseResponse<AppInfoResult>> getAppInfo() {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> collectClientMsg(@Query("userId") String userId, @Query("clientId") String clientId, @Query("os") int os, @Query("osVersion") String osVersion, @Query("deviceModel") String deviceModel, @Query("appVersion") String appVersion) {
        return null;
    }

    @Override
    public Observable<BaseResponse<User>> userDoGetInfor() {
        return null;
    }

    @Override
    public Observable<BaseResponse<FoodClassList>> getStallsgoodsClass(@Query("stallsId") String stallsId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Pager<Goods>>> getStallsGoods(@Query("apartmentId") int apartmentId, @Query("shopId") String shopId, @Query("goodsClassId") String goodsClassId, @Query("page") int page, @Query("pageSize") int pageSize, @Query("hotTag") int hotTag) {
        return null;
    }

    @Override
    public Observable<BaseResponse<VoucherList>> getUserCoupon() {
        return null;
    }

    @Override
    public Observable<BaseResponse<WorkTimeList>> getEstimatedTime(@Query("storeId") String storeId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> showexplainlabelReq() {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> placeorder(@Query("activityId") Integer activityId, @Query("buyerRemark") String buyerRemark, @Query("cartIds") String cartIds, @Query("channel") String channel, @Query("delyEndDate") String delyEndDate, @Query("delyFast") int delyFast, @Query("delyStartDate") String delyStartDate, @Query("shopId") String shopId, @Query("userAddressId") int userAddressId, @Query("voucherId") Integer voucherId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doOrderBindPhone(@Query("phone") String phone) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Order>> doPayOrderPrice(@Query("cart_ids") String cart_ids, @Query("voucherId") String voucherId, @Query("stallsId") String stallsId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<ActiveInfo>> userWalletPayPswIsAuthReq() {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> doPayOrder(@Query("bizId") String bizId, @Query("bizType") int bizType, @Query("payPwd") String payPwd, @Query("thdType") int thdType) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Order>> doPayOrder(@Query("bizId") String bizId, @Query("bizType") int bizType, @Query("thdType") int thdType) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> userWalletPayPswAuthReq(@Query("pdPassword") String pdPassword) {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> getQiNiuTokenRequest(@Query("bucketName") String bucketName) {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> doModifyAvatar(@Query("avatarOldKey") String avatarOldKey, @Query("avatarNewKey") String avatarNewKey) {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> upLoadUserIcon(@Body RequestBody Body) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Pager<Message>>> getMessageList(@Query("page") int page, @Query("pageSize") int pageSize) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doModifySex(@Query("sex") String sex) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doModifyNickName(@Query("nickname") String nikeName) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<Action>>> userPDActivity() {
        return null;
    }

    @Override
    public Observable<BaseResponse<Pager<PredepositLog>>> userPredepositLogsReq(@Query("type") Integer type, @Query("page") Integer page, @Query("pagesize") Integer pagesize) {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> userWalletPayPswModifly(@Query("oldPwd") String oldPwd, @Query("newPwd") String newPwd) {
        return null;
    }


    @Override
    public Observable<BaseResponse<Boolean>> userWalletPayPswCreate(@Query("payPwd") String pdPassword) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> userWalletPayPswCode(@Query("mobilePhone") String mobilePhone, @Query("post") String post) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> getRegisterCode(@Query("codeType") String codeType, @Query("phone") String mobilePhone) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Integer>> rapGetUserCashCount() {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> rapDoUserCash() {
        return null;
    }

    @Override
    public Observable<BaseResponse<UserAddressListEntity>> getAdressList(@Query("page") int page, @Query("pageSize") int pageSize) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> userDoAdressDelete(@Path("adressId") int adressId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doModifyAdress(@Query("address") String address, @Query("addressId") int addressId, @Query("apartmentId") int apartmentId, @Query("isDefault") int isDefault, @Query("name") String name, @Query("phone") String phone, @Query("sex") int sex) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> setAddressDefault(@Path("addressId") int addressId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<SchoolApartment>> getApartmentBySchoolId(@Query("schoolId") int schoolId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<UserAddress>> doAddAdress(@Query("trueName") String trueName, @Query("sex") String sex, @Query("apartmentId") int apartmentId, @Query("address") String address, @Query("mobPhone") String mobPhone, @Query("isDefault") String isDefault) {
        return null;
    }

    @Override
    public Observable<BaseResponse<InviteInfo>> getUserInviteInfo(@Query("userId") String userId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doUserFeedBack(@Query("content") String content) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doUserModifyPassword(@Query("oldPwd") String oldpassword, @Query("newPwd") String password) {
        return null;
    }

    @Override
    public Observable<BaseResponse<PredepositOrder>> doWellatPredeposit(@Query("paymentId") int paymentId, @Query("predeposit") double predeposit) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> SignUpReq(@Query("schoolId") int schoolId, @Query("mobilePhone") String mobilePhone, @Query("regcode") String regcode, @Query("password") String password, @Query("type") int type, @Query("invitationCode") String invitationCode) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> doRegister(@Query("inviteCode") String invitationCode, @Query("password") String password, @Query("phone") String phone, @Query("verificationCode") String regcode) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> orderDoCancal(@Query("orderId") String orderId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> userOrderComplete(@Path("orderId") String orderId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Integer>> userOrderHurry(@Path("orderId") String orderId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<VoucherList>> setTlement(@Query("cartIds") String cartIds, @Query("shopId") String shopId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> userOrderGoodEvaluate(@Path("orderId") String goodId, @Query("scores") int scores) {
        return null;
    }

    @Override
    public Observable<BaseResponse<String>> evaluate(@Query("content") String content, @Query("orderId") String orderId, @Query("service") int service, @Query("speed") int speed, @Query("taste") int taste) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<AreaCity>>> getSchoolByAreaId(@Query("areaId") int areaId, @Query("areaName") String areaName) {
        return null;
    }

    @Override
    public Observable<BaseResponse<List<Store>>> getSchoolStorList(@Query("schoolId") int schoolId) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Boolean>> rapDoUserJoin(@Query("name") String name, @Query("phone") String phone, @Query("certificate") String certificate, @Query("apartmentId") int apartmentId, @Query("address") String address) {
        return null;
    }
}
