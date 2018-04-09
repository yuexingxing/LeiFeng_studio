package com.tajiang.leifeng.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 学校档口
 * Created by Admins on 2016/11/1.
 */
public class StoreStalls implements Parcelable {

    public static final int STORE_STALLS_CLOSED = 0;  //休息

    public static final int STORE_STALLS_OPENED = 1;   //营业中

    public static final String STORE_DELY_TYPE_PT = "PT";     //平台配送

    public static final String STORE_DELY_TYPE_BUSS = "BUSS"; //商家配送

    private String shopId;

    private BigDecimal minMoney;  //起送价格

    private String notice;  //商家公告

    private String shopName; //档口名称

    private String shopImage; //档口图片

    private Long bussState; //档口状态

    private int arriveMins;  //预计送达时间

    private int delyFee; //配送费

    private int delyFeeAdd; //配送费溢价

    private String delyType; //配送费类型

    private int monthSellQty; //月销量

    private int packFee; //打包费

    private int discount;//折扣活动   0:为无折扣 1：有折扣

    private List<StallsFullcut> activityList;  //档口活动列表

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

    public int getDelyFee() {
        return delyFee;
    }

    public void setDelyFee(int delyFee) {
        this.delyFee = delyFee;
    }

    public int getDelyFeeAdd() {
        return delyFeeAdd;
    }

    public void setDelyFeeAdd(int delyFeeAdd) {
        this.delyFeeAdd = delyFeeAdd;
    }

    public String getDelyType() {
        return delyType;
    }

    public void setDelyType(String delyType) {
        this.delyType = delyType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getMonthSellQty() {
        return monthSellQty;
    }

    public void setMonthSellQty(int monthSellQty) {
        this.monthSellQty = monthSellQty;
    }

    public int getPackFee() {
        return packFee;
    }

    public void setPackFee(int packFee) {
        this.packFee = packFee;
    }

    public List<StallsFullcut> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<StallsFullcut> activityList) {
        this.activityList = activityList;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public Long getBussState() {
        return bussState;
    }

    public void setBussState(Long bussState) {
        this.bussState = bussState;
    }

    public int getArriveMins() {
        return arriveMins;
    }

    public void setArriveMins(int arriveMins) {
        this.arriveMins = arriveMins;
    }

    /**
     * ++++++++++++++++++++++++++++++++++  Parcelable  ++++++++++++++++++++++++++++++++++
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shopId);
        dest.writeSerializable(this.minMoney);
//        dest.writeString(this.give);
//        dest.writeValue(this.giveState);

        dest.writeString(this.notice);
        dest.writeString(this.shopName);
        dest.writeString(this.delyType);
        dest.writeInt(this.delyFee);
        dest.writeInt(this.packFee);
        dest.writeString(this.shopImage);
        dest.writeValue(this.bussState);
        dest.writeInt(this.discount);
        dest.writeTypedList(this.activityList);
    }

    public StoreStalls() {
    }

    protected StoreStalls(Parcel in) {
        this.shopId = in.readString();
        this.minMoney = (BigDecimal) in.readSerializable();
//        this.give = in.readString();
//        this.giveState = (Long) in.readValue(Long.class.getClassLoader());
        this.notice = in.readString();
        this.shopName = in.readString();
        this.delyType = in.readString();
        this.delyFee = in.readInt();
        this.packFee = in.readInt();
        this.shopImage = in.readString();
        this.bussState = (Long) in.readValue(Long.class.getClassLoader());
        this.discount = in.readInt();
        this.activityList = in.createTypedArrayList(StallsFullcut.CREATOR);
    }

    public static final Parcelable.Creator<StoreStalls> CREATOR = new Parcelable.Creator<StoreStalls>() {
        @Override
        public StoreStalls createFromParcel(Parcel source) {
            return new StoreStalls(source);
        }

        @Override
        public StoreStalls[] newArray(int size) {
            return new StoreStalls[size];
        }
    };
}