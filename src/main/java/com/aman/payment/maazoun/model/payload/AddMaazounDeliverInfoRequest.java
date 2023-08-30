package com.aman.payment.maazoun.model.payload;

import java.util.List;

public class AddMaazounDeliverInfoRequest {

	private List<ContractDeliverRequest> booksDeliverRequest;
	private Long sectorId;

	public AddMaazounDeliverInfoRequest() {
	}

	

	public List<ContractDeliverRequest> getBooksDeliverRequest() {
		return booksDeliverRequest;
	}



	public void setBooksDeliverRequest(List<ContractDeliverRequest> booksDeliverRequest) {
		this.booksDeliverRequest = booksDeliverRequest;
	}



	public Long getSectorId() {
		return sectorId;
	}



	public void setSectorId(Long sectorId) {
		this.sectorId = sectorId;
	}



	
	
	
}
