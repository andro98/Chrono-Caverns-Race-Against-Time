package com.aman.payment.maazoun.model.dto;

import java.util.List;

public class SupplyOrderPagingDTO {
	private Long count;
	private List<String> supplyOrders;
	private int totalPages;
	
	
	public SupplyOrderPagingDTO() {
		super();
	}
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	

	public List<String> getSupplyOrders() {
		return supplyOrders;
	}

	public void setSupplyOrders(List<String> supplyOrders) {
		this.supplyOrders = supplyOrders;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
}
