package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class MaazounDeliverInfoReportDTO {

	private String locationName;
	private String createAt;
	private String createBy;
	private String posName;
	private String sectorName;

	private String maazounName;
	private String maazounNationalId;

	private String bookTypeName;
	private String refRequestNumber;
	private String prosecutorLogoUrl;
	private String bookFinancialNumber;
	
	private String sequance;
	private String bookInventoryReference;

	public MaazounDeliverInfoReportDTO() {
		super();
	}

	public String getSectorName() {
		return sectorName;
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

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getMaazounName() {
		return maazounName;
	}

	public void setMaazounName(String maazounName) {
		this.maazounName = maazounName;
	}

	public String getMaazounNationalId() {
		return maazounNationalId;
	}

	public void setMaazounNationalId(String maazounNationalId) {
		this.maazounNationalId = maazounNationalId;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public String getRefRequestNumber() {
		return refRequestNumber;
	}

	public void setRefRequestNumber(String refRequestNumber) {
		this.refRequestNumber = refRequestNumber;
	}

	public String getProsecutorLogoUrl() {
		return prosecutorLogoUrl;
	}

	public void setProsecutorLogoUrl(String prosecutorLogoUrl) {
		this.prosecutorLogoUrl = prosecutorLogoUrl;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public String getSequance() {
		return sequance;
	}

	public void setSequance(String sequance) {
		this.sequance = sequance;
	}

	public String getBookInventoryReference() {
		return bookInventoryReference;
	}

	public void setBookInventoryReference(String bookInventoryReference) {
		this.bookInventoryReference = bookInventoryReference;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
