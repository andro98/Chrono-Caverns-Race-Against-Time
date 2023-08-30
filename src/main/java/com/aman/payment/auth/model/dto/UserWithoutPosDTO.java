package com.aman.payment.auth.model.dto;

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class UserWithoutPosDTO implements AuthBaseDTO <UserWithoutPosDTO>{

	public UserWithoutPosDTO() {
		// TODO Auto-generated constructor stub
	}
	
	private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String address;
    private String mobile;
    private String active;
    private String role;
    private String roleId;
  
    
	
	
	public UserWithoutPosDTO(String id, String username, String firstName, String lastName, String active, String role, String roleId) {
		super();
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		this.role = role;
		this.roleId = roleId;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	@Override
	public UserWithoutPosDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		
		return new UserWithoutPosDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(username), 
				cryptoMngrAuthService.encrypt(firstName), 
				cryptoMngrAuthService.encrypt(lastName),
				cryptoMngrAuthService.encrypt(active), 
				cryptoMngrAuthService.encrypt(role), 
				cryptoMngrAuthService.encrypt(roleId));
	}
   

}
