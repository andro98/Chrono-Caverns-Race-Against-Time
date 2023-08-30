package com.aman.payment.maazoun.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounBookValidation;
import com.aman.payment.maazoun.repository.MaazounBookValidationRepository;
import com.aman.payment.maazoun.service.MaazounBookValidationService;

@Service
@Transactional
public class MaazounBookValidationServiceImpl implements MaazounBookValidationService {

	@Autowired
    private final MaazounBookValidationRepository repository;
	
	public MaazounBookValidationServiceImpl(MaazounBookValidationRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounBookValidation save(MaazounBookValidation entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounBookValidation> save(List<MaazounBookValidation> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounBookValidation>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounBookValidation> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounBookValidation> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounBookValidation> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounBookValidation>) repository.findAll();
	}

	@Override
	public Page<MaazounBookValidation> findAll(Pageable pageable) {
		Page<MaazounBookValidation> entityPage = repository.findAll(pageable);
        List<MaazounBookValidation> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounBookValidation update(MaazounBookValidation entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounBookValidation> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}
	
	

}
