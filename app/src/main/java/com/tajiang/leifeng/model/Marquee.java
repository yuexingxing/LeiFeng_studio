package com.tajiang.leifeng.model;

/**
 * Created by hexiuhui
 */
public class Marquee {
    private String action;         //内容
    private String categoryName;  //类型
    private String showName;      //姓名

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}
