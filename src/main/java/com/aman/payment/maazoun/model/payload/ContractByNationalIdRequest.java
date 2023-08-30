package com.aman.payment.maazoun.model.payload;

public class ContractByNationalIdRequest{

	private String maazounNationalId;
	private long maazouniaId;

	public String getMaazounNationalId() {
		return maazounNationalId;
	}

	public void setMaazounNationalId(String maazounNationalId) {
		this.maazounNationalId = maazounNationalId;
	}

	public long getMaazouniaId() {
		return maazouniaId;
	}

	public void setMaazouniaId(long maazouniaId) {
		this.maazouniaId = maazouniaId;
	}
	
	

}
