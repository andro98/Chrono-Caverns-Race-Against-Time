package com.aman.payment.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.dto.FinanacialMidsDTO;
import com.aman.payment.core.model.payload.FinancialReportRequest;


@Repository
public interface TransactionCustomRepository {
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByTransaction(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByPOS(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsBySector(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByLocation(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByCourt(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByCity(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByDaily(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsByDaily(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByAgent(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByTypeName(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsByTransaction(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getCanceledAuditReportFinancialMidDetailsByTransaction(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsBySector(FinancialReportRequest financialReportRequest);
}