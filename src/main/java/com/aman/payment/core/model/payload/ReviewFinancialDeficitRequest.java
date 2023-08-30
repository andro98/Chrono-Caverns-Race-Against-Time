package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class ReviewFinancialDeficitRequest implements CoreBasePayload<ReviewFinancialDeficitRequest> {

	private String financialDeficitId;
	private String userName;
	private String status;
	private String comment;

	public ReviewFinancialDeficitRequest() {
	}

	public ReviewFinancialDeficitRequest(String financialDeficitId, String userName, String status, String comment) {
		super();
		this.financialDeficitId = financialDeficitId;
		this.userName = userName;
		this.status = status;
		this.comment = comment;
	}

	public String getFinancialDeficitId() {
		return financialDeficitId;
	}

	public void setFinancialDeficitId(String financialDeficitId) {
		this.financialDeficitId = financialDeficitId;
	}

	public String getUserName() {
		return userName;
	}

	public String getStatus() {
		return status;
	}

	public String getComment() {
		return comment;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public ReviewFinancialDeficitRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {

		return new ReviewFinancialDeficitRequest(cryptoMngrCoreService.decrypt(financialDeficitId),
				cryptoMngrCoreService.decrypt(userName), cryptoMngrCoreService.decrypt(status),
				cryptoMngrCoreService.decrypt(comment));
	}

}
