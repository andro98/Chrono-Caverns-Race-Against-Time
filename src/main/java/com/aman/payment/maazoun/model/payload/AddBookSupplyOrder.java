package com.aman.payment.maazoun.model.payload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "AddEditApplicantRequest", description = "Add/Edit Applicant Request DTO Payload")
public class AddBookSupplyOrder {

	private String refSupplyOrderNumber;
	private Long sectorId;
	private String custody;
	private List<BookListRequest> bookList;
	private MultipartFile imageUrl;
//	private String maazouniaChurchId;
//	private String maazouniaChurchNameType;
	
	private Long maazounId;

	public AddBookSupplyOrder() {
		super();
	}
	public List<BookListRequest> getBookList() {
		return bookList;
	}

	public void setBookList(List<BookListRequest> bookList) {
		this.bookList = bookList;
	}

	public String getRefSupplyOrderNumber() {
		return refSupplyOrderNumber;
	}

	public void setRefSupplyOrderNumber(String refSupplyOrderNumber) {
		this.refSupplyOrderNumber = refSupplyOrderNumber;
	}

	public MultipartFile getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(MultipartFile imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Long getSectorId() {
		return sectorId;
	}
	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}
	 
	public Long getMaazounId() {
		return maazounId;
	}
	public void setMaazounId(Long maazounId) {
		this.maazounId = maazounId;
	}
	public String getCustody() {
		return custody;
	}
	public void setCustody(String custody) {
		this.custody = custody;
	}
//	public String getMaazouniaChurchId() {
//		return maazouniaChurchId;
//	}
//	public void setMaazouniaChurchId(String maazouniaChurchId) {
//		this.maazouniaChurchId = maazouniaChurchId;
//	}
//	public String getMaazouniaChurchNameType() {
//		return maazouniaChurchNameType;
//	}
//	public void setMaazouniaChurchNameType(String maazouniaChurchNameType) {
//		this.maazouniaChurchNameType = maazouniaChurchNameType;
//	}
	

	
}