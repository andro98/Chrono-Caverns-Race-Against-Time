package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Menu;
import com.aman.payment.auth.model.dto.MenuDTO;
import com.aman.payment.auth.service.CryptoMngrAuthService;

@Service
public class MenuMapper {
	
	/*
	 * menu to menuDTO
	 * menus to menuDTOs
	 * ****************************************************************************************************
	 */
	public MenuDTO menuToMenuDTO(Menu menu) {
        return createMenuDTO(menu);
    }
	
	public List<MenuDTO> menusToMenuDTOs(List<Menu> menus) {
		return menus.stream().filter(Objects::nonNull)
				.map(this::menuToMenuDTO).collect(Collectors.toList());
	}
	
	public Set<MenuDTO> menusToMenuDTOs(Set<Menu> menus) {
		return menus.stream().filter(Objects::nonNull)
				.map(this::menuToMenuDTO).collect(Collectors.toSet());
	}
	
	private MenuDTO createMenuDTO(Menu menu) {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setNameAr(menu.getNameAr());
		menuDTO.setNameEn(menu.getName());
		menuDTO.setIcon(menu.getIcon());
		menuDTO.setId(String.valueOf(menu.getId()));
		menuDTO.setIsPrivilage(String.valueOf(menu.getIsPrivilage()));
		menuDTO.setKeyViewName(menu.getKeyViewName());
		menuDTO.setOrdering(String.valueOf(menu.getOrdering()));
		menuDTO.setUrl(menu.getUrl());
		menuDTO.setServiceName(menu.getServiceFk().getName());
		
		return menuDTO;
	}
}
