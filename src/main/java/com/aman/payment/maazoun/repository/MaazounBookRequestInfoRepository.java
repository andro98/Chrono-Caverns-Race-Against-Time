package com.aman.payment.maazoun.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;


@Repository
public interface MaazounBookRequestInfoRepository extends PagingAndSortingRepository<MaazounBookRequestInfo, Long>,
	JpaSpecificationExecutor<MaazounBookRequestInfo>, MaazounBookRequestInfoCustomRepository {

	public Page<MaazounBookRequestInfo> findByTransactionCodeAndStatusFkNot(String transactionCode, String statusFk, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByTransactionCodeAndPosFkInAndStatusFkNot(String transactionCode, Set<Pos> posFk,
			String statusFk, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByRefRequestNumberAndStatusFkNotOrderByIdDesc(String refRequestNumber, String statusFk, 
			Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByRefRequestNumberAndPosFkInAndStatusFkNotOrderByIdDesc(String refRequestNumber, Set<Pos> posFk, 
			String statusFk, Pageable pageable);
	
	public List<MaazounBookRequestInfo> findByRefRequestNumber(String refRequestNumber);
	
	public Page<MaazounBookRequestInfo> findByBookSerialNumberAndStatusFkNot(String bookSerialNumber, String statusFk, 
			Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByBookSerialNumberAndPosFkInAndStatusFkNot(String bookSerialNumber, Set<Pos> posFk, 
			String statusFk, Pageable pageable);
	
}