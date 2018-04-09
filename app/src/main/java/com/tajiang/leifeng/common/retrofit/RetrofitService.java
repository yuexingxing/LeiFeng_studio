package com.tajiang.leifeng.common.retrofit;

import com.tajiang.leifeng.common.http.BaseResponse;
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
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Admins on 2016/12/27.
 */
public interface RetrofitService {

    //TODO.......Created By QiLi.........

    /**
     * 登录接口
     * @param userName
     * @param psw
     * @return
     */
    @FormUrlEncoded
    @POST("/base/user/login")
    Observable<BaseResponse<TokenInfo>> doLogin(@Field("phone") String userName, @Field("password") String psw, @Field("clientId") String clientId, @Field("imei") String imei);

    /**
     * 用户退出接口
     * @return
     */
    @POST("/base/user/logout")
    Observable<BaseResponse<Boolean>> doLogout();

    /**
     * 订单-获取订单
     * @param page       页码
     * @param pageSize       页数
     */
    @POST("/buyer/order/list")
    Observable<BaseResponse<Pager<Order>>> getOrderList(@Query("page") int page,
                                                        @Query("pageSize") int pageSize);

    /**
     * 订单-获取订单详情
     */
    @POST("/buyer/order/detail")
    Observable<BaseResponse<Order>> getOrderDetail(@Query("orderId") String orderId);

    /**
     * 用户 - 登陆 - QQ登陆
     *
     * @param accessToken
     * @param uid
     */
    @POST("/base/user/thirdparty")
    Observable<BaseResponse<Boolean>> doLoginByQQ(@Query("accessToken") String accessToken,
                                                  @Query("authType") int authType,
                                                  @Query("clientId") String clientId,
                                                  @Query("imei") String imei,
                                                  @Query("uid") String uid);

    /**
     * 用户 - 登陆 - 微信登陆
     *
     * @param accessToken
     * @param uid
     */
    @POST("callback/wechat/auth")
    Observable<BaseResponse<Boolean>>  doLoginByWeiChat(@Query("accessToken") String accessToken, @Query("uid") String uid);

    /**
     * 用户 - 登陆 - 微博登陆
     *
     * @param accessToken
     * @param uid
     */
    @POST("callback/weibo/auth")
    Observable<BaseResponse<Boolean>>  doLoginByWeiBo(@Query("accessToken") String accessToken, @Query("uid") String uid);

    /**
     * 获取广告Banner图片
     *
     * @param adType 广告位id（app首页轮播图列表id:9）
     */
    @GET("/base/ad")
    Observable<BaseResponse<List<Adv>>>  getAdvList(@Query("schoolId")int schoolId, @Query("adType") int adType);

    /**
     * 获取食堂配送楼栋
     */
    @POST("/buyer/shop/list")
    Observable<BaseResponse<Pager<StoreStalls>>> getStalls(@Query("apartmentId") int apartmentId,
                                                              @Query("page") int page,
                                                              @Query("pageSize") int pageSize,
                                                              @Query("rangeCode") String rangeCode,
                                                              @Query("recomTag") int recomTag);

    /**
     * 获取头条跑马灯
     */
    @POST("/buyer/order/marquee")
    Observable<BaseResponse<List<Marquee>>> getMarquee(@Query("schoolId") int schoolId);

    /**
     * 获取主营业务列表
     */
    @POST("/buyer/shop/rangelist")
    Observable<BaseResponse<List<ChannelEntity>>> getRangelist();

    /**
     * 获取食堂配送楼栋
     * @param schoolId 配送学校Id
     */
    @POST("/buyer/school/apartmentlist")
    Observable<BaseResponse<List<StoreDelivered>>> getStoreDistributionRange(@Query("schoolId") int schoolId);

    /**
     * 选择学校 - 获取定位城市的学校
     * @param areaName 学校所在地区名字
     */
    @POST("/buyer/school/list")
    Observable<BaseResponse<List<AreaCity>>> getSchoolOfLocalCity(@Query("areaName") String areaName);

    /**
     * 学校 - LBS:附近学校
     */
    @FormUrlEncoded
    @POST("/buyer/school/nearschool")
    Observable<BaseResponse<List<School>>> getNearSchools(@Field("lat") double lat,
                                                          @Field("lng") double lng,
                                                          @Field("distance") int distance);
    /**
     * 学校 - 检索学校
     */
    @POST("/buyer/school/search")
    Observable<BaseResponse<List<School>>> getSchoolSearchList(@Query("schoolName") String schoolName);

    /**
     * 选择学校 - 获取热门城市列表
     */
    @POST("area/hotCity")
    Observable<BaseResponse<List<City>>> getCityHotList();

    /**
     * 选择学校 - 获取所有城市列表
     */
    @POST("/buyer/area/list")
    Observable<BaseResponse<CityData>> getCityList();

