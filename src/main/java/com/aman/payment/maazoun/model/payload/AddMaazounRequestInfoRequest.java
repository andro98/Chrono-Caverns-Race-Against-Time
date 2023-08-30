package com.aman.payment.maazoun.model.payload;

import java.util.List;

public class AddMaazounRequestInfoRequest {

	private String createdBy;
	private Double amount;
	private Long maazounId;
	private Long sectorId;
	private String merchantId;
	private String paymentTypeId;
	private String serviceId;
	private String visaNumber;
	private String paymentEcrResponse;
	private String paymentEcrRequest;
	private String paymentCode;
	private List<BookListRequest> bookList;
 	
	public AddMaazounRequestInfoRequest() {
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(Long maazounId) {
		this.maazounId = maazounId;
	}

	public List<BookListRequest> getBookList() {
		return bookList;
	}

	public void setBookList(List<BookListRequest> bookList) {
		this.bookList = bookList;
	}

	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public String getPaymentEcrResponse() {
		return paymentEcrResponse;
	}

	public void setPaymentEcrResponse(String paymentEcrResponse) {
		this.paymentEcrResponse = paymentEcrResponse;
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
 
}
