package com.aman.payment.maazoun.model.dto;

import java.util.List;

public class MaazounProfilePagingDTO {
	private Long count;
	private List<String> maazouns;
	private int totalPages;
	
	
	public MaazounProfilePagingDTO() {
		super();
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}


	public List<String> getMaazouns() {
		return maazouns;
	}

	public void setMaazouns(List<String> maazouns) {
		this.maazouns = maazouns;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
}
