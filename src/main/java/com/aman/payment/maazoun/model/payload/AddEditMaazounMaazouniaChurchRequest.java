package com.aman.payment.maazoun.model.payload;


import io.swagger.annotations.ApiModel;

@ApiModel(value = "AddEditMaazounMaazouniaChurchRequest", description = "Add/Edit Maazoun MaazouniaChurch DTO Payload")
public class AddEditMaazounMaazouniaChurchRequest{

	private String maazounId;
	private String maazouniaChurchId;
	private String maazouniaType;
	
	public AddEditMaazounMaazouniaChurchRequest() {
		super();
	}

	public String getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(String maazounId) {
		this.maazounId = maazounId;
	}

	public String getMaazouniaChurchId() {
		return maazouniaChurchId;
	}

	public void setMaazouniaChurchId(String maazouniaChurchId) {
		this.maazouniaChurchId = maazouniaChurchId;
	}

	public String getMaazouniaType() {
		return maazouniaType;
	}

	public void setMaazouniaType(String maazouniaType) {
		this.maazouniaType = maazouniaType;
	}

	

	

}