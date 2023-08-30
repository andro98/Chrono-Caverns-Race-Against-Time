package com.aman.payment.maazoun.model.payload;

public class URLRequest {

	private String url;
	private String refRequestNumber;

	public URLRequest() {
	}

	public URLRequest(String url) {
		super();
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRefRequestNumber() {
		return refRequestNumber;
	}

	public void setRefRequestNumber(String refRequestNumber) {
		this.refRequestNumber = refRequestNumber;
	}

}
