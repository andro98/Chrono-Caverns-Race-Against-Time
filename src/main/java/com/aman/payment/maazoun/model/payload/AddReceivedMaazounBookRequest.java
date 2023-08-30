package com.aman.payment.maazoun.model.payload;

import java.util.List;

public class AddReceivedMaazounBookRequest {

	private String bookSerialNumber;
	private String bookFinancialNumber;
	private List<AddReceivedMaazounContractRequest> contractList;
	private Long sectorId;
	private String maazouniaChurchNameType;

	public AddReceivedMaazounBookRequest() {
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

	public List<AddReceivedMaazounContractRequest> getContractList() {
		return contractList;
	}

	public void setContractList(List<AddReceivedMaazounContractRequest> contractList) {
		this.contractList = contractList;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public String getMaazouniaChurchNameType() {
		return maazouniaChurchNameType;
	}

	public void setMaazouniaChurchNameType(String maazouniaChurchNameType) {
		this.maazouniaChurchNameType = maazouniaChurchNameType;
	}


	

}
