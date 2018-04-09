package com.tajiang.leifeng.utils;

import com.tajiang.leifeng.application.TJApp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admins on 2016/11/4.
 */
public class BuyCarMapUtils {

    private static BuyCarMapUtils mBuyCarMapUtils;
    private Map<String, BuyCarUtils> buyCarMap;

    public static BuyCarMapUtils getIns() {
        if (mBuyCarMapUtils == null) {
            synchronized (UserUtils.class) {
                mBuyCarMapUtils = new BuyCarMapUtils();
            }
        }
        return mBuyCarMapUtils;
    }

    private BuyCarMapUtils() {
        this.buyCarMap = new HashMap<>();
    }

    public static BuyCarUtils getCurBuyCarUtils() {
        String stallsId = TJApp.getIns().getStallsId();
        if (getIns().buyCarMap.get(stallsId) == null) {
            mBuyCarMapUtils.buyCarMap.put(stallsId, new BuyCarUtils());
        }
        return mBuyCarMapUtils.buyCarMap.get(stallsId);
    }

    public static BuyCarUtils getSpecCurBuyCarUtils(String stallsId) {
        if (getIns().buyCarMap.get(stallsId) == null) {
            mBuyCarMapUtils.buyCarMap.put(stallsId, new BuyCarUtils());
        }
        return mBuyCarMapUtils.buyCarMap.get(stallsId);
    }

    public void clearAllBuyCar() {
        buyCarMap.clear();
    }
}
