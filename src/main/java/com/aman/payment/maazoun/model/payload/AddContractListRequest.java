package com.aman.payment.maazoun.model.payload;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "BookListRequest", description = "Add Book List Request DTO Payload")
public class AddContractListRequest{

	private String contractSerialNumber;
	private String contractFinancialNumber;

	public AddContractListRequest() {
		super();
	}

	public String getContractSerialNumber() {
		return contractSerialNumber;
	}

	public void setContractSerialNumber(String contractSerialNumber) {
		this.contractSerialNumber = contractSerialNumber;
	}

	public String getContractFinancialNumber() {
		return contractFinancialNumber;
	}

	public void setContractFinancialNumber(String contractFinancialNumber) {
		this.contractFinancialNumber = contractFinancialNumber;
	}

	
	
	
	

}