    /**
     * 获取APP信息
     */
    @GET("app/auth/androidVersion")
    Observable<BaseResponse<AppInfoResult>> getAppInfo();

    /**
     * 用户-收集手机信息
     * @param userId
     * @param  clientId
     * @param  os
     * @param  osVersion
     * @param  deviceModel
     * @param  appVersion
     */
    @POST("app/collectClientMsg")
    Observable<BaseResponse<Boolean>> collectClientMsg(@Query("userId") String userId,
                                                          @Query("clientId")String clientId,
                                                          @Query("os")int os,
                                                          @Query("osVersion")String osVersion,
                                                          @Query("deviceModel")String deviceModel,
                                                          @Query("appVersion")String appVersion);

    /**
     * 用户 - 信息
     */
    @GET("/base/user/current")
    Observable<BaseResponse<User>> userDoGetInfor();

    /**
     * 根据档口获取菜品分类
     * @param shopId 商家id
     */
    @POST("/buyer/shop/detail")
    Observable<BaseResponse<FoodClassList>> getStallsgoodsClass(@Query("shopId") String shopId);

    /**
     * 获取档口菜品信息
     * @param shopId 档口Id
     * @param goodsClassId
     * @param page
     * @param pageSize
     */
    @POST("/buyer/goods/list")
        Observable<BaseResponse<Pager<Goods>>> getStallsGoods(@Query("apartmentId") int apartmentId,
                                                              @Query("shopId") String shopId,
                                                              @Query("goodsClassId") String goodsClassId,
                                                              @Query("page")int page,
                                                              @Query("pageSize")int pageSize,
                                                              @Query("hotTag") int hotTag);




    /**
     * 用户-下单获取可用红包
     */
    @GET("/base/activity/redpacket/queryuserredpacketlist")
    Observable<BaseResponse<VoucherList>> getUserCoupon();

    /**
     * 点餐 - 预计到达时间
     * @param shopId
     */
    @POST("/buyer/shop/worktime")
    Observable<BaseResponse<WorkTimeList>> getEstimatedTime(@Query("shopId") String shopId);


    /**
     * 订单-获取备注标签
     */
    @GET("app/auth/showexplainlabel")
    Observable<BaseResponse<String>> showexplainlabelReq();

    /**
     * 订单-确认下单
     */
    @POST("/buyer/order/placeorder")
    Observable<BaseResponse<String>> placeorder(@Query("activityId") Integer activityId,
                                                @Query("buyerRemark") String buyerRemark,
                                                @Query("cartIds") String cartIds,
                                                @Query("channel") String channel,
                                                @Query("delyEndDate") String delyEndDate,
                                                @Query("delyFast") int delyFast,
                                                @Query("delyStartDate") String delyStartDate,
                                                @Query("shopId") String shopId,
                                                @Query("userAddressId") int userAddressId,
                                                @Query("voucherId") Integer voucherId);

    /**
     * 点餐 - 带走 - 绑定手机
     */
    @POST("bindPhone")
    Observable<BaseResponse<Boolean>> doOrderBindPhone(@Query("phone") String phone);

    /**
     * 获取下单价格 - 获取实际优惠后价格
     *
     * @param cart_ids      购物车所有商品
     * @param voucherId     优惠券id
     * @param stallsId      档口id
     */
    @POST("order/settlement")
    Observable<BaseResponse<Order>> doPayOrderPrice(@Query("cart_ids") String cart_ids,
                                                       @Query("voucherId")String voucherId,
                                                       @Query("stallsId")String stallsId);

    /**
     * 用户 - 钱包 - 支付密码 - 是否认证
     */
    @GET("/base/acct/info")
    Observable<BaseResponse<ActiveInfo>> userWalletPayPswIsAuthReq();

    /**
     * 点餐 - 下单 直接下单
     *
     */
    @POST("/base/acct/payinfo")
    Observable<BaseResponse<String>> doPayOrder(
                                               @Query("bizId") String bizId,
                                               @Query("bizType")int bizType,
                                               @Query("payPwd")String payPwd,
                                               @Query("thdType")int thdType);


    /**
     * 点餐 - 支付-微信，支付宝
     *
     */
    @POST("/base/acct/payinfo")
    Observable<BaseResponse<Order>> doPayOrder(
                                                @Query("bizId") String bizId,
                                                @Query("bizType")int bizType,
                                                @Query("thdType")int thdType);

    /**
     * 用户 - 钱包 - 支付密码 - 认证
     */
    @POST("pdauth/auth")
    Observable<BaseResponse<Boolean>> userWalletPayPswAuthReq(@Query("pdPassword")String pdPassword);



    //TODO.......Created By QiLi.........

