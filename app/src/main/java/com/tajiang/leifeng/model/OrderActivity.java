package com.tajiang.leifeng.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Admins on 2016/11/15.
 */
public class OrderActivity implements Serializable{

    public static final int STATE_HAVE = 1;   //享受此次优惠价格
    public static final int STATE_NONE_HAVE = 0;

    private String name;

    private int state;

    private int type;

    private BigDecimal price;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
