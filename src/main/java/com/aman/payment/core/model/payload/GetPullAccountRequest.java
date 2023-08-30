package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;


public class GetPullAccountRequest implements CoreBasePayload<GetPullAccountRequest> {

	private String settlementCode;
	
	private Long pullAccountId;

	public GetPullAccountRequest() {
	}

	

	public GetPullAccountRequest(String settlementCode) {
		super();
		this.settlementCode = settlementCode;
	}



	public String getSettlementCode() {
		return settlementCode;
	}



	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}



	public Long getPullAccountId() {
		return pullAccountId;
	}



	public void setPullAccountId(Long pullAccountId) {
		this.pullAccountId = pullAccountId;
	}



	@Override
	public GetPullAccountRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		return new GetPullAccountRequest(cryptoMngrCoreService.decrypt(settlementCode));
	}

}
