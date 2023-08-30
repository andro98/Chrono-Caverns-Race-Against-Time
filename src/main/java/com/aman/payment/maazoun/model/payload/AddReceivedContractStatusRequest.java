package com.aman.payment.maazoun.model.payload;

public class AddReceivedContractStatusRequest {

	private String maazounWarehouseId;
	private String receivedStatus;
	private String receivedComment;

	public AddReceivedContractStatusRequest() {
	}

	public String getMaazounWarehouseId() {
		return maazounWarehouseId;
	}

	public void setMaazounWarehouseId(String maazounWarehouseId) {
		this.maazounWarehouseId = maazounWarehouseId;
	}

	public String getReceivedStatus() {
		return receivedStatus;
	}

	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}

	public String getReceivedComment() {
		return receivedComment;
	}

	public void setReceivedComment(String receivedComment) {
		this.receivedComment = receivedComment;
	}

	
	

	

}
