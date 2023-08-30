package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class URLRequest implements CoreBasePayload<URLRequest> {

	private String url;

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

	@Override
	public URLRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {

		return new URLRequest(cryptoMngrCoreService.decrypt(url));
	}

}
