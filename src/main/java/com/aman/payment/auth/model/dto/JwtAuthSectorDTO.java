package com.aman.payment.auth.model.dto;

import org.json.JSONObject;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class JwtAuthSectorDTO implements AuthBaseDTO<JwtAuthSectorDTO>{
    private String id;
    private String name;
    private String locationName;
    private String locationId;
    
    public JwtAuthSectorDTO() {
    	super();
    }


	public JwtAuthSectorDTO(String id, String name, String locationName, String locationId) {
		super();
		this.id = id;
		this.name = name;
		this.locationName = locationName;
		this.locationId = locationId;
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


	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public String getLocationId() {
		return locationId;
	}


	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtAuthSectorDTO other = (JwtAuthSectorDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		
		return new JSONObject(this).toString();
		

	}
	@Override
	public JwtAuthSectorDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new JwtAuthSectorDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name), 
				cryptoMngrAuthService.encrypt(locationName), 
				cryptoMngrAuthService.encrypt(locationId));

	}
    
	
}