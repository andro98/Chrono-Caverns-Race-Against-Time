package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.Service;
import com.aman.payment.auth.repository.ServiceRepository;
import com.aman.payment.auth.service.ServiceService;

@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository repository;

    public ServiceServiceImpl(ServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Service save(Service entity) {
        return repository.save(entity);
    }

    @Override
    public List<Service> save(List<Service> entities) {
        return (List<Service>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "serviceCache",key = "#id", unless = "#result==null")
    public Optional<Service> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Service> findAll() {
        return (List<Service>) repository.findAll();
    }

    @Override
    public Page<Service> findAll(Pageable pageable) {
        Page<Service> entityPage = repository.findAll(pageable);
        List<Service> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Service update(Service entity, Long id) {
        Optional<Service> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<Service> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public List<Service> getServiceActive(String statusFk) {
		return repository.findByStatusFk(statusFk);
	}
	
}