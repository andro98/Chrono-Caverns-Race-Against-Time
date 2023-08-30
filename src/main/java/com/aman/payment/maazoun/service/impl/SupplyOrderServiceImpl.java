package com.aman.payment.maazoun.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookSupplyOrder;
import com.aman.payment.maazoun.model.SupplyOrder;
import com.aman.payment.maazoun.repository.SupplyOrderRepository;
import com.aman.payment.maazoun.service.SupplyOrderService;

@Service
@Transactional
public class SupplyOrderServiceImpl implements SupplyOrderService {

	@Autowired
	private final SupplyOrderRepository repository;

	public SupplyOrderServiceImpl(SupplyOrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public SupplyOrder save(SupplyOrder entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public List<SupplyOrder> save(List<SupplyOrder> entities) {
		// TODO Auto-generated method stub
		return repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);

	}

	@Override
	public void deleteAll(List<SupplyOrder> entities) {
		repository.deleteAll(entities);

	}

	@Override
	public Optional<SupplyOrder> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<SupplyOrder> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<SupplyOrder> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAll(pageable);
	}

	@Override
	public SupplyOrder update(SupplyOrder entity, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SupplyOrder> findByStatusFkAndPosIn(String statusFk, Set<Pos> posSet, Pageable pageable,
			Date from, Date to) {
		return repository.findByCreatedAtAfterAndCreatedAtBeforeAndStatusFkAndPosFkInOrderByIdDesc(from, to, statusFk,
				posSet, pageable);
	}
	
	@Override
	public Page<SupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeAndStatusFk(Date from, Date to,
			String statusFk, Pageable pageable) {
		return repository.findByCreatedAtAfterAndCreatedAtBeforeAndStatusFkOrderByIdDesc(from, to, statusFk, pageable);
	}

	@Override
	public Optional<SupplyOrder> findByRefSupplyOrderNumber(String refSupplyOrderNumber) {
		
 		return repository.findByRefSupplyOrderNumber(refSupplyOrderNumber);
	}
}
