package com.aman.payment.ecr.integration;

public enum POSTransType {

	SALE(2),
			 VOID(3),
			 REFUND(4),
			 CHECK_STATUS(6);
	
	private int value;
	
	POSTransType(int value){
		this.value = value;
	}
}
