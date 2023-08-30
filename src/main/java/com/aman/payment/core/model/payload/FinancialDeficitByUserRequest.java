package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class FinancialDeficitByUserRequest implements CoreBasePayload<FinancialDeficitByUserRequest> {

	private String userName;

	public FinancialDeficitByUserRequest() {
	}

	public FinancialDeficitByUserRequest(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	
	@Override
	public FinancialDeficitByUserRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		return new FinancialDeficitByUserRequest(cryptoMngrCoreService.decrypt(userName));
	}

}
