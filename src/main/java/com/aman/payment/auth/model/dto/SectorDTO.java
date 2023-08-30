package com.aman.payment.auth.model.dto;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class SectorDTO implements AuthBaseDTO<SectorDTO>{

	private String id;
	private String name;
	private String status;
	private String locationName;
	private String locationId;

	public SectorDTO() {
	}
	
	public SectorDTO(String id, String name, String status, String locationName, String locationId) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.locationName = locationName;
		this.locationId = locationId;
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

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	@Override
	public SectorDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new SectorDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name),
				cryptoMngrAuthService.encrypt(status), 
				cryptoMngrAuthService.encrypt(locationName),
				cryptoMngrAuthService.encrypt(locationId));
	}


}