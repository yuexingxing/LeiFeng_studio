package com.tajiang.leifeng.common.http;

import java.io.File;

import okhttp3.RequestBody;

/**
 * Created by Admins on 2016/12/28.
 */
public class HttpHandler implements HttpLoad{

    private HttpLoad httpLoad;

    public HttpHandler(HttpResponseListener listener) {
        httpLoad = new HttpForRetrofit(listener);
    }

    //TODO.......Created By QiLi.........


    /**
     * 取消网络请求
     */
    @Override
    public void cancel() {
        httpLoad.cancel();
    }

    @Override
    public void doLogin(String userName, String psw, String clientId, String imei, int tag) {
        httpLoad.doLogin(userName, psw, clientId, imei, tag);
    }

    /*
    * 用户退出
    * */
    @Override
    public void doLogout(int tag) {
        httpLoad.doLogout(tag);
    }

    @Override
    public void getOrderList(int page, int pageSize, int tag) {
        httpLoad.getOrderList(page, pageSize, tag);
    }

    @Override
    public void getOrderDetail(String orderId, int tag) {
        httpLoad.getOrderDetail(orderId, tag);
    }

    @Override
    public void doLoginByQQ(String accessToken, int authType, String clientId, String imei, String uid, int tag) {
        httpLoad.doLoginByQQ(accessToken, authType, clientId, imei, uid, tag);
    }

    @Override
    public void doLoginByWeiChat(String accessToken, String uid, int tag) {
        httpLoad.doLoginByWeiChat(accessToken, uid, tag);
    }

    @Override
    public void doLoginByWeiBo(String accessToken, String uid, int tag) {
        httpLoad.doLoginByWeiBo(accessToken, uid, tag);
    }

    @Override
    public void getAdvList(int schoolId, int apId, int tag) {
        httpLoad.getAdvList(schoolId, apId, tag);
    }

    @Override
    public void getStalls(int apartmentId, int page, int pageSize, String rangeCode, int recomTag, int tag) {
        httpLoad.getStalls(apartmentId, page, pageSize, rangeCode, recomTag, tag);
    }

    @Override
    public void getMarquee(int schoolId, int tag) {
        httpLoad.getMarquee(schoolId, tag);
    }

    @Override
    public void getRangelist(int tag) {
        httpLoad.getRangelist(tag);
    }

    @Override
    public void getStoreDistributionRange(int schoolId, int tag) {
        httpLoad.getStoreDistributionRange(schoolId, tag);
    }

    @Override
    public void getSchoolOfLocalCity(String areaName, int tag) {
        httpLoad.getSchoolOfLocalCity(areaName,tag);
    }

    @Override
    public void getNearSchools(double lat, double lng, int distance, int tag) {
        httpLoad.getNearSchools(lat, lng, distance, tag);
    }

    @Override
    public void getSchoolSearchList(String schoolName, int tag) {
        httpLoad.getSchoolSearchList(schoolName, tag);
    }

    @Override
    public void getCityHotList(int tag) {
        httpLoad.getCityHotList(tag);
    }

    @Override
    public void getCityList(int tag) {
        httpLoad.getCityList(tag);
    }

    @Override
    public void getAppInfo(int tag) {
        httpLoad.getAppInfo(tag);
    }

    @Override
    public void collectClientMsg(String userId, String clientId, int os, String osVersion, String deviceModel, String appVersion, int tag) {
        httpLoad.collectClientMsg(userId, clientId, os, osVersion, deviceModel, appVersion, tag);
    }

    @Override
    public void userDoGetInfor(int tag) {
        httpLoad.userDoGetInfor(tag);
    }

    @Override
    public void getStallsgoodsClass(String stallsId, int tag) {
        httpLoad.getStallsgoodsClass(stallsId, tag);
    }

    @Override
    public void getStallsGoods(int apartmentId, String shopId, String goodsClassId, int page, int pageSize, int hotTag, int tag) {
        httpLoad.getStallsGoods(apartmentId, shopId, goodsClassId, page, pageSize, hotTag, tag);
    }

    @Override
    public void getUserCoupon(int tag) {
        httpLoad.getUserCoupon(tag);
    }

    @Override
    public void getEstimatedTime(String shopId, int tag) {
        httpLoad.getEstimatedTime(shopId, tag);
    }

    @Override
    public void showexplainlabelReq(int tag) {
        httpLoad.showexplainlabelReq(tag);
    }

    @Override
    public void placeorder(Integer activityId, String buyerRemark, String cartIds, String channel, String delyEndDate, int delyFast, String delyStartDate, String shopId, int userAddressId, Integer voucherId, int tag) {
        httpLoad.placeorder(activityId, buyerRemark, cartIds, channel, delyEndDate, delyFast, delyStartDate, shopId, userAddressId, voucherId, tag);
    }

    @Override
    public void doOrderBindPhone(String phone, int tag) {
        httpLoad.doOrderBindPhone(phone, tag);
    }

    @Override
    public void doPayOrderPrice(String cart_ids, String voucherId, String stallsId, int tag) {
        httpLoad.doPayOrderPrice(cart_ids, voucherId, stallsId, tag);
    }

    @Override
    public void userWalletPayPswIsAuthReq(int tag) {
        httpLoad.userWalletPayPswIsAuthReq(tag);
    }

    @Override
    public void doPayOrder(String bizId, int bizType, String payPwd, int thdType, int tag) {
        httpLoad.doPayOrder(bizId, bizType, payPwd, thdType, tag);
    }

    @Override
    public void doPayOrder(String bizId, int bizType, int thdType, int tag) {
        httpLoad.doPayOrder(bizId, bizType, thdType, tag);
    }

