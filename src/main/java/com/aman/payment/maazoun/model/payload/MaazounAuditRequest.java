package com.aman.payment.maazoun.model.payload;

public class MaazounAuditRequest {

	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String durationFrom;
	private String durationTo;
	private String serviceId;
	private String total;
	private String reportType;
	private String locationId;
	private String agentName;

	public MaazounAuditRequest() {
		super();
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

	public String getDurationTo() {
		return durationTo;
	}

	public void setDurationFrom(String durationFrom) {
		this.durationFrom = durationFrom;
	}

	public void setDurationTo(String durationTo) {
		this.durationTo = durationTo;
	}


	public String getServiceId() {
		return serviceId;
	}



	public String getTotal() {
		return total;
	}



	public String getReportType() {
		return reportType;
	}



	public String getLocationId() {
		return locationId;
	}



	public String getAgentName() {
		return agentName;
	}



	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}



	public void setTotal(String total) {
		this.total = total;
	}



	public void setReportType(String reportType) {
		this.reportType = reportType;
	}



	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}



	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

}
