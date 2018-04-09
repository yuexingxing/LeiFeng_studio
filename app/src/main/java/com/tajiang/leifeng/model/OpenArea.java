package com.tajiang.leifeng.model;

import java.util.List;

/**
 * Created by 12154 on 2016/1/19.
 */
public class OpenArea {

    /**
     * areaId : 175
     * areaName : 杭州市
     * areaParentId : null
     * areaSort : null
     * areaDeep : null
     * subAreaList : [{"areaId":2134,"areaName":"上城区","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2135,"areaName":"下城区","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2136,"areaName":"临安市","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2137,"areaName":"余杭区","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2138,"areaName":"富阳市","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2139,"areaName":"建德市","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2140,"areaName":"拱墅区","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2141,"areaName":"桐庐县","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2142,"areaName":"江干区","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2143,"areaName":"淳安县","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2144,"areaName":"滨江区","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2145,"areaName":"萧山区","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null},{"areaId":2146,"areaName":"西湖区","areaParentId":null,"areaSort":null,"areaDeep":null,"subAreaList":null}]
     */

    private int areaId;
    private String areaName;
    private Object areaParentId;
    private Object areaSort;
    private Object areaDeep;
    private Integer num;
    /**
     * areaId : 2134
     * areaName : 上城区
     * areaParentId : null
     * areaSort : null
     * areaDeep : null
     * subAreaList : null
     */

    private List<SubAreaListEntity> subAreaList;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

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

    public void setSubAreaList(List<SubAreaListEntity> subAreaList) {
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

    public List<SubAreaListEntity> getSubAreaList() {
        return subAreaList;
    }


}
