package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServiceQuota;
import com.aman.payment.auth.repository.SubServiceQuotaRepository;
import com.aman.payment.auth.service.SubServiceQuotaService;

@org.springframework.stereotype.Service
@Transactional
public class SubServiceQuotaServiceImpl implements SubServiceQuotaService {

	private final SubServiceQuotaRepository repository;
	
	public SubServiceQuotaServiceImpl(SubServiceQuotaRepository repository) {
		this.repository = repository;
	}

	@Override
	public SubServiceQuota save(SubServiceQuota entity) {
		return repository.save(entity);
	}

	@Override
    public List<SubServiceQuota> save(List<SubServiceQuota> entities) {
        return (List<SubServiceQuota>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "serviceCache",key = "#id", unless = "#result==null")
    public Optional<SubServiceQuota> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<SubServiceQuota> findAll() {
        return (List<SubServiceQuota>) repository.findAll();
    }

    @Override
    public Page<SubServiceQuota> findAll(Pageable pageable) {
        Page<SubServiceQuota> entityPage = repository.findAll(pageable);
        List<SubServiceQuota> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public SubServiceQuota update(SubServiceQuota entity, Long id) {
        Optional<SubServiceQuota> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<SubServiceQuota> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public List<SubServiceQuota> findBySubServiceFk(SubService subServiceFk) {
		// TODO Auto-generated method stub
		return repository.findBySubServiceFk(subServiceFk);
	}
	
    

}