package com.aman.payment.maazoun.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookReviewDTO;
import com.aman.payment.maazoun.model.dto.MaazounContractAuditDTO;
import com.aman.payment.maazoun.model.payload.AdvancedSearchCollectionInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;

public interface MaazounCollectionInfoService extends MaazounGenericService<MaazounBookCollectionInfo, Long> {
	
	public void updateRefRequestNumberById(String id, String refRequestNumber);
	
	public Page<MaazounBookCollectionInfo> findByMaazounNationalId(String nationalId, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByMaazounCardNumber(String status, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByMaazounMobile(String mobile, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByTransactionCode(String transactionCode, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByTransactionCodeAndPosFkIn(String transactionCode, Set<Pos> posSet, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByRefRequestNumber(String refRequestNumber, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByRefRequestNumberAndPosFkIn(String refRequestNumber, Set<Pos> posSet, Pageable pageable);
	
	public List<MaazounBookCollectionInfo> findByRefRequestNumber(String refRequestNumber);
	
	public Page<MaazounBookCollectionInfo> findByContractNumber(String contractNumber, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByContractNumberAndPosFkIn(String contractNumber, Set<Pos> posSet, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByBookSerialNumber(String bookSerialNumber, Pageable pageable);
	
	public List<MaazounBookCollectionInfo> findByBookSerialNumber(String bookSerialNumber, String statusFk);
	
	public Optional<MaazounBookCollectionInfo> findByContractNumber(String contractNumber, String statusFk);
	
	public List<MaazounBookCollectionInfo> findByContractNumberAndStatusFkOrStatusFk(String contractNumber, String statusFk, String statusFk2);
	
	public List<MaazounBookCollectionInfo> findByContractNumber(String contractNumber);
	
	public List<MaazounBookCollectionInfo> findByStatusFkOrStatusFkAndMaazounNationalId(String maazounNationalId, String statusFk, 
			String statusFk2);
	
	public List<MaazounBookCollectionInfo> findByStatusFkAndMaazounNationalId(String maazounNationalId, String statusFk);
	
	public Page<MaazounBookCollectionInfo> findByStatusFkAndLocationIdIn(String statusFk, Set<Long> locationIds, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> advancedSearch(AdvancedSearchCollectionInfoTransactionRequest obj, CustomUserDetails customUserDetails);
	
	public List<MaazounBookReviewDTO> getContractsUnderReview(String statusFk, Set<Long> locationIds);
	
	public Page<MaazounContractAuditDTO> getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndLocation(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndAgentAndLocation(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public void updateStatusByBookSerialNumber(String statusFk, String receivedBy, 
			 Date receivedAt, String bookSerialNumber, String receivedComment) throws Exception;
	
	public void editCollection(String receiptUrl, String refRequestNumber);
	
	public List<MaazounBookCollectionInfo> findByStatusFkOrStatusFkAndPosIds(String statusFk, String statusFk2, String statusFk3,  Set<Pos> posSet);
	
	public List<MaazounBookCollectionInfo> findByStatusFkAndPosIds(String statusFk, Set<Pos> posSet);
	
	public Page<MaazounBookAuditSectorDTO> getContractCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditSectorDTO> getContractCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookCollectionInfo> findByStatusFkAndPosIdsAndDate(String statusFk, Set<Pos> posSet, Date durationFrom, Date durationTo, Pageable pageable);

	public List<MaazounBookCollectionInfo>  findByMaazounNationalIdAndStatusFkOrStatusFkOrStatusFk(String maazounNationalId, String statusFk,String statusFk2, String  statusFk3);
	 
}