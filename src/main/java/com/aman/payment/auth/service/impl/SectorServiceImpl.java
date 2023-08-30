package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Location;
import com.aman.payment.auth.model.MaazouniaChurch;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.repository.SectorRepository;
import com.aman.payment.auth.service.SectorService;

@Service
@Transactional
public class SectorServiceImpl implements SectorService {
    private final SectorRepository repository;

    public SectorServiceImpl(SectorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Sector save(Sector entity) {
        return repository.save(entity);
    }

    @Override
    public List<Sector> save(List<Sector> entities) {
        return (List<Sector>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Sector> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Sector> findAll() {
        return (List<Sector>) repository.findAll();
    }

    @Override
    public Page<Sector> findAll(Pageable pageable) {
        Page<Sector> entityPage = repository.findAll(pageable);
        List<Sector> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Sector update(Sector entity, Long id) {
        Optional<Sector> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<Sector> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public Sector findByName(String name) {
		// TODO Auto-generated method stub
		return repository.findByName(name);
	}

	@Override
	public List<Sector> findByLocationFk(Location locationFk) {
		// TODO Auto-generated method stub
		return repository.findByLocationFk(locationFk);
	}

	@Override
	public Page<Sector> lookforSector(String searchBy, Pageable pageable) {
		
		Page<Sector> entityPage = repository.findByNameContainsOrStatusFkContains(searchBy, searchBy, pageable);
		return new PageImpl<Sector>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}
	
	
}