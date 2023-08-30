package com.aman.payment.auth.model.dto;

import org.json.JSONObject;

public class MaazounMaazouniaChurchDTO {
	private String maazounId;
	private String maazouniaChurchId;
	private String maazouniaChurchName;
	private String maazouniaType;
	private String id;

	public MaazounMaazouniaChurchDTO() {
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

	public String getMaazouniaChurchName() {
		return maazouniaChurchName;
	}

	public void setMaazouniaChurchName(String maazouniaChurchName) {
		this.maazouniaChurchName = maazouniaChurchName;
	}

	public String getMaazouniaType() {
		return maazouniaType;
	}

	public void setMaazouniaType(String maazouniaType) {
		this.maazouniaType = maazouniaType;
	}

	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}