package com.aman.payment.maazoun.model.payload;

public class DeleteMaazounContractRequest {

	private String collectionInfoId;
	private String contractNumber;

	public DeleteMaazounContractRequest() {
	}

	public DeleteMaazounContractRequest(String collectionInfoId, String contractNumber) {
		super();
		this.collectionInfoId = collectionInfoId;
		this.contractNumber = contractNumber;
	}

	public String getCollectionInfoId() {
		return collectionInfoId;
	}

	public void setCollectionInfoId(String collectionInfoId) {
		this.collectionInfoId = collectionInfoId;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

}
