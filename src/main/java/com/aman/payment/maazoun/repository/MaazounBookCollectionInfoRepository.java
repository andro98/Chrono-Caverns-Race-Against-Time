package com.aman.payment.maazoun.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;


@Repository
public interface MaazounBookCollectionInfoRepository extends PagingAndSortingRepository<MaazounBookCollectionInfo, Long>,
		JpaSpecificationExecutor<MaazounBookCollectionInfo>, MaazounBookCollectionInfoCustomRepository {

	
	@Modifying
	@Query("update MaazounBookCollectionInfo u set u.refRequestNumber = :refRequestNumber where u.id = :id")
	public void updateRefRequestNumberById(@Param("refRequestNumber") String refRequestNumber, @Param("id") long id);
	
	@Modifying
	@Query("update MaazounBookCollectionInfo u set u.statusFk = :statusFk, u.receivedBy = :receivedBy, u.receivedAt = :receivedAt, u.receivedComment= :receivedComment where u.bookSerialNumber = :bookSerialNumber")
	public void updateStatusByBookSerialNumber(@Param("statusFk") String statusFk, @Param("receivedBy") String receivedBy, 
			@Param("receivedAt") Date receivedAt, @Param("bookSerialNumber") String bookSerialNumber, @Param("receivedComment") String receivedComment);
	
	@Modifying
	@Query("update MaazounBookCollectionInfo u set u.receiptUrl = :receiptUrl where u.refRequestNumber = :refRequestNumber")
	public void editCollection(@Param("receiptUrl") String receiptUrl, @Param("refRequestNumber") String refRequestNumber);
	
	public Page<MaazounBookCollectionInfo> findByTransactionCode(String transactionCode, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByTransactionCodeAndPosFkIn(String transactionCode, Set<Pos> posFk, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByRefRequestNumberOrderByIdDesc(String refRequestNumber, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByRefRequestNumberAndPosFkInOrderByIdDesc(String refRequestNumber, Set<Pos> posFk, Pageable pageable);
	
	public List<MaazounBookCollectionInfo> findByRefRequestNumber(String refRequestNumber);
	
	public Page<MaazounBookCollectionInfo> findByBookSerialNumberOrderByIdDesc(String bookSerialNumber, Pageable pageable);
	
	public List<MaazounBookCollectionInfo> findByBookSerialNumberAndStatusFkOrderByIdDesc(String bookSerialNumber, String statusFk);
	
	public Optional<MaazounBookCollectionInfo> findByContractNumberAndStatusFk(String contractNumber, String statusFk);
	
	public Page<MaazounBookCollectionInfo> findByContractNumber(String contractNumber, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByContractNumberAndPosFkIn(String contractNumber, Set<Pos> posFk, Pageable pageable);
	
	public List<MaazounBookCollectionInfo> findByContractNumber(String contractNumber);
	
	public List<MaazounBookCollectionInfo> findByStatusFkAndMaazounNationalId(String statusFk, String maazounNationalId);
	
	public Page<MaazounBookCollectionInfo> findByStatusFkAndLocationIdInOrderByIdDesc(String statusFk, Set<Long> locationIds, Pageable pageable);
	
	@Query("select u from MaazounBookCollectionInfo u where u.posFk IN:posSet and (u.statusFk = :statusFk or u.statusFk = :statusFk2 or u.statusFk = :statusFk3)")
	public List<MaazounBookCollectionInfo> findByStatusFkOrStatusFkAndPosFkInOrderByCreatedAtDesc(String statusFk, String statusFk2, String statusFk3, Set<Pos> posSet);
	
	@Query("select u from MaazounBookCollectionInfo u where u.statusFk = :statusFk and u.posFk IN:posSet")
	public List<MaazounBookCollectionInfo> findByStatusFkAndPosFkInOrderByCreatedAtDesc(String statusFk, Set<Pos> posSet);
	
	//@Query("select u from MaazounBookCollectionInfo u where u.contractNumber= ?1 and (u.statusFk = ?2 or u.statusFk = ?3)")
	public List<MaazounBookCollectionInfo> findByContractNumberAndStatusFkOrStatusFk(String contractNumber, String statusFk,
			String statusFk2);
	
	@Query("select u from MaazounBookCollectionInfo u where u.maazounNationalId= :maazounNationalId and (u.statusFk = :statusFk or u.statusFk = :statusFk2)")
	public List<MaazounBookCollectionInfo> findByMaazounNationalIdAndStatusFkOrStatusFk(String maazounNationalId, String statusFk, String statusFk2);
	
	@Query("select u from MaazounBookCollectionInfo u where u.createdAt >= :durationFrom and  u.createdAt <= :durationTo and u.statusFk = :statusFk and u.posFk IN:posSet")
	public Page<MaazounBookCollectionInfo> findByCreatedAtBeforeCreatedAtAfterStatusFkAndPosFkInOrderByCreatedAtDesc(String statusFk, Set<Pos> posSet , Date durationFrom, Date durationTo, Pageable pageable);
	 
	
	@Query("select u from MaazounBookCollectionInfo u where u.maazounNationalId= :maazounNationalId and (u.statusFk = :statusFk or u.statusFk = :statusFk2 or u.statusFk = :statusFk3)")
	public List<MaazounBookCollectionInfo> findByMaazounNationalIdAndStatusFkOrStatusFkOrStatusFk(String maazounNationalId, String statusFk, String statusFk2, String statusFk3);
	 
	
}