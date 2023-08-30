package com.aman.payment.maazoun.model.dto;

import java.util.List;

public class MaazounCollectInfoPagingDTO {
	private Long count;
	private List<String> contracts;
	private int totalPages;

	public MaazounCollectInfoPagingDTO() {
		super();
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}


	public List<String> getContracts() {
		return contracts;
	}

	public void setContracts(List<String> contracts) {
		this.contracts = contracts;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}
