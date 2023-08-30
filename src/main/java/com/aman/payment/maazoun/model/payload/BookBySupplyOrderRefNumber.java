package com.aman.payment.maazoun.model.payload;

public class BookBySupplyOrderRefNumber {
	private String  supplyOrderRefNumber;
	 
	public BookBySupplyOrderRefNumber() {
		
	}
 
	public BookBySupplyOrderRefNumber(String supplyOrderRefNumber) {
		super();
		this.supplyOrderRefNumber = supplyOrderRefNumber;
	}

	public String getSupplyOrderRefNumber() {
		return supplyOrderRefNumber;
	}

	public void setSupplyOrderRefNumber(String supplyOrderRefNumber) {
		this.supplyOrderRefNumber = supplyOrderRefNumber;
	}
	
	
	
}
