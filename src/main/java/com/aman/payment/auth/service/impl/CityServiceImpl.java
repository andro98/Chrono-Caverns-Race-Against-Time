package com.aman.payment.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.City;
import com.aman.payment.auth.repository.CityRepository;
import com.aman.payment.auth.service.CityService;

@Service
@Transactional
public class CityServiceImpl implements CityService {
    private final CityRepository repository;

    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public City save(City entity) {
        return repository.save(entity);
    }

    @Override
    public List<City> save(List<City> entities) {
        return (List<City>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<City> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<City> findAll() {
        return (List<City>) repository.findAll();
    }

    @Override
    public Page<City> findAll(Pageable pageable) {
        Page<City> entityPage = repository.findAll(pageable);
        List<City> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public City update(City entity, Long id) {
        Optional<City> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

	@Override
	public void deleteAll(List<City> entities) {
		repository.deleteAll(entities);
	}
}