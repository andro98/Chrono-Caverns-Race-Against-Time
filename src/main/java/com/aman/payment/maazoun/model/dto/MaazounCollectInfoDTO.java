package com.aman.payment.maazoun.model.dto;

import java.util.List;

import org.json.JSONObject;

import com.aman.payment.maazoun.model.MaazounContractQuota;

public class MaazounCollectInfoDTO extends MaazounContractQuota{

	private String id;
	private String locationId;
	private String locationName;
	private String createAt;
	private String createBy;
	private String posId;
	private String posName;
	private String sectorName;
	private String status;
	private String imageUrl;
	private String receiptUrl;
	private String maazounName;
	private String maazounNationalId;
	private String transactionCode;
	private String bookSerialNumber;
	private String bookFinancialNumber;
	private String amount;
	private String refRequestNumber;
	private String paymentCode;
	private String contractNumber;
	private String typeId;
	private String contractType;
	private String sectorId;

	private String feeAmount;
	private String contractPaidAmount;
	private String bookTypeName;
	private String contractDate;
	private String husbandName;
	private String wifeName;
	private List<String> validationSet;
	private String contractReviewFlag;
	private String wifeNationalId;
	private String husbandNationalId;
	private String maazouniaChurchNameType;
	private String custody;
	private String currentBookStatus;
	private String contractCount;
  

	public MaazounCollectInfoDTO() {
		super();
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

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}



	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
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


	public List<String> getValidationSet() {
		return validationSet;
	}


	public void setValidationSet(List<String> validationSet) {
		this.validationSet = validationSet;
	}


	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public String getContractReviewFlag() {
		return contractReviewFlag;
	}

	public void setContractReviewFlag(String contractReviewFlag) {
		this.contractReviewFlag = contractReviewFlag;
	}
	
	public String getWifeNationalId() {
		return wifeNationalId;
	}

	public void setWifeNationalId(String wifeNationalId) {
		this.wifeNationalId = wifeNationalId;
	}

	public String getHusbandNationalId() {
		return husbandNationalId;
	}

	public void setHusbandNationalId(String husbandNationalId) {
		this.husbandNationalId = husbandNationalId;
	}
	
	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getMaazouniaChurchNameType() {
		return maazouniaChurchNameType;
	}

	public void setMaazouniaChurchNameType(String maazouniaChurchNameType) {
		this.maazouniaChurchNameType = maazouniaChurchNameType;
	}
   
	 
	public String getCustody() {
		return custody;
	}

	public void setCustody(String custody) {
		this.custody = custody;
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

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
