package com.aman.payment.core.model.payload;

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.core.model.TransactionECR;
import com.aman.payment.core.model.dto.TransactionMidsDTO;
import com.aman.payment.core.service.CryptoMngrCoreService;

public class TransactionPaymentRequest implements CoreBasePayload<TransactionPaymentRequest> {

	private String serviceId;
	private Set<TransactionItemsRequest> subServiceIds;
	private String userName;
	private String amount;
	private String visaNumber;
	private String posId;
	private String locationCode;
	private String locationName;
	private String merchantId;
	private String paymentTypeId;
	private String tax;
	private String paymentCode;
	private TransactionECR transactionECRFk;
	private double contractPaidAmount;
	private long contractCount;
	private Set<TransactionMidsDTO> transactionMids;

	public TransactionPaymentRequest() {
	}
	
	public TransactionPaymentRequest(String serviceId, Set<TransactionItemsRequest> subServiceIds, String userName,
			String amount, String visaNumber, String posId, String merchantId, String paymentTypeId, String tax,
			String locationCode, String locationName) {
		super();
		this.serviceId = serviceId;
		this.subServiceIds = subServiceIds;
		this.userName = userName;
		this.amount = amount;
		this.visaNumber = visaNumber;
		this.posId = posId;
		this.merchantId = merchantId;
		this.paymentTypeId = paymentTypeId;
		this.tax = tax;
		this.locationCode = locationCode;
		this.locationName = locationName;
	}

	public TransactionPaymentRequest(String serviceId, Set<TransactionItemsRequest> subServiceIds, String userName,
			String amount, String visaNumber, String posId, String merchantId, String paymentTypeId, String tax,
			String locationCode, String locationName, String paymentCode) {
		super();
		this.serviceId = serviceId;
		this.subServiceIds = subServiceIds;
		this.userName = userName;
		this.amount = amount;
		this.visaNumber = visaNumber;
		this.posId = posId;
		this.merchantId = merchantId;
		this.paymentTypeId = paymentTypeId;
		this.tax = tax;
		this.locationCode = locationCode;
		this.locationName = locationName;
		this.paymentCode = paymentCode;
	}

	public String getServiceId() {
		return serviceId;
	}

	public Set<TransactionItemsRequest> getSubServiceIds() {
		return subServiceIds;
	}

	public String getAmount() {
		return amount;
	}

	public String getPosId() {
		return posId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public String getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setSubServiceIds(Set<TransactionItemsRequest> subServiceIds) {
		this.subServiceIds = subServiceIds;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
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

	 
	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public TransactionECR getTransactionECRFk() {
		return transactionECRFk;
	}

	public void setTransactionECRFk(TransactionECR transactionECRFk) {
		this.transactionECRFk = transactionECRFk;
	}

	public double getContractPaidAmount() {
		return contractPaidAmount;
	}

	public void setContractPaidAmount(double contractPaidAmount) {
		this.contractPaidAmount = contractPaidAmount;
	}

	public long getContractCount() {
		return contractCount;
	}

	public void setContractCount(long contractCount) {
		this.contractCount = contractCount;
	}

	public Set<TransactionMidsDTO> getTransactionMids() {
		return transactionMids;
	}

	public void setTransactionMids(Set<TransactionMidsDTO> transactionMids) {
		this.transactionMids = transactionMids;
	}

	@Override
	public TransactionPaymentRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {

		Set<TransactionItemsRequest> eTransactionItems = new HashSet<TransactionItemsRequest>();
		subServiceIds.stream().forEach(s -> {
			eTransactionItems.add(s.decrypt(cryptoMngrCoreService));
		});

		return new TransactionPaymentRequest(cryptoMngrCoreService.decrypt(serviceId), 
				eTransactionItems,
				cryptoMngrCoreService.decrypt(userName), 
				cryptoMngrCoreService.decrypt(amount),
				cryptoMngrCoreService.decrypt(visaNumber), 
				cryptoMngrCoreService.decrypt(posId),
				cryptoMngrCoreService.decrypt(merchantId), 
				cryptoMngrCoreService.decrypt(paymentTypeId),
				cryptoMngrCoreService.decrypt(tax), 
				cryptoMngrCoreService.decrypt(locationCode),
				cryptoMngrCoreService.decrypt(locationName),
				cryptoMngrCoreService.decrypt(paymentCode)
				);
	}

}
