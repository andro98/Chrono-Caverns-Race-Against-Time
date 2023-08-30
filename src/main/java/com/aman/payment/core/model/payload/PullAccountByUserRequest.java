package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class PullAccountByUserRequest implements CoreBasePayload<PullAccountByUserRequest> {

	private String username;

	public PullAccountByUserRequest() {
	}

	public PullAccountByUserRequest(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public PullAccountByUserRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		return new PullAccountByUserRequest(cryptoMngrCoreService.decrypt(username));
	}

}
