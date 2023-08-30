package com.aman.payment.maazoun.model.dto;

import java.util.List;

import org.json.JSONObject;

public class MaazounBookHistoryDTO {

	private String bookType;

	private String serialNumber;
	private String financialNumber;
	private String bookLastStatus;
	private long numberOfContracts;

	private String suppliedDateBy;
	private String suppliedReviewedDateBy;
	private String custody;
	private String soldDateBy;
	private String maazounNameAndType;
	private String maazounNationalId;

	private String sectorName;
	private String deliveryDateBy;
	private String deliveryApprovedDateBy;

	private List<String> contracts;

	public MaazounBookHistoryDTO() {
		super();
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getFinancialNumber() {
		return financialNumber;
	}

	public void setFinancialNumber(String financialNumber) {
		this.financialNumber = financialNumber;
	}

	public String getBookLastStatus() {
		return bookLastStatus;
	}

	public void setBookLastStatus(String bookLastStatus) {
		this.bookLastStatus = bookLastStatus;
	}

	

	public long getNumberOfContracts() {
		return numberOfContracts;
	}

	public void setNumberOfContracts(long numberOfContracts) {
		this.numberOfContracts = numberOfContracts;
	}

	public String getSuppliedDateBy() {
		return suppliedDateBy;
	}

	public void setSuppliedDateBy(String suppliedDateBy) {
		this.suppliedDateBy = suppliedDateBy;
	}

	public String getSuppliedReviewedDateBy() {
		return suppliedReviewedDateBy;
	}

	public void setSuppliedReviewedDateBy(String suppliedReviewedDateBy) {
		this.suppliedReviewedDateBy = suppliedReviewedDateBy;
	}

	public String getCustody() {
		return custody;
	}

	public void setCustody(String custody) {
		this.custody = custody;
	}

	public String getSoldDateBy() {
		return soldDateBy;
	}

	public void setSoldDateBy(String soldDateBy) {
		this.soldDateBy = soldDateBy;
	}

	public String getMaazounNameAndType() {
		return maazounNameAndType;
	}

	public void setMaazounNameAndType(String maazounNameAndType) {
		this.maazounNameAndType = maazounNameAndType;
	}

	public String getMaazounNationalId() {
		return maazounNationalId;
	}

	public void setMaazounNationalId(String maazounNationalId) {
		this.maazounNationalId = maazounNationalId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getDeliveryDateBy() {
		return deliveryDateBy;
	}

	public void setDeliveryDateBy(String deliveryDateBy) {
		this.deliveryDateBy = deliveryDateBy;
	}

	public String getDeliveryApprovedDateBy() {
		return deliveryApprovedDateBy;
	}

	public void setDeliveryApprovedDateBy(String deliveryApprovedDateBy) {
		this.deliveryApprovedDateBy = deliveryApprovedDateBy;
	}

	public List<String> getContracts() {
		return contracts;
	}

	public void setContracts(List<String> contracts) {
		this.contracts = contracts;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}