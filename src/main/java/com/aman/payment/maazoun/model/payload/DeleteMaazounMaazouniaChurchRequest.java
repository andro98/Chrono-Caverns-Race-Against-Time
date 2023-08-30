package com.aman.payment.maazoun.model.payload;


import io.swagger.annotations.ApiModel;

@ApiModel(value = "DeleteMaazounMaazouniaChurchRequest", description = "Delete Maazoun MaazouniaChurch DTO Payload")
public class DeleteMaazounMaazouniaChurchRequest{

	private String maazouniaChurchId;
	
	public DeleteMaazounMaazouniaChurchRequest() {
		super();
	}

	public String getMaazouniaChurchId() {
		return maazouniaChurchId;
	}

	public void setMaazouniaChurchId(String maazouniaChurchId) {
		this.maazouniaChurchId = maazouniaChurchId;
	}
	

}