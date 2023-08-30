package com.aman.payment.maazoun.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounInventoryNumber;
import com.aman.payment.maazoun.repository.MaazounInventoryNumberRepository;
import com.aman.payment.maazoun.service.MaazounInventoryNumberService;

@Service
@Transactional
public class MaazounInventoryNumberServiceImpl implements MaazounInventoryNumberService {

	@Autowired
    private final MaazounInventoryNumberRepository repository;
	
	public MaazounInventoryNumberServiceImpl(MaazounInventoryNumberRepository repository) {
        this.repository = repository;
    }

	@Override
	public MaazounInventoryNumber save(MaazounInventoryNumber entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<MaazounInventoryNumber> save(List<MaazounInventoryNumber> entities) {
		// TODO Auto-generated method stub
		return (List<MaazounInventoryNumber>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounInventoryNumber> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounInventoryNumber> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounInventoryNumber> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounInventoryNumber>) repository.findAll();
	}

	@Override
	public Page<MaazounInventoryNumber> findAll(Pageable pageable) {
		Page<MaazounInventoryNumber> entityPage = repository.findAll(pageable);
        List<MaazounInventoryNumber> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounInventoryNumber update(MaazounInventoryNumber entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounInventoryNumber> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
	}

	@Override
	public Optional<MaazounInventoryNumber> findBySectorId(Long sectorId) {
		// TODO Auto-generated method stub
		return repository.findBySectorId(sectorId);
	}

	@Override
	public Optional<MaazounInventoryNumber> findByTypeId(String typeId) {
		// TODO Auto-generated method stub
		return repository.findByTypeId(typeId);
	}

	@Override
	public Optional<MaazounInventoryNumber> findByYear(String year) {
		// TODO Auto-generated method stub
		return repository.findByYear(year);
	}

	@Override
	public Optional<MaazounInventoryNumber> findBySectorIdAndTypeIdAndYear(Long sectorId, String typeId, Long year) {
		// TODO Auto-generated method stub
		return repository.findBySectorIdAndTypeIdAndYear(sectorId, typeId, year);
	}

	
	
	

}
