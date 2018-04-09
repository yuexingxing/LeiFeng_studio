package com.tajiang.leifeng.common.http;

/**
 * Created by Admins on 2016/12/28.
 */
public class TJRequestTag {

    public final static int TAG_DEFAULT = -1;
    /**
     * 七牛 - 请求TOKEN
     */
    public final static int TAG_QINIU_TOKEN_REQUEST = 20001;
    /**
     * 百度推送 - 请求channelId
     */
    public final static int TAG_PUSH_CHANNEL_ID = 20002;
    /**
     * 获取App版本信息
     */
    public final static int TAG_APP_INFO = 20003;

    /**
     * 软件 - 获取广告Banner图片
     */
    public final static int TAG_GET_ADV_LIST = 000;
    /***********************************************************************************************/
    /**
     * 学校 -  获得所有学校和宿舍列表
     */
    public final static int TAG_GET_SCHOOL_APARTMENT_LIST = 001;
    /**
     * 学校 - 获取指定学校的餐厅列表
     */
    public final static int TAG_GET_SCHOOL_STORE_LIST = 002;
    /**
     * 学校 - 获取指定餐厅的套餐列表
     */
    public final static int TAG_GET_GOOD_LIST_BY_STORE_ID = 003;
    /**
     * 学校 - 获取指定餐厅的套餐信息
     */
    public final static int TAG_GET_SCHOOL_STORE_INFOR = 004;
    /**
     * 学校 - LBS:附近学校
     */
    public final static int TAG_GET_SCHOOL_NEARS = 005;
    /**
     * 学校 - 开通学校的地区列表
     */
    public final static int TAG_GET_SCHOOL_OPEN_AREA = 006;
    /**
     * 学校 - 开通学校的地区列表
     */
    public final static int TAG_GET_SCHOOL_SEARCH = 007;

    /**
     * 学校 - 学校：学校餐厅列表V2
     */
    public final static int TAG_GET_SCHOOL_STORE_LIST_V2 = 8;

    /**
     * 选择学校 - 根据城市获取学校列表
     */
    public final static int TAG_GET_SCHOOL_OF_LOCAL_CITY = 9;

    /**
     * 选择学校 - 获取城市列表
     */
    public final static int TAG_GET_SCHOOL_CITY_LIST = 10;

    /**
     * 选择学校 - 获取热门城市
     */
    public final static int TAG_GET_SCHOOL_CITY_HOT = 11;

    /**
     * 选择学校 - 获取指定区域Id的学校
     */
    public final static int TAG_GET_SCHOOL_BY_AREA_ID = 12;
    /***********************************************************************************************/
    /**
     * 下单 - 未支付订单支付
     */
    public static final int TAG_PAY = 200;
    /**
     * 下单 - 取消
     */
    public static final int TAG_ORDER_CANCEL = 201;
    /**
     * 下单 - 是否帮助带饭
     */
    public static final int TAG_HELP = 202;
    /**
     * 下单 - 服务费
     */
    public static final int TAG_PAY_SHIPPING_FEE = 203;
    /**
     * 下单 - 服务费
     */
    public static final int TAG_PAY_BIND_PHONE = 204;
    /**
     * 下单 - 获取订单标签
     */
    public static final int TAG_ORDER_SHOW_EXPLAIN_LABEL = 205;
    /**
     * 下单 - 预计到达时间
     */
    public static final int TAG_ORDER_GET_ESTIMATED_TIME = 206;

    /*
    * 确认下单
    * */
    public static final int TAG_ORDER_PLACEORDER = 207;
    /**

     /***********************************************************************************************/
    /**
     * 订单 - 食堂吃
     */
    public static final int TAG_GET_ORDER = 301;
    /**
     * 订单 - 选座位
     */
    public static final int TAG_ORDER_CHOOSE_SEAT = 302;
    /**
     * 订单 - 确认收获
     */
    public static final int TAG_ORDER_COMPLETE = 303;
    /**
     * 订单 - 商品 - 评价
     */
    public static final int TAG_GOOD_EVALUATE = 304;

    /**
     * 订单 - 商品 - 催单
     */
    public static final int TAG_ORDER_HURRY = 305;

    /**
     * 订单 - 商品 - 获取结算订单详情
     */
    public static final int TAG_ORDER_SETTLEMENT = 306;

    /**
     * 订单 - 获取订单详情
     */
    public static final int TAG_GET_ORDERDETAIL = 307;

    /***********************************************************************************************/
    /**
     * 用户 - 登录
     */
    public static final int TAG_USER_LOGIN = 400;

    /**
     * 用户 - 获取验证码
     */
    public static final int TAG_GET_CODE = 401;
    /**
     * 用户 - 注册
     */
    public static final int TAG_USER_REGISTER = 402;
    /**
     * 用户 - 代金券
     */
    public static final int TAG_USER_COUPON = 405;
    /**
     * 用户 - 找回密码
     */
    public static final int TAG_USER_RESET_PASSWORD = 406;
    /**
     * 用户 - 地址
     */
    public static final int TAG_USER_ADRESS = 407;
    /**
     * 用户 - 地址 - 添加
     */
    public static final int TAG_USER_ADDRESS_ADD = 408;


