package com.aman.payment.maazoun.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounCheckListLookup;
import com.aman.payment.maazoun.repository.MaazounCheckListRepository;
import com.aman.payment.maazoun.service.MaazounCheckListService;

@Service
@Transactional
public class MaazounCheckListServiceImpl implements MaazounCheckListService {

	@Autowired
    private final MaazounCheckListRepository  repository;
	
	public MaazounCheckListServiceImpl(MaazounCheckListRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounCheckListLookup save(MaazounCheckListLookup entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounCheckListLookup> save(List<MaazounCheckListLookup> entities) {
		// TODO Auto-generated method stub
		return repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounCheckListLookup> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounCheckListLookup> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounCheckListLookup> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<MaazounCheckListLookup> findAll(Pageable pageable) {
		Page<MaazounCheckListLookup> entityPage = repository.findAll(pageable);
        List<MaazounCheckListLookup> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounCheckListLookup update(MaazounCheckListLookup entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounCheckListLookup> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

	@Override
	public List<MaazounCheckListLookup> findBySubServiceId(long subServiceId) {
		// TODO Auto-generated method stub
		return repository.findBySubServiceId(subServiceId);
	}
	
	

}
