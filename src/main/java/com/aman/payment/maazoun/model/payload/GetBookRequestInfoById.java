package com.aman.payment.maazoun.model.payload;

public class GetBookRequestInfoById {

	private long requestInfoId;
	private long maazounId;
	private Long maazouniaId;

	public GetBookRequestInfoById() {
	}

	public long getRequestInfoId() {
		return requestInfoId;
	}

	public void setRequestInfoId(long requestInfoId) {
		this.requestInfoId = requestInfoId;
	}

	public long getMaazounId() {
		return maazounId;
	}

	public void setMaazounId(long maazounId) {
		this.maazounId = maazounId;
	}

	public Long getMaazouniaId() {
		return maazouniaId;
	}

	public void setMaazouniaId(Long maazouniaId) {
		this.maazouniaId = maazouniaId;
	}

	

	

}
