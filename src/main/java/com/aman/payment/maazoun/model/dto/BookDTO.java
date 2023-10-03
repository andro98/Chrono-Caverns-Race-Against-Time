package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

import com.aman.payment.maazoun.model.MaazounBookQuota;

public class BookDTO extends MaazounBookQuota{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String serialNumber;
	private String bookTypeName;
	private String bookTypeId;
	private String bookType;
	private String locationId;
	private String locationName;
	private String createAt;
	private String createBy;
	private String posId;
	private String posName;
	private String typeId;
	private String type;
	private String amanLogoUrl;
	private Double fees;
	private Long contractCount;
	private String maazounname;
	private String maazounId;
	private String maazounNationalId;
	private String bookFinancialNumber;
	private String status;
	private String isPrinted;
	private String contractNumber;
	private String sectorId;
	private String sectorName;
	private String contractFinancialNumber;
	private String  supplyOrderId;
	private String maazounWarehouseId;
	private String collectionInfoId;
	private String receivedStatus;
	
	private String custody;
	private String maazouniaChurchNameType;
	private String bookInventoryReference;
	private String refSupplyOrderNumber;

	public BookDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public String getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(String bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmanLogoUrl() {
		return amanLogoUrl;
	}

	public void setAmanLogoUrl(String amanLogoUrl) {
		this.amanLogoUrl = amanLogoUrl;
	}

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	public Long getContractCount() {
		return contractCount;
	}

	public void setContractCount(Long contractCount) {
		this.contractCount = contractCount;
	}	

	public String getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(String maazounId) {
		this.maazounId = maazounId;
	}
	
	

	public String getMaazounNationalId() {
		return maazounNationalId;
	}

	public void setMaazounNationalId(String maazounNationalId) {
		this.maazounNationalId = maazounNationalId;
	}
	
	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}
	 
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	 
	public String getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
	}
	
	 
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
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

	public String getMaazounname() {
		return maazounname;
	}

	public void setMaazounname(String maazounname) {
		this.maazounname = maazounname;
	}

	public String getMaazounWarehouseId() {
		return maazounWarehouseId;
	}

	public void setMaazounWarehouseId(String maazounWarehouseId) {
		this.maazounWarehouseId = maazounWarehouseId;
	}

	public String getContractFinancialNumber() {
		return contractFinancialNumber;
	}

	public void setContractFinancialNumber(String contractFinancialNumber) {
		this.contractFinancialNumber = contractFinancialNumber;
	}

	public String getCollectionInfoId() {
		return collectionInfoId;
	}

	public void setCollectionInfoId(String collectionInfoId) {
		this.collectionInfoId = collectionInfoId;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}
	
	 

	public String getCustody() {
		return custody;
	}

	public void setCustody(String custody) {
		this.custody = custody;
	}

	public String getReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	public String getMaazouniaChurchNameType() {
		return maazouniaChurchNameType;
	}

	public void setMaazouniaChurchNameType(String maazouniaChurchNameType) {
		this.maazouniaChurchNameType = maazouniaChurchNameType;
	}

	public String getBookInventoryReference() {
		return bookInventoryReference;
	}

	public void setBookInventoryReference(String bookInventoryReference) {
		this.bookInventoryReference = bookInventoryReference;
	}
	 
	public String getSupplyOrderId() {
		return supplyOrderId;
	}

	public void setSupplyOrderId(String supplyOrderId) {
		this.supplyOrderId = supplyOrderId;
	}

	public String getRefSupplyOrderNumber() {
		return refSupplyOrderNumber;
	}

	public void setRefSupplyOrderNumber(String refSupplyOrderNumber) {
		this.refSupplyOrderNumber = refSupplyOrderNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serialNumber == null) ? 0 : serialNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDTO other = (BookDTO) obj;
		if (serialNumber == null) {
			if (other.serialNumber != null)
				return false;
		} else if (!serialNumber.equals(other.serialNumber))
			return false;
		return true;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}
}
