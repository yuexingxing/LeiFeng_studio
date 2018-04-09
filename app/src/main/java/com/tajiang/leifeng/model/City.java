package com.tajiang.leifeng.model;

/**
 * Created by ciko on 16/5/5.
 */
public class City {

    /**
     * areaId : 180
     * areaName : 绍兴市
     * areaParentId : 11
     * areaSort : 0
     * areaDeep : 2
     * subAreaList : null
     * num : null
     */

    private int areaId;
    private String areaName;
    private int areaParentId;
    private int areaSort;
    private int areaDeep;
    private Object subAreaList;
    private Object num;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(int areaParentId) {
        this.areaParentId = areaParentId;
    }

    public int getAreaSort() {
        return areaSort;
    }

    public void setAreaSort(int areaSort) {
        this.areaSort = areaSort;
    }

    public int getAreaDeep() {
        return areaDeep;
    }

    public void setAreaDeep(int areaDeep) {
        this.areaDeep = areaDeep;
    }

    public Object getSubAreaList() {
        return subAreaList;
    }

    public void setSubAreaList(Object subAreaList) {
        this.subAreaList = subAreaList;
    }

    public Object getNum() {
        return num;
    }

    public void setNum(Object num) {
        this.num = num;
    }
}
