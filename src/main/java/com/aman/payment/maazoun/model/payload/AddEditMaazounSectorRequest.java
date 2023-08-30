package com.aman.payment.maazoun.model.payload;


import io.swagger.annotations.ApiModel;

@ApiModel(value = "AddEditMaazounPosRequest", description = "Add/Edit Maazoun Sector DTO Payload")
public class AddEditMaazounSectorRequest{

	private String maazounId;
	private String sectorId;
	
	public AddEditMaazounSectorRequest() {
		super();
	}

	public String getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(String maazounId) {
		this.maazounId = maazounId;
	}

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}



	

}