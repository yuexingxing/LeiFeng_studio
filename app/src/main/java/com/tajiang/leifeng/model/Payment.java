package com.tajiang.leifeng.model;

public class Payment {

	private Byte paymentId;

    private String paymentCode;

    private String paymentName;

    private String paymentState;

    private String paymentConfig;
	
    public Byte getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Byte paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public String getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(String paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

}