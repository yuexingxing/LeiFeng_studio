package com.tajiang.leifeng.model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by 12154 on 2016/1/27.
 */
public class Action implements Serializable{
    /**
     * id : 1
     * name : 满三十送5元
     * preAmountStart : 30.0
     * preAmountEnd : 49.99
     * awardAmount : 5.0
     * createTime : 1453103222000
     * startTime : 1453103215000
     * endTime : 1459496818000
     * giveUserLevel : 0
     */

    private int id;
    private String name;
    private double preAmountStart;
    private double preAmountEnd;
    private double awardAmount;
    private long createTime;
    private long startTime;
    private long endTime;
    private int giveUserLevel;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreAmountStart(double preAmountStart) {
        this.preAmountStart = preAmountStart;
    }

    public void setPreAmountEnd(double preAmountEnd) {
        this.preAmountEnd = preAmountEnd;
    }

    public void setAwardAmount(double awardAmount) {
        this.awardAmount = awardAmount;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setGiveUserLevel(int giveUserLevel) {
        this.giveUserLevel = giveUserLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPreAmountStart() {
        return preAmountStart;
    }

    public double getPreAmountEnd() {
        return preAmountEnd;
    }

    public double getAwardAmount() {
        return awardAmount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getGiveUserLevel() {
        return giveUserLevel;
    }
}
