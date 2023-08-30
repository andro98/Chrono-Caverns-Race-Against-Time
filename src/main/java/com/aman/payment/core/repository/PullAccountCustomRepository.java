package com.aman.payment.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.FinancialAuditDTO;
import com.aman.payment.core.model.payload.ApprovedClaimsSearchRequest;
import com.aman.payment.core.model.payload.FinancialReportRequest;


@Repository
public interface PullAccountCustomRepository {
	
	public Page<FinancialAuditDTO> getAuditReportByDaily(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportByLocation(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportByCourt(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportBySector(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportByPOS(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportByAgentAndLocation(FinancialReportRequest financialReportRequest);
	
	public Page<PullAccount> findApprovedClaimsAdvancedSearch(ApprovedClaimsSearchRequest approvedClaimsSearchRequest);
	
	public Page<PullAccount> getFinancialReviewReport(FinancialReportRequest financialReportRequest);
	
	public List<FinancialAuditDTO> getReportPermissionDetails(FinancialReportRequest financialReportRequest);
}