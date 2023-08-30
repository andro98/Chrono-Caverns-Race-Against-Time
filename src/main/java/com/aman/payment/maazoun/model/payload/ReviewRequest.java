package com.aman.payment.maazoun.model.payload;

public class ReviewRequest {

	private String id;
	private String comment;
	private String status;
	private String custody;

	public ReviewRequest() {
	}

	public ReviewRequest(String id, String comment, String status) {
		super();
		this.id = id;
		this.comment = comment;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustody() {
		return custody;
	}

	public void setCustody(String custody) {
		this.custody = custody;
	}

}
