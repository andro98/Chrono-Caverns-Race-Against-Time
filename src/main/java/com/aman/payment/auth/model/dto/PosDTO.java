package com.aman.payment.auth.model.dto;

import java.util.Set;

import org.json.JSONObject;


public class PosDTO {
    private String id;
    private String name;
    private String code;
//    private String location_id;
//    private String location;
//    private String location_code;
    private String status;
    private Set<ServiceDTO> services;
    private String description;

    private Set<UserWithoutPosDTO> users;
    private String passportImageOption;
//    private String sectorName;
//    private String sectorId;
    
    private Set<SectorDTO> sectors;

    public PosDTO() {
    	super();
    }

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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


	public Set<ServiceDTO> getServices() {
		return services;
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


	public void setServices(Set<ServiceDTO> services) {
		this.services = services;
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