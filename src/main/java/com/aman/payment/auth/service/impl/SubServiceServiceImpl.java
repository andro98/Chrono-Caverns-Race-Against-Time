package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.Service;
import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.repository.SubServiceRepository;
import com.aman.payment.auth.service.SubServiceService;

@org.springframework.stereotype.Service
@Transactional
public class SubServiceServiceImpl implements SubServiceService {
	
    private final SubServiceRepository repository;

    public SubServiceServiceImpl(SubServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public SubService save(SubService entity) {
        return repository.save(entity);
    }

    @Override
    public List<SubService> save(List<SubService> entities) {
        return (List<SubService>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "serviceCache",key = "#id", unless = "#result==null")
    public Optional<SubService> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<SubService> findAll() {
        return (List<SubService>) repository.findAll();
    }

    @Override
    public Page<SubService> findAll(Pageable pageable) {
        Page<SubService> entityPage = repository.findAll(pageable);
        List<SubService> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public SubService update(SubService entity, Long id) {
        Optional<SubService> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<SubService> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public List<SubService> getSubServiceByServiceFk(Service service) {
		return repository.getSubServiceByServiceFk(service);
	}
}