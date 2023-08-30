package com.aman.payment.auth.model.dto;

import java.util.List;

public class MaazouniaChurchsPagingDTO {
	private Long count;
	private List<MaazouniaChurchDTO> maazouniaChurchs;
	private int totalPages;
	
	
	public MaazouniaChurchsPagingDTO() {
		super();
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<MaazouniaChurchDTO> getMaazouniaChurchs() {
		return maazouniaChurchs;
	}

	public void setMaazouniaChurchs(List<MaazouniaChurchDTO> maazouniaChurchs) {
		this.maazouniaChurchs = maazouniaChurchs;
	}

	
}
