package com.aman.payment.core.model.payload;

import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class TransactionRefundRequest implements CoreBasePayload<TransactionRefundRequest> {

	private String refundTransactionCode;
	private MultipartFile attRefund;
	private String userName;
	private String visaNumber;
	private String settlementCode;
	private String locationCode;
	private String locationName;
	private String paymentTypeId;
	private String comment;
	private String refundPaymentCode;
	private String refundECRresponse;
	private String refundECRrequest;

	public TransactionRefundRequest() {
	}

	public TransactionRefundRequest(String refundTransactionCode, MultipartFile attRefund, String userName, String visaNumber,
			String settlementCode, String paymentTypeId, String locationCode, String locationName, String comment) {
		super();
		this.refundTransactionCode = refundTransactionCode;
		this.attRefund = attRefund;
		this.userName = userName;
		this.visaNumber = visaNumber;
		this.settlementCode = settlementCode;
		this.paymentTypeId = paymentTypeId;
		this.locationCode = locationCode;
		this.locationName = locationName;
		this.comment = comment;
	}

	public String getRefundTransactionCode() {
		return refundTransactionCode;
	}

	public MultipartFile getAttRefund() {
		return attRefund;
	}

	public String getUserName() {
		return userName;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public String getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setRefundTransactionCode(String refundTransactionCode) {
		this.refundTransactionCode = refundTransactionCode;
	}

	public void setAttRefund(MultipartFile attRefund) {
		this.attRefund = attRefund;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSettlementCode() {
		return settlementCode;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	public String getRefundPaymentCode() {
		return refundPaymentCode;
	}

	public void setRefundPaymentCode(String refundPaymentCode) {
		this.refundPaymentCode = refundPaymentCode;
	}

	public String getRefundECRresponse() {
		return refundECRresponse;
	}

	public void setRefundECRresponse(String refundECRresponse) {
		this.refundECRresponse = refundECRresponse;
	}

	public String getRefundECRrequest() {
		return refundECRrequest;
	}

	public void setRefundECRrequest(String refundECRrequest) {
		this.refundECRrequest = refundECRrequest;
	}

	@Override
	public TransactionRefundRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {

		return new TransactionRefundRequest(cryptoMngrCoreService.decrypt(refundTransactionCode), 
				attRefund, 
				cryptoMngrCoreService.decrypt(userName), 
				cryptoMngrCoreService.decrypt(visaNumber), 
				cryptoMngrCoreService.decrypt(settlementCode), 
				cryptoMngrCoreService.decrypt(paymentTypeId),
				cryptoMngrCoreService.decrypt(locationCode),
				cryptoMngrCoreService.decrypt(locationName),
				cryptoMngrCoreService.decrypt(comment));
	}

}
