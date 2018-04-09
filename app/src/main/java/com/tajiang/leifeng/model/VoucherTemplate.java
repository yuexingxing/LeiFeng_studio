package com.tajiang.leifeng.model;

import java.math.BigDecimal;

public class VoucherTemplate {
	
	private Integer id;

    private String title;

    private String description;

    private long startDate;

    private long endDate;

    private Integer price;

    private BigDecimal limit;

    private Integer storeId;

    private String storeName;

    private Integer creatorId;

    private Byte type;

    private Byte state;

    private Integer total;

    private Integer giveout;

    private Integer used;

    private long createdAt;

    private long updatedAt;

    private Integer points;

    private Integer eachlimit;

    private String styleimg;

    private String customimg;
	
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return description;
	}

	public void setDesc(String desc) {
		this.description = desc;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getGiveout() {
		return giveout;
	}

	public void setGiveout(Integer giveout) {
		this.giveout = giveout;
	}

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
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

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getEachlimit() {
		return eachlimit;
	}

	public void setEachlimit(Integer eachlimit) {
		this.eachlimit = eachlimit;
	}

	public String getStyleimg() {
		return styleimg;
	}

	public void setStyleimg(String styleimg) {
		this.styleimg = styleimg;
	}

	public String getCustomimg() {
		return customimg;
	}

	public void setCustomimg(String customimg) {
		this.customimg = customimg;
	}

}