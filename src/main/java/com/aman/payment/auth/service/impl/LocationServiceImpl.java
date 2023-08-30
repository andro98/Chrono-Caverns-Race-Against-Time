package com.aman.payment.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Location;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.repository.LocationRepository;
import com.aman.payment.auth.service.LocationService;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;

    public LocationServiceImpl(LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Location save(Location entity) {
        return repository.save(entity);
    }

    @Override
    public List<Location> save(List<Location> entities) {
        return (List<Location>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Location> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Location> findAll() {
        return (List<Location>) repository.findAll();
    }

    @Override
    public Page<Location> findAll(Pageable pageable) {
        Page<Location> entityPage = repository.findAll(pageable);
        List<Location> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Location update(Location entity, Long id) {
        Optional<Location> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<Location> entities) {
		repository.deleteAll(entities);
	}

	@Override
	public Page<Location> lookforLocation(String searchBy, Pageable pageable) {
		Page<Location> entityPage = repository.findByNameContainsOrCodeContainsOrDescriptionContains(searchBy, searchBy, searchBy, pageable);
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	@Override
	public Location findBySector(Sector sector) {
		Set<Sector> sectors = new HashSet<Sector>();
		sectors.add(sector);
		return repository.findBySectors(sectors);
	}
	
	
}