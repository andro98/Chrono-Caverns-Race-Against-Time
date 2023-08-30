package com.aman.payment.ecr.integration;

public enum POSTransStatus {

	APPROVED(0), //transaction approved by host successfully.
	HOST_DECLINED(1), //transaction declined by host, check response code for details.
	CARD_DECLINED(2), //transaction declined by card and will be reversed.
	FAILED(3); //transaction failed, try again later.
	
	private int value;
	
	POSTransStatus(int value){
		this.value = value;
	}
	
}
