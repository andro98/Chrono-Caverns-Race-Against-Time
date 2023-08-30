package com.aman.payment.maazoun.model.payload;

public class SupplyOrderDetailsBetweenDates {

	private String pageNo;
	private String pageSize;
	private String CreateAt;
	private String CreateAtEnd;

	public SupplyOrderDetailsBetweenDates() {
	}

	public SupplyOrderDetailsBetweenDates(String pageNo, String pageSize, String createAt, String createAtEnd) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		CreateAt = createAt;
		CreateAtEnd = createAtEnd;
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

	public String getCreateAt() {
		return CreateAt;
	}

	public void setCreateAt(String createAt) {
		CreateAt = createAt;
	}

	public String getCreateAtEnd() {
		return CreateAtEnd;
	}

	public void setCreateAtEnd(String createAtEnd) {
		CreateAtEnd = createAtEnd;
	}

	@Override
	public String toString() {
		return "SupplyOrderDetailsBetweenDates{" + "pageNo='" + pageNo + '\'' + ", pageSize='" + pageSize + '\''
				+ ", CreateAt='" + CreateAt + '\'' + ", CreateAtEnd='" + CreateAtEnd + '\'' + '}';
	}
}
