package com.aman.payment.maazoun.model.dto;

import java.util.List;

public class MaazounRequestInfoPagingDTO {
	private Long count;
	private List<String> bookRequests;
	private int totalPages;

	public MaazounRequestInfoPagingDTO() {
		super();
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<String> getBookRequests() {
		return bookRequests;
	}

	public void setBookRequests(List<String> bookRequests) {
		this.bookRequests = bookRequests;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}
