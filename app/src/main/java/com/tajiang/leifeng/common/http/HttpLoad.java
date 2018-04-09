package com.tajiang.leifeng.common.http;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Admins on 2016/12/27.
 */
public interface HttpLoad {

    //TODO.......Created By QiLi.........

    /**
     * 取消当前网络请求
     */
    void cancel();


    /**
     * 用户登录
     * @param userName
     * @param psw
     */
    void doLogin(String userName, String psw, String clientId,String imei,int tag);

    /**
     * 用户退出登录
     */
    void doLogout(int tag);

    /**
     * 订单-获取订单
     * @param page       页码
     * @param pageSize       页数
     */
    void getOrderList(int page, int pageSize, int tag);

    /**
     * 订单-获取订单详情
     */
    void getOrderDetail(String orderId, int tag);

    /**
     * 用户 - 登陆 - QQ登陆
     *
     * @param accessToken
     * @param uid
     */
    void doLoginByQQ(String accessToken, int authType, String clientId, String imei, String uid, int tag);

    /**
     * 用户 - 登陆 - 微信登陆
     *
     * @param accessToken
     * @param uid
     */
    void  doLoginByWeiChat( String accessToken, String uid, int tag);

    /**
     * 用户 - 登陆 - 微博登陆
     *
     * @param accessToken
     * @param uid
     */
    void  doLoginByWeiBo( String accessToken,  String uid, int tag);

    /**
     * 获取广告Banner图片
     *
     * @param apId 广告位id（app首页轮播图列表id:9）
     */
    void  getAdvList(int schoolId, int apId, int tag);

    /**
     * 获取食堂配送楼栋
     */
    void getStalls(int apartmentId, int page, int pageSize, String rangeCode, int recomTag, int tag);

    /**
     * 获取头条跑马灯
     */
    void getMarquee(int schoolId, int tag);

    /**
     * 获取主营业务列表
     */
    void getRangelist(int tag);

    /**
     * 获取食堂配送楼栋
     * @param schoolId 配送学校Id
     */
    void getStoreDistributionRange(int schoolId, int tag);

    /**
     * 选择学校 - 获取定位城市的学校
     * @param areaName 学校所在地区名字
     */
    void getSchoolOfLocalCity(String areaName, int tag);

    /**
     * 学校 - LBS:附近学校
     */
    void getNearSchools(double lat, double lng, int distance, int tag);

    /**
     * 学校 - 检索学校
     */
    void getSchoolSearchList(String schoolName, int tag);

    /**
     * 选择学校 - 获取热门城市列表
     */
    void getCityHotList(int tag);

    /**
     * 选择学校 - 获取所有城市列表
     */
    void getCityList(int tag);


    void getAppInfo(int tag);

    /**
     * 用户-收集手机信息
     * @param userId
     * @param  clientId
     * @param  os
     * @param  osVersion
     * @param  deviceModel
     * @param  appVersion
     */
    void collectClientMsg( String userId, String clientId, int os, String osVersion, String deviceModel, String appVersion, int tag);

    /**
     * 用户 - 信息
     */
    void userDoGetInfor(int tag);


    /**
     * 根据档口获取菜品分类
     * @param stallsId 配送学校Id
     */
    void getStallsgoodsClass(String stallsId, int tag);


    /**
     * 获取档口菜品信息
     * @param shopId 档口Id
     * @param goodsClassId
     * @param page
     * @param pageSize
     */
     void getStallsGoods(int apartmentId, String shopId, String goodsClassId, int page, int pageSize, int hotTag, int tag);

    /**
     * 用户-下单获取可用红包
     */
    void getUserCoupon(int tag);

    /**
     * 点餐 - 预计到达时间
     */
    void getEstimatedTime(String shopId, int tag);

    /**
     * 订单-获取备注标签
     */
    void showexplainlabelReq(int tag);

    /**
     * 订单-确认下单
     */
    void placeorder(Integer activityId, String buyerRemark, String cartIds, String channel, String delyEndDate, int delyFast, String delyStartDate, String shopId, int userAddressId, Integer voucherId, int tag);

    /**
     * 点餐 - 带走 - 绑定手机
     */
    void doOrderBindPhone(String phone, int tag);

    /**
     * 获取下单价格 - 获取实际优惠后价格
     *
     * @param cart_ids      购物车所有商品
     * @param voucherId     优惠券id
     * @param stallsId      档口id
     */
    void doPayOrderPrice(String cart_ids, String voucherId, String stallsId, int tag);


    /**
     * 用户 - 钱包 - 支付密码 - 是否认证
     */
    void userWalletPayPswIsAuthReq(int tag);

    /**
     * 点餐 - 下单直接下单
     *
     * @param bizId   业务ID(如订单号)
     * @param bizType 业务类型
     * @param payPwd     支付密码
     * @param thdType     支付方式
     */
    void doPayOrder(String bizId, int bizType, String payPwd, int thdType, int tag);

    /**
     * 支付-微信，支付宝
     *
     * @param bizId   业务ID(如订单号)
     * @param bizType 业务类型
     * @param thdType     支付方式
     */
    void doPayOrder(String bizId, int bizType, int thdType, int tag);

    /**
     * 用户 - 钱包 - 支付密码 - 认证
     */
    void userWalletPayPswAuthReq(String pdPassword, int tag);

    //TODO.......Created By QiLi.........

