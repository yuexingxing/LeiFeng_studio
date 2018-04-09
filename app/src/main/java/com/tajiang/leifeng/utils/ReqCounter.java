package com.tajiang.leifeng.utils;

public class ReqCounter {

    private Integer countSum = 0;

    public OnTaskCompleteListener mOnTaskCompleteListener;

    public ReqCounter (Integer countSum, OnTaskCompleteListener taskCompleteListener){
        this.countSum = countSum;
        mOnTaskCompleteListener = taskCompleteListener;
    }

    public void countDown(){

        countSum--;

        if(countSum == 0) {
            mOnTaskCompleteListener.onTaskComplete();
        }
    }

    public interface OnTaskCompleteListener {
        void onTaskComplete();
    }

}
