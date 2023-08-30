package com.aman.payment.maazoun.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.MaazounBookSupplyOrder;

@Repository
public interface MaazounBookSupplyOrderRepository extends JpaRepository<MaazounBookSupplyOrder,Long> {

	public Page<MaazounBookSupplyOrder> findByPosFkInOrderByIdDesc(Set<Pos> posSet, Pageable pageable);
	
	public MaazounBookSupplyOrder findByRefSupplyOrderNumber(String supplyOrderRefNumber) ;
	
	public Page<MaazounBookSupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeAndPosFkInOrderByIdDesc(Date from, Date to, Set<Pos> posSet, Pageable pageable);
	
	public Page<MaazounBookSupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeAndStatusFkAndPosFkInOrderByIdDesc(Date from, Date to, String statusFk, Set<Pos> posSet, Pageable pageable);
	
	public Page<MaazounBookSupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeOrderByIdDesc(Date from, Date to, Pageable pageable);
	
	public Page<MaazounBookSupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeAndStatusFkOrderByIdDesc(Date from, Date to, String statusFk, Pageable pageable);

}
