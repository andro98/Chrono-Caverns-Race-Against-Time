package com.aman.payment.core.model.payload;

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.core.service.CryptoMngrCoreService;


public class ApprovedPullAccountRequest implements CoreBasePayload<ApprovedPullAccountRequest> {

	private String pageNo;
	private String pageSize;
	private String sortBy;
	private String username;
	private Set<String> posIds;

	public ApprovedPullAccountRequest() {
	}

	public ApprovedPullAccountRequest(String pageNo, String pageSize,
			String sortBy, String username, Set<String> PosIds) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.sortBy = sortBy;
		this.username = username;
		this.posIds = PosIds;
	}

	
	
	public Set<String> getPosIds() {
		return posIds;
	}

	public void setPosIds(Set<String> posIds) {
		this.posIds = posIds;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	@Override
	public ApprovedPullAccountRequest decrypt(CryptoMngrCoreService cryptoMngrCoreService) {
		Set<String> ePosIds = new HashSet<String>();
		if(posIds!=null) {
			posIds.stream().forEach(s -> {
				ePosIds.add(cryptoMngrCoreService.decrypt(s));
			});
		}
		
		return new ApprovedPullAccountRequest(cryptoMngrCoreService.decrypt(pageNo),
				cryptoMngrCoreService.decrypt(pageSize), cryptoMngrCoreService.decrypt(sortBy),
				cryptoMngrCoreService.decrypt(username), ePosIds);
	}

}
