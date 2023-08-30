package com.aman.payment.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.core.model.TransactionECR;
import com.aman.payment.core.repository.TransactionECRRepository;
import com.aman.payment.core.service.TransactionECRService;

@Service
@Transactional
public class TransactionECRServiceImpl implements TransactionECRService {
    private final TransactionECRRepository repository;

    public TransactionECRServiceImpl(TransactionECRRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionECR save(TransactionECR entity) {
        return repository.save(entity);
    }

    @Override
    public List<TransactionECR> save(List<TransactionECR> entities) {
        return (List<TransactionECR>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TransactionECR> findAll() {
        return (List<TransactionECR>) repository.findAll();
    }

    @Override
    public Page<TransactionECR> findAll(Pageable pageable) {
        Page<TransactionECR> entityPage = repository.findAll(pageable);
        List<TransactionECR> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public TransactionECR update(TransactionECR entity, Long id) {
        Optional<TransactionECR> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
    
    @Override
	public void deleteAll(List<TransactionECR> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public Optional<TransactionECR> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}
}