    /**
     * 用户 - 地址 - 修改
     */
    public static final int TAG_USER_ADDRESS_MODIFY = 409;

    /**
     * 用户 - 地址 - 删除
     */
    public static final int TAG_USER_ADRESS_DELETE = 410;
    /**
     * 用户 - QQ登录
     */
    public static final int TAG_USER_LOGIN_OTHER = 411;
    /**
     * 用户 - 微信登陆
     */
    public static final int TAG_USER_LOGIN_WEIXIN = 412;
    /**
     * 用户 - 修改性别
     */
    public static final int TAG_USER_MODIFY_SEX = 413;
    /**
     * 用户 - 修改昵称
     */
    public static final int TAG_USER_MODIFY_NICK_NAME = 414;
    /**
     * 用户 - 修改头像
     */
    public static final int TAG_USER_MODIFY_AVATAR = 415;
    /**
     * 用户 - 获取用户信息
     */
    public static final int TAG_USER_INFO = 416;
    /**
     * 用户 - 钱包 - 充值活动
     */
    public static final int TAG_USER_PD_Activity = 417;
    /**
     * 用户 - 钱包 - 充值
     */
    public static final int TAG_WELLAT_PREDEPOSIT = 418;

    /**
     * 用户 - 钱包 - 消费记录
     */
    public static final int TAG_USER_WELLAT_PREDEPOSIT_LOGS = 418;

    /**
     * 用户 - 钱包 - 支付密码 - 是否认证
     */
    public static final int TAG_USER_WELLAT_IS_AUTH = 419;

    /**
     * 用户 - 钱包 - 支付密码 - 创建
     */
    public static final int TAG_USER_WELLAT_CREATE = 420;

    /**
     * 用户 - 钱包 - 支付密码 - 修改
     */
    public static final int TAG_USER_WELLAT_MODIFLY = 421;

    /**
     * 用户 - 钱包 - 支付密码 - 认证
     */
    public static final int TAG_USER_WELLAT_AUTH = 422;

    /**
     * 用户 - 钱包 - 支付密码 - 验证码
     */
    public static final int TAG_USER_WELLAT_CODE = 423;

    /**
     * 用户 - 反馈
     */
    public static final int TAG_USER_FEED_BACK = 424;

    /**
     * 用户 - 邀请
     */
    public static final int TAG_USER_INVITE = 425;

    /**
     * 用户-修改密码
     */
    public static  final  int TAG_MODIFY_PASSWORD = 426;

    /**
     * 用户 - 地址 - 修改
     */
    public static final int TAG_USER_ADDRESS_DEFAULT = 427;

    /**
     * 用户退出
     */
    public static final int TAG_USER_LOGOUT = 428;

    /**
     * 用户 -- 消息列表
     */
    public static final int TAG_USER_MESSAGE = 429;

    /***********************************************************************************************/
    /**
     * 抢单
     */
    public static final int TAG_ORDER_RAP = 501;
    /**
     * 抢单 - 优惠金额
     */
    public static final int TAG_ORDER_RAP_COUPON_MONEY = 502;
    /**
     * 抢单 - 提现
     */
    public static final int TAG_ORDER_RAP_USER_CASH = 503;
    /**
     * 抢单 - 申请认证带餐
     */
    public static final int TAG_ORDER_RAP_USER_JOIN = 504;
    /**
     * 抢单 - 可体现金额
     */
    public static final int TAG_ORDER_RAP_USER_CASH_COUNT = 505;
    /**
     * 抢单 - 订单池
     */
    public static final int TAG_ORDER_RAP_ORDER_POOL = 506;

    /***********************************************************************************************/
    /**
     * 点餐 - 套餐类别
     */
    public static final int TAG_FOOD_SELECT_FOOF_CLASS = 601;

    /**
     * 用户 - 收集用户信息
     */
    public static final int TAG_COLLECT_CLIENT = 602;

    /***********************************************************************************************/
    /**
     * 用户 - 下单支付前温馨提醒
     */
    public static final int TAG_REMINDER_TIPS = 701;

    /**
     * 用户 - 下单获取可用红包
     */
    public static final int TAG_GET_COUPON = 702;

    /**
     * 用户 - 获取食堂以及配送宿舍区和楼栋
     */
    public static final int TAG_GET_STORE_DELIVERED_APARTMENT = 703;

    /**
     * 用户 - 根据食堂获取食堂下的档口
     */
    public static final int TAG_GET_STORE_STALLS = 704;

    /**
     * 用户 - 根据档口获取菜品分类
     */
    public static final int TAG_GET_STORE_STALLS_FOOD_CLASS = 705;

    /**
     * 用户 - 根据档口获取菜品分类
     */
    public static final int TAG_GET_STORE_STALLS_GOODS = 706;

    /**
     * 下单 - 获取优惠后支付价格
     */
    public static final int TAG_PAY_PRICE = 707;

    /**
     * 首页获取跑马灯
     */
    public static final int TAG_GET_MARQUEE = 708;

    /**
     * 获取主营业务列表
     */
    public static final int TAG_GET_RANGELIST = 709;

}
