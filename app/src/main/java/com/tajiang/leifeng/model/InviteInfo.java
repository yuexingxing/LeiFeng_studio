package com.tajiang.leifeng.model;

/**
 * Created by ciko on 16/4/22.
 */
public class InviteInfo {

    /**
     * id : 18ydg
     * userId : 18ydg
     * invitationCode : s1n3j8
     * friendUserId : null
     * createAt : 1460959571000
     * updateAt : 1460959571000
     * inviteNumber : 0
     * usId : null
     * linkaddress : http://www.itajiang.com/shareApp/shareapp.html
     * red : 邀请一位好友可获得1元红包<br>  好友不限，优惠不限
     */

    private String id;
    private String userId;
    private String invitationCode;
    private Object friendUserId;
    private long createAt;
    private long updateAt;
    private int inviteNumber;
    private Object usId;
    private String linkaddress;
    private String red;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public Object getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(Object friendUserId) {
        this.friendUserId = friendUserId;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public int getInviteNumber() {
        return inviteNumber;
    }

    public void setInviteNumber(int inviteNumber) {
        this.inviteNumber = inviteNumber;
    }

    public Object getUsId() {
        return usId;
    }

    public void setUsId(Object usId) {
        this.usId = usId;
    }

    public String getLinkaddress() {
        return linkaddress;
    }

    public void setLinkaddress(String linkaddress) {
        this.linkaddress = linkaddress;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }
}
