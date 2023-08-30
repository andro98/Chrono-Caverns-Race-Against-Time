package com.aman.payment.maazoun.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAvailableDTO;
import com.aman.payment.maazoun.model.dto.MaazounRequestInfoPagingDTO;
import com.aman.payment.maazoun.model.payload.AdvancedSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoByMaazounId;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;


@Repository
public interface MaazounBookRequestInfoCustomRepository {

	public Page<MaazounBookRequestInfo> findByMaazounNationalIdAndStatusFkNot(String nationalId, String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByMaazounCardNumberAndStatusFkNot(String cardNumber, String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByMaazounMobileAndStatusFkNot(String mobile, String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> advancedSearch(
			AdvancedSearchBookRequestInfoTransactionRequest advancedSearchBookRequestInfoTransactionRequest,
			CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditDTO> getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditDTO> getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditDTO> getAuditByCreatedDateAndAgentAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditSectorDTO> getBookCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditSectorDTO> getBookCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAvailableDTO> getBookRequestInfoByMaazounId(GetBookRequestInfoByMaazounId getBookRequestInfoByMaazounId);
	
}