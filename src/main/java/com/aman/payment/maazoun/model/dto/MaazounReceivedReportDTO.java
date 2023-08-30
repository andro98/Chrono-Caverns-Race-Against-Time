package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class MaazounReceivedReportDTO {

	private String locationName;
	private String receivedAt;
	private String receivedBy;
	private String posName;
	private String sectorName;
	private String status;

	private String maazounName;
	private String maazounNationalId;
	private String bookTypeName;

	private String prosecutorLogoUrl;

	private String bookSerialNumber;
	private String bookFinancialNumber;
	private String contractNumber;
	private String contractFinancialNumber;
	private String sequance;
	private String maazouniaName;
	private String bookInventoryReference;

	public MaazounReceivedReportDTO() {
		super();
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(String receivedAt) {
		this.receivedAt = receivedAt;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getProsecutorLogoUrl() {
		return prosecutorLogoUrl;
	}

	public void setProsecutorLogoUrl(String prosecutorLogoUrl) {
		this.prosecutorLogoUrl = prosecutorLogoUrl;
	}

	public String getBookSerialNumber() {
		return bookSerialNumber;
	}

	public void setBookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getSequance() {
		return sequance;
	}

	public void setSequance(String sequance) {
		this.sequance = sequance;
	}

	public String getContractFinancialNumber() {
		return contractFinancialNumber;
	}

	public void setContractFinancialNumber(String contractFinancialNumber) {
		this.contractFinancialNumber = contractFinancialNumber;
	}

	public String getMaazouniaName() {
		return maazouniaName;
	}

	public void setMaazouniaName(String maazouniaName) {
		this.maazouniaName = maazouniaName;
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
