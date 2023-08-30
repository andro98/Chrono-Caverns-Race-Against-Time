package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.InsuranceNumber;
import com.aman.payment.auth.repository.InsuranceNumberRepository;
import com.aman.payment.auth.service.InsuranceNumberService;

@Service
@Transactional
public class InsuranceNumberServiceImpl implements InsuranceNumberService {
    private final InsuranceNumberRepository repository;

    public InsuranceNumberServiceImpl(InsuranceNumberRepository repository) {
        this.repository = repository;
    }

    @Override
    public InsuranceNumber save(InsuranceNumber entity) {
        return repository.save(entity);
    }

    @Override
    public List<InsuranceNumber> save(List<InsuranceNumber> entities) {
        return (List<InsuranceNumber>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<InsuranceNumber> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<InsuranceNumber> findAll() {
        return (List<InsuranceNumber>) repository.findAll();
    }

    @Override
    public Page<InsuranceNumber> findAll(Pageable pageable) {
        Page<InsuranceNumber> entityPage = repository.findAll(pageable);
        List<InsuranceNumber> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public InsuranceNumber update(InsuranceNumber entity, Long id) {
        Optional<InsuranceNumber> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<InsuranceNumber> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public Optional<InsuranceNumber> findByServiceIdAndLocationId(long serviceId, long locationId) {
		return repository.findByServiceIdAndLocationId(serviceId, locationId);
	}

	@Override
	public Optional<InsuranceNumber> findByBookTypeIdAndLocationIdAndServiceId(long bookTypeId, long locationId,
			long serviceId) {
		// TODO Auto-generated method stub
		return repository.findByBookTypeIdAndLocationIdAndServiceId(bookTypeId, locationId, serviceId);
	}
}