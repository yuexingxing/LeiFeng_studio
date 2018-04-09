package com.tajiang.leifeng.model;

/**
 * Created by 12154 on 2016/1/27.
 */
public class PredepositOrder {

    /**
     * id : null
     * pdrSn : 6201601271925320004
     * userId : 1k6zx
     * activityId : 2
     * userName : vfg
     * pdrAmount : 50.0
     * pdrAward : 10.0
     * paymentCode : alipay
     * paymentName : 支付宝
     * tradeSn : null
     * createTime : null
     * paymentState : 0
     * paymentTime : null
     * sign : service="mobile.securitypay.pay"&partner="2088021116143809"&_input_charset="utf-8"&notify_url="http://114.215.180.201:8888/taj/order/alipay/callback/payOff"&out_trade_no="6201601271925320004"&subject="全民雷锋预充值"&payment_type="1"&seller_id="hztj@hztajiang.com"&total_fee="50.0"&body="全民雷锋预充值"
     * signed : jsXCZmL5htqXn2cPm0yrOX75LlU01vv6+uEXawvCeqvuKOz8bzqEM03uzhSteE4uA9yk3Cojoaf2f2wsUHSKP0uGKQtiW1OQjnrfNvwHsXezpntUxjz2fV+Px8OERgPFtTxVLWqpKHTzba6z41p4DxErTIj/9joM89jzCYqk7Tk=
     * appid : null
     * mch_id : null
     * nonce_str : null
     * prepay_id : null
     * trade_type : null
     * timestamp : null
     */

    private String id;
    private long pdrSn;
    private String userId;
    private int activityId;
    private String userName;
    private double pdrAmount;
    private double pdrAward;
    private String paymentCode;
    private String paymentName;
    private String tradeSn;
    private String createTime;
    private int paymentState;
    private String paymentTime;
    private String sign;
    private String signed;
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String prepay_id;
    private String trade_type;
    private String timestamp;
    private String payInfo;

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPdrSn(long pdrSn) {
        this.pdrSn = pdrSn;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPdrAmount(double pdrAmount) {
        this.pdrAmount = pdrAmount;
    }

    public void setPdrAward(double pdrAward) {
        this.pdrAward = pdrAward;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public void setTradeSn(String tradeSn) {
        this.tradeSn = tradeSn;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setPaymentState(int paymentState) {
        this.paymentState = paymentState;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public long getPdrSn() {
        return pdrSn;
    }

    public String getUserId() {
        return userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public String getUserName() {
        return userName;
    }

    public double getPdrAmount() {
        return pdrAmount;
    }

    public double getPdrAward() {
        return pdrAward;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public String getTradeSn() {
        return tradeSn;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getPaymentState() {
        return paymentState;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public String getSign() {
        return sign;
    }

    public String getSigned() {
        return signed;
    }

    public String getAppid() {
        return appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
