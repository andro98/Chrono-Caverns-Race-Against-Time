package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class ReviewSettlementRequest implements CoreBasePayload<ReviewSettlementRequest> {

	private String pullAccountId;
	private String userName;
	private String status;
	private String comment;

	public ReviewSettlementRequest() {
	}

	public ReviewSettlementRequest(String pullAccountId, String userName, String status, String comment) {
		super();
		this.pullAccountId = pullAccountId;
		this.userName = userName;
		this.status = status;
		this.comment = comment;
	}



	public String getPullAccountId() {
		return pullAccountId;
	}

	public void setPullAccountId(String pullAccountId) {
		this.pullAccountId = pullAccountId;
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
	public ReviewSettlementRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {

		return new ReviewSettlementRequest(cryptoMngrCoreService.decrypt(pullAccountId),
				cryptoMngrCoreService.decrypt(userName), cryptoMngrCoreService.decrypt(status),
				cryptoMngrCoreService.decrypt(comment));
	}

}
