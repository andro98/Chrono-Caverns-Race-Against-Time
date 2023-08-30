package com.aman.payment.core.model.payload;

import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "PosMidRequest", description = "Request POS MID Request Format Request DTO Payload")
public class PosMidRequest {

	/*
	 * maazounBookFinancialNumner or collectionIfoId in case collect contract
	 */
	private List<String> maazounBookFinancialNumner;
	private Double totalAmount;
	private long sectorId;
	private String refundRefRequestNumber;
 

	public PosMidRequest() {
		super();
	}

	public List<String> getMaazounBookFinancialNumner() {
		return maazounBookFinancialNumner;
	}

	public void setMaazounBookFinancialNumner(List<String> maazounBookFinancialNumner) {
		this.maazounBookFinancialNumner = maazounBookFinancialNumner;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getSectorId() {
		return sectorId;
	}

	public void setSectorId(long sectorId) {
		this.sectorId = sectorId;
	}

	public String getRefundRefRequestNumber() {
		return refundRefRequestNumber;
	}

	public void setRefundRefRequestNumber(String refundRefRequestNumber) {
		this.refundRefRequestNumber = refundRefRequestNumber;
	}

	 

}