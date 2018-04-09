package com.tajiang.leifeng.model;

/**
 * Created by 12154 on 2016/1/27.
 */
public class PredepositLog {

    private int id;
    private String userId;
    private String userName;
    private int type;
    private double avAmount;
    private long addTime;
    private String description;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAvAmount(double avAmount) {
        this.avAmount = avAmount;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getType() {
        return type;
    }

    public double getAvAmount() {
        return avAmount;
    }

    public long getAddTime() {
        return addTime;
    }

    public String getDescription() {
        return description;
    }
}
