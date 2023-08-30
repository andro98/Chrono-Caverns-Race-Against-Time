package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class StockLabelDTO {

	private Long stockLabelId;
	private String labelCode;
	private String statusFk;
	private Long locationId;
	private String locationName;
	private String createdBy;
	private String createdAt;
	private String updatedAt;
	private String updatedBy;
	private String bookTypeId;

	public StockLabelDTO() {
		super();
	}

	public Long getStockLabelId() {
		return stockLabelId;
	}

	public void setStockLabelId(Long stockLabelId) {
		this.stockLabelId = stockLabelId;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
  public String getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(String bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
