package com.aman.payment.core.model.dto;

public class TransactionMidsDTO {

	private Double midValue;
	private String midAccount;
	private String midAccountEnc;
	private String midBank;
	private String beneficiary;

	public TransactionMidsDTO() {
		super();
	}

	public Double getMidValue() {
		return midValue;
	}

	public void setMidValue(Double midValue) {
		this.midValue = midValue;
	}

	public String getMidAccount() {
		return midAccount;
	}

	public void setMidAccount(String midAccount) {
		this.midAccount = midAccount;
	}

	public String getMidBank() {
		return midBank;
	}

	public void setMidBank(String midBank) {
		this.midBank = midBank;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getMidAccountEnc() {
		return midAccountEnc;
	}

	public void setMidAccountEnc(String midAccountEnc) {
		this.midAccountEnc = midAccountEnc;
	}

}