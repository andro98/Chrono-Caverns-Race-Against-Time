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
import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.payload.SearchDeliveredInfoTransactionRequest;
import com.aman.payment.maazoun.repository.MaazounDeliverInfoRepository;
import com.aman.payment.maazoun.service.MaazounDeliverInfoService;

@Service
@Transactional
public class MaazounDeliverInfoServiceImpl implements MaazounDeliverInfoService {

	@Autowired
    private final MaazounDeliverInfoRepository repository;
	
	public MaazounDeliverInfoServiceImpl(MaazounDeliverInfoRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounBookDeliverInfo save(MaazounBookDeliverInfo entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounBookDeliverInfo> save(List<MaazounBookDeliverInfo> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounBookDeliverInfo>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounBookDeliverInfo> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounBookDeliverInfo> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounBookDeliverInfo> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounBookDeliverInfo>) repository.findAll();
	}

	@Override
	public Page<MaazounBookDeliverInfo> findAll(Pageable pageable) {
		Page<MaazounBookDeliverInfo> entityPage = repository.findAll(pageable);
        List<MaazounBookDeliverInfo> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounBookDeliverInfo update(MaazounBookDeliverInfo entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounBookDeliverInfo> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

	@Override
	public List<MaazounBookDeliverInfo> getDeliverRequests(String statusFk, Set<Long> locationIds) {
		// TODO Auto-generated method stub
		return repository.findByStatusFkAndLocationIdIn(statusFk, locationIds);
	}

	@Override
	public Page<MaazounBookDeliverInfo> searchDeliveredRequests(CustomUserDetails customUserDetails, 
			SearchDeliveredInfoTransactionRequest searchObj) {
		// TODO Auto-generated method stub
		return repository.searchDeliveredRequests(customUserDetails, searchObj);
	}

	@Override
	public List<MaazounBookDeliverInfo> findByRefRequestNumber(String refRequestNumber) {
		// TODO Auto-generated method stub
		return repository.findByRefRequestNumber(refRequestNumber);
	}
	
	

}
