package com.aman.payment.auth.model.dto;

import org.json.JSONObject;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class LocationDTO implements AuthBaseDTO<LocationDTO>{

	private String id;
	private String name;
	private String code;
	private String city;
	private String cityAr;
	private String cityId;
	private String status;
	private String description;
	private String nameAndCity;
	private String passportImageOption;
	private String permissionImageOption;

	public LocationDTO() {
	}
	
	public LocationDTO(String id, String name, String code, String city, String cityAr, String cityId, String status,
	 String	description, String passportImageOption,String permissionImageOption, String nameAndCity) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.city = city;
		this.cityAr = cityAr;
		this.cityId = cityId;
		this.status = status;
		this.description=description;
		this.passportImageOption = passportImageOption;
		this.permissionImageOption = permissionImageOption;	
		this.nameAndCity = nameAndCity;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getCityAr() {
		return cityAr;
	}
	
	public void setCityAr(String cityAr) {
		this.cityAr = cityAr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	public String toString() {

		return new JSONObject(this).toString();

	}

	public String getNameAndCity() {
		return nameAndCity;
	}

	public void setNameAndCity(String nameAndCity) {
		this.nameAndCity = nameAndCity;
	}

	@Override
	public LocationDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new LocationDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name), 
				cryptoMngrAuthService.encrypt(code), 
				cryptoMngrAuthService.encrypt(city), 
				cryptoMngrAuthService.encrypt(cityAr), 
				cryptoMngrAuthService.encrypt(cityId), 
				cryptoMngrAuthService.encrypt(status), 
				cryptoMngrAuthService.encrypt(description),
				cryptoMngrAuthService.encrypt(passportImageOption),
				cryptoMngrAuthService.encrypt(permissionImageOption),
				cryptoMngrAuthService.encrypt(nameAndCity));
	}


}