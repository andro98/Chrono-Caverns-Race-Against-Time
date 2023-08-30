package com.aman.payment.core.model.dto;

import java.util.List;

public class FinancialAuditPagingDTO {

	private Long count;
	private List<String> transactions;
	private int totalPages;
	
	public FinancialAuditPagingDTO() {
		super();
	}
	public FinancialAuditPagingDTO(Long count, List<String> transactions, int totalPages) {
		super();
		this.count = count;
		this.transactions = transactions;
		this.totalPages = totalPages;
	}
	public Long getCount() {
		return count;
	}
	public List<String> getTransactions() {
		return transactions;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public void setTransactions(List<String> transactions) {
		this.transactions = transactions;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}


	
	
   
}