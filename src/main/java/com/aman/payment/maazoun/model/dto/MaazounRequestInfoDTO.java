package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class MaazounRequestInfoDTO {

	private String id;
	private String locationId;
	private String locationName;
	private String createAt;
	private String createBy;
	private String posId;
	private String posName;
	private String status;
	private String updatedAt;
	private String updatedBy;
	private String imageUrl;
	private String maazounName;
	private String maazounNationalId;
	private String maazounCardNumber;
	private String transactionCode;
	private String bookSerialNumber;
	private String amount;
	private String refRequestNumber;
	private String sectorName;
	private String bookTypeName;
	private String paymentCode;
	private String contractNumber;
	private String currentBookStatus;
	private String contractCount;
	private String bookFinancialNumber;
	private String maazouniaName;
	
	public MaazounRequestInfoDTO() {
		super();
	}


	public String getMaazounCardNumber() {
		return maazounCardNumber;
	}

	public void setMaazounCardNumber(String maazounCardNumber) {
		this.maazounCardNumber = maazounCardNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
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

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

	public String getMaazounNationalId() {
		return maazounNationalId;
	}

	public void setMaazounNationalId(String maazounNationalId) {
		this.maazounNationalId = maazounNationalId;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getBookSerialNumber() {
		return bookSerialNumber;
	}

	public void setBookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRefRequestNumber() {
		return refRequestNumber;
	}



	public void setRefRequestNumber(String refRequestNumber) {
		this.refRequestNumber = refRequestNumber;
	}



	public String getSectorName() {
		return sectorName;
	}


	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}


	public String getBookTypeName() {
		return bookTypeName;
	}


	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}


	public String getPaymentCode() {
		return paymentCode;
	}


	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}


	public String getContractNumber() {
		return contractNumber;
	}


	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}


	public String getCurrentBookStatus() {
		return currentBookStatus;
	}


	public void setCurrentBookStatus(String currentBookStatus) {
		this.currentBookStatus = currentBookStatus;
	}


	public String getContractCount() {
		return contractCount;
	}


	public void setContractCount(String contractCount) {
		this.contractCount = contractCount;
	}


	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}


	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}


	public String getMaazouniaName() {
		return maazouniaName;
	}


	public void setMaazouniaName(String maazouniaName) {
		this.maazouniaName = maazouniaName;
	}


	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
