package com.tajiang.leifeng.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.tajiang.leifeng.activity.HomeActivity;
import com.tajiang.leifeng.model.BuyCar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BuyCarUtils {

    private static BuyCarUtils mBuyCarUtils;

    private List<Integer> markList = new ArrayList<>();
    private String markInput = "";

    private List<String> allMark = new ArrayList<>();

    private BuyCar buyCar;

//    public static BuyCarUtils getIns() {
//        if (mBuyCarUtils == null) {
//            synchronized (UserUtils.class) {
//                mBuyCarUtils = new BuyCarUtils();
//            }
//        }
//        return mBuyCarUtils;
//    }
//
//    private BuyCarUtils() {
//        buyCar = new BuyCar();
//    }

    public BuyCarUtils() {
        buyCar = new BuyCar();
    }

    public BuyCar getBuyCar() {
        return buyCar;
    }

    /**
     * 清空购物车
     */
    public void clearBuyCar() {
        buyCar = new BuyCar();
        markList = new ArrayList<Integer>();
        allMark = new ArrayList<String>();
        markInput = "";
    }

    /**
     * 获取购物车的商品数量
     */
    public int getBuyCarGoodSumCount() {
        return buyCar.getSumCount();
    }

    /**
     * 获取购物车的商品总价
     */
    public BigDecimal getBuyCarGoodSumMoney() {
        return buyCar.getSumPrice();
    }

    /**
     * 获取购物车的商品总价
     */
    public BigDecimal getBuyCarGoodSumWithBoxFee() {
        return buyCar.getSumPriceWithBoxFee();
    }

    public String getMarkInput() {
        return markInput;
    }

    public void setMarkInput(String markInput) {
        this.markInput = markInput;
    }

    public List<Integer> getMarkList() {
        return markList;
    }

    public void setMarkList(List<Integer> markList) {
        this.markList = markList;
    }

    public List<String> getAllMark() {
        return allMark;
    }

    public void setAllMark(List<String> allMark) {
        this.allMark = allMark;
    }

    public String getMarkerText() {

        List<String> allMark = getAllMark();
        List<Integer> tagCheckList = getMarkList();
        String inputMarker = getMarkInput();

        StringBuffer markerString = new StringBuffer();
        if (allMark.size() != 0) {
            for (Integer position : tagCheckList) {
                markerString.append(allMark.get(position) + "，");
            }
        }

        if (TextUtils.isEmpty(inputMarker)) {
            if (tagCheckList.size() != 0) {
                markerString.delete(markerString.length() - 1, markerString.length());
            }
        } else {
            markerString.append(inputMarker);
        }

        return markerString.toString();
    }

    //跳转到食堂模块首页
    public static void intent2FoodActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("CODE_ORDER_PAY", "CODE_ORDER_PAY");
        context.startActivity(intent);
    }

}
