package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class SupplyOrderReferenceNumberRequest implements AuthBasePayload<SupplyOrderReferenceNumberRequest>{

	private String sectorId;
	
	public SupplyOrderReferenceNumberRequest() {
		// TODO Auto-generated constructor stub
	}

	public SupplyOrderReferenceNumberRequest(String sectorId) {
		super();
		this.sectorId = sectorId;
	}

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	
	@Override
	public SupplyOrderReferenceNumberRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		// TODO Auto-generated method stub
		return  new SupplyOrderReferenceNumberRequest(
				cryptoMngrAuthService.decrypt(sectorId)) ;
	}
}
