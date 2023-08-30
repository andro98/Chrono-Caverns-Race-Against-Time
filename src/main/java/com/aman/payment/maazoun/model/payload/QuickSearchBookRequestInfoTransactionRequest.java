package com.aman.payment.maazoun.model.payload;

public class QuickSearchBookRequestInfoTransactionRequest {

	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String refRequestNumber;
	private String transactionCode;
	private String bookSerialNumber;
	private String total;

	public QuickSearchBookRequestInfoTransactionRequest() {
	}

	public QuickSearchBookRequestInfoTransactionRequest(String pageNo, String pageSize, String sortBy,
			String refRequestNumber, String transactionCode, String bookSerialNumber, String total) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.refRequestNumber = refRequestNumber;
		this.transactionCode = transactionCode;
		this.bookSerialNumber = bookSerialNumber;
		this.total = total;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
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

	public String getRefRequestNumber() {
		return refRequestNumber;
	}

	public void setRefRequestNumber(String refRequestNumber) {
		this.refRequestNumber = refRequestNumber;
	}

	public String getBookSerialNumber() {
		return bookSerialNumber;
	}

	public void setBookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}

}