    @Override
    public void userWalletPayPswAuthReq(String pdPassword, int tag) {
        httpLoad.userWalletPayPswAuthReq(pdPassword, tag);
    }

    @Override
    public void getQiNiuTokenRequest(String bucketName, int tag) {
        httpLoad.getQiNiuTokenRequest(bucketName, tag);
    }

    @Override
    public void upLoadUserIcon(RequestBody Body, int tag) {
        httpLoad.upLoadUserIcon(Body, tag);
    }

    @Override
    public void getMessageList(int page, int pageSize, int tag) {
        httpLoad.getMessageList(page, pageSize, tag);
    }

    @Override
    public void doModifySex(String sex, int tag) {
        httpLoad.doModifySex(sex, tag);
    }

    @Override
    public void doModifyNickName(String nikeName, int tag) {
        httpLoad.doModifyNickName(nikeName, tag);
    }

    @Override
    public void userPDActivity(int tag) {
        httpLoad.userPDActivity(tag);
    }

    @Override
    public void userPredepositLogsReq(Integer type, Integer page, Integer pagesize, int tag) {
        httpLoad.userPredepositLogsReq(type, page, pagesize, tag);
    }

    @Override
    public void userWalletPayPswModifly(String oldPwd, String psw, int tag) {
        httpLoad.userWalletPayPswModifly(oldPwd, psw, tag);
    }

    @Override
    public void userWalletPayPswCreate(String psw, int tag) {
        httpLoad.userWalletPayPswCreate(psw, tag);
    }

    @Override
    public void userWalletPayPswCode(String phone, String code, int tag) {
        httpLoad.userWalletPayPswCode(phone, code, tag);
    }

    @Override
    public void getRegisterCode(String codeType, String phone, int tag) {
        httpLoad.getRegisterCode(codeType, phone, tag);
    }


    //TODO.......Created By QiLi.........


    public void rapGetUserCashCount(int tag) {
        httpLoad.rapGetUserCashCount(tag);
    }

    public void rapDoUserCash(int tag) {
        httpLoad.rapDoUserCash(tag);
    }

    public void getAdressList(int page, int pageSize, int tag) {
        httpLoad.getAdressList(page, pageSize, tag);
    }


    public void userDoAdressDelete(int id, int tag) {
        httpLoad.userDoAdressDelete(id,tag);
    }

    public void doModifyAdress(String address, int addressId, int apartmentId,int isDefault, String name, String phone, int sex, int tagUserAddressModify) {
        httpLoad.doModifyAdress(address, addressId, apartmentId, isDefault, name, phone, sex, tagUserAddressModify);
    }

    @Override
    public void setAddressDefault(int addressId, int tagSetAddressDefault) {
        httpLoad.setAddressDefault(addressId, tagSetAddressDefault);
    }

    public void getApartmentBySchoolId(int schoolId, int tagGetSchoolApartmentList) {
        httpLoad.getApartmentBySchoolId(schoolId,tagGetSchoolApartmentList);
    }

    public void doAddAdress(String trueName, int sex, int apartmentId, String address, String mobPhone, int isDefault, int tagUserAddressAdd) {
        httpLoad.doAddAdress(trueName,sex,apartmentId,address,mobPhone,isDefault,tagUserAddressAdd);
    }

    public void getUserInviteInfo(String userId, int tagUserInvite) {
        httpLoad.getUserInviteInfo(userId,tagUserInvite);
    }

    public void doUserFeedBack(String content, int tagUserFeedBack) {
        httpLoad.doUserFeedBack(content, tagUserFeedBack);
    }

    public void doUserModifyPassword(String oldpassword, String password,int tag) {
        httpLoad.doUserModifyPassword(oldpassword,password,tag);
    }


    public void doWellatPredeposit(int paymentId, double predeposit, int tag) {
        httpLoad.doWellatPredeposit(paymentId, predeposit, tag);
    }

    @Override
    public void SignUpReq(int schoolId, String mobilePhone, String regcode, String password, Integer type, String invitationCode, int tag) {
        httpLoad.SignUpReq(schoolId, mobilePhone, regcode, password, type, invitationCode, tag);
    }

    @Override
    public void doRegister(String invitationCode, String password, String phone, String regcode, int tag) {
        httpLoad.doRegister(invitationCode, password, phone, regcode, tag);
    }

    @Override
    public void orderDoCancal(String orderId, int tag) {
        httpLoad.orderDoCancal(orderId, tag);
    }

    @Override
    public void userOrderComplete(String orderId, int tag) {
        httpLoad.userOrderComplete(orderId, tag);
    }

    @Override
    public void userOrderHurry(String orderId, int tag) {
        httpLoad.userOrderHurry(orderId, tag);
    }

    @Override
    public void setTlement(String cartIds, String shopId, int tag) {
        httpLoad.setTlement(cartIds, shopId, tag);
    }

    @Override
    public void userOrderGoodEvaluate(String goodId, int numStar, int tag) {
        httpLoad.userOrderGoodEvaluate(goodId, numStar, tag);
    }

    @Override
    public void evaluate(String content, String orderId, int service, int speed, int taste, int tag) {
        httpLoad.evaluate(content, orderId, service, speed, taste, tag);
    }

    @Override
    public void getSchoolByAreaId(int areaId, String areaName, int tag) {
        httpLoad.getSchoolByAreaId(areaId, areaName, tag);
    }

    @Override
    public void getSchoolStorList(int schoolId, int tag) {
        httpLoad.getSchoolStorList(schoolId, tag);
    }

    @Override
    public void rapDoUserJoin(String name, String phone, String certificate, Integer apartmentId, String address, int tag) {
        httpLoad.rapDoUserJoin(name, phone, certificate, apartmentId, address, tag);
    }


}
