package com.aman.payment.maazoun.model.payload;

import java.util.List;

import com.aman.payment.maazoun.model.MaazounBookQuota;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "BookListRequest", description = "Add Book List Request DTO Payload")
public class BookListRequest extends MaazounBookQuota{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long typeId;
	private String type;
	private String serialNumber;
	private Long bookWarehouseId;
	private Long contractCount;
	private String bookFinancialNumber;
	private Double fees;
	private List<AddContractListRequest> contracts;
	private String maazouniaChurchId;
	private String maazouniaChurchNameType;
	private Long sectorId;
	
	public BookListRequest() {
		super();
	}

	
	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getContractCount() {
		return contractCount;
	}

	public void setContractCount(Long contractCount) {
		this.contractCount = contractCount;
	}

	public String getBookFinancialNumber() {
		return bookFinancialNumber;
	}

	public void setBookFinancialNumber(String bookFinancialNumber) {
		this.bookFinancialNumber = bookFinancialNumber;
	}



	public Long getBookWarehouseId() {
		return bookWarehouseId;
	}


	public void setBookWarehouseId(Long bookWarehouseId) {
		this.bookWarehouseId = bookWarehouseId;
	}


	public Double getFees() {
		return fees;
	}


	public void setFees(Double fees) {
		this.fees = fees;
	}


	public List<AddContractListRequest> getContracts() {
		return contracts;
	}


	public void setContracts(List<AddContractListRequest> contracts) {
		this.contracts = contracts;
	}


	public String getMaazouniaChurchId() {
		return maazouniaChurchId;
	}


	public void setMaazouniaChurchId(String maazouniaChurchId) {
		this.maazouniaChurchId = maazouniaChurchId;
	}


	public String getMaazouniaChurchNameType() {
		return maazouniaChurchNameType;
	}


	public void setMaazouniaChurchNameType(String maazouniaChurchNameType) {
		this.maazouniaChurchNameType = maazouniaChurchNameType;
	}


	public Long getSectorId() {
		return sectorId;
	}


	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}


	
	

}