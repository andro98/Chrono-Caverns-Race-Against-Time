package com.aman.payment.maazoun.model.dto;

import java.util.List;

public class MaazounDeliverInfoPagingDTO {
	private Long count;
	private List<String> transactions;
	private int totalPages;

	public MaazounDeliverInfoPagingDTO() {
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

}
