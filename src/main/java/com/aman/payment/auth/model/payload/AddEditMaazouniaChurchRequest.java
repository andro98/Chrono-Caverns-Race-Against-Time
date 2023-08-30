package com.aman.payment.auth.model.payload;

import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class AddEditMaazouniaChurchRequest implements AuthBasePayload<AddEditMaazouniaChurchRequest>{

	private String id;
	private String name;
	private String sectorId;
	private String status;
	private String createdBy;
	private MultipartFile creationAttacment;
	private String type;
	private String createtionDate;


	public AddEditMaazouniaChurchRequest(String id, String name, String sectorId, String  status, String createdBy,String type,
			String createtionDate) {
		super();
		this.id = id;
		this.name = name;
		this.sectorId = sectorId;
		this.status = status;
		this.createdBy = createdBy;
		this.type = type;
		this.createtionDate = createtionDate;
	}

	public AddEditMaazouniaChurchRequest() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
  
	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	 
	public MultipartFile getCreationAttacment() {
		return creationAttacment;
	}

	public void setCreationAttacment(MultipartFile creationAttacment) {
		this.creationAttacment = creationAttacment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
  
	public String getCreatetionDate() {
		return createtionDate;
	}

	public void setCreatetionDate(String createtionDate) {
		this.createtionDate = createtionDate;
	}

	@Override
	public AddEditMaazouniaChurchRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new AddEditMaazouniaChurchRequest(
				id!=null?cryptoMngrAuthService.decrypt(id):null, 
				cryptoMngrAuthService.decrypt(name), 
				cryptoMngrAuthService.decrypt(sectorId),
				cryptoMngrAuthService.decrypt(status),
				cryptoMngrAuthService.decrypt(createdBy),
				cryptoMngrAuthService.decrypt(type),
				cryptoMngrAuthService.decrypt(createtionDate));
	}

}