package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class MaazounBookAvailableDTO {

	private String bookType;
	private String reqInfoId;

	private String bookSerialNumber;
	private String bookFininacialNumber;
	private String bookStatus;
	private String sector;
	private String maazouniaName;
	private String maazounName;
	private String maazounNationalId;

	private String soldDate;
	private String soldBy;
	private String custody;

	public MaazounBookAvailableDTO() {
		super();
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getBookSerialNumber() {
		return bookSerialNumber;
	}

	public void setBookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}

	public String getBookFininacialNumber() {
		return bookFininacialNumber;
	}

	public void setBookFininacialNumber(String bookFininacialNumber) {
		this.bookFininacialNumber = bookFininacialNumber;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getSoldDate() {
		return soldDate;
	}

	public void setSoldDate(String soldDate) {
		this.soldDate = soldDate;
	}

	public String getSoldBy() {
		return soldBy;
	}

	public void setSoldBy(String soldBy) {
		this.soldBy = soldBy;
	}

	public String getReqInfoId() {
		return reqInfoId;
	}

	public void setReqInfoId(String reqInfoId) {
		this.reqInfoId = reqInfoId;
	}

	public String getMaazouniaName() {
		return maazouniaName;
	}

	public void setMaazouniaName(String maazouniaName) {
		this.maazouniaName = maazouniaName;
	}

	public String getCustody() {
		return custody;
	}

	public void setCustody(String custody) {
		this.custody = custody;
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

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}