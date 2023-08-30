package com.aman.payment.maazoun.model.dto;

import java.util.List;

import org.json.JSONObject;

public class MaazounBookReviewDTO {

	private String id;
	private String createAt;
	private String createBy;
	private String posName;
	private String locationName;
	private String status;
	private String imageUrl;
	private String maazounName;
	private String maazounNationalId;
	private String transactionCode;
	private String contractNumber;
	private String feeAmount;
	private String contractPaidAmount;
	private String bookTypeName;
	private String contractDate;
	private String husbandName;
	private String wifeName;
	private List<ContractValidationDTO> validationSet;

	public MaazounBookReviewDTO() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCreateAt() {
		return createAt;
	}


	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public String getPosName() {
		return posName;
	}


	public void setPosName(String posName) {
		this.posName = posName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getMaazounName() {
		return maazounName;
	}


	public void setMaazounName(String maazounName) {
		this.maazounName = maazounName;
	}


	public String getTransactionCode() {
		return transactionCode;
	}


	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}


	public String getContractNumber() {
		return contractNumber;
	}


	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getFeeAmount() {
		return feeAmount;
	}


	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}


	public String getContractPaidAmount() {
		return contractPaidAmount;
	}


	public void setContractPaidAmount(String contractPaidAmount) {
		this.contractPaidAmount = contractPaidAmount;
	}


	public String getBookTypeName() {
		return bookTypeName;
	}


	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public String getContractDate() {
		return contractDate;
	}


	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}


	public String getHusbandName() {
		return husbandName;
	}


	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}


	public String getWifeName() {
		return wifeName;
	}


	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}


	public List<ContractValidationDTO> getValidationSet() {
		return validationSet;
	}


	public void setValidationSet(List<ContractValidationDTO> validationSet) {
		this.validationSet = validationSet;
	}


	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public String getMaazounNationalId() {
		return maazounNationalId;
	}


	public void setMaazounNationalId(String maazounNationalId) {
		this.maazounNationalId = maazounNationalId;
	}


	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
