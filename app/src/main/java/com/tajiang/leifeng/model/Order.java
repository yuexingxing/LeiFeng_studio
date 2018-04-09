package com.tajiang.leifeng.model;

import com.tajiang.leifeng.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


public class Order implements Serializable {

    public static final int COUNT_TIME_TRANSFER = 15 * 60 * 1000; //订单配送倒计时15分钟

    public static final int TYPE_MESS = 0;
    public static final int TYPE_TAKE_AWAY = 1;
    public static final int TYPE_PASS_ME = 2;

    public static final int STATE_CANNEL_1 = 60; // 买家未付款取消
    public static final int STATE_CANNEL_2 = 61; // 未付款超时取消
    public static final int STATE_CANNEL_3 = 62; // 商家未接单买家取消
    public static final int STATE_CANNEL_4 = 63; // 买家退单取消

    public static final int STATE_NO_PAY = 10; // 未付款
    public static final int STATE_PAY = 20; // 已付款
    public static final int STATE_TAKE = 30;  //已接单
    public static final int STATE_DELIVERY = 40; //配送中
    public static final int STATE_TRANSFERED = 50; //骑士已送达

    public static final int STATE_COMPLETE = 51; // 已完成

    private String orderId;  //订单id

    private String delyEndDate;  //配送结束时间
    private String delyStartDate;  //配送开始时间

    private int shopId;  //商家id

    private String shopName; //商家名称

    private String createDate;  //下单时间

    private int lockState;  //锁定状态

    private int rateState;  //评价状态

    private Integer orderState;  //订单状态

    private long updatedAt;

    private String receiverName;  //收货人姓名

    private String receiverPhone;  //收货人电话

    private String receiverAddr;  //收货人地址

    private int receiverSex;  //收货人性别

    private int totalDelyFee; //总配送费

    private int totalPackFee; //总打包费

    private int voucherMoney; //优惠卷金额

    private int delyFast;  //是否尽快送达

    private String buyerRemark;  //备注

    private String takeInfo;

    private String takerId;

    private BigDecimal finalMoney;  //实际应付金额

    private Integer serviceState;

    private List<OrderGoods> orderGoodsList;

    private List<TraceInfo> orderTraceList;

    /**********
     * 支付宝、微信签名
     **********/
    private String sign;

    private String signed;

    private String payInfo;

    /*************
     * 微信相关
     **************/
    private String appid;
    private String noncestr;
    private String prepayid;
    private String partnerid;
    private String trade_type;
    private String timestamp;

    private List<OrderActivity> orderActivityList;

    public List<OrderActivity> getOrderActivityList() {
        return orderActivityList;
    }

    public void setOrderActivityList(List<OrderActivity> orderActivityList) {
        this.orderActivityList = orderActivityList;
    }

    public List<TraceInfo> getOrderTraceList() {
        return orderTraceList;
    }

    public void setOrderTraceList(List<TraceInfo> orderTraceList) {
        this.orderTraceList = orderTraceList;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getDelyEndDate() {
        return delyEndDate;
    }

    public void setDelyEndDate(String delyEndDate) {
        this.delyEndDate = delyEndDate;
    }

    public String getDelyStartDate() {
        return delyStartDate;
    }

    public void setDelyStartDate(String delyStartDate) {
        this.delyStartDate = delyStartDate;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public void setReceiverAddr(String receiverAddr) {
        this.receiverAddr = receiverAddr;
    }

    public int getTotalDelyFee() {
        return totalDelyFee;
    }

    public void setTotalDelyFee(int totalDelyFee) {
        this.totalDelyFee = totalDelyFee;
    }

    public int getTotalPackFee() {
        return totalPackFee;
    }

    public void setTotalPackFee(int totalPackFee) {
        this.totalPackFee = totalPackFee;
    }

    public int getVoucherMoney() {
        return voucherMoney;
    }

    public void setVoucherMoney(int voucherMoney) {
        this.voucherMoney = voucherMoney;
    }

    public int getDelyFast() {
        return delyFast;
    }

    public void setDelyFast(int delyFast) {
        this.delyFast = delyFast;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getServiceState() {
        return serviceState;
    }

    public void setServiceState(Integer serviceState) {
        this.serviceState = serviceState;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getReceiverSex() {
        return receiverSex;
    }

    public void setReceiverSex(int receiverSex) {
        this.receiverSex = receiverSex;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getLockState() {
        return lockState;
    }

    public String getBuyerRemark() {
        return buyerRemark;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public void setLockState(int lockState) {
        this.lockState = lockState;
    }

    public int getRateState() {
        return rateState;
    }

    public void setRateState(int rateState) {
        this.rateState = rateState;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getTakeInfo() {
        return takeInfo;
    }

    public void setTakeInfo(String takeInfo) {
        this.takeInfo = takeInfo == null ? null : takeInfo.trim();
    }

    public String getTakerId() {
        return takerId;
    }

    public void setTakerId(String takerId) {
        this.takerId = takerId;
    }

    public BigDecimal getFinalMoney() {
        return finalMoney;
    }

    public void setFinalMoney(BigDecimal finalMoney) {
        this.finalMoney = finalMoney;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }


    public List<OrderGoods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}