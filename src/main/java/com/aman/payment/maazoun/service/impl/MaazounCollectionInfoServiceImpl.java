package com.aman.payment.maazoun.service.impl;

import java.util.Date;
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
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.dto.MaazounBookAuditSectorDTO;
import com.aman.payment.maazoun.model.dto.MaazounBookReviewDTO;
import com.aman.payment.maazoun.model.dto.MaazounContractAuditDTO;
import com.aman.payment.maazoun.model.payload.AdvancedSearchCollectionInfoTransactionRequest;
import com.aman.payment.maazoun.model.payload.MaazounAuditRequest;
import com.aman.payment.maazoun.repository.MaazounBookCollectionInfoRepository;
import com.aman.payment.maazoun.service.MaazounCollectionInfoService;

@Service
@Transactional
public class MaazounCollectionInfoServiceImpl implements MaazounCollectionInfoService {

	@Autowired
    private final MaazounBookCollectionInfoRepository repository;
	
	public MaazounCollectionInfoServiceImpl(MaazounBookCollectionInfoRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounBookCollectionInfo save(MaazounBookCollectionInfo entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounBookCollectionInfo> save(List<MaazounBookCollectionInfo> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounBookCollectionInfo>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounBookCollectionInfo> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounBookCollectionInfo> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounBookCollectionInfo> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounBookCollectionInfo>) repository.findAll();
	}

	@Override
	public Page<MaazounBookCollectionInfo> findAll(Pageable pageable) {
		Page<MaazounBookCollectionInfo> entityPage = repository.findAll(pageable);
        List<MaazounBookCollectionInfo> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounBookCollectionInfo update(MaazounBookCollectionInfo entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounBookCollectionInfo> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

	@Override
	public void updateRefRequestNumberById(String id, String refRequestNumber) {
		// TODO Auto-generated method stub
		repository.updateRefRequestNumberById(refRequestNumber, Long.valueOf(id));
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByMaazounNationalId(String nationalId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByMaazounCardNumber(String status, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByMaazounMobile(String mobile, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByTransactionCode(String transactionCode, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByTransactionCode(transactionCode, pageable);
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByRefRequestNumber(String refRequestNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByRefRequestNumberOrderByIdDesc(refRequestNumber, pageable);
	}
	
	@Override
	public List<MaazounBookCollectionInfo> findByRefRequestNumber(String refRequestNumber) {
		// TODO Auto-generated method stub
		return repository.findByRefRequestNumber(refRequestNumber);
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByBookSerialNumber(String bookSerialNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByBookSerialNumberOrderByIdDesc(bookSerialNumber, pageable);
	}

	@Override
	public Page<MaazounBookCollectionInfo> advancedSearch(AdvancedSearchCollectionInfoTransactionRequest obj, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.advancedSearch(obj, customUserDetails);
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByStatusFkAndLocationIdIn(String statusFk, Set<Long> locationIds, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByStatusFkAndLocationIdInOrderByIdDesc(statusFk, locationIds, pageable);
	}

	@Override
	public List<MaazounBookReviewDTO> getContractsUnderReview(String statusFk, Set<Long> locationIds) {
		// TODO Auto-generated method stub
		return repository.getContractCollectedStatusFk(statusFk, locationIds);
	}

	@Override
	public List<MaazounBookCollectionInfo> findByBookSerialNumber(String bookSerialNumber, String statusFk) {
		// TODO Auto-generated method stub
		return repository.findByBookSerialNumberAndStatusFkOrderByIdDesc(bookSerialNumber, statusFk);
	}

	@Override
	public Optional<MaazounBookCollectionInfo> findByContractNumber(String contractNumber, String statusFk) {
		// TODO Auto-generated method stub
		return repository.findByContractNumberAndStatusFk(contractNumber, statusFk);
	}

	@Override
	public Page<MaazounContractAuditDTO> getAuditByCreatedDate(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getAuditByCreatedDate(maazounAuditRequest, customUserDetails);
	}

	@Override
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndLocation(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getAuditByCreatedDateAndLocation(maazounAuditRequest, customUserDetails);
	}
	
	@Override
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndCourt(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getAuditByCreatedDateAndCourt(maazounAuditRequest, customUserDetails);
	}

	@Override
	public Page<MaazounContractAuditDTO> getAuditByCreatedDateAndAgentAndLocation(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getAuditByCreatedDateAndAgentAndLocation(maazounAuditRequest, customUserDetails);
	}

	@Override
	public void updateStatusByBookSerialNumber(String statusFk, String receivedBy, Date receivedAt,
			String bookSerialNumber, String receivedComment) {
		repository.updateStatusByBookSerialNumber(statusFk, receivedBy, receivedAt, bookSerialNumber,receivedComment);
		
	}

	@Override
	public List<MaazounBookCollectionInfo> findByStatusFkOrStatusFkAndPosIds(String statusFk, String statusFk2, String statusFk3, Set<Pos> posSet) {
		// TODO Auto-generated method stub
		return repository.findByStatusFkOrStatusFkAndPosFkInOrderByCreatedAtDesc(statusFk, statusFk2, statusFk3, posSet);
	}
	
	@Override
	public List<MaazounBookCollectionInfo> findByStatusFkAndPosIds(String statusFk, Set<Pos> posSet) {
		// TODO Auto-generated method stub
		return repository.findByStatusFkAndPosFkInOrderByCreatedAtDesc(statusFk, posSet);
	}

	@Override
	public Page<MaazounBookAuditSectorDTO> getContractCustomAuditBySectors(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getContractCustomAuditBySectors(maazounAuditRequest, customUserDetails);
	}
	
	@Override
	public Page<MaazounBookAuditSectorDTO> getContractCustomAuditByCourts(MaazounAuditRequest maazounAuditRequest, CustomUserDetails customUserDetails) {
		// TODO Auto-generated method stub
		return repository.getContractCustomAuditByCourts(maazounAuditRequest, customUserDetails);
	}

	@Override
	public List<MaazounBookCollectionInfo> findByContractNumberAndStatusFkOrStatusFk(String statusFk,
			String statusFk2, String contractNumber) {
		// TODO Auto-generated method stub
		return repository.findByContractNumberAndStatusFkOrStatusFk(contractNumber,statusFk, statusFk2);
	}

	@Override
	public List<MaazounBookCollectionInfo> findByStatusFkOrStatusFkAndMaazounNationalId(String maazounNationalId,String statusFk,
			String statusFk2) {
 		return repository.findByMaazounNationalIdAndStatusFkOrStatusFk(maazounNationalId, statusFk, statusFk2);
	}
	
	@Override
	public List<MaazounBookCollectionInfo> findByStatusFkAndMaazounNationalId(String maazounNationalId,String statusFk) {
 		return repository.findByStatusFkAndMaazounNationalId(statusFk, maazounNationalId);
	}

	@Override
	public List<MaazounBookCollectionInfo> findByContractNumber(String contractNumber) {
		// TODO Auto-generated method stub
		return repository.findByContractNumber(contractNumber);
	}

	@Override
	public void editCollection(String receiptUrl, String refRequestNumber) {
		// TODO Auto-generated method stub
		repository.editCollection(receiptUrl, refRequestNumber);
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByStatusFkAndPosIdsAndDate(String statusFk, Set<Pos> posSet, Date durationFrom, Date durationTo, Pageable pageable) {
		
		return repository.findByCreatedAtBeforeCreatedAtAfterStatusFkAndPosFkInOrderByCreatedAtDesc(statusFk, posSet, durationFrom, durationTo, pageable);
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByTransactionCodeAndPosFkIn(String transactionCode, Set<Pos> posSet,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByTransactionCodeAndPosFkIn(transactionCode, posSet, pageable);
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByRefRequestNumberAndPosFkIn(String refRequestNumber, Set<Pos> posSet,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByRefRequestNumberAndPosFkInOrderByIdDesc(refRequestNumber, posSet, pageable);
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByContractNumber(String contractNumber, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByContractNumber(contractNumber, pageable);
	}

	@Override
	public Page<MaazounBookCollectionInfo> findByContractNumberAndPosFkIn(String contractNumber, Set<Pos> posSet,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findByContractNumberAndPosFkIn(contractNumber, posSet, pageable);
	}

	@Override
	public List<MaazounBookCollectionInfo> findByMaazounNationalIdAndStatusFkOrStatusFkOrStatusFk(String maazounNationalId, String statusFk,String statusFk2, String  statusFk3) {
		 
		return repository.findByMaazounNationalIdAndStatusFkOrStatusFkOrStatusFk(maazounNationalId, statusFk, statusFk2, statusFk3);
	}
 
}
