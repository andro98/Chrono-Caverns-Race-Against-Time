package com.aman.payment.core.model.dto;

import java.util.List;

public class TransactionPagingDTO {
	private Long count;
	private List<TransactionDTO> transactions;
	private int totalPages;
	
	
	public TransactionPagingDTO() {
		super();
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<TransactionDTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
}
