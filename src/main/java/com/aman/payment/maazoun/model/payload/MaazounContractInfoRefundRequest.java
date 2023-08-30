package com.aman.payment.maazoun.model.payload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class MaazounContractInfoRefundRequest {

	private List<String> maazounCollectionInfoIds;
	private String paymentEcrResponse;
	private String paymentEcrRequest;
	private MultipartFile attRefund;
	private String visaNumber;
	private String comment;
	private long posId;
	private String paymentCode;
	private String settlementCode;

	public MaazounContractInfoRefundRequest() {
	}

	public List<String> getMaazounCollectionInfoIds() {
		return maazounCollectionInfoIds;
	}

	public void setMaazounCollectionInfoIds(List<String> maazounCollectionInfoIds) {
		this.maazounCollectionInfoIds = maazounCollectionInfoIds;
	}

	public MultipartFile getAttRefund() {
		return attRefund;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setAttRefund(MultipartFile attRefund) {
		this.attRefund = attRefund;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPaymentEcrResponse() {
		return paymentEcrResponse;
	}

	public void setPaymentEcrResponse(String paymentEcrResponse) {
		this.paymentEcrResponse = paymentEcrResponse;
	}

	public long getPosId() {
		return posId;
	}

	public void setPosId(long posId) {
		this.posId = posId;
	}

	public String getPaymentEcrRequest() {
		return paymentEcrRequest;
	}

	public void setPaymentEcrRequest(String paymentEcrRequest) {
		this.paymentEcrRequest = paymentEcrRequest;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getSettlementCode() {
		return settlementCode;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}
 
}
