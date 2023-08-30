package com.aman.payment.maazoun.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounQuotaExceptionAtt;
import com.aman.payment.maazoun.repository.MaazounQuotaExceptionAttRepository;
import com.aman.payment.maazoun.service.MaazounQuotaExceptionAttService;
import com.aman.payment.maazoun.service.MaazounQuotaExceptionAttService;

@Service
@Transactional
public class MaazounQuotaExceptionAttServiceImpl implements MaazounQuotaExceptionAttService {

	@Autowired
    private final MaazounQuotaExceptionAttRepository repository;
	
	public MaazounQuotaExceptionAttServiceImpl(MaazounQuotaExceptionAttRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounQuotaExceptionAtt save(MaazounQuotaExceptionAtt entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounQuotaExceptionAtt> save(List<MaazounQuotaExceptionAtt> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounQuotaExceptionAtt>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounQuotaExceptionAtt> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounQuotaExceptionAtt> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounQuotaExceptionAtt> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounQuotaExceptionAtt>) repository.findAll();
	}

	@Override
	public Page<MaazounQuotaExceptionAtt> findAll(Pageable pageable) {
		Page<MaazounQuotaExceptionAtt> entityPage = repository.findAll(pageable);
        List<MaazounQuotaExceptionAtt> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounQuotaExceptionAtt update(MaazounQuotaExceptionAtt entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounQuotaExceptionAtt> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

}
