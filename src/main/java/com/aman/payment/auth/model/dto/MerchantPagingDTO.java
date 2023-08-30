package com.aman.payment.auth.model.dto;

import java.util.List;

public class MerchantPagingDTO {
	private Long count;
	private List<MerchantDTO> transactions;
	private int totalPages;
	
	
	public MerchantPagingDTO() {
		super();
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<MerchantDTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<MerchantDTO> transactions) {
		this.transactions = transactions;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
}
