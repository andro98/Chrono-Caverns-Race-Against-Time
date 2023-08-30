package com.aman.payment.maazoun.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Sector;
import com.aman.payment.maazoun.model.MaazounProfile;
import com.aman.payment.maazoun.model.dto.MaazounProfileAuditDTO;
import com.aman.payment.maazoun.model.payload.MaazounProfileAuditRequest;
import com.aman.payment.maazoun.model.payload.SearchmaazounProfileRequest;
import com.aman.payment.maazoun.repository.MaazounProfileRepository;
import com.aman.payment.maazoun.service.MaazounProfileService;

@Service
@Transactional
public class MaazounProfileServiceImpl implements MaazounProfileService {

	@Autowired
    private final MaazounProfileRepository repository;
	
	public MaazounProfileServiceImpl(MaazounProfileRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounProfile save(MaazounProfile entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounProfile> save(List<MaazounProfile> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounProfile>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounProfile> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounProfile> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounProfile> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounProfile>) repository.findAll();
	}

	@Override
	public Page<MaazounProfile> findAll(Pageable pageable) {
		Page<MaazounProfile> entityPage = repository.findAll(pageable);
        List<MaazounProfile> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounProfile update(MaazounProfile entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounProfile> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

	@Override
	public MaazounProfile findByNationalId(String nationalId) {
		// TODO Auto-generated method stub
		return repository.findByNationalId(nationalId);
	}

	@Override
	public MaazounProfile findByMobile(String mobileNumber) {
		// TODO Auto-generated method stub
		return repository.findByMobile(mobileNumber);
	}

	@Override
	public MaazounProfile findByCardNumber(String cardNumber) {
		// TODO Auto-generated method stub
		return repository.findByCardNumber(cardNumber);
	}

	@Override
	public List<MaazounProfile> findAllByActive(Boolean active) {
		return repository.findAllByActiveTrue();
	}

	@Override
	public Page<MaazounProfileAuditDTO> getMaazounCustomAudit(MaazounProfileAuditRequest maazounProfileAuditRequest) {
		// TODO Auto-generated method stub
		if(maazounProfileAuditRequest.getCustody() != null && Boolean.valueOf(maazounProfileAuditRequest.getCustody())) {
			return repository.getMaazounCustodyCustomAudit(maazounProfileAuditRequest);
		}else if(maazounProfileAuditRequest.getSerials() != null && Boolean.valueOf(maazounProfileAuditRequest.getSerials())) {
			return repository.getMaazounSerialsCustomAudit(maazounProfileAuditRequest);
		}else {
			return repository.getMaazounCustomAudit(maazounProfileAuditRequest);
		}
		
	}

	@Override
	public Page<MaazounProfile> findBySectorsIn(Set<Sector> sectors, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findBySectorsIn(sectors, pageable);
	}

	@Override
	public List<MaazounProfile> findByNationalIdOrNameOrMobile(
			SearchmaazounProfileRequest searchmaazounProfileRequest) {
		// TODO Auto-generated method stub
		return repository.findByNationalIdContainingOrNameContainingOrMobileContaining(searchmaazounProfileRequest.getSearchKey(), searchmaazounProfileRequest.getSearchKey(), 
				searchmaazounProfileRequest.getSearchKey());
	}

	@Override
	public List<MaazounProfile> findBySectorsIn(Set<Sector> sectors) {
		// TODO Auto-generated method stub
		return repository.findBySectorsIn(sectors);

	}
	
	

}
