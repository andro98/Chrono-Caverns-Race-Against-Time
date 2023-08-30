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

import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookSupplyOrder;
import com.aman.payment.maazoun.repository.MaazounBookSupplyOrderRepository;
import com.aman.payment.maazoun.service.MaazounBookSupplyOrderService;

@Service
@Transactional
public class MaazounBookSupplyOrderServiceImpl implements MaazounBookSupplyOrderService {

	@Autowired
	private final MaazounBookSupplyOrderRepository repository;

	public MaazounBookSupplyOrderServiceImpl(MaazounBookSupplyOrderRepository repository) {

		this.repository = repository;
	}

	@Override
	public MaazounBookSupplyOrder save(MaazounBookSupplyOrder entity) {

		return repository.save(entity);
	}

	@Override
	public List<MaazounBookSupplyOrder> save(List<MaazounBookSupplyOrder> entities) {
		return (List<MaazounBookSupplyOrder>) repository.saveAll(entities);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void deleteAll(List<MaazounBookSupplyOrder> entities) {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}

	@Override
	public Optional<MaazounBookSupplyOrder> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<MaazounBookSupplyOrder> findAll() {
		// TODO Auto-generated method stub
		return (List<MaazounBookSupplyOrder>) repository.findAll();
	}

	@Override
	public Page<MaazounBookSupplyOrder> findAll(Pageable pageable) {
		Page<MaazounBookSupplyOrder> entityPage = repository.findAll(pageable);
		List<MaazounBookSupplyOrder> entities = entityPage.getContent();
		return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
	}

	@Override
	public MaazounBookSupplyOrder update(MaazounBookSupplyOrder entity, Long id) {
		// TODO Auto-generated method stub
		Optional<MaazounBookSupplyOrder> optional = findById(id);
		if (optional.isPresent()) {
			return save(entity);
		}
		return null;
	}

	@Override
	public Page<MaazounBookSupplyOrder> findByPosIn(Set<Pos> posSet, Pageable pageable, Date from, Date to) {
		return repository.findByCreatedAtAfterAndCreatedAtBeforeAndPosFkInOrderByIdDesc(from, to, posSet, pageable);
	}

	@Override
	public Page<MaazounBookSupplyOrder> findByStatusFkAndPosIn(String statusFk, Set<Pos> posSet, Pageable pageable,
			Date from, Date to) {
		return repository.findByCreatedAtAfterAndCreatedAtBeforeAndStatusFkAndPosFkInOrderByIdDesc(from, to, statusFk,
				posSet, pageable);
	}

	@Override
	public MaazounBookSupplyOrder findByRefSupplyOrderNumber(String supplyOrderRefNumber) {
		// TODO Auto-generated method stub
		return repository.findByRefSupplyOrderNumber(supplyOrderRefNumber);
	}

	@Override
	public Page<MaazounBookSupplyOrder> findByCreatedAtAfterAndCreatedAtBefore(Date from, Date to, Pageable pageable) {
		return repository.findByCreatedAtAfterAndCreatedAtBeforeOrderByIdDesc(from, to, pageable);

	}

	@Override
	public Page<MaazounBookSupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeAndStatusFk(Date from, Date to,
			String statusFk, Pageable pageable) {
		
		return repository.findByCreatedAtAfterAndCreatedAtBeforeAndStatusFkOrderByIdDesc(from, to, statusFk, pageable);
	}

}
