package com.aman.payment.auth.model.dto;

import java.util.Set;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class MerchantDTO implements AuthBaseDTO<MerchantDTO>{

	private String id;
	private String name;
	private String code;
	private String address;
	private String phone1;
	private String phone2;
	private String mobile;
	private String fax;
	private String email;
	private String description;
	private String status;
	private Set<ServiceDTO> services;

	public MerchantDTO() {
	}
	
	public MerchantDTO(String id, String name, String code, String address, String phone1, String phone2, String mobile,
			String fax, String email, String description, String status,
			Set<ServiceDTO> services) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.address = address;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.mobile = mobile;
		this.fax = fax;
		this.email = email;
		this.description = description;
		this.status = status;
		this.services = services;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<ServiceDTO> getServices() {
		return services;
	}

	public void setServices(Set<ServiceDTO> services) {
		this.services = services;
	}
	
	

	public String getStatus() {
		return status;
	}

	@Override
	public MerchantDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new MerchantDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name), 
				cryptoMngrAuthService.encrypt(code), 
				cryptoMngrAuthService.encrypt(address), 
				cryptoMngrAuthService.encrypt(phone1), 
				cryptoMngrAuthService.encrypt(phone2), 
				cryptoMngrAuthService.encrypt(mobile), 
				cryptoMngrAuthService.encrypt(fax), 
				cryptoMngrAuthService.encrypt(email), 
				cryptoMngrAuthService.encrypt(description), 
				cryptoMngrAuthService.encrypt(status), 
				services);
	}


}