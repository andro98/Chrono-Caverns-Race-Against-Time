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

	long marriageBookCount8 = 0;
	long authenticationBookCount8 = 0;
	long reviewBookCount8 = 0;
	long mullahMarriageBookCount8 = 0;
	long divorceBookCount8 = 0;
	
	long marriageBookCount15 = 0;
	long authenticationBookCount15 = 0;
	long reviewBookCount15 = 0;
	long mullahMarriageBookCount15 = 0;
	long divorceBookCount15 = 0;
	
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

	public long getMarriageBookCount8() {
		return marriageBookCount8;
	}

	public void setMarriageBookCount8(long marriageBookCount8) {
		this.marriageBookCount8 = marriageBookCount8;
	}

	public long getAuthenticationBookCount8() {
		return authenticationBookCount8;
	}

	public void setAuthenticationBookCount8(long authenticationBookCount8) {
		this.authenticationBookCount8 = authenticationBookCount8;
	}

	public long getReviewBookCount8() {
		return reviewBookCount8;
	}

	public void setReviewBookCount8(long reviewBookCount8) {
		this.reviewBookCount8 = reviewBookCount8;
	}

	public long getMullahMarriageBookCount8() {
		return mullahMarriageBookCount8;
	}

	public void setMullahMarriageBookCount8(long mullahMarriageBookCount8) {
		this.mullahMarriageBookCount8 = mullahMarriageBookCount8;
	}

	public long getDivorceBookCount8() {
		return divorceBookCount8;
	}

	public void setDivorceBookCount8(long divorceBookCount8) {
		this.divorceBookCount8 = divorceBookCount8;
	}

	public long getMarriageBookCount15() {
		return marriageBookCount15;
	}

	public void setMarriageBookCount15(long marriageBookCount15) {
		this.marriageBookCount15 = marriageBookCount15;
	}

	public long getAuthenticationBookCount15() {
		return authenticationBookCount15;
	}

	public void setAuthenticationBookCount15(long authenticationBookCount15) {
		this.authenticationBookCount15 = authenticationBookCount15;
	}

	public long getReviewBookCount15() {
		return reviewBookCount15;
	}

	public void setReviewBookCount15(long reviewBookCount15) {
		this.reviewBookCount15 = reviewBookCount15;
	}

	public long getMullahMarriageBookCount15() {
		return mullahMarriageBookCount15;
	}

	public void setMullahMarriageBookCount15(long mullahMarriageBookCount15) {
		this.mullahMarriageBookCount15 = mullahMarriageBookCount15;
	}

	public long getDivorceBookCount15() {
		return divorceBookCount15;
	}

	public void setDivorceBookCount15(long divorceBookCount15) {
		this.divorceBookCount15 = divorceBookCount15;
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

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
