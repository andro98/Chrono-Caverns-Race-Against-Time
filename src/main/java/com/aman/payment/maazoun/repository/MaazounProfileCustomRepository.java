package com.aman.payment.maazoun.repository;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.dto.MaazounProfileAuditDTO;
import com.aman.payment.maazoun.model.payload.MaazounProfileAuditRequest;


@Repository
public interface MaazounProfileCustomRepository {
	
	public Page<MaazounProfileAuditDTO> getMaazounCustomAudit(MaazounProfileAuditRequest maazounProfileAuditRequest);
	
	public Page<MaazounProfileAuditDTO> getMaazounCustodyCustomAudit(MaazounProfileAuditRequest maazounProfileAuditRequest);
	
	public Page<MaazounProfileAuditDTO> getMaazounSerialsCustomAudit(MaazounProfileAuditRequest maazounProfileAuditRequest);
	
}