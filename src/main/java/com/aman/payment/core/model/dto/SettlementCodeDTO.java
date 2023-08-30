package com.aman.payment.core.model.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class SettlementCodeDTO {
	
	private String settlementCode;
	
	
	public SettlementCodeDTO() {
		super();
	}


	public String getSettlementCode() {
		return settlementCode;
	}


	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}


	@Override
	public String toString() {
		
		JSONObject settlementDTO = new JSONObject();
		
		try {
			settlementDTO.put("settlementCode", settlementCode);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return settlementDTO.toString();
	}
	
	
	
	
	
	
}
