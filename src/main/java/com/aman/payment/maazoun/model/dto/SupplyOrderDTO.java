package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class SupplyOrderDTO {

	private String id;
	private String locationId;
	private String locationName;
	private String createAt;
	private String createBy;
	private String posId;
	private String posName;
	private String sectorId;
	private String sectorName;
	private String status;
	private String isReviewed;
	private String updatedAt;
	private String updatedBy;
	private String imageUrl;
	private String supplyOrderType;
	private String custody;

	long marriageBookCount = 0;
	long authenticationBookCount = 0;
	long reviewBookCount = 0;
	long mullahMarriageBookCount = 0;
	long divorceBookCount = 0;
	
	private String refSupplyOrderNumber;
	
	public SupplyOrderDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsReviewed() {
		return isReviewed;
	}

	public void setIsReviewed(String isReviewed) {
		this.isReviewed = isReviewed;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public long getMarriageBookCount() {
		return marriageBookCount;
	}

	public void setMarriageBookCount(long marriageBookCount) {
		this.marriageBookCount = marriageBookCount;
	}

	public long getAuthenticationBookCount() {
		return authenticationBookCount;
	}

	public void setAuthenticationBookCount(long authenticationBookCount) {
		this.authenticationBookCount = authenticationBookCount;
	}

	public long getReviewBookCount() {
		return reviewBookCount;
	}

	public void setReviewBookCount(long reviewBookCount) {
		this.reviewBookCount = reviewBookCount;
	}

	public long getMullahMarriageBookCount() {
		return mullahMarriageBookCount;
	}

	public void setMullahMarriageBookCount(long mullahMarriageBookCount) {
		this.mullahMarriageBookCount = mullahMarriageBookCount;
	}

	public long getDivorceBookCount() {
		return divorceBookCount;
	}

	public void setDivorceBookCount(long divorceBookCount) {
		this.divorceBookCount = divorceBookCount;
	}
	
	public String getRefSupplyOrderNumber() {
		return refSupplyOrderNumber;
	}

	public void setRefSupplyOrderNumber(String refSupplyOrderNumber) {
		this.refSupplyOrderNumber = refSupplyOrderNumber;
	}

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}
 
	public String getSupplyOrderType() {
		return supplyOrderType;
	}

	public void setSupplyOrderType(String supplyOrderType) {
		this.supplyOrderType = supplyOrderType;
	}
 
	
	public String getCustody() {
		return custody;
	}

	public void setCustody(String custody) {
		this.custody = custody;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
