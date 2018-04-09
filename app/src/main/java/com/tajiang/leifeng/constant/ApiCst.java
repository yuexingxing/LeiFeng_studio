package com.tajiang.leifeng.constant;

import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;

public class ApiCst {

    // 正式服务器
    public static final String HOST = "rest.itajiang.com";
    // 测试服务器
    public static final String HOST_TEST = "test.itajiang.com:8888";
    //大兄弟本地服务器
    public static final String BIG_BROTHER = "192.168.0.59:8888";
    public static final String H_BROTHER = "192.168.0.81:8889";

//    public static final String ZD_TEST = "121.196.217.158:9001";
//        public static final String ZD_TEST = "apipre.axpapp.com";
      public static final String ZD_TEST = "apiwmdev.axpapp.com";
//        public static final String ZD_TEST = "apipre.itajiang.com";
//    private static final String HOST_TEST = "192.168.0.14:8889";

    public static String DEV_AppKey = "56880eac0528297a62db334ea9516410";// 测试库APPKEY
//    public static String PRE_AppKey = "56880eac0528297a62db334ea9516410";// 预发布APPKEY
//    public static String FORMAL_AppKey = "t6880eac0528w97a62db334ea9v16g10";// 正式 APPKEY


    //TODO 线上版本请使用此URL
//    public static final String ROOT_URL = TJApp.isDebug ? "https://" + HOST_TEST + "/taj/" : "https://" + HOST + "/taj/";

    //TODO 线下测试版本请使用此URL
    public static String ROOT_URL = "http://" + SharedPreferencesUtils.get(TJApp.getIns().getApplicationContext(),
            SharedPreferencesUtils.HOST_TEST_ROOT_URL, ZD_TEST);

    /**
     * 获取服务器版本信息
     */
    public static final String APP_INFO = ROOT_URL + "app/auth/androidVersion";

    /**
     * 广告：app首页轮播图列表
     */
    public static final String ADV = ROOT_URL + "adv";

    /**
     * 百度推送
     */
    public static final String PUSH_CHANNEL_ID = ROOT_URL + "bindChannelId";

    /**
     * 附近学校
     */
    public static final String NEAR_SCHOOLS = ROOT_URL + "lbs/nearschools";

    /**
     * 开通学校的地区列表
     */
    public static final String OPEN_AREA = ROOT_URL + "area/openarea";

    /**
     * 选学校 - 定位城市的学校列表
     */
    public static final String SCHOOL_OF_LOACAL_CITY = ROOT_URL + "lbs/nearCitySchool";

    /**
     * 选学校 - 获取城市列表
     */
    public static final String SCHOOL_GET_CITY = ROOT_URL + "area/getCity";

    /**
     * 选学校 - 获取热门城市
     */
    public static final String SCHOOL_GET_CITY_HOT = ROOT_URL + "area/hotCity";    /**

     * 选学校 - 获取制定区域的学校
     */
    public static final String SCHOOL_GET_BY_AREA_ID = ROOT_URL + "school/acquisitionAreaSchool";

    /**
     * 学校
     */
    public static final String SCHOOL = ROOT_URL + "school";

    /**
     * 学校 - 套餐：指定id套餐
     */
    public static final String GOODS = ROOT_URL + "goods";

    /**
     * 学校 - 宿舍列表
     */
    public static final String SCHOOL_GET_APARTMENT_LIST = ROOT_URL + "school/schoolApartmentZones";
    /**
     * 学校 - 指定学校的餐厅列表
     */
    public static final String SCHOOL_STORE_LIST = ROOT_URL + "school/stores";
    /**
     * 学校 - 餐厅:套餐列表
     */
    public static final String STORE = ROOT_URL + "store";

    /**
     * 学校 - 餐厅:套餐列表V2
     */
    public static final String SCHOOL_STORE_LIST_V2 = ROOT_URL + "school/v2/schoolStoreList";

    /**
     * 学校 - 收索
     */
    public static final String SCHOOL_SEARCH = ROOT_URL + "school/search";

