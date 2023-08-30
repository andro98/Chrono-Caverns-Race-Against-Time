package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class TransactionItemsRequest implements CoreBasePayload<TransactionItemsRequest>{
	
	private String subSrviceId;
	private String fees;

	
	public TransactionItemsRequest() {}


	public TransactionItemsRequest(String subSrviceId, String fees) {
		super();
		this.subSrviceId = subSrviceId;
		this.fees = fees;
	}


	public String getSubSrviceId() {
		return subSrviceId;
	}


	public String getFees() {
		return fees;
	}


	public void setSubSrviceId(String subSrviceId) {
		this.subSrviceId = subSrviceId;
	}


	public void setFees(String fees) {
		this.fees = fees;
	}


	@Override
	public TransactionItemsRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		return new TransactionItemsRequest(cryptoMngrCoreService.decrypt(subSrviceId), 
				cryptoMngrCoreService.decrypt(fees));
	}

	
}
