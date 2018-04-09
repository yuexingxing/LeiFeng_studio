package com.tajiang.leifeng.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Admins on 2016/11/1.
 */

public class StallsFullcut implements Parcelable {

    private int activityId;  //活动id

    private String activityName; //活动名称

    private BigDecimal discountedPrice; //优惠金额

    private int limitPrice; //限制金额

    private String url;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(int limitPrice) {
        this.limitPrice = limitPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityName);
        dest.writeString(this.url);

    }

    public StallsFullcut() {
    }

    protected StallsFullcut(Parcel in) {
        this.activityName = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<StallsFullcut> CREATOR = new Parcelable.Creator<StallsFullcut>() {
        @Override
        public StallsFullcut createFromParcel(Parcel source) {
            return new StallsFullcut(source);
        }

        @Override
        public StallsFullcut[] newArray(int size) {
            return new StallsFullcut[size];
        }
    };
}


//public class StallsFullcut implements Parcelable {
//
//    private String stallsId;
//
//    private BigDecimal price;
//
//    private BigDecimal limitPrice;
//
//    private long starTime;
//
//    private long endTime;
//
//    private Long subsidyWay;
//
//    private BigDecimal platformPrice;
//
//    private BigDecimal stallsPrice;
//
//    private Long state;
//
//    private Long del;
//
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public BigDecimal getLimitPrice() {
//        return limitPrice;
//    }
//
//    public void setLimitPrice(BigDecimal limitPrice) {
//        this.limitPrice = limitPrice;
//    }
//
//    public long getStarTime() {
//        return starTime;
//    }
//
//    public void setStarTime(long starTime) {
//        this.starTime = starTime;
//    }
//
//    public long getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(long endTime) {
//        this.endTime = endTime;
//    }
//
//    public Long getSubsidyWay() {
//        return subsidyWay;
//    }
//
//    public void setSubsidyWay(Long subsidyWay) {
//        this.subsidyWay = subsidyWay;
//    }
//
//    public BigDecimal getPlatformPrice() {
//        return platformPrice;
//    }
//
//    public void setPlatformPrice(BigDecimal platformPrice) {
//        this.platformPrice = platformPrice;
//    }
//
//    public BigDecimal getStallsPrice() {
//        return stallsPrice;
//    }
//
//    public void setStallsPrice(BigDecimal stallsPrice) {
//        this.stallsPrice = stallsPrice;
//    }
//
//    public Long getState() {
//        return state;
//    }
//
//    public void setState(Long state) {
//        this.state = state;
//    }
//
//    public Long getDel() {
//        return del;
//    }
//
//    public void setDel(Long del) {
//        this.del = del;
//    }
//
//    public String getStallsId() {
//        return stallsId;
//    }
//
//    public void setStallsId(String stallsId) {
//        this.stallsId = stallsId;
//    }
//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.stallsId);
//        dest.writeSerializable(this.price);
//        dest.writeSerializable(this.limitPrice);
//        dest.writeLong(this.starTime);
//        dest.writeLong(this.endTime);
//        dest.writeValue(this.subsidyWay);
//        dest.writeSerializable(this.platformPrice);
//        dest.writeSerializable(this.stallsPrice);
//        dest.writeValue(this.state);
//        dest.writeValue(this.del);
//    }
//
//    public StallsFullcut() {
//    }
//
//    protected StallsFullcut(Parcel in) {
//        this.stallsId = in.readString();
//        this.price = (BigDecimal) in.readSerializable();
//        this.limitPrice = (BigDecimal) in.readSerializable();
//        this.starTime = in.readLong();
//        this.endTime = in.readLong();
//        this.subsidyWay = (Long) in.readValue(Long.class.getClassLoader());
//        this.platformPrice = (BigDecimal) in.readSerializable();
//        this.stallsPrice = (BigDecimal) in.readSerializable();
//        this.state = (Long) in.readValue(Long.class.getClassLoader());
//        this.del = (Long) in.readValue(Long.class.getClassLoader());
//    }
//
//    public static final Parcelable.Creator<StallsFullcut> CREATOR = new Parcelable.Creator<StallsFullcut>() {
//        @Override
//        public StallsFullcut createFromParcel(Parcel source) {
//            return new StallsFullcut(source);
//        }
//
//        @Override
//        public StallsFullcut[] newArray(int size) {
//            return new StallsFullcut[size];
//        }
//    };
//}