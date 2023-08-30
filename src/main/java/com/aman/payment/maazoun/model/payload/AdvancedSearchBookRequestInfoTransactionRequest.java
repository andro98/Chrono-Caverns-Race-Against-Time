package com.aman.payment.maazoun.model.payload;

import java.util.Set;

public class AdvancedSearchBookRequestInfoTransactionRequest{

	private String durationFrom;
	private String durationTo;
	private String mobileNumber;
	private String transactionCode;
	private String idNumber;
	private String status;
	private String maazounName;
	private String locationName;
	private String paymentMethod;
	private String createdBy;
	private String posId;
	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String total;
	private Set<String> transactionCodeSet;
	private Set<String> nationalIds;
	private String agentName;
	private String passportNumber;
	private String nationalId;
	private Long sectorId;

	public AdvancedSearchBookRequestInfoTransactionRequest() {
	}

	public String getDurationFrom() {
		return durationFrom;
	}

	public String getDurationTo() {
		return durationTo;
	}

	public void setDurationFrom(String durationFrom) {
		this.durationFrom = durationFrom;
	}

	public void setDurationTo(String durationTo) {
		this.durationTo = durationTo;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getPosId() {
		return posId;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public Set<String> getTransactionCodeSet() {
		return transactionCodeSet;
	}

	public void setTransactionCodeSet(Set<String> transactionCodeSet) {
		this.transactionCodeSet = transactionCodeSet;
	}

	public Set<String> getNationalIds() {
		return nationalIds;
	}

	public void setNationalIds(Set<String> nationalIds) {
		this.nationalIds = nationalIds;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	public String getNationalIdString() {
		return nationalIds.stream().reduce("", (x, y) -> x.concat(",'").concat(y).concat("' "));
	}
	
	public String getTransactionCodeString() {
		return transactionCodeSet.stream().reduce("", (x, y) -> x.concat(",'").concat(y).concat("' "));
	}
	 
	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getMaazounName() {
		return maazounName;
	}

	public void setMaazounName(String maazounName) {
		this.maazounName = maazounName;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}
	
	
}
