package com.aman.payment.auth.model.dto;

import org.json.JSONObject;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class MaazouniaChurchDTO implements AuthBaseDTO<MaazouniaChurchDTO>{
    private String id;
    private String name;
    private String status;
    private String sectorId;
    private String sectorName;
    private String type;
    private String createtionDate;
    private String creationAttacmentUrl;
    
    public MaazouniaChurchDTO() {
    	super();
    }


	public MaazouniaChurchDTO(String id, String name, String status, String sectorId, String sectorName, String type,
			String createtionDate, String creationAttacmentUrl) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.sectorId = sectorId;
		this.sectorName = sectorName;
		this.type = type;
		this.createtionDate = createtionDate;
		this.creationAttacmentUrl = creationAttacmentUrl;
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

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getSectorId() {
		return sectorId;
	}


	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
 

	public String getSectorName() {
		return sectorName;
	}


	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
 
	public String getCreatetionDate() {
		return createtionDate;
	}


	public void setCreatetionDate(String createtionDate) {
		this.createtionDate = createtionDate;
	}


	public String getCreationAttacmentUrl() {
		return creationAttacmentUrl;
	}


	public void setCreationAttacmentUrl(String creationAttacmentUrl) {
		this.creationAttacmentUrl = creationAttacmentUrl;
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
		MaazouniaChurchDTO other = (MaazouniaChurchDTO) obj;
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
	public MaazouniaChurchDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new MaazouniaChurchDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name),
				cryptoMngrAuthService.encrypt(status),
				cryptoMngrAuthService.encrypt(sectorId),
				cryptoMngrAuthService.encrypt(sectorName),
				cryptoMngrAuthService.encrypt(type),
				cryptoMngrAuthService.encrypt(createtionDate),
				cryptoMngrAuthService.encrypt(creationAttacmentUrl));
	}

    
	
}