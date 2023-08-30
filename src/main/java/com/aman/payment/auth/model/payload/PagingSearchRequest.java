package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class PagingSearchRequest implements AuthBasePayload<PagingSearchRequest> {

	
	
	
	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String searchBy;

	public PagingSearchRequest() {
	}

	public PagingSearchRequest(String pageNo, String pageSize, String sortBy, String searchBy) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.searchBy = searchBy;
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

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	@Override
	public PagingSearchRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {

		return new PagingSearchRequest(cryptoMngrAuthService.decrypt(pageNo),
				cryptoMngrAuthService.decrypt(pageSize), cryptoMngrAuthService.decrypt(sortBy),
				cryptoMngrAuthService.decrypt(searchBy));
	}

	
	 

}
