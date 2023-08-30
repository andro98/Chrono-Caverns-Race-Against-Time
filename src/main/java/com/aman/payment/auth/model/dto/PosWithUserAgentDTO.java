package com.aman.payment.auth.model.dto;

import java.util.Set;

import org.json.JSONObject;

public class PosWithUserAgentDTO {

	private String id;
	private String name;
	private String code;
//    private String location_id;
//    private String location;
//    private String location_code;
	private String status;
	private Set<UserWithoutPosDTO> users;
	private String passportImageOption;
//    private String sectorId;
//    private String sectorName;
	private Set<SectorDTO> sectors;

	public PosWithUserAgentDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<UserWithoutPosDTO> getUsers() {
		return users;
	}

	public void setUsers(Set<UserWithoutPosDTO> users) {
		this.users = users;
	}

	public String getPassportImageOption() {
		return passportImageOption;
	}

	public void setPassportImageOption(String passportImageOption) {
		this.passportImageOption = passportImageOption;
	}

	public Set<SectorDTO> getSectors() {
		return sectors;
	}

	public void setSectors(Set<SectorDTO> sectors) {
		this.sectors = sectors;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}
	
}
