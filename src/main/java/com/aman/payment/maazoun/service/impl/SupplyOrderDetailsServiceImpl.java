package com.aman.payment.maazoun.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.SupplyOrder;
import com.aman.payment.maazoun.model.SupplyOrderDetails;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsSearchRequest;
import com.aman.payment.maazoun.repository.SupplyOrderDetailsRepository;
import com.aman.payment.maazoun.service.SupplyOrderDetailsService;

@Service
@Transactional
public class SupplyOrderDetailsServiceImpl implements SupplyOrderDetailsService {

	@Autowired
	private final SupplyOrderDetailsRepository repository;

	public SupplyOrderDetailsServiceImpl(SupplyOrderDetailsRepository repository) {
		this.repository = repository;
	}

	@Override
	public SupplyOrderDetails save(SupplyOrderDetails entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<SupplyOrderDetails> save(List<SupplyOrderDetails> entities) {
		return (List<SupplyOrderDetails>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);

	}

	@Override
	public void deleteAll(List<SupplyOrderDetails> entities) {
		repository.deleteAll(entities);

	}

	@Override
	public Optional<SupplyOrderDetails> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<SupplyOrderDetails> findAll() {
		return (List<SupplyOrderDetails>) repository.findAll();
	}

	@Override
	public Page<SupplyOrderDetails> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAll(pageable);
	}

	@Override
	public SupplyOrderDetails update(SupplyOrderDetails entity, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SupplyOrderDetails> findAllBySectorFK(Set<Long> sectorIds, Pageable pageable,
			CustomUserDetails customUserDetails) {

		Page<SupplyOrderDetails> entityPage = repository.findAllBySectorFK(sectorIds, pageable, customUserDetails);
		return new PageImpl<>(entityPage.getContent(), pageable, entityPage.getTotalElements());
	}

	@Override
	public List<SupplyOrderDetails> findAllByCreatedAtBetween(Date createdAt, Date createdAtEnd) {
		return repository.findAllByCreatedAtBetween(createdAt, createdAtEnd);
	}

	@Override
	public Page<SupplyOrderDetails> findAllByCreatedAtBetweenAndSectorFK(String createdAt, String createdAtEnd,
			Set<Long> sectorFK, Pageable pageable) {
		return repository.findAllByCreatedAtBetweenAndSectorFK(createdAt, createdAtEnd, sectorFK.toString(), pageable);
	}

	@Override
	public Page<SupplyOrderDetails> findBysectorFKIn(Set<String> sectors, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findBysectorFKIn(sectors, pageable);
	}

	@Override
	public List<SupplyOrderDetails> searchSupplyOrderDetails(
			SupplyOrderDetailsSearchRequest supplyOrderDetailsSearchRequest, Set<Long> sectorIds) {
		// TODO Auto-generated method stub
		return repository.searchInSupplyOrderDetails(supplyOrderDetailsSearchRequest, sectorIds);
	}
	
	@Override
	public SupplyOrderDetails findBySupplyOrderFk(String bookTypeFK, SupplyOrder supplyOrderFk) {
		// TODO Auto-generated method stub
		return repository.findByBookTypeFKAndSupplyOrderFk(bookTypeFK, supplyOrderFk);
	}

}
