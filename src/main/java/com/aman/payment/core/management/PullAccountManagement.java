package com.aman.payment.core.management;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.PullAccountDTO;
import com.aman.payment.core.model.dto.PullAccountPagingDTO;
import com.aman.payment.core.model.payload.ApprovedPullAccountRequest;
import com.aman.payment.core.model.payload.CloseDayPullAccountRequest;
import com.aman.payment.core.model.payload.GetPullAccountRequest;
import com.aman.payment.core.model.payload.OpeningPullAccountRequest;
import com.aman.payment.core.model.payload.PullAccountByUserRequest;
import com.aman.payment.core.model.payload.URLRequest;

public interface PullAccountManagement {

	public Optional<PullAccount> validatePullAccount(String createdBy, String settlementCode, long serviceId);
	
	public Optional<PullAccount> validatePullAccount(String settlementCode, long serviceId);
	
	public PullAccount savePullAccount(PullAccount pullAccount);
	
	public List<PullAccountDTO> getNewOrRejectedPullAccount(PullAccountByUserRequest pullAccountByUserRequest);
	
	public PullAccountDTO closeDayPullAccount(CloseDayPullAccountRequest closeDayPullAccountRequest);
	
	public InputStream downloadPullAccountAttFile(URLRequest uRLRequest);
	
	public PullAccountPagingDTO approvedPullAccountByUser(ApprovedPullAccountRequest approvedPullAccountRequest);
	
	public List<String> openingPullAccountByUser(OpeningPullAccountRequest openingPullAccountRequest);
	
	public List<String> openingPullAccount();

	public String getSettlementCode(GetPullAccountRequest decryptGetPullAccountRequest);
	
	public String updateSettlementCode(GetPullAccountRequest decryptGetPullAccountRequest);

}
