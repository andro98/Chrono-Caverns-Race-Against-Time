package com.aman.payment.core.model.dto;

import org.json.JSONObject;

public class FinanacialMidsDTO {

	private String transactionCode;
	private String canceledTransactionCode;
	private String settlementCode;
	private String totalAmountAndCanceled;
	private String totalRefundAmount;
	private String totalCanceledAmount;
	private String totalAmount;
	private String serviceName;
	private String merchantName;
	private String posName;
	private String posId;
	private String locationName;
	private String courtName;
	private String cityName;
	private String sectorName;
	private String paymentMethod;
	private String visaNumber;
	private String typeName;
	private String paymentCode;
	private String refRequestNumber;
	
	private String createdDate;
	private String canceledCreatedDate;
	private String createdBy;
	private String transactionStatus;
	private String pullAccountStatus;
	
	private String bookAmanAccount;
	private String bookCentralAccount;
	private String bookNyabaAccount;
	
	private String contractBankNaserAccount;
	private String contractFinancialMinistryAccount;
	private String contractAbnyatMhakemAccount;
	private String contractGamyatMazoounenAccount;
	private String totalAmountCreditors;
	
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getSettlementCode() {
		return settlementCode;
	}
	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getPosName() {
		return posName;
	}
	public void setPosName(String posName) {
		this.posName = posName;
	}
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getSectorName() {
		return sectorName;
	}
	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getVisaNumber() {
		return visaNumber;
	}
	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getPullAccountStatus() {
		return pullAccountStatus;
	}
	public void setPullAccountStatus(String pullAccountStatus) {
		this.pullAccountStatus = pullAccountStatus;
	}
	public String getBookAmanAccount() {
		return bookAmanAccount;
	}
	public void setBookAmanAccount(String bookAmanAccount) {
		this.bookAmanAccount = bookAmanAccount;
	}
	public String getBookCentralAccount() {
		return bookCentralAccount;
	}
	public void setBookCentralAccount(String bookCentralAccount) {
		this.bookCentralAccount = bookCentralAccount;
	}
	public String getBookNyabaAccount() {
		return bookNyabaAccount;
	}
	public void setBookNyabaAccount(String bookNyabaAccount) {
		this.bookNyabaAccount = bookNyabaAccount;
	}
	public String getContractBankNaserAccount() {
		return contractBankNaserAccount;
	}
	public void setContractBankNaserAccount(String contractBankNaserAccount) {
		this.contractBankNaserAccount = contractBankNaserAccount;
	}
	public String getContractFinancialMinistryAccount() {
		return contractFinancialMinistryAccount;
	}
	public void setContractFinancialMinistryAccount(String contractFinancialMinistryAccount) {
		this.contractFinancialMinistryAccount = contractFinancialMinistryAccount;
	}
	public String getContractAbnyatMhakemAccount() {
		return contractAbnyatMhakemAccount;
	}
	public void setContractAbnyatMhakemAccount(String contractAbnyatMhakemAccount) {
		this.contractAbnyatMhakemAccount = contractAbnyatMhakemAccount;
	}
	public String getContractGamyatMazoounenAccount() {
		return contractGamyatMazoounenAccount;
	}
	public void setContractGamyatMazoounenAccount(String contractGamyatMazoounenAccount) {
		this.contractGamyatMazoounenAccount = contractGamyatMazoounenAccount;
	}

	
	
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public String getRefRequestNumber() {
		return refRequestNumber;
	}
	public void setRefRequestNumber(String refRequestNumber) {
		this.refRequestNumber = refRequestNumber;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTotalAmountCreditors() {
		return totalAmountCreditors;
	}
	public void setTotalAmountCreditors(String totalAmountCreditors) {
		this.totalAmountCreditors = totalAmountCreditors;
	}
	
	public String getCourtName() {
		return courtName;
	}
	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
	
	public String getTotalAmountAndCanceled() {
		return totalAmountAndCanceled;
	}
	public void setTotalAmountAndCanceled(String totalAmountAndCanceled) {
		this.totalAmountAndCanceled = totalAmountAndCanceled;
	}
	public String getTotalRefundAmount() {
		return totalRefundAmount;
	}
	public void setTotalRefundAmount(String totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}
	public String getTotalCanceledAmount() {
		return totalCanceledAmount;
	}
	public void setTotalCanceledAmount(String totalCanceledAmount) {
		this.totalCanceledAmount = totalCanceledAmount;
	}
	
	public String getCanceledTransactionCode() {
		return canceledTransactionCode;
	}
	public void setCanceledTransactionCode(String canceledTransactionCode) {
		this.canceledTransactionCode = canceledTransactionCode;
	}
	public String getCanceledCreatedDate() {
		return canceledCreatedDate;
	}
	public void setCanceledCreatedDate(String canceledCreatedDate) {
		this.canceledCreatedDate = canceledCreatedDate;
	}
	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

	

}