    /**
     * 点餐 - 分类
     */
    public static final String FOOD_SELECT_SCREEN = ROOT_URL + "goods/getgoodsClass";
    /**
     * 老版本
     * 点餐 - 分类
     */
//    public static final String FOOD_SELECT_SCREEN = ROOT_URL + "goods/goodsClass";

    /**
     * 抢单 - 优惠金额
     */
    public static final String ORDER_RAP_COUPON_MONEY = ROOT_URL + "app/voucherTip";
    /**
     * 抢单 - 提现
     */
    public static final String ORDER_RAP_USER_CASH = ROOT_URL + "withdrawCash";

    /**
     * 抢单 - 申请认证带餐
     */
    public static final String ORDER_RAP_USER_JOIN = ROOT_URL + "userjoinin";
    /**
     * 抢单 - 可体现金额
     */
    public static final String ORDER_RAP_USER_CASH_COUNT = ROOT_URL + "getCash";
    /**
     * 抢单 - 订单池
     */
    public static final String ORDER_RAP_ORDER_POOL = ROOT_URL + "order/orderPool";

    /**
     * 用户 - 手机注册以及忘记密码
     */
    public static final String SIGNUP = ROOT_URL + "newsignup";

    /**
     * 用户 - 获取优惠券：列表
     */
    public static final String USER_VOUCHER = ROOT_URL + "voucher";

    /**
     * 用户 - 获取验证码
     */
    public static final String USER_REGCODE = ROOT_URL + "regcode";

    /**
     * 用户 - 获取消息
     */
    public static final String USER_MESSAGE = ROOT_URL + "message";

    /**
     * 用户 - 收货地址
     */
    public static final String USER_ADDRESS = ROOT_URL + "userAddress";

    /**
     * 用户 - 收货地址：地址数量
     */
    public static final String USER_ADDRESS_COUNT = ROOT_URL + "userAddress/count";

    /**
     * 用户 - 收货地址：获得默认地址
     */
    public static final String USER_ADDRESS_DEFAULT = ROOT_URL + "userAddress/default";

    /**
     * 用户 - 登录接口
     */
    public static final String USER_SIGNIN_CHECK = ROOT_URL + "signin_check";

    /**
     * 用户 - 收集手机信息
     */
    public static final String COLLECT_CLIENT_MSG = ROOT_URL + "app/collectClientMsg";

    /**
     * 用户 - 登陆 - QQ登陆
     */
    public static final String QQ_AUTH = ROOT_URL + "callback/qq/auth";

    /**
     * 用户 - 登陆 - 新浪登陆
     */
    public static final String WEIBO_AUTH = ROOT_URL + "callback/weibo/auth";

    /**
     * 用户 - 登陆 - 新浪登陆
     */
    public static final String WECHAT_AUTH = ROOT_URL + "callback/wechat/auth";

    /**
     * 用户 - 登陆 - 微信 - 获取access_token
     */
    public static final String WEIXIN_AUTH_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 用户 - 手机注册以及忘记密码
     */
    public static final String USER_SIGNUP = ROOT_URL + "signup";
    /**
     * 用户 - 修改密码
     */
    public static final String USER_MODIFY_PASSWORD = ROOT_URL + "modifyPassword";
    /**
     * 用户 - 修改昵称
     */
    public static final String USER_MODIFY_NICK_NAME = ROOT_URL + "modifyNickname";
    /**
     * 用户 - 修改头像
     */
    public static final String USER_MODIFY_AVATAR = ROOT_URL + "modifyAvatar";
    /**
     * 用户 - 修改
     */
    public static final String USER_MODIFY_SEX = ROOT_URL + "modifySex";
    /**
     * 用户 - 获取用户信息
     */
    public static final String USER_FEED_BACK = ROOT_URL + "feedback";

    /**
     * 用户 - 获取用户信息
     */
    public static final String USER_INFO = ROOT_URL + "myInfo";


    /**
     * 用户 - 钱包 - 充值活动
     */
    public static final String USER_PD_ACTIVITY = ROOT_URL + "pdr/pdActivity";

    /**
     * 用户 - 钱包 - 预存款日志记录
     */
    public static final String USER_PREDEPOSIT_LOGS = ROOT_URL + "/new/predeposit/logs";

    /**
     * 用户 - 钱包 - 预存款日志记录
     */
    public static final String USER_PREDEPOSIT = ROOT_URL + "predeposit";

