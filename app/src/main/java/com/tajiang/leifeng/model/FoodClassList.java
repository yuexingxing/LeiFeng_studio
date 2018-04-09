package com.tajiang.leifeng.model;

import java.util.List;

/**
 * Created by hexiuhui
 */
public class FoodClassList {
    private List<FoodClass> goodsClassList;  //菜品分类

    private List<String> labelList;  //备注标签

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public List<FoodClass> getGoodsClassList() {
        return goodsClassList;
    }

    public void setGoodsClassList(List<FoodClass> goodsClassList) {
        this.goodsClassList = goodsClassList;
    }
}
