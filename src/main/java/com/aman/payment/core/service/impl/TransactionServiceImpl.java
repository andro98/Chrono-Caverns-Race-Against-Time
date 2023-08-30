package com.aman.payment.core.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.Transaction;
import com.aman.payment.core.model.dto.FinanacialMidsDTO;
import com.aman.payment.core.model.dto.TransactionDTO;
import com.aman.payment.core.model.payload.FinancialReportRequest;
import com.aman.payment.core.model.payload.TransactionDurationRequest;
import com.aman.payment.core.repository.TransactionRepository;
import com.aman.payment.core.service.TransactionService;
import com.aman.payment.core.util.UtilCore;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepository repository;

//	@Autowired
//	public TransactionServiceImpl(TransactionRepository repository) {
//		this.repository = repository;
//	}

	@Override
	public Transaction save(Transaction entity) {
		return repository.save(entity);
	}

	@Override
	public List<Transaction> save(List<Transaction> entities) {
		return (List<Transaction>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<Transaction> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Transaction> findAll() {
		return (List<Transaction>) repository.findAll();
	}

	@Override
	public Page<Transaction> findAll(Pageable pageable) {
		Page<Transaction> entityPage = repository.findAll(pageable);
		List<Transaction> entities = entityPage.getContent();
		return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public Transaction update(Transaction entity, Long id) {
		Optional<Transaction> optional = findById(id);
		if (optional.isPresent()) {
			return save(entity);
		}
		return null;
	}

	@Override
	public Page<Transaction> findAll(String statusFk, Pageable pageable) {
		Page<Transaction> entityPage = repository.findByStatusFk(statusFk, pageable);
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	@Override
	public List<Transaction> findAll(String status) throws Exception {
		return repository.findByStatusFk(status);
	}

	@Override
	public void deleteAll(List<Transaction> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public Optional<Transaction> findByTransCode(String transCode) {
		return repository.findByTransCode(transCode);
	}

	@Override
	public Page<Transaction> findByDuration(TransactionDurationRequest transactionDurationRequest) {
		
		Pageable pageable = PageRequest.of(Integer.valueOf(transactionDurationRequest.getPageNo()), 
				Integer.valueOf(transactionDurationRequest.getPageSize()));
		
		Page<Transaction> entityPage = repository.findByCreatedAtAfterAndCreatedAtBefore(
				Date.valueOf(transactionDurationRequest.getDurationFrom()), 
				Date.valueOf(transactionDurationRequest.getDurationTo()),
				pageable);
		
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}
	
	@Override
	public String saveAttachedFile(MultipartFile attFile, String settlementCode, String filePath) {
		String targetPath = null;
		
		if(attFile != null && !attFile.getOriginalFilename().equals("foo.txt")) {
			String attUrl = filePath+"/"+UtilCore.saveFolderNamingFormat();
			String targetFileName = "Refund_"+settlementCode+"_"+attFile.getOriginalFilename();
			
			targetPath = attUrl+"/"+targetFileName;
			
			try {
				Files.createDirectories(Paths.get(attUrl));
				Files.copy(attFile.getInputStream(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return targetPath;
	}

	@Override
	public Set<TransactionDTO> findTransactionByPosId(long posId) {
		return repository.findByPosId(posId);
	}

	@Override
	public Set<Transaction> findTransactionByPullAccountFk(PullAccount pullAccountFk) {
		return repository.findByPullAccountFk(pullAccountFk);
	}

	@Override
	public void deleteTransaction(String transactionCode) {
		repository.deleteByTransCode(transactionCode);
	}

	@Override
	public Page<Transaction> getAuditReportFinancialDetails(FinancialReportRequest financialReportRequest, String status) {
		Pageable pageable = PageRequest.of(Integer.valueOf(financialReportRequest.getPageNo()), 
				Integer.valueOf(financialReportRequest.getPageSize()));
		
		Page<Transaction> entityPage = repository.findByServiceIdAndStatusFkAndCreatedAtAfterAndCreatedAtBefore(
				Long.valueOf(financialReportRequest.getServiceId()), status,
				Date.valueOf(financialReportRequest.getDurationFrom()), 
				Date.valueOf(financialReportRequest.getDurationTo()),
				pageable);
		
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByTransaction(
			FinancialReportRequest financialReportRequest) {
		return repository.getAuditReportFinancialMidDetailsByTransaction(financialReportRequest);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsByTransaction(
			FinancialReportRequest financialReportRequest) {
		return repository.getRefundAuditReportFinancialMidDetailsByTransaction(financialReportRequest);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getCanceledAuditReportFinancialMidDetailsByTransaction(
			FinancialReportRequest financialReportRequest) {
		return repository.getCanceledAuditReportFinancialMidDetailsByTransaction(financialReportRequest);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByPOS(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getAuditReportFinancialMidDetailsByPOS(financialReportRequest);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsBySector(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getAuditReportFinancialMidDetailsBySector(financialReportRequest);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsBySector(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getRefundAuditReportFinancialMidDetailsBySector(financialReportRequest);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByLocation(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getAuditReportFinancialMidDetailsByLocation(financialReportRequest);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByCourt(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getAuditReportFinancialMidDetailsByCourt(financialReportRequest);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByCity(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getAuditReportFinancialMidDetailsByCity(financialReportRequest);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByDaily(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getAuditReportFinancialMidDetailsByDaily(financialReportRequest);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getRefundAuditReportFinancialMidDetailsByDaily(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getRefundAuditReportFinancialMidDetailsByDaily(financialReportRequest);
	}

	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByAgent(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getAuditReportFinancialMidDetailsByAgent(financialReportRequest);
	}
	
	@Override
	public Page<FinanacialMidsDTO> getAuditReportFinancialMidDetailsByTypeName(
			FinancialReportRequest financialReportRequest) {
		// TODO Auto-generated method stub
		return repository.getAuditReportFinancialMidDetailsByTypeName(financialReportRequest);
	}

}