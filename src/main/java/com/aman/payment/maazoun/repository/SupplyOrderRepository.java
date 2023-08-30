package com.aman.payment.maazoun.repository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.maazoun.model.SupplyOrder;

@Repository
public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, Long> {
	
	public Page<SupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeAndStatusFkAndPosFkInOrderByIdDesc(
			Date from, Date to, String statusFk, Set<Pos> posSet, Pageable pageable);
	
	public Page<SupplyOrder> findByCreatedAtAfterAndCreatedAtBeforeAndStatusFkOrderByIdDesc(
			Date from, Date to, String statusFk, Pageable pageable);
	
	public  Optional<SupplyOrder> findByRefSupplyOrderNumber(String refSupplyOrderNumber);

}