    /**
     * 用户 - 钱包 - 支付密码 - 修改
     */
    public static final String USER_WELLAT_PAY_PSD_MODIFY = ROOT_URL + "pdauth/updatepassword";

    /**
     * 用户 - 钱包 - 支付密码 - 创建
     */
    public static final String USER_WELLAT_PAY_PSD_CREATE = ROOT_URL + "pdauth";

    /**
     * 用户 - 钱包 - 支付密码 - 认证
     */
    public static final String USER_WELLAT_PAY_PSD_AUTH = ROOT_URL + "pdauth/auth";

    /**
     * 用户 - 钱包 - 支付密码 - 是否设置过
     */
    public static final String USER_WELLAT_PAY_PSD_IS_AUTH = ROOT_URL + "pdauth/isAuth";

    /**
     * 用户 - 钱包 - 支付密码 - 获取验证码
     */
    public static final String USER_WELLAT_PAY_PSD_CODE = ROOT_URL + "pdauth/verifyregcode";

    /**
     * 用户 - 邀请好友
     */
    public static final String USER_INVITE = ROOT_URL + "/invitefriends/invite";

    /**
     * 用户 - 用户下单提醒
     */
    public static final String USER_REMINDER_TIPS = ROOT_URL + "app/fastigium";

    /**
     * 订单：获取订单
     */
    public static final String ORDER = ROOT_URL + "order";

    /**
     * 订单：下单
     */
//    public static final String ORDER_PAY = ROOT_URL + "order/v2";
    public static final String ORDER_PAY = ROOT_URL + "order/placeOrder";

    /**
     * 订单：下单
     */
    public static final String ORDER_PAY_PRICE = ROOT_URL + "order/settlement";

    /**
     * 订单：取消
     */
    public static final String ORDER_CANCEL = ROOT_URL + "order/cancelOrder";
    /**
     * 下单 - 服务费
     */
    public static final String ORDER_SHIPPING_FEE = ROOT_URL + "app/auth/shippingFee";

    /**
     * 下单(新)- 服务费
     */
    public static final String ORDER_GET_SHIPPING_FEE = ROOT_URL + "app/auth/getshippingFee";
    /**
     * 下单 - 添加号码
     */
    public static final String ORDER_BIND_PHONE = ROOT_URL + "bindPhone";

    /**
     * 下单 - 带饭
     */
    public static final String TAKER = ROOT_URL + "taker";
    /**
     * 下单 - 获取备注标签
     */
    public static final String ORDER_SHOW_EXPLAIN_LABEL = ROOT_URL + "app/auth/showexplainlabel";
    /**
     * 下单 - 获取备注标签
     */
    public static final String ORDER_PAY_TAG = ROOT_URL + "order/payOrder";

    /**
     * 下单 - 预计到达时间
     */
    public static final String ORDER_ESTIMATED_TIME = ROOT_URL + "app/estimatedtime";

    /**暂时没用的接口*/
    /**
     * 用户 - 个人记录（浏览的餐厅记录...）
     */
    public static final String USER_RECORDS = ROOT_URL + "userRecords";

    /**
     * 获取七牛Token
     */
    public static final String GET_QINIU_TOKEN = ROOT_URL + "getQiniuToken";

    /**
     * 用户-下单获取可用红包
     */
    public static final String GET_USER_COUPON = ROOT_URL + "coupon";

    /**
     * 用户-下单获取可用红包
     */
    public static final String GET_STORE_DELIVERED_APARTMENT = ROOT_URL + "store/getStoreDistributionRange";

    /**
     * 用户-根据食堂获取食堂下的档口
     */
    public static final String GET_STORE_STALLS = ROOT_URL + "school/getStalls";

    /**
     * 用户-根据档口ID，获取档口下的菜品类别列表
     */
    public static final String GET_STORE_STALLS_FOOD_CLASS = ROOT_URL + "goods/getStallsgoodsClass";

    /**
     * 用户-根据档口ID，获取档口下的菜单列表
     */
    public static final String GET_STORE_STALLS_GOODS = ROOT_URL + "goods/getStallsGoods";

}
