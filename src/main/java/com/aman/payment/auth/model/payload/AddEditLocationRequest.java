package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class AddEditLocationRequest implements AuthBasePayload<AddEditLocationRequest>{

	private String id;
	private String name;
	private String description;
	private String cityId;
	private String status;
	private String passportImageOption;
	private String permissionImageOption;

	public AddEditLocationRequest(String id, String name, String description, String cityId, String status,
			String passportImageOption, String permissionImageOption) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.cityId = cityId;
		this.status = status;
		this.passportImageOption = passportImageOption;
	    this.permissionImageOption = permissionImageOption;
	}

	public AddEditLocationRequest() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassportImageOption() {
		return passportImageOption;
	}

	public void setPassportImageOption(String passportImageOption) {
		this.passportImageOption = passportImageOption;
	}
	
	

	public String getPermissionImageOption() {
		return permissionImageOption;
	}

	public void setPermissionImageOption(String permissionImageOption) {
		this.permissionImageOption = permissionImageOption;
	}

	@Override
	public AddEditLocationRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new AddEditLocationRequest(cryptoMngrAuthService.decrypt(id), 
				cryptoMngrAuthService.decrypt(name), 
				cryptoMngrAuthService.decrypt(description), 
				cryptoMngrAuthService.decrypt(cityId), 
				cryptoMngrAuthService.decrypt(status),
				cryptoMngrAuthService.decrypt(passportImageOption),
				cryptoMngrAuthService.decrypt(permissionImageOption));
	}

}