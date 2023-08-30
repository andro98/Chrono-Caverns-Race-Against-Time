package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Role;
import com.aman.payment.auth.model.dto.RoleDTO;
import com.aman.payment.auth.service.CryptoMngrAuthService;

@Service
public class RoleMapper {
	
	@Autowired
	private MenuMapper menuMapper;
	/*
	 * role to roleDTO
	 * roles to rolesDTOs
	 * ****************************************************************************************************
	 */
	public RoleDTO roleToRoleDTO(Role role) {
        return createRoleDTO(role);
    }
	
	public List<RoleDTO> rolesToRoleDTOs(List<Role> roles) {
		return roles.stream().filter(Objects::nonNull)
				.map(this::roleToRoleDTO).collect(Collectors.toList());
	}
	
	public Set<RoleDTO> rolesToRoleDTOs(Set<Role> roles) {
		return roles.stream().filter(Objects::nonNull)
				.map(this::roleToRoleDTO).collect(Collectors.toSet());
	}
	
	private RoleDTO createRoleDTO(Role role) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setName(role.getName());
		roleDTO.setId(String.valueOf(role.getId()));
		roleDTO.setMenus(menuMapper.menusToMenuDTOs(role.getMenus()));
		return roleDTO;
	}
	
	
}
