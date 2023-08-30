package com.aman.payment.core.model.payload;

public class FinancialReportRequest{

	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String durationFrom;
	private String durationTo;
	private String serviceId;
	private String reportType;
	private String locationCode;
	private String agentName;
	private String settlementCode;
	private long posId;

	public FinancialReportRequest() {
		super();
	}

	public FinancialReportRequest(String pageNo, String pageSize, String sortBy, 
			String durationFrom, String durationTo, String serviceId, String reportType,
			String locationCode, String agentName, String settlementCode, long posId) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.durationFrom = durationFrom;
		this.durationTo = durationTo;
		this.serviceId = serviceId;
		this.reportType = reportType;
		this.locationCode = locationCode;
		this.agentName = agentName;
		this.settlementCode = settlementCode;
		this.posId = posId;
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

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getReportType() {
		return reportType;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}



	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getSettlementCode() {
		return settlementCode;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	public long getPosId() {
		return posId;
	}

	public void setPosId(long posId) {
		this.posId = posId;
	}

}
