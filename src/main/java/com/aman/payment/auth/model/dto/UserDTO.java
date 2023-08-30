package com.aman.payment.auth.model.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class UserDTO{

	private String id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private String address;
	private String mobile;
	private String phone;
	private String imageURL;
	private String active;
	private String role;
	private String roleId;

	private Set<PosDTO> posSet;
	private Set<SectorDTO> sectorSet;

	public UserDTO() {
		super();
	}

	public UserDTO(String id, String username, String firstName, String lastName, String email, String gender,
			String address, String mobile, String imageURL, String active, String role, String roleId,
			Set<PosDTO> posSet, String phone, Set<SectorDTO> sectorSet) {
		super();
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.address = address;
		this.mobile = mobile;
		this.imageURL = imageURL;
		this.active = active;
		this.role = role;
		this.roleId = roleId;
		this.posSet = posSet;
		this.phone = phone;
		this.sectorSet = sectorSet;
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

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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

	public Set<PosDTO> getPosSet() {
		return posSet;
	}

	public void setPosSet(Set<PosDTO> posSet) {
		this.posSet = posSet;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<SectorDTO> getSectorSet() {
		return sectorSet;
	}

	public void setSectorSet(Set<SectorDTO> sectorSet) {
		this.sectorSet = sectorSet;
	}

	@Override
	public String toString() {

		JSONObject user = new JSONObject();
		
		try {
			user.put("id", id);
			user.put("username", username);
			user.put("firstName", firstName);
			user.put("lastName", lastName);
			user.put("email", email);
			user.put("gender", gender);
			user.put("address", address);
			user.put("mobile", mobile);
			user.put("phone", phone);
			user.put("active", active);
			user.put("role", role);
			user.put("roleId", roleId);

			HashSet<JSONObject> posList = new HashSet<JSONObject>();
			HashSet<JSONObject> sectorsList = new HashSet<JSONObject>();

			for (PosDTO pos : posSet) {

				JSONObject posArr = new JSONObject();
				
				posArr.put("code", pos.getCode());
				posArr.put("id", pos.getId());
				posArr.put("dscription", pos.getDescription());
//				posArr.put("location", pos.getLocation());
//				posArr.put("locationCode", pos.getLocation_code());
//				posArr.put("locationId", pos.getLocation_id());
				posArr.put("name", pos.getName());
				posArr.put("status", pos.getStatus());

				posList.add(posArr);
				
				for(SectorDTO sector : pos.getSectors()) {
					
					JSONObject authSectorResponse = new JSONObject();
					
					authSectorResponse.put("id", sector.getId());
					authSectorResponse.put("sectorName", sector.getName());
					authSectorResponse.put("locationName", sector.getLocationName());
					authSectorResponse.put("locationId", sector.getLocationId());
					sectorsList.add(authSectorResponse);
					
				}

			}

			user.put("posSet", posList);
			user.put("sectorSet", sectorsList);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return user.toString();
	}

}