    /**
     * 七牛 - 请求TOKEN
     */
    void getQiNiuTokenRequest(String bucketName,int tag);


    /**
     * 用户 - 修改 - 头像
     * @return
     */
    void upLoadUserIcon(RequestBody Body, int tag);

    /**
     * 用户 - 消息列表
     * @return
     */
    void getMessageList(int page, int pageSize,int tag);

    /**
     * 用户 - 修改 - 性别
     */
    void doModifySex(String sex,int tag);

    /**
     * 用户 - 修改 - 昵称
     * @param nikeName
     * @param tag
     */
    void doModifyNickName(String nikeName, int tag);

    /**
     * 用户 - 钱包 - 充值活动
     * @param tag
     */
    void userPDActivity(int tag);

    /**
     * 用户 - 钱包 - 预存款日志记录
     * @param type
     * @param page
     * @param pagesize
     * @param tag
     */
    void userPredepositLogsReq(Integer type, Integer page, Integer pagesize, int tag);

    /**
     * 用户 - 钱包 - 支付密码 - 修改
     * @param psw
     * @param tag
     */
    void userWalletPayPswModifly(String oldPwd, String psw, int tag);

    /**
     * 用户 - 钱包 - 支付密码 - 创建
     * @param psw
     * @param tag
     */
    void userWalletPayPswCreate(String psw, int tag);

    /**
     * 用户 - 钱包 - 支付密码 - 获取验证码
     * @param phone
     * @param code
     * @param tag
     */
    void userWalletPayPswCode(String phone, String code, int tag);

    /**
     * 用户 - 获取验证码
     * @param phone
     * @param tag
     */
    void getRegisterCode(String codeType, String phone, int tag);

    /**
     * 优惠券提现
     * @param tag
     */
    void rapGetUserCashCount(int tag);

    /**
     * 抢单 - 提现
     * @param tag
     */
    void rapDoUserCash(int tag);

    /**
     * 获取地址列表
     * @param tag
     */
    void getAdressList(int page, int pageSize, int tag);

    /**
     * 删除地址
     * @param id
     * @param tag
     */
    void userDoAdressDelete(int id, int tag);

    /**
     * 用户-修改地址
     * @param addressId
     * @param apartmentId
     * @param address
     * @param tagUserAddressModify
     */
    void doModifyAdress(String address, int addressId, int apartmentId,int isDefault, String name, String phone, int sex, int tagUserAddressModify);

    /**
     * 用户-设置默认地址
     * @param addressId
     */
    void setAddressDefault (int addressId, int tagSetAddressDefault);

    /**
     * 点餐 - 获取学校列表和宿舍列表
     * @param schoolId
     * @param tagGetSchoolApartmentList
     */
    void getApartmentBySchoolId(int schoolId, int tagGetSchoolApartmentList);

    /**
     * 用户 - 收货地址
     * @param trueName
     * @param sex
     * @param apartmentId
     * @param address
     * @param mobPhone
     * @param isDefault
     * @param tagUserAddressAdd
     */
    void doAddAdress(String trueName, int sex, int apartmentId, String address, String mobPhone, int isDefault, int tagUserAddressAdd);

    /**
     * 用户 - 邀请好友
     * @param userId
     * @param tagUserInvite
     */
    void getUserInviteInfo(String userId, int tagUserInvite);

    /**
     * 用户 - 提交反馈信息
     * @param content
     * @param tagUserFeedBack
     */
    void doUserFeedBack(String content, int tagUserFeedBack);

    /**
     *用户 - 修改密码
     * @param oldpassword
     * @param password
     * @param tag
     */
    void doUserModifyPassword(String oldpassword, String password,int tag);


    /**
     * 用户 - 钱包 -  充值
     */
    void doWellatPredeposit(int paymentId, double predeposit, int tag);


    /**
     * 用户-修改密码
     */
    void  SignUpReq(int schoolId, String mobilePhone, String regcode,
                     String password, Integer type, String invitationCode, int tag);

    /**
     * 用户-注册
     */
    void  doRegister(String invitationCode, String password, String phone, String regcode, int tag);

    /**
     * 取消订单
     */
    void orderDoCancal(String orderId, int tag);

    /**
     * 订单 - 确认收获
     * @param orderId
     */
    void userOrderComplete(String orderId, int tag);

    /**
     * 订单 - 催单
     * @param orderId
     */
    void userOrderHurry(String orderId, int tag);

    /**
     * 订单 - 获取结算订单详情
     */
    void setTlement(String cartIds, String shopId, int tag);

    /**
     * 订单 - 商品 - 评价
     */
    void userOrderGoodEvaluate(String goodId, int numStar, int tag);

    /*
    * 评价
    * */
    void evaluate(String content, String orderId, int service, int speed, int taste, int tag);

    /**
     * 选择学校 - 获取学校通过城市Id
     */
    void getSchoolByAreaId(int areaId, String areaName, int tag);

    /**
     * 点餐 - 指定学校的餐厅列表
     * @param schoolId 学校id
     */
    void getSchoolStorList(int schoolId, int tag);

    /**
     * 抢单 - 用户 - 认证
     *
     * @param phone       电话
     * @param certificate 证件照片地址
     * @param apartmentId 宿舍id
     * @param address     详细地址
     */
    void rapDoUserJoin(String name, String phone, String certificate, Integer apartmentId, String address, int tag);




}
