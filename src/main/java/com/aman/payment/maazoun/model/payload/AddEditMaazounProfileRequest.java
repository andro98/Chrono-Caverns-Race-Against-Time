package com.aman.payment.maazoun.model.payload;


import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "AddEditMaazounProfileRequest", description = "Add/Edit Maazoun Request DTO Payload")
public class AddEditMaazounProfileRequest{

	private String id;
	private String name;
	private String nationalId;
	private String mobile;
	private String imageUrl;
	private String address;
	private String active;
	private String cardNumber;
	private MultipartFile fileMaazounImgAtt;
	private String maazounType;
	private String maazounQuota;
	private String custody;
	private String suspendedReason;
	private Long bookMoragaaQuota;
    private Long bookZawagMuslimQuota;
    private Long bookZawagMellyQuota;
    private Long bookTasadokQuota;
    private Long bookTalakQuota;
    private Long bookMoragaaQuotaContractCount;
    private Long bookZawagMuslimQuotaContractCount;
    private Long bookZawagMellyQuotaContractCount;
    private Long bookTasadokQuotaContractCount;
    private Long bookTalakQuotaContractCount;
    private String hasExeption;
	private MultipartFile exeptionAtt;

	public AddEditMaazounProfileRequest() {
		super();
	}

	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNationalId() {
		return nationalId;
	}

	public String getMobile() {
		return mobile;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
 

	public MultipartFile getFileMaazounImgAtt() {
		return fileMaazounImgAtt;
	}

	public void setFileMaazounImgAtt(MultipartFile fileMaazounImgAtt) {
		this.fileMaazounImgAtt = fileMaazounImgAtt;
	}


	public String getMaazounType() {
		return maazounType;
	}


	public void setMaazounType(String maazounType) {
		this.maazounType = maazounType;
	}


	public String getMaazounQuota() {
		return maazounQuota;
	}


	public void setMaazounQuota(String maazounQuota) {
		this.maazounQuota = maazounQuota;
	}


	public String getCustody() {
		return custody;
	}


	public void setCustody(String custody) {
		this.custody = custody;
	}


	public Long getBookMoragaaQuota() {
		return bookMoragaaQuota;
	}


	public void setBookMoragaaQuota(Long bookMoragaaQuota) {
		this.bookMoragaaQuota = bookMoragaaQuota;
	}


	public Long getBookZawagMuslimQuota() {
		return bookZawagMuslimQuota;
	}


	public void setBookZawagMuslimQuota(Long bookZawagMuslimQuota) {
		this.bookZawagMuslimQuota = bookZawagMuslimQuota;
	}


	public Long getBookZawagMellyQuota() {
		return bookZawagMellyQuota;
	}


	public void setBookZawagMellyQuota(Long bookZawagMellyQuota) {
		this.bookZawagMellyQuota = bookZawagMellyQuota;
	}


	public Long getBookTasadokQuota() {
		return bookTasadokQuota;
	}


	public void setBookTasadokQuota(Long bookTasadokQuota) {
		this.bookTasadokQuota = bookTasadokQuota;
	}


	public Long getBookTalakQuota() {
		return bookTalakQuota;
	}


	public void setBookTalakQuota(Long bookTalakQuota) {
		this.bookTalakQuota = bookTalakQuota;
	}


	public String getSuspendedReason() {
		return suspendedReason;
	}


	public void setSuspendedReason(String suspendedReason) {
		this.suspendedReason = suspendedReason;
	}


	public Long getBookMoragaaQuotaContractCount() {
		return bookMoragaaQuotaContractCount;
	}


	public void setBookMoragaaQuotaContractCount(Long bookMoragaaQuotaContractCount) {
		this.bookMoragaaQuotaContractCount = bookMoragaaQuotaContractCount;
	}


	public Long getBookZawagMuslimQuotaContractCount() {
		return bookZawagMuslimQuotaContractCount;
	}


	public void setBookZawagMuslimQuotaContractCount(Long bookZawagMuslimQuotaContractCount) {
		this.bookZawagMuslimQuotaContractCount = bookZawagMuslimQuotaContractCount;
	}


	public Long getBookZawagMellyQuotaContractCount() {
		return bookZawagMellyQuotaContractCount;
	}


	public void setBookZawagMellyQuotaContractCount(Long bookZawagMellyQuotaContractCount) {
		this.bookZawagMellyQuotaContractCount = bookZawagMellyQuotaContractCount;
	}


	public Long getBookTasadokQuotaContractCount() {
		return bookTasadokQuotaContractCount;
	}


	public void setBookTasadokQuotaContractCount(Long bookTasadokQuotaContractCount) {
		this.bookTasadokQuotaContractCount = bookTasadokQuotaContractCount;
	}


	public Long getBookTalakQuotaContractCount() {
		return bookTalakQuotaContractCount;
	}


	public void setBookTalakQuotaContractCount(Long bookTalakQuotaContractCount) {
		this.bookTalakQuotaContractCount = bookTalakQuotaContractCount;
	}

	public String getHasExeption() {
		return hasExeption;
	}

	public void setHasExeption(String hasExeption) {
		this.hasExeption = hasExeption;
	}


	public MultipartFile getExeptionAtt() {
		return exeptionAtt;
	}


	public void setExeptionAtt(MultipartFile exeptionAtt) {
		this.exeptionAtt = exeptionAtt;
	}

	
 

}