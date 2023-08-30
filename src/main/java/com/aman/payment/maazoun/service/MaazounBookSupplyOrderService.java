package com.aman.payment.maazoun.service;

import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookSupplyOrder;

public interface MaazounBookSupplyOrderService extends MaazounGenericService<MaazounBookSupplyOrder, Long> {

	public Page<MaazounBookSupplyOrder> findByPosIn(Set<Pos> posSet, Pageable pageable, Date from, Date to);

	public Page<MaazounBookSupplyOrder> findByStatusFkAndPosIn(String status, Set<Pos> posSet, Pageable pageable,
			Date from, Date to);

	public MaazounBookSupplyOrder findByRefSupplyOrderNumber(String supplyOrderRefNumber);

	public Page<MaazounBookSupplyOrder> findByCreatedAtAfterAndCreatedAtBefore(Date from, Date to, Pageable pageable);

	public Page<MaazounBookSupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeAndStatusFk(Date from, Date to,
			String statusFk, Pageable pageable);

}