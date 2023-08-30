package com.aman.payment.maazoun.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.Sector;
import com.aman.payment.maazoun.model.MaazounProfile;
import com.aman.payment.maazoun.model.dto.MaazounProfileAuditDTO;
import com.aman.payment.maazoun.model.payload.MaazounProfileAuditRequest;
import com.aman.payment.maazoun.model.payload.SearchmaazounProfileRequest;

public interface MaazounProfileService extends MaazounGenericService<MaazounProfile, Long> {
	
	public MaazounProfile findByNationalId(String nationalId);
	
	public MaazounProfile findByMobile(String mobileNumber);
	
	public MaazounProfile findByCardNumber(String cardNumber);
	
	public List<MaazounProfile> findAllByActive(Boolean active);
	
	public Page<MaazounProfileAuditDTO> getMaazounCustomAudit(MaazounProfileAuditRequest maazounProfileAuditRequest);
	
	public Page<MaazounProfile> findBySectorsIn(Set<Sector> sectors, Pageable pageable);
	
	public List<MaazounProfile> findByNationalIdOrNameOrMobile(SearchmaazounProfileRequest SearchmaazounProfileRequest);
	 
	public List<MaazounProfile> findBySectorsIn(Set<Sector> sectors);


}