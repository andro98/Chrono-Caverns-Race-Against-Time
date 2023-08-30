package com.aman.payment.maazoun.model.payload;

public class GetBookRequestInfoByMaazounId {

	private String pageNo;
	private String pageSize;
	private String sortBy;
	private long maazounId;
	private String availableStatus;
	private String total;

	public GetBookRequestInfoByMaazounId() {
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

	public long getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(long maazounId) {
		this.maazounId = maazounId;
	}

	public String getAvailableStatus() {
		return availableStatus;
	}

	public void setAvailableStatus(String availableStatus) {
		this.availableStatus = availableStatus;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	

}
