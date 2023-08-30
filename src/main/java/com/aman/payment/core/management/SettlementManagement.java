package com.aman.payment.core.management;

import java.io.InputStream;
import java.util.List;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.core.model.dto.FinancialDeficitDTO;
import com.aman.payment.core.model.dto.FinancialDeficitPagingDTO;
import com.aman.payment.core.model.dto.SettlementDTO;
import com.aman.payment.core.model.dto.SettlementPagingDTO;
import com.aman.payment.core.model.payload.ApprovedClaimsSearchRequest;
import com.aman.payment.core.model.payload.ApprovedFinancialDeficitRequest;
import com.aman.payment.core.model.payload.ApprovedPullAccountRequest;
import com.aman.payment.core.model.payload.ReviewFinancialDeficitRequest;
import com.aman.payment.core.model.payload.ReviewSettlementRequest;
import com.aman.payment.core.model.payload.SettlementByPosIdsRequest;
import com.aman.payment.core.model.payload.URLRequest;

public interface SettlementManagement {
	
	public List<FinancialDeficitDTO> getNotApprovedFinancialDeficit(
			SettlementByPosIdsRequest settlementByPosIdsRequest);
	
	public FinancialDeficitPagingDTO getApprovedFinancialDeficit(
			ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest);
	
	public InputStream downloadPullAccountAttFile(URLRequest uRLRequest);
	
	public FinancialDeficitDTO reviewFinancialDeficit(ReviewFinancialDeficitRequest reviewFinancialDeficitRequest);
	
	public List<SettlementDTO> getNotApprovedPullAccount(
			SettlementByPosIdsRequest settlementByPosIdsRequest);
	
	public SettlementPagingDTO getApprovedPullAccount(
			ApprovedPullAccountRequest approvedPullAccountRequest,CustomUserDetails customUserDetails);
	
	public SettlementDTO reviewSettlement(ReviewSettlementRequest reviewSettlementRequest);
	
	public SettlementPagingDTO getApprovedPullAccountSearch(ApprovedClaimsSearchRequest approvedClaimsSearchRequest);
}
