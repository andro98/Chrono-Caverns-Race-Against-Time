package com.aman.payment.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.Transaction;
import com.aman.payment.core.model.dto.FinanacialMidsDTO;
import com.aman.payment.core.model.dto.TransactionDTO;
import com.aman.payment.core.model.payload.FinancialReportRequest;

@Repository
public interface TransactionRepository
		extends PagingAndSortingRepository<Transaction, Long>, TransactionCustomRepository{

	public Page<Transaction> findByStatusFk(String statusFk, Pageable pageable);

	public List<Transaction> findByStatusFk(String statusFk);
	
	public List<Transaction> findByStatusFkOrStatusFkAndCreatedAtAfter(String statusFk1, 
			String statusFk2, Date createdAt);
	
	public Optional<Transaction> findByTransCode(String transCode);
	
	public Page<Transaction> findByCreatedAtAfterAndCreatedAtBefore(Date createdAtFrom, Date createdAtTo, 
			Pageable pageable);
	
	public Set<TransactionDTO> findByPosId(long posId);
	
	public Set<Transaction> findByPullAccountFk(PullAccount pullAccountFk);
	
	public void deleteByTransCode(String transCode);
	
	public Page<Transaction> findByServiceIdAndStatusFkAndCreatedAtAfterAndCreatedAtBefore(Long serviceId, String status, 
			Date createdAtFrom, Date createdAtTo, Pageable pageable);
	
	
}