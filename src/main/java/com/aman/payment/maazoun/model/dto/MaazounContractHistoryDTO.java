package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class MaazounContractHistoryDTO {

	private String contractSerialNumber;
	private String contractFinancialNumber;

	private String collectedDateBy;
	private String reviewedDateBy;
	private String receivedDateBy;
	private String receivedStatus;


	public MaazounContractHistoryDTO() {
		super();
	}

	public String getContractSerialNumber() {
		return contractSerialNumber;
	}

	public void setContractSerialNumber(String contractSerialNumber) {
		this.contractSerialNumber = contractSerialNumber;
	}

	public String getContractFinancialNumber() {
		return contractFinancialNumber;
	}

	public void setContractFinancialNumber(String contractFinancialNumber) {
		this.contractFinancialNumber = contractFinancialNumber;
	}

	public String getCollectedDateBy() {
		return collectedDateBy;
	}

	public void setCollectedDateBy(String collectedDateBy) {
		this.collectedDateBy = collectedDateBy;
	}

	public String getReviewedDateBy() {
		return reviewedDateBy;
	}

	public void setReviewedDateBy(String reviewedDateBy) {
		this.reviewedDateBy = reviewedDateBy;
	}

	public String getReceivedDateBy() {
		return receivedDateBy;
	}

	public void setReceivedDateBy(String receivedDateBy) {
		this.receivedDateBy = receivedDateBy;
	}

	public String getReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}
	
	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}