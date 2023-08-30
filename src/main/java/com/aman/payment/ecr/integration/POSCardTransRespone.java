package com.aman.payment.ecr.integration;

public class POSCardTransRespone {

	POSTransStatus transStatus; //transaction status.
	String merchantID; //merchant id.
	String terminalID; //terminal id.
	String responseCode; //host response for example “00”, “05”, “XX”.
	String pan; //masked card pan.
	String issuerName; //card scheme name.
	String expiryDate; //card expiry date (MMYY).
	String batchNo; //batch number.
	String stan; //seq. transaction Audit number.
	String amount; //transaction amount.
	String currency; //currency code (USD , EGP , GPB , EUR).
	String dateTime; //transaction date time (DDMMYYHHMMSS).
	String authCode; //authorization code (null for declined transactions).
	String rrn;
	String receiptNo; //receipt number, (null for declined transactions).
	String inputMethod; //input method.
	String aid; //chip application id - AID: A000000000003030999.
	String appName; //chip application Name.
	String pinEntryMode; //pin entry mode.
	String cardHolderName; //card holder name.
	 
}
