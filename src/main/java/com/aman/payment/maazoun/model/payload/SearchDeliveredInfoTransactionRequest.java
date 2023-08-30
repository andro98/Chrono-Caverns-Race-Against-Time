package com.aman.payment.maazoun.model.payload;

public class SearchDeliveredInfoTransactionRequest{

	private String durationFrom;
	private String durationTo;
	private String reqRefNumber;
	private String locationId;
	private String posId;
	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String total;

	public SearchDeliveredInfoTransactionRequest() {
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

	public String getReqRefNumber() {
		return reqRefNumber;
	}

	public void setReqRefNumber(String reqRefNumber) {
		this.reqRefNumber = reqRefNumber;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
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

	
	
	
}
