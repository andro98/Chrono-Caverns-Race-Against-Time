package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class AddEditSectorRequest implements AuthBasePayload<AddEditSectorRequest>{

	private String id;
	private String name;
	private String locationId;
	private String  status;
	private String createdBy;
	

	

	public AddEditSectorRequest(String id, String name, String locationId, String  status, String createdBy) {
		super();
		this.id = id;
		this.name = name;
		this.locationId = locationId;
		this.status = status;
		this.createdBy = createdBy;
	}

	public AddEditSectorRequest() {
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

	

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
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

	@Override
	public AddEditSectorRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new AddEditSectorRequest(
				id!=null?cryptoMngrAuthService.decrypt(id):null, 
				cryptoMngrAuthService.decrypt(name), 
				cryptoMngrAuthService.decrypt(locationId),
				cryptoMngrAuthService.decrypt(status),
				cryptoMngrAuthService.decrypt(createdBy));
	}

}