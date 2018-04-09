package com.tajiang.leifeng.model;

import java.util.List;

/**
 * Created by 12154 on 2016/1/25.
 */
public class SchoolLocal {

    /**
     * id : mhaa
     * createdAt : null
     * updatedAt : null
     * schoolId : 1vfh2
     * schoolName : 华联
     * storeList : []
     */

    private String id;
    private Object createdAt;
    private Object updatedAt;
    private String schoolId;
    private String schoolName;


    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    private List<Store> storeList;

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }


    public String getId() {
        return id;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public List<?> getStoreList() {
        return storeList;
    }
}
