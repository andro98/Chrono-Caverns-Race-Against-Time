package com.aman.payment.auth.model.dto;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class MenuDTO implements AuthBaseDTO<MenuDTO>{
	private String id;
	private String name;
	private String nameAr;
	private String keyViewName;
	private String url;
	private String icon;
	private String ordering;
	private String isPrivilage;
	private String serviceName;

	public MenuDTO() {
		super();
	}

	public MenuDTO(String id, String name, String nameAr, String keyViewName, String url, String icon, String ordering,
			String isPrivilage,String serviceName) {
		super();
		this.id = id;
		this.name = name;
		this.nameAr = nameAr;
		this.keyViewName = keyViewName;
		this.url = url;
		this.icon = icon;
		this.ordering = ordering;
		this.isPrivilage = isPrivilage;
		this.serviceName=serviceName;
	}



	public String getName() {
		return name;
	}

	public void setNameEn(String name) {
		this.name = name;
	}

	public String getNameAr() {
		return nameAr;
	}

	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	public String getKeyViewName() {
		return keyViewName;
	}

	public void setKeyViewName(String keyViewName) {
		this.keyViewName = keyViewName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOrdering() {
		return ordering;
	}

	public void setOrdering(String ordering) {
		this.ordering = ordering;
	}

	public String getIsPrivilage() {
		return isPrivilage;
	}

	public void setIsPrivilage(String isPrivilage) {
		this.isPrivilage = isPrivilage;
	}



	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public MenuDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new MenuDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name), 
				cryptoMngrAuthService.encrypt(nameAr), 
				cryptoMngrAuthService.encrypt(keyViewName), 
				cryptoMngrAuthService.encrypt(url), 
				cryptoMngrAuthService.encrypt(icon), 
				cryptoMngrAuthService.encrypt(ordering), 
				cryptoMngrAuthService.encrypt(isPrivilage),
				cryptoMngrAuthService.encrypt(serviceName));
	}

    
}
