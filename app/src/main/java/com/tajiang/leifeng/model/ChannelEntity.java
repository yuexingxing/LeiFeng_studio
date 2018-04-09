package com.tajiang.leifeng.model;

import java.io.Serializable;

/**
 * hexiuhui
 */
public class ChannelEntity implements Serializable {

    private String code; //业务类型
    private String image; //图片
    private String label; // 标签
    private String name; //业务名称

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
