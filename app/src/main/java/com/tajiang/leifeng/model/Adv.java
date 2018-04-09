package com.tajiang.leifeng.model;

import java.io.Serializable;


public class Adv implements Serializable{

	private static final long serialVersionUID = -8894976363008461757L;

	private String id;

    private String title;

    private String imgUrl;

    private Integer type;

    private Integer slideSort;

    private String content;

    private long createdAt;

    private long updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSlideSort() {
        return slideSort;
    }

    public void setSlideSort(Integer slideSort) {
        this.slideSort = slideSort;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}