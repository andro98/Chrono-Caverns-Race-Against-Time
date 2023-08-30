package com.aman.payment.auth.model.dto;

import java.util.List;

public class SettingPagingDTO {

	
	private Long count;
	private List<SettingDTO> settings;
	private int totalPages;
	
	public SettingPagingDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<SettingDTO> getSettings() {
		return settings;
	}

	public void setSettings(List<SettingDTO> settings) {
		this.settings = settings;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
	
	
}
