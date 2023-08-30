package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class MaazounDeliverInfoDTO {

	private String id;
	private String imageUrl;
	private String posId;
	private String createAt;
	private String createBy;
	private String locationId;
	private String status;
	private String updatedAt;
	private String updatedBy;
	private String refRequestNumber;
	private String receiptUrl;
	public MaazounDeliverInfoDTO() {
		super();
	}

	public MaazounDeliverInfoDTO(String id, String imageUrl, String posId, String createAt, String createBy,
			String locationId, String status, String updatedAt, String updatedBy, String refRequestNumber,
			String receiptUrl) {
		super();
		this.id = id;
		this.imageUrl = imageUrl;
		this.posId = posId;
		this.createAt = createAt;
		this.createBy = createBy;
		this.locationId = locationId;
		this.status = status;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.refRequestNumber = refRequestNumber;
		this.receiptUrl = receiptUrl;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRefRequestNumber() {
		return refRequestNumber;
	}

	public void setRefRequestNumber(String refRequestNumber) {
		this.refRequestNumber = refRequestNumber;
	}
	 
	public String getReceiptUrl() {
		return receiptUrl;
	}

	public void setReceiptUrl(String receiptUrl) {
		this.receiptUrl = receiptUrl;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
