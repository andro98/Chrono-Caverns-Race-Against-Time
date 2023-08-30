package com.aman.payment.maazoun.model.payload;

public class SupplyOrderDetailsSearchRequest {

	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String sectorId;
	private String refSupplyOrderNumber ;

	public SupplyOrderDetailsSearchRequest() {
		// TODO Auto-generated constructor stub
	}
	

	public SupplyOrderDetailsSearchRequest(String pageNo, String pageSize, String sortBy, String sectorId) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.sectorId = sectorId;
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

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}


	public String getRefSupplyOrderNumber() {
		return refSupplyOrderNumber;
	}


	public void setRefSupplyOrderNumber(String refSupplyOrderNumber) {
		this.refSupplyOrderNumber = refSupplyOrderNumber;
	}

	 

}
