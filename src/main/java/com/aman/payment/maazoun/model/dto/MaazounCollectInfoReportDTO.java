package com.aman.payment.maazoun.model.dto;

import javax.persistence.Column;

import org.json.JSONObject;

import com.aman.payment.maazoun.model.MaazounContractQuota;

public class MaazounCollectInfoReportDTO extends MaazounContractQuota{

	private String locationName;
	private String createAt;
	private String createBy;
	private String posName;
	private String sectorName;
	private String status;
	
	private String maazounName;
	private String maazounNationalId;
	private String maazounCardNumber;
	private String bookTypeName;
	private String bookTypeId;
	
	private String transactionCode;
	private String amanLogoUrl;
	private String prosecutorLogoUrl;

	// parameters related with print books registered
	private String totalAmount;
	private String feeAmount;
	private String contractPaidAmount;
	private String bookSerialNumber;
	private String bookFinancialNumber;
	private String contractFinancialNumber;
	private String sequance;
	private String refRequestNumber;
	private String contractDate;
	private String contractNumber;
	private String husbandName;
	private String wifeName;
	
	private Double contractPaidAdvanceFees;
	private Double contractPaidDelayedFees;
	private Double contractPaidConditionsFees;
	
	private String maazouniaName;

	public MaazounCollectInfoReportDTO() {
		super();
	}

	public String getMaazounCardNumber() {
		return maazounCardNumber;
	}

	public String getSectorName() {
		return sectorName;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
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

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
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

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
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

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getProsecutorLogoUrl() {
		return prosecutorLogoUrl;
	}

	public void setProsecutorLogoUrl(String prosecutorLogoUrl) {
		this.prosecutorLogoUrl = prosecutorLogoUrl;
	}

	public String getContractFinancialNumber() {
		return contractFinancialNumber;
	}

	public void setContractFinancialNumber(String contractFinancialNumber) {
		this.contractFinancialNumber = contractFinancialNumber;
	}

	public Double getContractPaidAdvanceFees() {
		return contractPaidAdvanceFees;
	}

	public void setContractPaidAdvanceFees(Double contractPaidAdvanceFees) {
		this.contractPaidAdvanceFees = contractPaidAdvanceFees;
	}

	public Double getContractPaidDelayedFees() {
		return contractPaidDelayedFees;
	}

	public void setContractPaidDelayedFees(Double contractPaidDelayedFees) {
		this.contractPaidDelayedFees = contractPaidDelayedFees;
	}

	public Double getContractPaidConditionsFees() {
		return contractPaidConditionsFees;
	}

	public void setContractPaidConditionsFees(Double contractPaidConditionsFees) {
		this.contractPaidConditionsFees = contractPaidConditionsFees;
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
