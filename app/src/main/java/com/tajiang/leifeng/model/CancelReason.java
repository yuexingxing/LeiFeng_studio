package com.tajiang.leifeng.model;

import java.io.Serializable;

/**
 * Created by wushu on 2017-02-05.
 */

public class CancelReason implements Serializable{
    private boolean isChoose = false;
    private String reasonText;

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    @Override
    public String toString() {
        return "CancelReason{" +
                "isChoose=" + isChoose +
                ", reasonText='" + reasonText + '\'' +
                '}';
    }

    public CancelReason( String reasonText) {
        this.reasonText = reasonText;
    }
}
