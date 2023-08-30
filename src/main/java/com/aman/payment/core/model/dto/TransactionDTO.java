package com.aman.payment.core.model.dto;

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class TransactionDTO implements CoreBaseDTO<TransactionDTO> {

	private String transactionCode;
	private String settlementCode;
	private String amount;
	private String serviceId;
	private String merchantId;
	private String posId;
	private String paymentMethod;
	private String visaNumber;
	private String refundParentId;
	private String attRefundUrl;
	private String createdDate;
	private String createdBy;
	private String status;
	private String tax;
	private Set<TransactionItemDTO> transactionItems;
	
	private String locationName;
	private String merchantName; 
	private String serviceName;

	public TransactionDTO(String transactionCode, String settlementCode, String amount, String serviceId,
			String merchantId, String posId, String paymentMethod, String visaNumber, String refundParentId,
			String attRefundUrl, String createdDate, String createdBy, String status, String tax,
			Set<TransactionItemDTO> transactionItems) {
		super();
		this.transactionCode = transactionCode;
		this.settlementCode = settlementCode;
		this.amount = amount;
		this.serviceId = serviceId;
		this.merchantId = merchantId;
		this.posId = posId;
		this.paymentMethod = paymentMethod;
		this.visaNumber = visaNumber;
		this.refundParentId = refundParentId;
		this.attRefundUrl = attRefundUrl;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.status = status;
		this.tax = tax;
		this.transactionItems = transactionItems;
	}

	public String getSettlementCode() {
		return settlementCode;
	}

	public String getAmount() {
		return amount;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public String getPosId() {
		return posId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public String getRefundParentId() {
		return refundParentId;
	}

	public String getAttRefundUrl() {
		return attRefundUrl;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getTax() {
		return tax;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public void setRefundParentId(String refundParentId) {
		this.refundParentId = refundParentId;
	}

	public void setAttRefundUrl(String attRefundUrl) {
		this.attRefundUrl = attRefundUrl;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public TransactionDTO() {
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<TransactionItemDTO> getTransactionItems() {
		return transactionItems;
	}

	public void setTransactionItems(Set<TransactionItemDTO> transactionItems) {
		this.transactionItems = transactionItems;
	}
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public TransactionDTO encrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		Set<TransactionItemDTO> eTransactionItems = new HashSet<TransactionItemDTO>();
		transactionItems.stream().forEach(s -> {
			eTransactionItems.add(s.encrypt(cryptoMngrCoreService));
		});
		return new TransactionDTO(cryptoMngrCoreService.encrypt(transactionCode), 
				cryptoMngrCoreService.encrypt(settlementCode), 
				cryptoMngrCoreService.encrypt(amount), 
				cryptoMngrCoreService.encrypt(serviceId), 
				cryptoMngrCoreService.encrypt(merchantId), 
				cryptoMngrCoreService.encrypt(posId), 
				cryptoMngrCoreService.encrypt(paymentMethod), 
				cryptoMngrCoreService.encrypt(visaNumber), 
				cryptoMngrCoreService.encrypt(refundParentId), 
				cryptoMngrCoreService.encrypt(attRefundUrl), 
				cryptoMngrCoreService.encrypt(createdDate), 
				cryptoMngrCoreService.encrypt(createdBy), 
				cryptoMngrCoreService.encrypt(status), 
				cryptoMngrCoreService.encrypt(tax), 
				eTransactionItems);
	}

}