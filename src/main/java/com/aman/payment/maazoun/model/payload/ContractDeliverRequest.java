package com.aman.payment.maazoun.model.payload;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ContractDeliverRequest", description = "Add Book List Request DTO Payload")
public class ContractDeliverRequest {

	private Long warehouseId;
	private String bookFinancialNumber;
	private String bookTypeName;
	private String maazounName;
	private String maazounNationalId;
	private String locationName;
	private String sectorName;
	private String bookInventoryReference;

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
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

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getBookInventoryReference() {
		return bookInventoryReference;
	}

	public void setBookInventoryReference(String bookInventoryReference) {
		this.bookInventoryReference = bookInventoryReference;
	}

	
	

}