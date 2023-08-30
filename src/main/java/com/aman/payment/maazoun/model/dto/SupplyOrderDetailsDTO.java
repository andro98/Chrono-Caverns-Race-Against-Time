package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class SupplyOrderDetailsDTO {

	private String id;
	private String createAt;
	private String createBy;
	private String bookTypeId;
	private String bookTypeName;
	private String sectorId;
	private String sectorName;
	private String attUrl;
	private String currentBookTypeCount;
	private String remainingBookTypeCount;
	private String bookCount;
	private String refSupplyOrderNumber;

	public SupplyOrderDetailsDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getCurrentBookTypeCount() {
		return currentBookTypeCount;
	}

	public void setCurrentBookTypeCount(String currentBookTypeCount) {
		currentBookTypeCount = currentBookTypeCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(String bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
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

	public String getAttUrl() {
		return attUrl;
	}

	public void setAttUrl(String attUrl) {
		this.attUrl = attUrl;
	}
	
	public String getBookCount() {
		return bookCount;
	}

	public void setBookCount(String bookCount) {
		this.bookCount = bookCount;
	}

	public String getRefSupplyOrderNumber() {
		return refSupplyOrderNumber;
	}

	public void setRefSupplyOrderNumber(String refSupplyOrderNumber) {
		this.refSupplyOrderNumber = refSupplyOrderNumber;
	}

	public String getRemainingBookTypeCount() {
		return remainingBookTypeCount;
	}

	public void setRemainingBookTypeCount(String remainingBookTypeCount) {
		this.remainingBookTypeCount = remainingBookTypeCount;
	}

	@Override
	public String toString() {
		return new JSONObject(this).toString();

	}

	
}
