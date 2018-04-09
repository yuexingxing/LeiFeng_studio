package com.tajiang.leifeng.constant;

/**
 * @author lvhua
 *
 * 2015年7月31日
 */
public class TJCst {

    /** 项目名称 */
    public static final String PROJECT = "tjLeifeng";

    public static final String CUSTOMER_SERVICES_PHONE = "4009920278";

    /** 是否处于调试模式 */
    public static final boolean DEBUG = true;

    public static final String REST_SERVER = "";

    /** 测试库地址 */
    public static final String TEST_SERVER = "";

    public static final int HTTP_TEST_PORT = 8080;

    public static final int HTTP_REST_PORT = 80;

    public static final int HTTPS_PORT = 443;

    //获取短信验证码类型
    public static final String REG_CODE = "REG_CODE";  //注册验证码
    public static final String BIND_PHONE_CODE = "BIND_PHONE_CODE";  //绑定手机号
    public static final String RESET_PWD = "RESET_PWD";  //重置登录密码
    public static final String PAY_PWD_CODE = "PAY_PWD_CODE ";  //重置支付密码

    public static final int LOGIN_TYLE_WB = 2;  //微博登录
    public static final int LOGIN_TYLE_WX = 1;  //微信登录
    public static final int LOGIN_TYLE_QQ = 3;  //QQ登录

    /** QQ登录的APP KEY*/
	public static final String QQ_APP_ID = "1104782664";

    /** 微信登录 */
    public static final String WEIXIN_APP_ID = "wxac3c909fb055d4e3";
    public static final String WEIXIN_APP_SECRET = "a0e9606b5a4072c0d6e65cc58a128988";

    /** 新浪微博APP KEY*/
    public static final String WEIBO_APP_KEY  = "4130297999";
    /** 新浪微博回调页。*/
    public static final String WEIBO_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
     /** 新浪微博Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。*/
    public static final String WEIBO_SCOPE = "email,direct_messages_read,direct_messages_write," + "friendships_groups_read,friendships_groups_write,statuses_to_me_read," + "follow_app_official_microblog," + "invitation_write";

}
