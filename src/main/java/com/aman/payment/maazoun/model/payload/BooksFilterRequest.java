package com.aman.payment.maazoun.model.payload;

public class BooksFilterRequest extends PaginationRequest{

	private String status;
	private Long sectorId;
	private String durationFrom;
	private String durationTo;
	private String refSupplyOrderNumber;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSectorId() {
		return sectorId;
	}

	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}

	public String getDurationFrom() {
		return durationFrom;
	}

	public void setDurationFrom(String durationFrom) {
		this.durationFrom = durationFrom;
	}

	public String getDurationTo() {
		return durationTo;
	}

	public void setDurationTo(String durationTo) {
		this.durationTo = durationTo;
	}

	public String getRefSupplyOrderNumber() {
		return refSupplyOrderNumber;
	}

	public void setRefSupplyOrderNumber(String refSupplyOrderNumber) {
		this.refSupplyOrderNumber = refSupplyOrderNumber;
	}
	 
	
}
