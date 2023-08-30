package com.aman.payment.core.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.FinancialDeficit;
import com.aman.payment.core.model.PullAccount;

@Repository
public interface FinancialDeficitRepository extends JpaRepository<FinancialDeficit, Long> {
	
	@Query("select u from FinancialDeficit u where u.createdBy = ?1 and (u.statusFk = ?2 or u.statusFk = ?3)")
	public List<FinancialDeficit> findByCreatedByAndStatusFkOrStatusFk(String createdBy, String statusFk1, String statusFk2);
	
	public Page<FinancialDeficit> findByCreatedByAndStatusFkOrderByCreatedAtDesc(String createdBy, String statusFk, Pageable paging);
	
	public List<FinancialDeficit> findByStatusFkNotAndPosIdInOrderByCreatedAtDesc(String statusFk,Set<Long> posSet);
	
	public Page<FinancialDeficit> findByStatusFkAndPosIdInOrderByCreatedAtDesc(String statusFk, Set<Long> posSet, Pageable paging);
	
	public FinancialDeficit findByPullAccountFk(PullAccount pullAccountFk);
	
	public Page<FinancialDeficit> findByApprovedByOrderByCreatedAtDesc(String approvedBy, Pageable paging);
}