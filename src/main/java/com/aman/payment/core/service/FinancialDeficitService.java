package com.aman.payment.core.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.model.FinancialDeficit;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.payload.ApprovedFinancialDeficitRequest;

public interface FinancialDeficitService extends GenericService<FinancialDeficit, Long> {
	
	public List<FinancialDeficit> findByCreatedByAndStatusFk(String username, String status1, String status2);
	
	public Page<FinancialDeficit> findByStatusFkAndCreatedBy(ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest,
			String statusFk, String createdBy);
	
	public String saveAttachedFile(MultipartFile attFile, String settlementCode, String filePath);
	
	public List<FinancialDeficit> findByStatusFkNotAndPosIn(Set<Long> posIds, String status);
	
	public Page<FinancialDeficit> findByStatusFkAndPosIn(ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest,
			Set<Long> posIds, String status);
	
	public FinancialDeficit findByPullAccountFk(PullAccount pullAccountFk);
	
	public Page<FinancialDeficit> findByApprovedBy(ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest);
}