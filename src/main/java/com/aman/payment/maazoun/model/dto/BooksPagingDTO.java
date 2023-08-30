package com.aman.payment.maazoun.model.dto;

import java.util.List;

public class BooksPagingDTO {
	private Long count;
	private List<String> books;
	private int totalPages;
	
	
	public BooksPagingDTO() {
		super();
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	public List<String> getBooks() {
		return books;
	}

	public void setBooks(List<String> books) {
		this.books = books;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
}
