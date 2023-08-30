package com.aman.payment.maazoun.model.payload;

public class AddMaazounCustodyContractRequest {

	private Long subServiceId;
	private Long maazounId;
	
	private String contractDate;
	private String bookFinancialNumber;
	private String contractFinancialNumber;
	
	private String husbandName;
	private String wifeName;
	private String husbandNationalId;
	private String wifeNationalId;
	private String husbandNationalIdExpiryDate;
	private String wifeNationalIdExpiryDate;
	
    private Long sectorId; 
    private Long bookWarehouseId;

	public AddMaazounCustodyContractRequest() {
	}

	public Long getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(Long maazounId) {
		this.maazounId = maazounId;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getHusbandName() {
		return husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public String getWifeName() {
		return wifeName;
	}

	public void setWifeName(String wifeName) {
		this.wifeName = wifeName;
	}

	public Long getSubServiceId() {
		return subServiceId;
	}

	public void setSubServiceId(Long subServiceId) {
		this.subServiceId = subServiceId;
	}

	public String getHusbandNationalId() {
		return husbandNationalId;
	}

	public void setHusbandNationalId(String husbandNationalId) {
		this.husbandNationalId = husbandNationalId;
	}

	public String getWifeNationalId() {
		return wifeNationalId;
	}

	public void setWifeNationalId(String wifeNationalId) {
		this.wifeNationalId = wifeNationalId;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}

	public String getContractFinancialNumber() {
		return contractFinancialNumber;
	}

	public void setContractFinancialNumber(String contractFinancialNumber) {
		this.contractFinancialNumber = contractFinancialNumber;
	}

	public Long getBookWarehouseId() {
		return bookWarehouseId;
	}

	public void setBookWarehouseId(Long bookWarehouseId) {
		this.bookWarehouseId = bookWarehouseId;
	}

	public String getHusbandNationalIdExpiryDate() {
		return husbandNationalIdExpiryDate;
	}

	public void setHusbandNationalIdExpiryDate(String husbandNationalIdExpiryDate) {
		this.husbandNationalIdExpiryDate = husbandNationalIdExpiryDate;
	}

	public String getWifeNationalIdExpiryDate() {
		return wifeNationalIdExpiryDate;
	}

	public void setWifeNationalIdExpiryDate(String wifeNationalIdExpiryDate) {
		this.wifeNationalIdExpiryDate = wifeNationalIdExpiryDate;
	}

	

}
