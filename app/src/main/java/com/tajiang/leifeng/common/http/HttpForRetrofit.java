package com.tajiang.leifeng.common.http;

import android.util.TypedValue;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.common.retrofit.RetrofitService;
import com.tajiang.leifeng.common.retrofit.TJRetrofit;
import com.tajiang.leifeng.common.retrofit.TJSubscriber;
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
import com.tajiang.leifeng.utils.NetWorkUtils;
import com.tajiang.leifeng.utils.ToastUtils;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Admins on 2016/12/28.
 */
public class HttpForRetrofit implements HttpLoad {

    private RetrofitService service;
    private HttpResponseListener listener;
    private Subscriber subscriber;

    public HttpForRetrofit(HttpResponseListener listener) {
        service = TJRetrofit.createRetrofitService();
        this.listener = listener;
    }

    /**
     * 执行网络请求
     * @param observable
     * @param <T>
     */
    private <T> void excuteRequest(Observable<BaseResponse<T>> observable, int tag) {
        if(NetWorkUtils.isConnected(TJApp.getIns())) {
            subscriber = new TJSubscriber<T>(listener, tag);
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(subscriber);
        } else {
            ToastUtils.showShort(R.string.msg_network_error);
        }
    }

    /**
     * 取消网络请求
     */
    @Override
    public void cancel() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }

    //TODO.......Created By QiLi.........

    /**
     * 用户登录
     * @param userName
     * @param psw
     */
    @Override
    public void doLogin(String userName, String psw, String clientId, String imei, int tag) {
        Observable<BaseResponse<TokenInfo>> observable = service.doLogin(userName, psw, clientId, imei);
        excuteRequest(observable, tag);
    }

    /**
     * 用户退出
     */
    @Override
    public void doLogout(int tag) {
        Observable<BaseResponse<Boolean>> observable = service.doLogout();
        excuteRequest(observable, tag);
    }

    /**
     * 订单-获取订单
     * @param page       页码
     * @param pageSize       页数
     */
    @Override
    public void getOrderList(int page, int pageSize, int tag) {
        Observable<BaseResponse<Pager<Order>>> observable = service.getOrderList(page, pageSize);
        excuteRequest(observable, tag);
    }

    @Override
    public void getOrderDetail(String orderId, int tag) {
        Observable<BaseResponse<Order>> observable = service.getOrderDetail(orderId);
        excuteRequest(observable, tag);
    }
    /**
     * 用户 - 登陆 - QQ登陆
     *
     * @param accessToken
     * @param uid
     */
    @Override
    public void doLoginByQQ(String accessToken, int authType, String clientId, String imei, String uid, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.doLoginByQQ(accessToken, authType, clientId, imei, uid);
        excuteRequest(observable, tag);
    }

    /**
     * 用户 - 登陆 - 微信登陆
     *
     * @param accessToken
     * @param uid
     */
    @Override
    public void doLoginByWeiChat(String accessToken, String uid, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.doLoginByWeiChat(accessToken, uid);
        excuteRequest(observable, tag);
    }
    /**
     * 用户 - 登陆 - 微博登陆
     *
     * @param accessToken
     * @param uid
     */
    @Override
    public void doLoginByWeiBo(String accessToken, String uid, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.doLoginByWeiBo(accessToken, uid);
        excuteRequest(observable, tag);
    }

    /**
     * 获取广告Banner图片
     *
     * @param apId 广告位id（app首页轮播图列表id:9）
     */
    @Override
    public void getAdvList(int schoolId, int apId, int tag) {
        Observable<BaseResponse<List<Adv>>> observable = service.getAdvList(schoolId, apId);
        excuteRequest(observable, tag);
    }

    /**
     * 获取食堂配送楼栋
     */
    @Override
    public void getStalls(int apartmentId, int page, int pageSize, String rangeCode, int recomTag, int tag) {
        Observable<BaseResponse<Pager<StoreStalls>>> observable = service.getStalls(apartmentId, page, pageSize, rangeCode, recomTag);
        excuteRequest(observable, tag);
    }

    @Override
    public void getMarquee(int schoolId, int tag) {
        Observable<BaseResponse<List<Marquee>>> observable = service.getMarquee(schoolId);
        excuteRequest(observable, tag);
    }

    @Override
    public void getRangelist(int tag) {
        Observable<BaseResponse<List<ChannelEntity>>> observable = service.getRangelist();
        excuteRequest(observable, tag);
    }

    /**
     * 获取食堂配送楼栋
     * @param schoolId 配送学校Id
     */
    @Override
    public void getStoreDistributionRange(int schoolId, int tag) {
        Observable<BaseResponse<List<StoreDelivered>>> observable = service.getStoreDistributionRange(schoolId);
        excuteRequest(observable, tag);
    }

    /**
     * 选择学校 - 获取定位城市的学校
     * @param areaName 学校所在地区名字
     */
    @Override
    public void getSchoolOfLocalCity(String areaName, int tag) {
        Observable<BaseResponse<List<AreaCity>>> observable = service.getSchoolOfLocalCity(areaName);
        excuteRequest(observable, tag);
    }

    /**
     * 学校 - LBS:附近学校
     */
    @Override
    public void getNearSchools(double lat, double lng, int distance, int tag) {
        Observable<BaseResponse<List<School>>> observable = service.getNearSchools(lat, lng, distance);
        excuteRequest(observable, tag);
    }

    /**
     * 学校 - 检索学校
     */
    @Override
    public void getSchoolSearchList(String schoolName, int tag) {
        Observable<BaseResponse<List<School>>> observable = service.getSchoolSearchList(schoolName);
        excuteRequest(observable, tag);
    }

    /**
     * 选择学校 - 获取热门城市列表
     */
    @Override
    public void getCityHotList(int tag) {
        Observable<BaseResponse<List<City>>> observable = service.getCityHotList();
        excuteRequest(observable, tag);
    }

    /**
     * 选择学校 - 获取所有城市列表
     */
    @Override
    public void getCityList(int tag) {
        Observable<BaseResponse<CityData>> observable = service.getCityList();
        excuteRequest(observable, tag);
    }

    @Override
    public void getAppInfo(int tag) {
        Observable<BaseResponse<AppInfoResult>> observable = service.getAppInfo();
        excuteRequest(observable, tag);
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
    @Override
    public void collectClientMsg(String userId, String clientId, int os, String osVersion, String deviceModel, String appVersion, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.collectClientMsg(userId, clientId, os, osVersion, deviceModel, appVersion);
        excuteRequest(observable, tag);
    }

    /**
     * 用户 - 信息
     */
    @Override
    public void userDoGetInfor(int tag) {
        Observable<BaseResponse<User>> observable = service.userDoGetInfor();
        excuteRequest(observable, tag);
    }

    /**
     * 根据档口获取菜品分类
     * @param stallsId 配送学校Id
     */
    @Override
    public void getStallsgoodsClass(String stallsId, int tag) {
        Observable<BaseResponse<FoodClassList>> observable = service.getStallsgoodsClass(stallsId);
        excuteRequest(observable, tag);
    }

    /**
     * 获取档口菜品信息
     * @param shopId 档口Id
     * @param goodsClassId
     * @param page
     * @param pageSize
     */
    @Override
    public void getStallsGoods(int apartmentId, String shopId, String goodsClassId, int page, int pageSize, int hotTag, int tag) {
        Observable<BaseResponse<Pager<Goods>>> observable = service.getStallsGoods(apartmentId, shopId, goodsClassId, page, pageSize, hotTag);
        excuteRequest(observable, tag);
    }

    /**
     * 用户-下单获取可用红包
     */
    @Override
    public void getUserCoupon(int tag) {
        Observable<BaseResponse<VoucherList>> observable = service.getUserCoupon();
        excuteRequest(observable, tag);
    }

    /**
     * 点餐 - 预计到达时间
     * @param storeId
     */
    public void getEstimatedTime(String storeId, int tag) {
        Observable<BaseResponse<WorkTimeList>> observable = service.getEstimatedTime(storeId);
        excuteRequest(observable, tag);
    }

    /**
     * 订单-获取备注标签
     */
    @Override
    public void showexplainlabelReq(int tag) {
        Observable<BaseResponse<String>> observable = service.showexplainlabelReq();
        excuteRequest(observable, tag);
    }

    /**
     * 订单-确认下单
     */
    @Override
    public void placeorder(Integer activityId, String buyerRemark, String cartIds, String channel, String delyEndDate, int delyFast, String delyStartDate, String shopId, int userAddressId, Integer voucherId, int tag) {
        Observable<BaseResponse<String>> observable = service.placeorder(activityId, buyerRemark, cartIds, channel, delyEndDate, delyFast, delyStartDate, shopId, userAddressId, voucherId);
        excuteRequest(observable, tag);
    }

    /**
     * 点餐 - 带走 - 绑定手机
     */
    @Override
    public void doOrderBindPhone(String phone, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.doOrderBindPhone(phone);
        excuteRequest(observable, tag);
    }

    /**
     * 获取下单价格 - 获取实际优惠后价格
     *
     * @param cart_ids      购物车所有商品
     * @param voucherId     优惠券id
     * @param stallsId      档口id
     */
    @Override
    public void doPayOrderPrice(String cart_ids, String voucherId, String stallsId, int tag) {
        Observable<BaseResponse<Order>> observable = service.doPayOrderPrice(cart_ids, voucherId, stallsId);
        excuteRequest(observable, tag);
    }

    /**
     * 用户 - 钱包 - 支付密码 - 是否认证
     */
    @Override
    public void userWalletPayPswIsAuthReq(int tag) {
        Observable<BaseResponse<ActiveInfo>> observable = service.userWalletPayPswIsAuthReq();
        excuteRequest(observable, tag);
    }

    /**
     * 点餐 - 下单 直接下单
     *
     */
    @Override
    public void doPayOrder(String bizId, int bizType, String payPwd, int thdType, int tag) {
        Observable<BaseResponse<String>> observable = service.doPayOrder(bizId, bizType, payPwd, thdType);
        excuteRequest(observable, tag);
    }

    @Override
    public void doPayOrder(String bizId, int bizType, int thdType, int tag) {
        Observable<BaseResponse<Order>> observable = service.doPayOrder(bizId, bizType, thdType);
        excuteRequest(observable, tag);
    }

    /**
     * 用户 - 钱包 - 支付密码 - 认证
     */
    @Override
    public void userWalletPayPswAuthReq(String pdPassword, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.userWalletPayPswAuthReq(pdPassword);
        excuteRequest(observable, tag);
    }

    /**
     * 七牛 - 请求TOKEN
     * @param tagQiniuTokenRequest
     */
    @Override
    public void getQiNiuTokenRequest(String bucketName,int tagQiniuTokenRequest) {
        Observable<BaseResponse<String>> observable = service.getQiNiuTokenRequest(bucketName);
        excuteRequest(observable, tagQiniuTokenRequest);
    }

    /**
     * 用户 - 修改头像
     */
    @Override
    public void upLoadUserIcon(RequestBody Body, int tag) {
        Observable<BaseResponse<String>> observable = service.upLoadUserIcon(Body);
        excuteRequest(observable, tag);
    }

    /**
     * 用户 - 消息列表
     */
    @Override
    public void getMessageList(int page, int pageSize, int tag) {
        Observable<BaseResponse<Pager<Message>>> observable = service.getMessageList(page, pageSize);
        excuteRequest(observable, tag);
    }


    /**
     * 用户 - 修改 - 性别
     */
    @Override
    public void doModifySex(String sex,int tagUserModifySex) {
        Observable<BaseResponse<Boolean>>observable = service.doModifySex(sex);
        excuteRequest(observable, tagUserModifySex);
    }

    /**
     * 修改 - 用户 - 昵称
     * @param nikeName
     */
    @Override
    public void doModifyNickName(String nikeName, int tagUserModifyNickName) {
        Observable<BaseResponse<Boolean>>observable = service.doModifyNickName(nikeName);
        excuteRequest(observable, tagUserModifyNickName);
    }

    /**
     * 用户 - 钱包 - 充值活动
     */
    @Override
    public void userPDActivity(int tag_user_pd_activity) {
        Observable<BaseResponse<List<Action>>>observable = service.userPDActivity();
        excuteRequest(observable, tag_user_pd_activity);
    }

    /**
     * 用户 - 钱包 - 预存款日志记录
     * @param type
     * @param page
     * @param pagesize
     */
    @Override
    public void userPredepositLogsReq(Integer type, Integer page, Integer pagesize, int tagUserWellatPredepositLogs) {
        Observable<BaseResponse<Pager<PredepositLog>>>observable = service.userPredepositLogsReq(type, page, pagesize);
        excuteRequest(observable, tagUserWellatPredepositLogs);
    }

    /**
     * 用户 - 钱包 - 支付密码 - 修改
     */
    @Override
    public void userWalletPayPswModifly(String oldPwd, String psw, int tag) {
        Observable<BaseResponse<String>>observable = service.userWalletPayPswModifly(oldPwd, psw);
        excuteRequest(observable, tag);
    }

    /**
     * 用户 - 钱包 - 支付密码 - 创建
     * @param pdPassword
     */
    @Override
    public void userWalletPayPswCreate(String pdPassword, int tagUserWellatCreate) {
        Observable<BaseResponse<Boolean>>observable = service.userWalletPayPswCreate(pdPassword);
        excuteRequest(observable, tagUserWellatCreate);
    }

    /**
     * 用户 - 钱包 - 支付密码 - 获取验证码
     * @param mobilePhone
     * @param post
     */
    @Override
    public void userWalletPayPswCode(String mobilePhone, String post, int tagUserWellatCode) {
        Observable<BaseResponse<Boolean>>observable = service.userWalletPayPswCode(mobilePhone,post);
        excuteRequest(observable, tagUserWellatCode);
    }

    /**
     * 用户 - 获取验证码
     * @param mobilePhone
     */
    @Override
    public void getRegisterCode(String codeType, String mobilePhone, int tagGetCode) {
        Observable<BaseResponse<Boolean>>observable = service.getRegisterCode(codeType, mobilePhone);
        excuteRequest(observable, tagGetCode);
    }



    //TODO.......Created By QiLi.........

    /**
     * 优惠券提现
     * @param tag
     */
    @Override
    public void rapGetUserCashCount(int tag) {
        Observable<BaseResponse<Integer>> observable = service.rapGetUserCashCount();
        excuteRequest(observable, tag);
    }

    /**
     *抢单 - 提现
     * @param tag
     */
    @Override
    public void rapDoUserCash(int tag) {
        Observable<BaseResponse<Boolean>> observable = service.rapDoUserCash();
        excuteRequest(observable, tag);
    }

    /**
     * 用户地址列表
     * @param tag
     */
    @Override
    public void getAdressList(int page, int pageSize, int tag) {
        Observable<BaseResponse<UserAddressListEntity>> observable = service.getAdressList(page, pageSize);
        excuteRequest(observable, tag);
    }

    /**
     * 删除地址
     * @param adressId
     * @param tag
     */
    @Override
    public void userDoAdressDelete(int adressId, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.userDoAdressDelete(adressId);
        excuteRequest(observable, tag);
    }

    /**
     * 用户-修改地址
     * @param addressId
     * @param sex
     * @param apartmentId
     * @param address
     * @param isDefault
     */
    @Override
    public void doModifyAdress(String address, int addressId, int apartmentId,int isDefault, String name, String phone, int sex, int tagUserAddressModify) {
        Observable<BaseResponse<Boolean>> observable = service.doModifyAdress(address, addressId, apartmentId, isDefault, name, phone, sex);
        excuteRequest(observable, tagUserAddressModify);
    }

    @Override
    public void setAddressDefault(int addressId, int tagSetAddressDefault) {
        Observable<BaseResponse<Boolean>> observable = service.setAddressDefault(addressId);
        excuteRequest(observable, tagSetAddressDefault);
    }

    /**
     * 点餐 - 获取学校列表和宿舍列表
     * @param schoolId
     * @param tag
     */
    @Override
    public void getApartmentBySchoolId(int schoolId, int tag) {
        Observable<BaseResponse<SchoolApartment>> observable = service.getApartmentBySchoolId(schoolId);
        excuteRequest(observable, tag);
    }

    /**
     * 用户-添加地址
     * @param trueName
     * @param sex
     * @param apartmentId
     * @param address
     * @param mobPhone
     * @param isDefault
     * @param tag
     */
    @Override
    public void doAddAdress(String trueName, int sex, int apartmentId, String address, String mobPhone, int isDefault, int tag) {
        Observable<BaseResponse<UserAddress>> observable = service.doAddAdress(trueName,sex+"",apartmentId,address,mobPhone,isDefault+"");
        excuteRequest(observable, tag);
    }

    /**
     * 用户 - 邀请好友
     * @param userId
     * @param tag
     */
    @Override
    public void getUserInviteInfo(String userId, int tag) {
        Observable<BaseResponse<InviteInfo>> observable = service.getUserInviteInfo(userId);
        excuteRequest(observable, tag);
    }

    /**
     * 用户 - 提交反馈信息
     * @param content
     * @param tag
     */
    @Override
    public void doUserFeedBack(String content, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.doUserFeedBack(content);
        excuteRequest(observable, tag);
    }

    /**
     * 用户-修改密码
     * @param oldpassword
     * @param password
     * @param tag
     */
    @Override
    public void doUserModifyPassword(String oldpassword, String password,int tag) {
        Observable<BaseResponse<Boolean>> observable = service.doUserModifyPassword(oldpassword,password);
        excuteRequest(observable, tag);
    }

    /**
     * 用户 - 钱包 -  充值
     */
    @Override
    public void doWellatPredeposit(int paymentId, double predeposit, int tag) {
        Observable<BaseResponse<PredepositOrder>> observable = service.doWellatPredeposit(paymentId, predeposit);
        excuteRequest(observable, tag);
    }

    /**
     * 用户-修改密码
     */
    @Override
    public void SignUpReq(int schoolId, String mobilePhone, String regcode, String password, Integer type, String invitationCode, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.SignUpReq(schoolId, mobilePhone, regcode, password, type, invitationCode);
        excuteRequest(observable, tag);
    }

    /**
     * 用户-注册
     */
    @Override
    public void doRegister(String invitationCode, String password, String phone, String regcode, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.doRegister(invitationCode, password, phone, regcode);
        excuteRequest(observable, tag);
    }

    /**
     * 取消订单
     */
    @Override
    public void orderDoCancal(String orderId, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.orderDoCancal(orderId);
        excuteRequest(observable, tag);
    }

    /**
     * 订单 - 确认收获
     * @param orderId
     */
    @Override
    public void userOrderComplete(String orderId, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.userOrderComplete(orderId);
        excuteRequest(observable, tag);
    }

    @Override
    public void userOrderHurry(String orderId, int tag) {
        Observable<BaseResponse<Integer>> observable = service.userOrderHurry(orderId);
        excuteRequest(observable, tag);
    }

    @Override
    public void setTlement(String cartIds, String shopId, int tag) {
        Observable<BaseResponse<VoucherList>> observable = service.setTlement(cartIds, shopId);
        excuteRequest(observable, tag);
    }

    /**
     * 订单 - 商品 - 评价
     */
    @Override
    public void userOrderGoodEvaluate(String goodId, int numStar, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.userOrderGoodEvaluate(goodId, numStar);
        excuteRequest(observable, tag);
    }

    @Override
    public void evaluate(String content, String orderId, int service1, int speed, int taste, int tag) {
        Observable<BaseResponse<String>> observable = service.evaluate(content, orderId, service1, speed, taste);
        excuteRequest(observable, tag);
    }

    /**
     * 选择学校 - 获取学校通过城市Id
     */
    @Override
    public void getSchoolByAreaId(int areaId, String areaName, int tag) {
        Observable<BaseResponse<List<AreaCity>>> observable = service.getSchoolByAreaId(areaId, areaName);
        excuteRequest(observable, tag);
    }

    /**
     * 点餐 - 指定学校的餐厅列表
     * @param schoolId 学校id
     */
    @Override
    public void getSchoolStorList(int schoolId, int tag) {
        Observable<BaseResponse<List<Store>>> observable = service.getSchoolStorList(schoolId);
        excuteRequest(observable, tag);
    }

    /**
     * 抢单 - 用户 - 认证
     *
     * @param phone       电话
     * @param certificate 证件照片地址
     * @param apartmentId 宿舍id
     * @param address     详细地址
     */
    @Override
    public void rapDoUserJoin(String name, String phone, String certificate, Integer apartmentId, String address, int tag) {
        Observable<BaseResponse<Boolean>> observable = service.rapDoUserJoin(name, phone, certificate, apartmentId, address);
        excuteRequest(observable, tag);
    }







}
