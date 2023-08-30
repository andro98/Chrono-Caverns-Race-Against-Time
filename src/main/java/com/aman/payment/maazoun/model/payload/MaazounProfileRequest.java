package com.aman.payment.maazoun.model.payload;

import com.aman.payment.maazoun.service.CryptoMngrMaazounService;

public class MaazounProfileRequest implements MaazounBasePayload<MaazounProfileRequest> {

	private String pageNo;
	private String pageSize;
	private String sortBy;

	public MaazounProfileRequest() {
	}

	public MaazounProfileRequest(String pageNo, String pageSize,
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
	public MaazounProfileRequest decrypt(CryptoMngrMaazounService cryptoMngrMaazounService) {

		return new MaazounProfileRequest(cryptoMngrMaazounService.decrypt(pageNo),
				cryptoMngrMaazounService.decrypt(pageSize), cryptoMngrMaazounService.decrypt(sortBy));
	}

}
