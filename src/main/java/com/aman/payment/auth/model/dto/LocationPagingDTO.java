package com.aman.payment.auth.model.dto;

import java.util.List;

public class LocationPagingDTO {
	private Long count;
	private List<LocationDTO> transactions;
	private int totalPages;
	
	
	public LocationPagingDTO() {
		super();
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<LocationDTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<LocationDTO> transactions) {
		this.transactions = transactions;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
}
