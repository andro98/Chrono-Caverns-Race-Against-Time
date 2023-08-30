package com.aman.payment.auth.model.dto;

import java.util.List;

public class UserPagingDTO {
	private Long count;
	private List<UserDTO> results;
	private List<String> transactions;
	private int totalPages;
	
	
	public UserPagingDTO() {
		super();
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<String> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<String> transactions) {
		this.transactions = transactions;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<UserDTO> getResults() {
		return results;
	}

	public void setResults(List<UserDTO> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "UserPagingDTO [count=" + count + ", transactions=" + transactions + ", totalPages=" + totalPages + "]";
	}

	
}
