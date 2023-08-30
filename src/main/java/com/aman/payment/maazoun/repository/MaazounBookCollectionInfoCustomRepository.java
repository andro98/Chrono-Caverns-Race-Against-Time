package com.aman.payment.maazoun.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookReviewDTO;
import com.aman.payment.maazoun.model.dto.MaazounContractAuditDTO;
import com.aman.payment.maazoun.model.payload.AdvancedSearchCollectionInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;


@Repository
public interface MaazounBookCollectionInfoCustomRepository {

	public Page<MaazounBookCollectionInfo> findByMaazounNationalId(String nationalId, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByMaazounCardNumber(String cardNumber, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> findByMaazounMobile(String mobile, Pageable pageable);
	
	public Page<MaazounBookCollectionInfo> advancedSearch(
			AdvancedSearchCollectionInfoTransactionRequest obj, CustomUserDetails customUserDetails);
	
	public List<MaazounBookReviewDTO> getContractCollectedStatusFk(String statusFk, Set<Long> locationIds);
	
	public Page<MaazounContractAuditDTO> getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndLocation(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndAgentAndLocation(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditSectorDTO> getContractCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditSectorDTO> getContractCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
}