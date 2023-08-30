package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;


public class OpeningPullAccountRequest implements CoreBasePayload<OpeningPullAccountRequest> {

	private String serviceId;

	public OpeningPullAccountRequest() {
	}

	public OpeningPullAccountRequest(String serviceId) {
		super();
		this.serviceId = serviceId;
	}

	

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public OpeningPullAccountRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		return new OpeningPullAccountRequest(cryptoMngrCoreService.decrypt(serviceId));
	}

}
