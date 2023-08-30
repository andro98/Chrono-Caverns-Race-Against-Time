package com.aman.payment.auth.model.dto;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class CityDTO implements AuthBaseDTO<CityDTO>{

    private String id;
    private String name;
    private String nameAr;

    public CityDTO() {
    }
    
    public CityDTO(String id, String name, String nameAr) {
		super();
		this.id = id;
		this.name = name;
		this.nameAr = nameAr;
	}

	public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

	public String getNameAr() {
		return nameAr;
	}

	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	@Override
	public CityDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new CityDTO(
				cryptoMngrAuthService.encrypt(id), 
				cryptoMngrAuthService.encrypt(name), 
				cryptoMngrAuthService.encrypt(nameAr));
	}


}