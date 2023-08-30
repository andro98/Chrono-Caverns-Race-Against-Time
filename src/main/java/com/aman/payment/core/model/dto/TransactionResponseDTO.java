package com.aman.payment.core.model.dto;

import java.util.Set;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class TransactionResponseDTO implements CoreBaseDTO<TransactionResponseDTO> {

	private String transactionCode;
	private String createdDate;
	private String status;
	private String settlementCode;
	private String refundUrl;
	private Set<TransactionItemDTO> transactionItemsDTO  ;
	

	public TransactionResponseDTO(String transactionCode, String createdDate, String status,
			String refundUrl,Set<TransactionItemDTO> transactionItemsDTO ) {
		super();
		this.transactionCode = transactionCode;
		this.createdDate = createdDate;
		this.status = status;
		this.refundUrl = refundUrl;
		this.transactionItemsDTO=transactionItemsDTO;
	}

	public TransactionResponseDTO() {
		// TODO Auto-generated constructor stub
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

	public String getSettlementCode() {
		return settlementCode;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	public String getRefundUrl() {
		return refundUrl;
	}

	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}

	

	public Set<TransactionItemDTO> getTransactionItemsDTO() {
		return transactionItemsDTO;
	}

	public void setTransactionItemsDTO(Set<TransactionItemDTO> transactionItemsDTO) {
		this.transactionItemsDTO = transactionItemsDTO;
	}

	@Override
	public TransactionResponseDTO encrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		return new TransactionResponseDTO(cryptoMngrCoreService.encrypt(transactionCode), cryptoMngrCoreService.encrypt(createdDate),
				cryptoMngrCoreService.encrypt(status), cryptoMngrCoreService.encrypt(refundUrl),transactionItemsDTO);
	}

}