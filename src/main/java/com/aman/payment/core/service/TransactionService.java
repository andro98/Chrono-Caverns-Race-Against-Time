package com.aman.payment.core.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.Transaction;
import com.aman.payment.core.model.dto.FinanacialMidsDTO;
import com.aman.payment.core.model.dto.TransactionDTO;
import com.aman.payment.core.model.payload.FinancialReportRequest;
import com.aman.payment.core.model.payload.TransactionDurationRequest;

public interface TransactionService extends GenericService<Transaction, Long> {

	public Page<Transaction> findAll(String status, Pageable pageable) throws Exception;

	public List<Transaction> findAll(String status) throws Exception;

	public Optional<Transaction> findByTransCode(String transCode);

	public Page<Transaction> findByDuration(TransactionDurationRequest transactionDurationRequest);
	
	public String saveAttachedFile(MultipartFile attFile, String settlementCode, String filePath);
	
	public Set<TransactionDTO> findTransactionByPosId(long posId);
	
	public Set<Transaction> findTransactionByPullAccountFk(PullAccount pullAccountFk);
	
	public void deleteTransaction(String transactionCode);
	
	public Page<Transaction> getAuditReportFinancialDetails(FinancialReportRequest financialReportRequest, String status);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByTransaction(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsByTransaction(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getCanceledAuditReportFinancialMidDetailsByTransaction(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByPOS(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsBySector(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsBySector(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByLocation(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByCourt(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByCity(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByDaily(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsByDaily(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByAgent(FinancialReportRequest financialReportRequest);
	
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByTypeName(FinancialReportRequest financialReportRequest);

}