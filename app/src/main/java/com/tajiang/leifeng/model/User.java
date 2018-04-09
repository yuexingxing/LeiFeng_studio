package com.tajiang.leifeng.model;

import java.io.Serializable;

public class User implements Serializable {

    public static final int NONE = -1;
    public static final int MAN = 0;
    public static final int WOMAN = 1;

    private static final long serialVersionUID = 3227917738203088491L;

    private String loginname;  //用户名

    /**
     * 全站唯一
     */
    private String nikeName = "未登录";  //昵称

    private String id;

    private String signature;

    private Integer apartmentId;

    private UserAddress userAddress;

    private Long roles;

    private String avatar = "ww";  //头像

    private String phone;  //电话

    private boolean leifeng;

    // 未认证1 正在审核2 3审核失败 4认证成功
    private int certified;

    private Boolean archive;

    /**
     * 客户端设备类型
     */
    private int deviceType;
    /**
     * IOS手机的token
     */
    private String deviceToken;
    /**
     * 性别 0女1男
     */
    private Integer sex = 3;

    /**
     * 密码
     */
    private String password;

    //用户地址数量
    private int userAddressCount = 0;

    //优惠券数量
    private int voucherNum = 0;

    private Double balance = 0.0;  //余额

    private Integer userLevel;

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getRoles() {
        return roles;
    }

    public void setRoles(Long roles) {
        this.roles = roles;
    }

    public boolean hasRole(long role) {
        return roles != null && (roles & role) != 0;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCertified() {
        return certified;
    }

    public void setCertified(int certified) {
        this.certified = certified;
    }

    public String getUsername() {
        return this.getLoginname();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public int getUserAddressCount() {
        return userAddressCount;
    }

    public void setUserAddressCount(int userAddressCount) {
        this.userAddressCount = userAddressCount;
    }

    public int getVoucherNum() {
        return voucherNum;
    }

    public void setVoucherNum(int voucherNum) {
        this.voucherNum = voucherNum;
    }

    @Override
    public String toString() {
        return "User [loginname=" + loginname + ", name=" + nikeName
                + ", signature=" + signature + ", apartmentId=" + apartmentId
                + ", userAddress=" + userAddress + ", roles=" + roles
                + ", avatar=" + avatar + ", phone=" + phone + ", takeState="
                + certified + ", archive=" + archive + ", deviceType="
                + deviceType + ", deviceToken=" + deviceToken + ", sex=" + sex
                + ", password=" + password + ", userAddressCount="
                + userAddressCount + ", voucherCount=" + voucherNum + "]";
    }

    public boolean isLeifeng() {
        return leifeng;
    }

    public void setLeifeng(boolean leifeng) {
        this.leifeng = leifeng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}