package com.tajiang.leifeng.model;

import java.util.List;

/**
 * hexiuhui
 */
public class CityData {

    private List<City> cityList;      //城市列表

    private List<City> hotCityList;  //热门城市列表

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public List<City> getHotCityList() {
        return hotCityList;
    }

    public void setHotCityList(List<City> hotCityList) {
        this.hotCityList = hotCityList;
    }
}
