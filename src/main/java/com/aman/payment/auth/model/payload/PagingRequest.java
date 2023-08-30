package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class PagingRequest implements AuthBasePayload<PagingRequest> {

	private String pageNo;
	private String pageSize;
	private String sortBy;

	public PagingRequest() {
	}

	public PagingRequest(String pageNo, String pageSize,
			String sortBy) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
	}

	public String getPageNo() {
		return pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	@Override
	public PagingRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {

		return new PagingRequest(cryptoMngrAuthService.decrypt(pageNo),
				cryptoMngrAuthService.decrypt(pageSize), cryptoMngrAuthService.decrypt(sortBy));
	}

}
