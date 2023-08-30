package com.aman.payment.core.model.dto;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class TransactionItemDTO implements CoreBaseDTO<TransactionItemDTO> {

	private String amount;
	private String serviceId;

	public TransactionItemDTO() {
		super();
	}

	public TransactionItemDTO(String amount, String serviceId) {
		super();
		this.amount = amount;
		this.serviceId = serviceId;
	}

	public String getAmount() {
		return amount;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public TransactionItemDTO encrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		return new TransactionItemDTO(cryptoMngrCoreService.encrypt(amount), cryptoMngrCoreService.encrypt(serviceId));
	}

}