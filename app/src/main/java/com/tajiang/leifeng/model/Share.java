package com.tajiang.leifeng.model;

/**
 * Created by 12154 on 2015/11/27.
 */
public class Share {

    private String url;
    private String title;
    private String description;
    private String styleImg;
    private String customimg;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStyleImg(String styleImg) {
        this.styleImg = styleImg;
    }

    public void setCustomimg(String customimg) {
        this.customimg = customimg;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStyleImg() {
        return styleImg;
    }

    public String getCustomimg() {
        return customimg;
    }
}
