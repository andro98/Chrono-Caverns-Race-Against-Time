package com.aman.payment.maazoun.service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.SupplyOrder;

public interface SupplyOrderService extends MaazounGenericService<SupplyOrder, Long> {

	public Page<SupplyOrder> findByStatusFkAndPosIn(String status, Set<Pos> posSet, Pageable pageable,
			Date from, Date to);
	public Page<SupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeAndStatusFk(Date from, Date to,
			String statusFk, Pageable pageable);
	
	public Optional<SupplyOrder> findByRefSupplyOrderNumber(String refSupplyOrderNumber);

}
