package com.aman.payment.maazoun.model.payload;

import java.util.List;

public class AddMaazounCollectionInfoRequest{

	private List<ContractListRequest> contractListRequest;
	private String totalAmount;
	private String paymentTypeId;
	private String visaNumber;
	private String paymentEcrResponse;
	private String paymentEcrRequest;
	private Long sectorId;
	private String maazouniaChurchNameType;
	private String paymentCode;
	
	public AddMaazounCollectionInfoRequest() {
	}

	public AddMaazounCollectionInfoRequest(List<ContractListRequest> contractListRequest) {
		super();
		this.contractListRequest = contractListRequest;
	}

	public List<ContractListRequest> getContractListRequest() {
		return contractListRequest;
	}

	public void setContractListRequest(List<ContractListRequest> contractListRequest) {
		this.contractListRequest = contractListRequest;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}


	public String getPaymentEcrResponse() {
		return paymentEcrResponse;
	}

	public void setPaymentEcrResponse(String paymentEcrResponse) {
		this.paymentEcrResponse = paymentEcrResponse;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public String getPaymentEcrRequest() {
		return paymentEcrRequest;
	}

	public void setPaymentEcrRequest(String paymentEcrRequest) {
		this.paymentEcrRequest = paymentEcrRequest;
	}

	public String getMaazouniaChurchNameType() {
		return maazouniaChurchNameType;
	}

	public void setMaazouniaChurchNameType(String maazouniaChurchNameType) {
		this.maazouniaChurchNameType = maazouniaChurchNameType;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	 
}
