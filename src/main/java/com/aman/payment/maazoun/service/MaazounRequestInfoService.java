package com.aman.payment.maazoun.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAvailableDTO;
import com.aman.payment.maazoun.model.payload.AdvancedSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoByMaazounId;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;

public interface MaazounRequestInfoService extends MaazounGenericService<MaazounBookRequestInfo, Long> {

	public Page<MaazounBookRequestInfo> findByMaazounNationalIdAndStatusFkNot(String nationalId, String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByMaazounCardNumberAndStatusFkNot(String cardNumber, String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByMaazounMobileAndStatusFkNot(String mobile, String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByTransactionCodeAndStatusFkNot(String transactionCode, String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByTransactionCodeAndPosFkInAndStatusFkNot(String transactionCode, Set<Pos> posSet,
			String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByRefRequestNumberAndStatusFkNot(String refRequestNumber, String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByRefRequestNumberAndPosFkInAndStatusFkNot(String refRequestNumber, Set<Pos> posSet,
			String status, Pageable pageable);
	
	public List<MaazounBookRequestInfo> findByRefRequestNumber(String refRequestNumber);
	
	public Page<MaazounBookRequestInfo> findByBookSerialNumberAndStatusFkNot(String bookSerialNumber, String status, Pageable pageable);
	
	public Page<MaazounBookRequestInfo> findByBookSerialNumberAndPosFkInAndStatusFkNot(String bookSerialNumber, Set<Pos> posSet, 
			String status, Pageable pageable);
	
	
	public Page<MaazounBookRequestInfo> advancedSearch(AdvancedSearchBookRequestInfoTransactionRequest advancedSearchBookRequestInfoTransactionRequest,
			CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditDTO> getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditDTO> getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditDTO> getAuditByCreatedDateAndAgentAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditSectorDTO> getBookCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAuditSectorDTO> getBookCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails);
	
	public Page<MaazounBookAvailableDTO> getBookRequestInfoByMaazounId(GetBookRequestInfoByMaazounId getBookRequestInfoByMaazounId);
}