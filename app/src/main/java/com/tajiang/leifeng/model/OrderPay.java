package com.tajiang.leifeng.model;

public class OrderPay {
	
	private String payId;

    private Long paySn;

    private String buyerId;

    private String apiPayState;
    
    public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public Long getPaySn() {
		return paySn;
	}

	public void setPaySn(Long paySn) {
		this.paySn = paySn;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getApiPayState() {
		return apiPayState;
	}

	public void setApiPayState(String apiPayState) {
		this.apiPayState = apiPayState;
	}

}