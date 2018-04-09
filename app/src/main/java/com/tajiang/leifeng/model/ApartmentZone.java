package com.tajiang.leifeng.model;

import java.util.List;

/**
 * Created by ciko on 16/3/16.
 */
public class ApartmentZone {

    private int id;
    private String schoolId;
    private String name;
    private long createdAt;
    private long updatedAt;
    /**
     * id : 147
     * schoolId : 1vfgu
     * name : 1幢
     * createdAt : 1450326100000
     * updatedAt : 1450326102000
     * zonesId : 172
     */

    private List<Apartment> apartmentList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Apartment> getApartmentList() {
        return apartmentList;
    }

    public void setApartmentList(List<Apartment> apartmentList) {
        this.apartmentList = apartmentList;
    }

}