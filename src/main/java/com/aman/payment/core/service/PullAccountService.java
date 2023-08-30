package com.aman.payment.core.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.FinancialAuditDTO;
import com.aman.payment.core.model.payload.ApprovedClaimsSearchRequest;
import com.aman.payment.core.model.payload.ApprovedPullAccountRequest;
import com.aman.payment.core.model.payload.FinancialReportRequest;
import com.aman.payment.core.model.payload.GetPullAccountRequest;

public interface PullAccountService extends GenericService<PullAccount, Long> {
	
	public Optional<PullAccount> findByStatusFkAndCreatedByAndSettlementCodeAndServiceId(String status, 
			String username, String settlementCode, long serviceId);
	
	public Optional<PullAccount> findByStatusFkAndPosIdAndServiceId(String status, long posId, long serviceId);
	
	public List<PullAccount> findByStatusFkAndCreatedByAndServiceId(String status, 
			String username, long serviceId);
	
	public Optional<PullAccount> findBySettlementCode(String settlementCode);
	
	public Optional<PullAccount> findBySettlementCodeAndServiceId(String settlementCode, long serviceId);
	
	public List<PullAccount> findByStatusFkAndServiceId(String status, long serviceId);
	
	public List<PullAccount> getNewOrRejectedPullAccountByUser(String username, String status1, String status2);
	
	public Page<PullAccount> findByStatusFkAndCreatedBy(ApprovedPullAccountRequest approvedPullAccountRequest,
			String statusFk, String createdBy);
	
	public String saveAttachedFile(MultipartFile attFile, String settlementCode, String attType, String filePath);
	
	public List<PullAccount> findByStatusFkNotAndPosIn(Set<Long> posIds, String status);
	
	public Page<PullAccount> findByStatusFkAndPosIn(ApprovedPullAccountRequest approvedPullAccountRequest,
			Set<Long> posIds, String status);
	
	public Page<PullAccount> findByApprovedBy(ApprovedPullAccountRequest approvedPullAccountRequest);
	
	public Page<PullAccount> getFinancialReviewReport(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportByDaily(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportByLocation(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportByCourt(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportBySector(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportByPOS(FinancialReportRequest financialReportRequest);
	
	public Page<FinancialAuditDTO> getAuditReportByAgentAndLocation(FinancialReportRequest financialReportRequest);
	
	public Page<PullAccount> findApprovedClaimsAdvancedSearch(ApprovedClaimsSearchRequest approvedClaimsSearchRequest);
	
	public List<FinancialAuditDTO> getReportPermissionDetails(FinancialReportRequest financialReportRequest);
	
	public Page<PullAccount> findAllApprovedRequests(ApprovedPullAccountRequest approvedPullAccountRequest);
	
	public List<PullAccount> findByStatusFk(String status);

	public Optional<PullAccount> getSettlementCode(GetPullAccountRequest decryptGetPullAccountRequest);

	public String updateSettlementStatus(GetPullAccountRequest decryptGetPullAccountRequest);

}