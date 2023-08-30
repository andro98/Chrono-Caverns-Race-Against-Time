package com.aman.payment.core.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.core.model.Transaction;
import com.aman.payment.core.model.TransactionItem;
import com.aman.payment.core.model.dto.TransactionDTO;
import com.aman.payment.core.model.dto.TransactionFinancialDetailsDTO;
import com.aman.payment.core.model.dto.TransactionItemDTO;
import com.aman.payment.core.model.dto.TransactionResponseDTO;
import com.aman.payment.core.service.CryptoMngrCoreService;

@Service
public class TransactionMapper {

	@Autowired
	private CryptoMngrCoreService cryptoMngrCoreService;
	
	/*
	 * transaction to transactionResponseDTO
	 * transactions to transactionsResponseDTOs
	 * ****************************************************************************************************
	 */
	public TransactionResponseDTO transactionToTransactionResponseDTO(Transaction transaction) {
		return createTransactionResponseDTO(transaction);
	}

	public List<TransactionResponseDTO> transactionsToTransactionResponseDTOs(List<Transaction> transactions) {
		return transactions.stream()
      .filter(Objects::nonNull)
				.map(this::transactionToTransactionResponseDTO).collect(Collectors.toList());
	}
	
	private TransactionResponseDTO createTransactionResponseDTO(Transaction transaction) {
		TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
		transactionResponseDTO.setCreatedDate(String.valueOf(transaction.getCreatedAt()));
		transactionResponseDTO.setStatus(transaction.getStatusFk());
		transactionResponseDTO.setTransactionCode(transaction.getTransCode());
		transactionResponseDTO.setSettlementCode(transaction.getSettlementCode());
		transactionResponseDTO.setRefundUrl(transaction.getAttRefundUrl());
		
		Set<TransactionItemDTO> transactionItems =new HashSet<TransactionItemDTO>();
		for (TransactionItem transactionItem: transaction.getTransactionItems()) {
			TransactionItemDTO transactionItemDTO=new TransactionItemDTO();
			transactionItemDTO.setAmount(transactionItem.getAmount() +"");
			transactionItemDTO.setServiceId(transactionItem.getSubServiceId()+"");
			transactionItems.add(transactionItemDTO);
			
		}
		transactionResponseDTO.setTransactionItemsDTO(transactionItems);
		return transactionResponseDTO;//.encrypt(cryptoMngrCoreService);
	}
	
	/*
	 * transactionItem to transactionItemDTO
	 * transactionItems to transactionItemDTOs
	 * ****************************************************************************************************
	 */
	public TransactionItemDTO transactionItemToTransactiontemDTO(TransactionItem transactionItem) {
		return createTransactionItemDTO(transactionItem);
	}

	public List<TransactionItemDTO> transactionItemsToTransactionItemDTOs(List<TransactionItem> transactionItems) {
		return transactionItems.stream()
      .filter(Objects::nonNull)
				.map(this::transactionItemToTransactiontemDTO).collect(Collectors.toList());
	}
	
	public Set<TransactionItemDTO> transactionItemsToTransactionItemDTOs(Set<TransactionItem> transactionItems) {
		return transactionItems.stream()
      .filter(Objects::nonNull)
				.map(this::transactionItemToTransactiontemDTO).collect(Collectors.toSet());
	}
	
	private TransactionItemDTO createTransactionItemDTO(TransactionItem transactionItem) {
		TransactionItemDTO transactionItemDTO = new TransactionItemDTO();
		transactionItemDTO.setAmount(String.valueOf(transactionItem.getAmount()));
		transactionItemDTO.setServiceId(String.valueOf(transactionItem.getSubServiceId()));
		return transactionItemDTO.encrypt(cryptoMngrCoreService);
	}
	
	/*
	 * transaction to transactionDTO
	 * transactions to transactionsDTOs
	 * ****************************************************************************************************
	 */
	public TransactionDTO transactionToTransactionDTO(Transaction transaction) {
		return createTransactionDTO(transaction);
	}

	public List<TransactionDTO> transactionsToTransactionDTOs(List<Transaction> transactions) {
		return transactions.stream()
      .filter(Objects::nonNull)
				.map(this::transactionToTransactionDTO).collect(Collectors.toList());
	}
	
	public Set<TransactionDTO> transactionsToTransactionDTOs(Set<Transaction> transactions) {
		return transactions.stream()
      .filter(Objects::nonNull)
				.map(this::transactionToTransactionDTO).collect(Collectors.toSet());
	}
	
	private TransactionDTO createTransactionDTO(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setStatus(transaction.getStatusFk());
		transactionDTO.setTransactionCode(transaction.getTransCode());
		transactionDTO.setAmount(String.valueOf(transaction.getAmount()));
		transactionDTO.setAttRefundUrl(transaction.getAttRefundUrl());
		transactionDTO.setCreatedBy(transaction.getCreatedBy());
		transactionDTO.setCreatedDate(String.valueOf(transaction.getCreatedAt()));
		transactionDTO.setMerchantId(String.valueOf(transaction.getMerchantId()));
		transactionDTO.setPaymentMethod(transaction.getPaymentTypeFk().getName());
		transactionDTO.setPosId(String.valueOf(transaction.getPosId()));
		transactionDTO.setRefundParentId(String.valueOf(transaction.getRefundParentId()));
		transactionDTO.setServiceId(String.valueOf(transaction.getServiceId()));
		transactionDTO.setTax(String.valueOf(transaction.getTax()));
		transactionDTO.setVisaNumber(transaction.getVisaNumber());
		transactionDTO.setTransactionItems(transactionItemsToTransactionItemDTOs(transaction.getTransactionItems()));
		
		return transactionDTO;
	}
	
	public TransactionFinancialDetailsDTO transactionToTransactionFinancialDetailsDTO(Transaction transaction) {
		return createToTransactionFinancialDetailsDTO(transaction);
	}
	
	private TransactionFinancialDetailsDTO createToTransactionFinancialDetailsDTO(Transaction transaction) {
		TransactionFinancialDetailsDTO transactionDTO = new TransactionFinancialDetailsDTO();
		transactionDTO.setStatus(transaction.getStatusFk());
		transactionDTO.setTransactionCode(transaction.getTransCode());
		transactionDTO.setAmount(String.valueOf(transaction.getAmount()));
		transactionDTO.setCreatedBy(transaction.getCreatedBy());
		transactionDTO.setCreatedDate(String.valueOf(transaction.getCreatedAt()));
		transactionDTO.setMerchantId(String.valueOf(transaction.getMerchantId()));
		transactionDTO.setPaymentMethod(transaction.getPaymentTypeFk().getName());
		transactionDTO.setPosId(String.valueOf(transaction.getPosId()));
		transactionDTO.setServiceId(String.valueOf(transaction.getServiceId()));
		transactionDTO.setVisaNumber(transaction.getVisaNumber());
		
		return transactionDTO;
	}

}
