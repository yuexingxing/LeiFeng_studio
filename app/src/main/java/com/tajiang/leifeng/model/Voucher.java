package com.tajiang.leifeng.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class Voucher implements Parcelable{

    public static final int STATE_USABLE = 1; //未使用
    public static final int STATE_USED = 2;  //已使用
    public static final int STATE_DATE_OUT = 3; //已过期
    /**
     * id : 1k6zp
     * createdAt : 1449987568000
     * updatedAt : 1449987571000
     * code : 9201512121437123651
     * tId : b8qp
     * title : 请你吃饭
     * description : 请你吃饭
     * startDate : 1449987550000
     * endDate : 1450073953000
     * price : 0.6
     * limitPrice : 0.0
     * storeId : null
     * state : 1
     * type : 0
     * ownerId : 26o0n
     * ownerName : ciko
     * orderId : null
     */

    private String id;
    private long createdAt;
    private long updatedAt;
    private String code;
    private String tId;
    private String title;
    private String policyName;
    private long startDate;
    private String expireTime;
    private int faceValue;
    private BigDecimal limitPrice;
    private String storeId;
    private String stallsId;

    private int state;
    private int type;
    private String ownerId;
    private String ownerName;
    private String orderId;

    public String getStallsId() {
        return stallsId;
    }

    public void setStallsId(String stallsId) {
        this.stallsId = stallsId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTId(String tId) {
        this.tId = tId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setLimitPrice(BigDecimal limitPrice) {
        this.limitPrice = limitPrice;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public String getCode() {
        return code;
    }

    public String getTId() {
        return tId;
    }

    public String getTitle() {
        return title;
    }

    public long getStartDate() {
        return startDate;
    }

    public BigDecimal getLimitPrice() {
        return limitPrice;
    }

    public String getStoreId() {
        return storeId;
    }

    public int getState() {
        return state;
    }

    public int getType() {
        return type;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(int faceValue) {
        this.faceValue = faceValue;
    }

    public String toString() {
        String str = "Title : " + getTitle()
                + ", Description : " + getPolicyName()
                + ", LimitPrice : " + getLimitPrice();
        return str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.createdAt);
        dest.writeLong(this.updatedAt);
        dest.writeString(this.code);
        dest.writeString(this.tId);
        dest.writeString(this.title);
        dest.writeString(this.policyName);
        dest.writeLong(this.startDate);
        dest.writeString(this.expireTime);
        dest.writeSerializable(this.faceValue);
        dest.writeSerializable(this.limitPrice);
        dest.writeString(this.storeId);
        dest.writeString(this.stallsId);
        dest.writeInt(this.state);
        dest.writeInt(this.type);
        dest.writeString(this.ownerId);
        dest.writeString(this.ownerName);
        dest.writeString(this.orderId);
    }

    public Voucher() {
    }

    protected Voucher(Parcel in) {
        this.id = in.readString();
        this.createdAt = in.readLong();
        this.updatedAt = in.readLong();
        this.code = in.readString();
        this.tId = in.readString();
        this.title = in.readString();
        this.policyName = in.readString();
        this.startDate = in.readLong();
        this.expireTime = in.readString();
        this.faceValue = in.readInt();
        this.limitPrice = (BigDecimal) in.readSerializable();
        this.storeId = in.readString();
        this.stallsId = in.readString();
        this.state = in.readInt();
        this.type = in.readInt();
        this.ownerId = in.readString();
        this.ownerName = in.readString();
        this.orderId = in.readString();
    }

    public static final Parcelable.Creator<Voucher> CREATOR = new Parcelable.Creator<Voucher>() {
        @Override
        public Voucher createFromParcel(Parcel source) {
            return new Voucher(source);
        }

        @Override
        public Voucher[] newArray(int size) {
            return new Voucher[size];
        }
    };

}