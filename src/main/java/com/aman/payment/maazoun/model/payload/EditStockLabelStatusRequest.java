package com.aman.payment.maazoun.model.payload;

import java.util.Set;

public class EditStockLabelStatusRequest {

	private Set<Long> stockLabelIds;

	public EditStockLabelStatusRequest() {
	}

	public Set<Long> getStockLabelIds() {
		return stockLabelIds;
	}

	public void setStockLabelIds(Set<Long> stockLabelIds) {
		this.stockLabelIds = stockLabelIds;
	}

	


}
