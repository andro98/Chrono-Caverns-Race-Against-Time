package com.aman.payment.maazoun.model.dto;

import org.json.JSONObject;

public class BooksAggregateDTO {

	private String id;
	private String count;
	private String type;
	private String locationName;

	public BooksAggregateDTO() {
		super();
	}

	public BooksAggregateDTO(String id, String count, String type, String locationName) {
		super();
		this.id = id;
		this.count = count;
		this.type = type;
		this.locationName = locationName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}

}
