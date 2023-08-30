package com.aman.payment.auth.model.dto;

import java.util.Set;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class RoleDTO implements AuthBaseDTO<RoleDTO>{

	private String id;
    private String name;
    private Set<MenuDTO> menus;
    
	public RoleDTO() {
		super();
	}
	
	public RoleDTO(String id, String name, Set<MenuDTO> menus) {
		super();
		this.id = id;
		this.name = name;
		this.menus = menus;
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

	public Set<MenuDTO> getMenus() {
		return menus;
	}

	public void setMenus(Set<MenuDTO> menus) {
		this.menus = menus;
	}

	@Override
	public RoleDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new RoleDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name), 
				menus);
	}


   
}