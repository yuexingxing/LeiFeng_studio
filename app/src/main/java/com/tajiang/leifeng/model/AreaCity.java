package com.tajiang.leifeng.model;

import java.util.List;

/**
 * Created by ciko on 16/5/4.
 */
public class AreaCity {

    /**
     * areaId : 2142
     * areaName : 江干区
     * areaParentId : null
     * areaSort : null
     * areaDeep : null
     * num : null
     * listSchool : [{"id":"1k6x1","createdAt":1445419084000,"updatedAt":1445419086000,"name":"浙江育英职业技术学院","areaId":2142,"cityId":175,"areaInfo":"","address":"浙江省杭州市江干区","logo":"","shipingFee":0.01},{"id":"7c","createdAt":1447756038000,"updatedAt":1447756041000,"name":"浙江理工大学","areaId":2142,"cityId":175,"address":"浙江","shipingFee":0.01},{"id":"b8qx","createdAt":1449034022000,"updatedAt":1449034026000,"name":"中国计量学院","areaId":2142,"cityId":175,"address":"浙江省杭州市下沙高教园区学源街258号（310018","shipingFee":0.01},{"id":"b8r5","createdAt":1457336616000,"updatedAt":1457336624000,"name":"浙江水利水电学院","areaId":2142,"cityId":175,"address":"浙江省杭州市江干区","shipingFee":0.01},{"id":"xpuz","createdAt":1459146495000,"updatedAt":1459146497000,"name":"杭州师范大学钱江学院","areaId":2142,"cityId":175,"address":"浙江省杭州市下沙高教园区学林街16号","shipingFee":0.01},{"id":"1k6yd","createdAt":1459425296000,"updatedAt":1459425299000,"name":"中国计量学院现代科技学院","areaId":2142,"cityId":175,"address":"浙江省杭州市下沙高教园区学源街  杭州桐庐富春江科技城梅林路东侧区块（在建）","shipingFee":0.01},{"id":"1k6z9","createdAt":1459941142000,"updatedAt":1459941144000,"name":"杭州电子科技大学(江干区)","areaId":2142,"cityId":175,"address":"浙江省杭州江干区下沙高教园区2号大街1158号","shipingFee":0.01},{"id":"ao","createdAt":1461725019000,"updatedAt":1461725022000,"name":"浙江经贸职业技术学院下沙校区","areaId":2142,"cityId":175,"address":"浙江省杭州江干区下沙高教园东区学林街280号","shipingFee":0.01}]
     */

    private int areaId;
    private String areaName;
    private Object areaParentId;
    private Object areaSort;
    private Object areaDeep;
    private Object num;
    /**
     * id : 1k6x1
     * createdAt : 1445419084000
     * updatedAt : 1445419086000
     * name : 浙江育英职业技术学院
     * areaId : 2142
     * cityId : 175
     * areaInfo :
     * address : 浙江省杭州市江干区
     * logo :
     * shipingFee : 0.01
     */

    private List<School> listSchool;

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

    public Object getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(Object areaParentId) {
        this.areaParentId = areaParentId;
    }

    public Object getAreaSort() {
        return areaSort;
    }

    public void setAreaSort(Object areaSort) {
        this.areaSort = areaSort;
    }

    public Object getAreaDeep() {
        return areaDeep;
    }

    public void setAreaDeep(Object areaDeep) {
        this.areaDeep = areaDeep;
    }

    public Object getNum() {
        return num;
    }

    public void setNum(Object num) {
        this.num = num;
    }

    public List<School> getListSchool() {
        return listSchool;
    }

    public void setListSchool(List<School> listSchool) {
        this.listSchool = listSchool;
    }
}
