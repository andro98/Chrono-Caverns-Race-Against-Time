package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class ContractValidationDTO {

	private String key;
	private String value;

	public ContractValidationDTO() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}
