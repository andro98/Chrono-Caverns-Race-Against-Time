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

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookAvailableDTO;
import com.aman.payment.maazoun.model.payload.AdvancedSearchBookRequestInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.GetBookRequestInfoByMaazounId;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;
import com.aman.payment.maazoun.repository.MaazounBookRequestInfoRepository;
import com.aman.payment.maazoun.service.MaazounRequestInfoService;

@Service
@Transactional
public class MaazounRequestInfoServiceImpl implements MaazounRequestInfoService {

	@Autowired
    private final MaazounBookRequestInfoRepository repository;
	
	public MaazounRequestInfoServiceImpl(MaazounBookRequestInfoRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounBookRequestInfo save(MaazounBookRequestInfo entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounBookRequestInfo> save(List<MaazounBookRequestInfo> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounBookRequestInfo>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounBookRequestInfo> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounBookRequestInfo> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounBookRequestInfo> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounBookRequestInfo>) repository.findAll();
	}

	@Override
	public Page<MaazounBookRequestInfo> findAll(Pageable pageable) {
		Page<MaazounBookRequestInfo> entityPage = repository.findAll(pageable);
        List<MaazounBookRequestInfo> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounBookRequestInfo update(MaazounBookRequestInfo entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounBookRequestInfo> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

	@Override
	public Page<MaazounBookRequestInfo> findByMaazounNationalIdAndStatusFkNot(String nationalId, String status, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByMaazounNationalIdAndStatusFkNot(nationalId, status, pageable);
	}

	@Override
	public Page<MaazounBookRequestInfo> findByMaazounCardNumberAndStatusFkNot(String cardNumber, String status, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByMaazounCardNumberAndStatusFkNot(cardNumber, status, pageable);
	}

	@Override
	public Page<MaazounBookRequestInfo> findByMaazounMobileAndStatusFkNot(String mobile, String status, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByMaazounMobileAndStatusFkNot(mobile, status, pageable);
	}

	@Override
	public Page<MaazounBookRequestInfo> findByTransactionCodeAndStatusFkNot(String transactionCode, String status, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByTransactionCodeAndStatusFkNot(transactionCode, status, pageable);
	}

	@Override
	public Page<MaazounBookRequestInfo> advancedSearch(
			AdvancedSearchBookRequestInfoTransactionRequest advancedSearchBookRequestInfoTransactionRequest,
			CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.advancedSearch(advancedSearchBookRequestInfoTransactionRequest, customUserDetails);
	}

	@Override
	public Page<MaazounBookRequestInfo> findByRefRequestNumberAndStatusFkNot(String refRequestNumber, String status,
			Pageable pageable) {
		return repository.findByRefRequestNumberAndStatusFkNotOrderByIdDesc(refRequestNumber, status, pageable);
	}
	
	@Override
	public List<MaazounBookRequestInfo> findByRefRequestNumber(String refRequestNumber) {
		return repository.findByRefRequestNumber(refRequestNumber);
	}

	@Override
	public Page<MaazounBookRequestInfo> findByBookSerialNumberAndPosFkInAndStatusFkNot(String bookSerialNumber, Set<Pos> posSet, String status,
			Pageable pageable) {
		return repository.findByBookSerialNumberAndPosFkInAndStatusFkNot(bookSerialNumber, posSet, status, pageable);
	}
	
	@Override
	public Page<MaazounBookRequestInfo> findByBookSerialNumberAndStatusFkNot(String bookSerialNumber, String status,
			Pageable pageable) {
		return repository.findByBookSerialNumberAndStatusFkNot(bookSerialNumber, status, pageable);
	}

	@Override
	public Page<MaazounBookAuditDTO> getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getAuditByCreatedDate(maazounAuditRequest, customUserDetails);
	}

	@Override
	public Page<MaazounBookAuditDTO> getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getAuditByCreatedDateAndCourt(maazounAuditRequest, customUserDetails);
	}

	@Override
	public Page<MaazounBookAuditDTO> getAuditByCreatedDateAndAgentAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getAuditByCreatedDateAndAgentAndCourt(maazounAuditRequest, customUserDetails);
	}

	@Override
	public Page<MaazounBookAuditSectorDTO> getBookCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getBookCustomAuditBySectors(maazounAuditRequest, customUserDetails);
	}
	
	@Override
	public Page<MaazounBookAuditSectorDTO> getBookCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getBookCustomAuditByCourts(maazounAuditRequest, customUserDetails);
	}

	@Override
	public Page<MaazounBookAvailableDTO> getBookRequestInfoByMaazounId(
			GetBookRequestInfoByMaazounId getBookRequestInfoByMaazounId) {
		// TODO Auto-generated method stub
		return repository.getBookRequestInfoByMaazounId(getBookRequestInfoByMaazounId);
	}

	@Override
	public Page<MaazounBookRequestInfo> findByTransactionCodeAndPosFkInAndStatusFkNot(String transactionCode,
			Set<Pos> posSet, String status, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByTransactionCodeAndPosFkInAndStatusFkNot(transactionCode, posSet, status, pageable);
	}

	@Override
	public Page<MaazounBookRequestInfo> findByRefRequestNumberAndPosFkInAndStatusFkNot(String refRequestNumber,
			Set<Pos> posSet, String status, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByRefRequestNumberAndPosFkInAndStatusFkNotOrderByIdDesc(refRequestNumber, posSet, status, pageable);
	}

}
