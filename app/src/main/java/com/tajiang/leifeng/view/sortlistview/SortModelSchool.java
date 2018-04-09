package com.tajiang.leifeng.view.sortlistview;

import com.tajiang.leifeng.model.School;

public class SortModelSchool {

    private String name;//显示的数据
    private String sortLetters;//显示数据拼音的首字母

    private School mSchool;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public School getSchool() {
        return mSchool;
    }

    public void setSchool(School school) {
        mSchool = school;
    }
}

