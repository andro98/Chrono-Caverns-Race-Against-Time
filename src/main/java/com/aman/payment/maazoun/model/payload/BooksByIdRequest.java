package com.aman.payment.maazoun.model.payload;

public class BooksByIdRequest{

	private String serialNumber;
	
	private String bookFinancialNumber;
	private Long maazounId;

	public BooksByIdRequest() {
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public Long getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(Long maazounId) {
		this.maazounId = maazounId;
	}
	

}
