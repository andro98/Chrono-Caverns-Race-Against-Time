package com.aman.payment.maazoun.model.payload;

public class StockLabelRequest {

	private Long locationId;
	private Long numberOfLabels;
	private Long bootTypeId;

	public StockLabelRequest() {
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getNumberOfLabels() {
		return numberOfLabels;
	}

	public void setNumberOfLabels(Long numberOfLabels) {
		this.numberOfLabels = numberOfLabels;
	}

	public Long getBootTypeId() {
		return bootTypeId;
	}

	public void setBootTypeId(Long bootTypeId) {
		this.bootTypeId = bootTypeId;
	}


}
