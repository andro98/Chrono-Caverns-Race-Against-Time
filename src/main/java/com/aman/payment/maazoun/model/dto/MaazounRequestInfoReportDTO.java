package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

import com.aman.payment.maazoun.model.MaazounBookQuota;

public class MaazounRequestInfoReportDTO extends MaazounBookQuota{

	private String locationName;
	private String sectorName;
	private String createAt;
	private String createBy;
	private String posName;
	private String status;
	private String maazounName;
	private String maazounNationalId;
	private String maazounCardNumber;
	private String transactionCode;
	private String amanLogoUrl;
	private String prosecutorLogoUrl;

	// parameters related with print books registered
	private Double amount;
	private Double fees;
	private String bookTypeName;
	private String bookTypeId;
	private String bookSerialNumber;
	private String contractCount;
	private String bookFinancialNumber;
	private String sequance;
	private String refRequestNumber;
	private String maazouniaName;
	
	

	public MaazounRequestInfoReportDTO() {
		super();
	}

	public String getMaazounCardNumber() {
		return maazounCardNumber;
	}

	public void setMaazounCardNumber(String maazounCardNumber) {
		this.maazounCardNumber = maazounCardNumber;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	public String getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(String bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public String getBookSerialNumber() {
		return bookSerialNumber;
	}

	public void setBookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}


	public String getAmanLogoUrl() {
		return amanLogoUrl;
	}

	public void setAmanLogoUrl(String amanLogoUrl) {
		this.amanLogoUrl = amanLogoUrl;
	}

	public String getSequance() {
		return sequance;
	}

	public void setSequance(String sequance) {
		this.sequance = sequance;
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

	

	public String getProsecutorLogoUrl() {
		return prosecutorLogoUrl;
	}

	public void setProsecutorLogoUrl(String prosecutorLogoUrl) {
		this.prosecutorLogoUrl = prosecutorLogoUrl;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	

	public String getContractCount() {
		return contractCount;
	}

	public void setContractCount(String contractCount) {
		this.contractCount = contractCount;
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
