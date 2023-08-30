package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class TransactionCodeRequest implements CoreBasePayload<TransactionCodeRequest> {

	private String transactionCode;
	private String posId;

	public TransactionCodeRequest() {
	}

	public TransactionCodeRequest(String transactionCode, String posId) {
		super();
		this.transactionCode = transactionCode;
		this.posId = posId;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	@Override
	public TransactionCodeRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {

		return new TransactionCodeRequest(
				cryptoMngrCoreService.decrypt(transactionCode),
				cryptoMngrCoreService.decrypt(posId));
	}

}
