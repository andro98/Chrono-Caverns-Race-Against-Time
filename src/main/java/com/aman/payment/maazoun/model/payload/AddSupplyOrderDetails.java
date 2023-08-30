package com.aman.payment.maazoun.model.payload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AddSupplyOrderDetails {
	
	private String sectorName;
	private Long sectorId;
	private List<AddSupplyOrderDetailsListRequest> supplyOrderDetailsList;
	private MultipartFile attUrl;
	private String CreatedBy;
	
	private long sequance;
	private long currentYearTwoDigits;
	private String supplyOrderReferenceNumber;
	
	public AddSupplyOrderDetails() {
		// TODO Auto-generated constructor stub
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public List<AddSupplyOrderDetailsListRequest> getSupplyOrderDetailsList() {
		return supplyOrderDetailsList;
	}

	public void setSupplyOrderDetailsList(List<AddSupplyOrderDetailsListRequest> supplyOrderDetailsList) {
		this.supplyOrderDetailsList = supplyOrderDetailsList;
	}

	public MultipartFile getAttUrl() {
		return attUrl;
	}

	public void setAttUrl(MultipartFile attUrl) {
		this.attUrl = attUrl;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public long getSequance() {
		return sequance;
	}

	public void setSequance(long sequance) {
		this.sequance = sequance;
	}

	public long getCurrentYearTwoDigits() {
		return currentYearTwoDigits;
	}

	public void setCurrentYearTwoDigits(long currentYearTwoDigits) {
		this.currentYearTwoDigits = currentYearTwoDigits;
	}

	public String getSupplyOrderReferenceNumber() {
		return supplyOrderReferenceNumber;
	}

	public void setSupplyOrderReferenceNumber(String supplyOrderReferenceNumber) {
		this.supplyOrderReferenceNumber = supplyOrderReferenceNumber;
	}

	
	
}
