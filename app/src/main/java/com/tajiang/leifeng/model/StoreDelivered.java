package com.tajiang.leifeng.model;

import java.util.List;

/**
 * Created by Admins on 2016/11/1.
 */
public class StoreDelivered {

    private String storeId;

    private int zonesId;  //	学校分区id

    private String zonesName;  //学校分区名称

    private List<Apartment> apartmentList;  //楼栋信息

    public List<Apartment> getApartmentList() {
        return apartmentList;
    }

    public void setApartmentList(List<Apartment> apartmentList) {
        this.apartmentList = apartmentList;
    }

    public int getZonesId() {
        return zonesId;
    }

    public void setZonesId(int zonesId) {
        this.zonesId = zonesId;
    }

    public String getZonesName() {
        return zonesName;
    }

    public void setZonesName(String zonesName) {
        this.zonesName = zonesName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

}
