package com.tajiang.leifeng.model;

import java.io.Serializable;

public class UserAddress implements Serializable{

	private static final long serialVersionUID = -8714610077333391357L;

	private int addressId = 0;

	private String userId;

    private String name;
    
    private int sex;

    private int apartmentId;

    private String detail;

    private String address;

    private String phone;

    private int isDefault;

    public String getSex() {
    	
    	if(sex == User.WOMAN){
    		return "女";
    	} 
    	
    	else if(sex == User.MAN){
    		return "男";
    	}
    	
		return "未设置";
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getIsDefault() {
        if (isDefault == 1) {
            return true;
        } else {
            return false;
        }
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
	public String toString() {
		return "UserAddress [ "+ "userId="
				+ userId + ", trueName=" + name + ", sex=" + sex
				+ ", apartmentId=" + apartmentId
				+ ", address=" + address + ", mobPhone=" + phone
				+  ", isDefault=" + isDefault + "]";
	}
}