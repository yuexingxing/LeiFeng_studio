package com.tajiang.leifeng.model;

/**
 * Created by 12154 on 2016/1/19.
 */
public class SubAreaListEntity {
    private int areaId;
    private String areaName;
    private Object areaParentId;
    private Object areaSort;
    private Object areaDeep;
    private Object subAreaList;

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setAreaParentId(Object areaParentId) {
        this.areaParentId = areaParentId;
    }

    public void setAreaSort(Object areaSort) {
        this.areaSort = areaSort;
    }

    public void setAreaDeep(Object areaDeep) {
        this.areaDeep = areaDeep;
    }

    public void setSubAreaList(Object subAreaList) {
        this.subAreaList = subAreaList;
    }

    public int getAreaId() {
        return areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public Object getAreaParentId() {
        return areaParentId;
    }

    public Object getAreaSort() {
        return areaSort;
    }

    public Object getAreaDeep() {
        return areaDeep;
    }

    public Object getSubAreaList() {
        return subAreaList;
    }
}