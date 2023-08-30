package com.aman.payment.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.PullAccount;

@Repository
public interface PullAccountRepository extends PagingAndSortingRepository<PullAccount, Long>, PullAccountCustomRepository{
	
	public Optional<PullAccount> findByStatusFkAndCreatedByAndSettlementCodeAndServiceId(
			String statusFk, String createdBy, String settlementCode, long serviceId);
	
	public List<PullAccount> findByStatusFkAndCreatedByAndServiceId(
			String statusFk, String createdBy, long serviceId);
	
	public Optional<PullAccount> findBySettlementCode(String settlementCode);
	
	public Optional<PullAccount> findBySettlementCodeAndServiceIdAndStatusFk(String settlementCode, long serviceId, String statusFk);
	
	@Query("select u from PullAccount u where u.createdBy = ?1 and (u.statusFk = ?2 or u.statusFk = ?3)")
	public List<PullAccount> findByCreatedByAndStatusFkOrStatusFkOrderByCreatedAtAsc(String createdBy, String statusFk, String statusFk2);
	
	public Page<PullAccount> findByStatusFkAndCreatedByOrderByCreatedAtDesc(String statusFk, String createdBy, Pageable pageable);
	
	public List<PullAccount> findByStatusFkNotAndPosIdInOrderByCreatedAtDesc(String statusFk,Set<Long> posSet);
	
	public Page<PullAccount> findByStatusFkAndPosIdInOrderByCreatedAtDesc(String statusFk, Set<Long> posSet, Pageable paging);

	public Page<PullAccount> findByCreatedAtAfterAndCreatedAtBeforeAndServiceId(Date createdAtFrom, Date createdAtTo, 
			long serviceId, Pageable pageable);
	
	public Page<PullAccount> findByCreatedAtAfterAndCreatedAtBefore(Date createdAtFrom, Date createdAtTo, 
			Pageable pageable);
	
	public List<PullAccount> findByStatusFkAndServiceId(String status, long serviceId);
	
	public Page<PullAccount> findByApprovedByAndPosIdInOrderByCreatedAtDesc(String approvedBy,Set<Long> ePosIds, Pageable pageable);
	
	public Page<PullAccount> findByStatusFk(String statusFk, Pageable paging);
	
	public List<PullAccount> findByStatusFk(String statusFk);

	public Optional<PullAccount> findFirstByStatusFkAndPosIdAndServiceIdOrderByCreatedAtDesc(String status, long posId, long serviceId);
	
	@Modifying
	@Query("update PullAccount u set u.statusFk= :statusFk where u.id = :id ")
	public void updateSettlementCode(@Param("statusFk") String statusFk,@Param("id") Long id);
	
}