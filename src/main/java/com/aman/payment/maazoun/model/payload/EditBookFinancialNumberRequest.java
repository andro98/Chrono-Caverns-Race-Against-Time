package com.aman.payment.maazoun.model.payload;

public class EditBookFinancialNumberRequest {
	
  	private String newBookFinancialNumber;
  	private String bookFinancialNumber;
  	private String bookTypeId;
  	private String bookType;
  	private String contractCount;
  	
	public EditBookFinancialNumberRequest() {
		super();
	}

	public String getNewBookFinancialNumber() {
		return newBookFinancialNumber;
	}

	public void setNewBookFinancialNumber(String newBookFinancialNumber) {
		this.newBookFinancialNumber = newBookFinancialNumber;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public String getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(String bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getContractCount() {
		return contractCount;
	}

	public void setContractCount(String contractCount) {
		this.contractCount = contractCount;
	}

	 
}
