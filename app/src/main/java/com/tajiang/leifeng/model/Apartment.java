package com.tajiang.leifeng.model;


import java.io.Serializable;

public class Apartment implements Serializable{
    
    private int apartmentId;
    private String apartmentName;

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + apartmentId +
                ", name='" + apartmentName + '\'' +
                '}';
    }
}