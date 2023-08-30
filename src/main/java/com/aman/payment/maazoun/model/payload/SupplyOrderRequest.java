package com.aman.payment.maazoun.model.payload;

public class SupplyOrderRequest{

	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String durationFrom;
	private String durationTo;
	private String status;
	

	public SupplyOrderRequest() {
	}

	 

	public SupplyOrderRequest(String pageNo, String pageSize, String sortBy, String durationFrom, String durationTo, String status) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.durationFrom = durationFrom;
		this.durationTo = durationTo;
		this.status = status;
	}



	public String getPageNo() {
		return pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}



	public String getDurationFrom() {
		return durationFrom;
	}



	public void setDurationFrom(String durationFrom) {
		this.durationFrom = durationFrom;
	}
 
	public String getDurationTo() {
		return durationTo;
	}
 
	public void setDurationTo(String durationTo) {
		this.durationTo = durationTo;
	}



	public String getStatus() {
		return status;
	}



	public void setStatusFk(String status) {
		this.status = status;
	}

}
