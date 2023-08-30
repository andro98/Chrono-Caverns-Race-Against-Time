package com.aman.payment.maazoun.model.payload;

public class MaazounProfileAuditRequest {

	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String total;
	private String maazounNationalId;
	private String custody;
	private String serials;

	public MaazounProfileAuditRequest() {
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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMaazounNationalId() {
		return maazounNationalId;
	}

	public void setMaazounNationalId(String maazounNationalId) {
		this.maazounNationalId = maazounNationalId;
	}

	public String getCustody() {
		return custody;
	}

	public void setCustody(String custody) {
		this.custody = custody;
	}

	public String getSerials() {
		return serials;
	}

	public void setSerials(String serials) {
		this.serials = serials;
	}

	

}