    /**
     * 七牛 - 请求TOKEN
     * @return
     */
    @POST("getQiniuToken")
    Observable<BaseResponse<String>> getQiNiuTokenRequest(@Query("bucketName") String bucketName);

    /**
     * 用户 - 修改 - 头像
     * @param avatarOldKey
     * @param avatarNewKey
     * @return
     */
    @POST("modifyAvatar")
    Observable<BaseResponse<String>> doModifyAvatar(@Query("avatarOldKey") String avatarOldKey,@Query("avatarNewKey") String avatarNewKey);


    /**
     * 用户 - 修改 - 头像
     * @return
     */
    @POST("/base/user/avatar")
    Observable<BaseResponse<String>> upLoadUserIcon(@Body RequestBody Body);

    /**
     * 用户 - 消息列表
     * @return
     */
    @GET("/base/msg/user/list")
    Observable<BaseResponse<Pager<Message>>> getMessageList(@Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 用户 - 修改 - 性别
     * @param sex
     * @return
     */
    @POST("modifySex")
    Observable<BaseResponse<Boolean>> doModifySex(@Query("sex") String sex);

    /**
     * 用户 - 修改 - 昵称
     * @param nikeName
     * @return
     */
    @POST("/base/user/nick")
    Observable<BaseResponse<Boolean>> doModifyNickName(@Query("nickname") String nikeName);


    /**
     * 用户 - 钱包 - 充值活动
     * @return
     */
    @GET("pdr/pdActivity")
    Observable<BaseResponse<List<Action>>> userPDActivity();

    /**
     *用户 - 钱包 - 预存款日志记录
     * @param type
     * @param page
     * @param pagesize
     * @return
     */
    @GET("new/predeposit/logs")
    Observable<BaseResponse<Pager<PredepositLog>>> userPredepositLogsReq(@Query("type") Integer type,
                                                                         @Query("page") Integer page,
                                                                         @Query("pagesize") Integer pagesize);

    /**
     * 用户 - 钱包 - 支付密码 - 修改
     * @return
     */
    @POST("/base/acct/updatepwd")
    Observable<BaseResponse<String>> userWalletPayPswModifly(@Query("oldPwd") String oldPwd, @Query("newPwd") String newPwd);

    /**
     * 用户 - 钱包 - 支付密码 - 创建
     * @param pdPassword
     * @return
     */
    @POST("/base/acct/setpwd")
    Observable<BaseResponse<Boolean>> userWalletPayPswCreate(@Query("payPwd") String pdPassword);

    /**
     *用户 - 钱包 - 支付密码 - 获取验证码
     * @param mobilePhone
     * @param post
     * @return
     */
    @POST("pdauth/verifyregcode")
    Observable<BaseResponse<Boolean>> userWalletPayPswCode(@Query("mobilePhone") String mobilePhone, @Query("regcode") String post);

    /**
     * 用户 - 获取验证码
     * @param mobilePhone
     * @return
     */
    @POST("/base/msg/code")
    Observable<BaseResponse<Boolean>> getRegisterCode(@Query("codeType") String codeType, @Query("phone") String mobilePhone);


    /**
     * 优惠券提现
     * @return
     */
    @GET("getCash")
    Observable<BaseResponse<Integer>> rapGetUserCashCount();

    /**
     * 抢单 - 提现
     * @return
     */
    @GET("withdrawCash")
    Observable<BaseResponse<Boolean>> rapDoUserCash();

    /**
     * 用户地址列表
     * @return
     */
    @GET("/base/user/address")
    Observable<BaseResponse<UserAddressListEntity>> getAdressList(@Query("page") int page, @Query("pageSize") int pageSize);

    /**
     * 删除地址
     * @param addressId
     * @return
     */
    @DELETE("/base/user/address")
    Observable<BaseResponse<Boolean>> userDoAdressDelete(@Query("addressId") int addressId);

    /**
     * 用户-修改地址
     * @param addressId
     * @param sex
     * @param apartmentId
     * @param address
     * @param isDefault
     * @return
     */
    @PUT("/base/user/address")
    Observable<BaseResponse<Boolean>> doModifyAdress(@Query("address") String address,
                                                     @Query("addressId") int addressId,
                                                     @Query("apartmentId") int apartmentId,
                                                     @Query("isDefault") int isDefault,
                                                     @Query("name") String name,
                                                     @Query("phone") String phone,
                                                     @Query("sex") int sex);

    /**
     * 用户-设置默认地址
     * @param addressId
     * @return
     */
    @POST("/base/user/address/default")
    Observable<BaseResponse<Boolean>> setAddressDefault(@Query("addressId") int addressId);

    /**
     * 点餐 - 获取学校列表和宿舍列表
     * @param schoolId
     * @return
     */
    @GET("school/schoolApartmentZones")
    Observable<BaseResponse<SchoolApartment>> getApartmentBySchoolId(@Query("schoolId") int schoolId);

    /**
     * 用户-添加地址
     * @param sex
     * @param apartmentId
     * @param address
     * @param mobPhone
     * @param isDefault
     * @return
     */
    @POST("/base/user/address")
    Observable<BaseResponse<UserAddress>> doAddAdress(@Query("name") String name,
                                                      @Query("sex") String sex,
                                                      @Query("apartmentId") int apartmentId,
                                                      @Query("address") String address,
                                                      @Query("phone") String mobPhone,
                                                      @Query("isDefault") String isDefault);

    /**
     * 用户 - 邀请好友
     * @param userId
     * @return
     */
    @GET("invitefriends/invite")
    Observable<BaseResponse<InviteInfo>> getUserInviteInfo(@Query("userId") String userId);

    /**
     * 用户 - 提交反馈信息
     * @param content
     * @return
     */
    @POST("/base/user/feedback")
    Observable<BaseResponse<Boolean>> doUserFeedBack(@Query("content") String content);

    /**
     * 用户 - 修改密码
     * @param oldpassword
     * @param password
     * @return
     */
    @POST("/base/user/pwd/modify")
    Observable<BaseResponse<Boolean>> doUserModifyPassword(@Query("oldPwd") String oldpassword,
                                                           @Query("newPwd") String password);


    /**
     * 用户 - 钱包 -  充值
     */
    @POST("predeposit")
    Observable<BaseResponse<PredepositOrder>> doWellatPredeposit(@Query("paymentId") int paymentId,
                                                                 @Query("predeposit") double predeposit);

    /**
     * 用户-修改密码
     */
    @POST("newsignup")
    Observable<BaseResponse<Boolean>> SignUpReq(@Query("schoolId") int schoolId,
                                                @Query("mobilePhone") String mobilePhone,
                                                @Query("regcode") String regcode,
                                                @Query("password") String password,
                                                @Query("type") int type,
                                                @Query("invitationCode") String invitationCode);

    /**
     * 用户-注册
     */
    @POST("/base/user/register")
    Observable<BaseResponse<Boolean>> doRegister(@Query("inviteCode") String invitationCode, @Query("password") String password, @Query("phone") String phone,@Query("verificationCode") String regcode);

    /**
     * 取消订单
     */
    @POST("/buyer/order/cancel")
    Observable<BaseResponse<Boolean>> orderDoCancal(@Query("orderId") String orderId);

    /**

     * 订单 - 确认收获
     * @param orderId
     */
    @POST("/buyer/order/complete")
    Observable<BaseResponse<Boolean>> userOrderComplete(@Query("orderId") String orderId);

    /**
     * 订单 - 催单
     */
    @POST("/buyer/order/reminder")
    Observable<BaseResponse<Integer>> userOrderHurry(@Query("orderId") String orderId);

    /**

     * 订单 - 获取结算订单详情
     */
    @POST("/buyer/order/settlement")
    Observable<BaseResponse<VoucherList>> setTlement(@Query("cartIds") String cartIds, @Query("shopId") String shopId);

    /**
     * 订单 - 商品 - 评价
     */
    @POST("order/orderGoods/{goodId}")
    Observable<BaseResponse<Boolean>> userOrderGoodEvaluate(@Path("orderId") String goodId, @Query("scores") int scores) ;

    /**
     * 订单 - 商品 - 评价
     */
    @POST("/buyer/order/evaluate")
    Observable<BaseResponse<String>> evaluate(@Query("content")String content,
                                                           @Query("orderId")String orderId,
                                                           @Query("service")int service,
                                                           @Query("speed")int speed,
                                                           @Query("taste")int taste) ;

    /**
     * 选择学校 - 获取学校通过城市Id
     */
    @GET("school/acquisitionAreaSchool")
    Observable<BaseResponse<List<AreaCity>>> getSchoolByAreaId(@Query("areaId") int areaId, @Query("areaName") String areaName);

    /**
     * 点餐 - 指定学校的餐厅列表
     * @param schoolId 学校id
     */
    @GET("school/stores")
    Observable<BaseResponse<List<Store>>> getSchoolStorList(@Query("schoolId") int schoolId);

    /**
     * 抢单 - 用户 - 认证
     *
     * @param phone       电话
     * @param certificate 证件照片地址
     * @param apartmentId 宿舍id
     * @param address     详细地址
     */
    @POST("userjoinin")
    Observable<BaseResponse<Boolean>> rapDoUserJoin(@Query("name") String name,
                                                    @Query("phone") String phone,
                                                    @Query("certificate") String certificate,
                                                    @Query("apartmentId") int apartmentId,
                                                    @Query("address") String address);



}