package com.tajiang.leifeng.model;

/**
 * Created by hexiuhui\
 * */
public class ActiveInfo {
    private int acctId;          //账户id
    private int activeState;    //是否激活
    private int balance;        //可用余额
    private int depositAmount; //小票机押金
    private int frozenBalance; //冻结余额
    private int userId;         //用户id

    public int getAcctId() {
        return acctId;
    }

    public void setAcctId(int acctId) {
        this.acctId = acctId;
    }

    public int getActiveState() {
        return activeState;
    }

    public void setActiveState(int activeState) {
        this.activeState = activeState;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(int depositAmount) {
        this.depositAmount = depositAmount;
    }

    public int getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(int frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
