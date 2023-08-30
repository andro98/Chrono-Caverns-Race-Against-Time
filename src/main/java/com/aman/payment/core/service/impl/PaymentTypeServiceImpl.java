package com.aman.payment.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Location;
import com.aman.payment.core.model.lookup.PaymentType;
import com.aman.payment.core.repository.PaymentTypeRepository;
import com.aman.payment.core.service.PaymentTypeService;

@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService {
    private final PaymentTypeRepository repository;

    public PaymentTypeServiceImpl(PaymentTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public PaymentType save(PaymentType entity) {
        return repository.save(entity);
    }

    @Override
    public List<PaymentType> save(List<PaymentType> entities) {
        return (List<PaymentType>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "paymentTypeCache",key = "#id", unless = "#result==null")
    public Optional<PaymentType> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<PaymentType> findAll() {
        return (List<PaymentType>) repository.findAll();
    }

    @Override
    public Page<PaymentType> findAll(Pageable pageable) {
        Page<PaymentType> entityPage = repository.findAll(pageable);
        List<PaymentType> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public PaymentType update(PaymentType entity, Long id) {
        Optional<PaymentType> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
    
    @Override
	public void deleteAll(List<PaymentType> entities) {
		repository.deleteAll(entities);
	}
}