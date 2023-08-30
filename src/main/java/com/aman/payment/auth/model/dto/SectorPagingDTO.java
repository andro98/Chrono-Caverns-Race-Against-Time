package com.aman.payment.auth.model.dto;

import java.util.List;

public class SectorPagingDTO {
	private Long count;
	private List<SectorDTO> sectors;
	private int totalPages;
	
	
	public SectorPagingDTO() {
		super();
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	

	public List<SectorDTO> getSectors() {
		return sectors;
	}

	public void setSectors(List<SectorDTO> sectors) {
		this.sectors = sectors;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
}
