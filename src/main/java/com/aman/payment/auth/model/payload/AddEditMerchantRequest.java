package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class AddEditMerchantRequest implements AuthBasePayload<AddEditMerchantRequest>{

	private String id;
	private String name;
	private String address;
	private String phone1;
	private String phone2;
	private String mobile;
	private String fax;
	private String email;
	private String description;
	private String cityId;
	private String status;

	public AddEditMerchantRequest() {
	}

	public AddEditMerchantRequest(String id, String name, String address, String phone1, String phone2, String mobile,
			String fax, String email, String description, String cityId, String status) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.mobile = mobile;
		this.fax = fax;
		this.email = email;
		this.description = description;
		this.cityId = cityId;
		this.status = status;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public AddEditMerchantRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new AddEditMerchantRequest(cryptoMngrAuthService.decrypt(id), 
				cryptoMngrAuthService.decrypt(name), 
				cryptoMngrAuthService.decrypt(address), 
				cryptoMngrAuthService.decrypt(phone1), 
				cryptoMngrAuthService.decrypt(phone2), 
				cryptoMngrAuthService.decrypt(mobile), 
				cryptoMngrAuthService.decrypt(fax), 
				cryptoMngrAuthService.decrypt(email), 
				cryptoMngrAuthService.decrypt(description), 
				cryptoMngrAuthService.decrypt(cityId), 
				cryptoMngrAuthService.decrypt(status));
	}

}