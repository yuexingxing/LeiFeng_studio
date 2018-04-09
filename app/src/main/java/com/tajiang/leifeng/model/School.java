package com.tajiang.leifeng.model;

import java.io.Serializable;
import java.util.List;


public class School implements Serializable{
	
    private String schoolName;

    private int schoolId;

    private Integer areaId;

    private Integer cityId;

    private String areaInfo;

    private String address;

    private String logo;

    private List<Apartment> apartmentList;
    
    private List<Store> storeList;
    
    public List<Apartment> getApartmentList() {
		return apartmentList;
	}

	public void setApartmentList(List<Apartment> apartmentList) {
		this.apartmentList = apartmentList;
	}

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAreaInfo() {
        return areaInfo;
    }

    public void setAreaInfo(String areaInfo) {
        this.areaInfo = areaInfo == null ? null : areaInfo.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

	public List<Store> getStoreList() {
		return storeList;
	}

	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "School{" +
                ", schoolName='" + schoolName + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", areaId=" + areaId +
                ", cityId=" + cityId +
                ", areaInfo='" + areaInfo + '\'' +
                ", address='" + address + '\'' +
                ", logo='" + logo + '\'' +
                ", apartmentList=" + apartmentList +
                ", storeList=" + storeList +
                '}';
    }
}