package com.tajiang.leifeng.model;

public class FoodClass {

    /**
     * id : 1
     * name : 鸡肉套餐
     * image :
     * storeId : 1k6x1
     * num : 6
     * sort : 0
     * description : null
     */

    private String classId;
    private String className;
    private int num;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "FoodClass{" +
                "id=" + classId +
                ", name='" + className + '\'' +
                ", num=" + num +
                '}';
    }
}
