package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class TransactionDurationRequest implements CoreBasePayload<TransactionDurationRequest> {

	private String durationFrom;
	private String durationTo;
	private String pageNo;
	private String pageSize;
	private String sortBy;

	public TransactionDurationRequest() {
	}

	public TransactionDurationRequest(String durationFrom, String durationTo, String pageNo, String pageSize,
			String sortBy) {
		super();
		this.durationFrom = durationFrom;
		this.durationTo = durationTo;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
	}

	public String getDurationFrom() {
		return durationFrom;
	}

	public String getDurationTo() {
		return durationTo;
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

	public void setDurationFrom(String durationFrom) {
		this.durationFrom = durationFrom;
	}

	public void setDurationTo(String durationTo) {
		this.durationTo = durationTo;
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
	public TransactionDurationRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {

		return new TransactionDurationRequest(cryptoMngrCoreService.decrypt(durationFrom),
				cryptoMngrCoreService.decrypt(durationTo), cryptoMngrCoreService.decrypt(pageNo),
				cryptoMngrCoreService.decrypt(pageSize), cryptoMngrCoreService.decrypt(sortBy));
	}

}
