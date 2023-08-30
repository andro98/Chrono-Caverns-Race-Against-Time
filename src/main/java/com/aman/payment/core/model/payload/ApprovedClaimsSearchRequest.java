package com.aman.payment.core.model.payload;

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.core.service.CryptoMngrCoreService;

public class ApprovedClaimsSearchRequest implements CoreBasePayload<ApprovedClaimsSearchRequest> {

	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String username;
	private Set<String> posIds;
	private String durationFrom;
	private String durationTo;
	private String branshCode;
	private String posId;
	
	 
	
	public ApprovedClaimsSearchRequest() {
	}

	public ApprovedClaimsSearchRequest(String pageNo, String pageSize,
			String sortBy, String username, Set<String> PosIds, String durationFrom, String durationTo,
			String branshCode, String posId) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.username = username;
		this.posIds = PosIds;
		this.durationFrom = durationFrom;
		this.durationTo = durationTo;
		this.branshCode =branshCode;
		this.posId = posId ;
	}

	
	public String getPageNo() {
		return pageNo;
	}



	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}



	public String getPageSize() {
		return pageSize;
	}



	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}



	public String getSortBy() {
		return sortBy;
	}



	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public Set<String> getPosIds() {
		return posIds;
	}



	public void setPosIds(Set<String> posIds) {
		this.posIds = posIds;
	}



	public String getDurationFrom() {
		return durationFrom;
	}



	public void setDurationFrom(String durationFrom) {
		this.durationFrom = durationFrom;
	}



	public String getDurationTo() {
		return durationTo;
	}



	public void setDurationTo(String durationTo) {
		this.durationTo = durationTo;
	}



	public String getBranshCode() {
		return branshCode;
	}



	public void setBranshCode(String branshCode) {
		this.branshCode = branshCode;
	}



	public String getPosId() {
		return posId;
	}



	public void setPosId(String posId) {
		this.posId = posId;
	}



	@Override
	public ApprovedClaimsSearchRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		Set<String> ePosIds = new HashSet<String>();
		if(posIds!=null) {
			posIds.stream().forEach(s -> {
				ePosIds.add(cryptoMngrCoreService.decrypt(s));
			});
		}
		
		return new ApprovedClaimsSearchRequest(cryptoMngrCoreService.decrypt(pageNo),
				cryptoMngrCoreService.decrypt(pageSize), cryptoMngrCoreService.decrypt(sortBy),
				cryptoMngrCoreService.decrypt(username), ePosIds,
				cryptoMngrCoreService.decrypt(durationFrom),
				cryptoMngrCoreService.decrypt(durationTo),
				cryptoMngrCoreService.decrypt(branshCode),
				cryptoMngrCoreService.decrypt(posId)
				
				);
		 
	}

}
