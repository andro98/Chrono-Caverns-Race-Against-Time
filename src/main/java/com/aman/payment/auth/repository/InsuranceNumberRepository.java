package com.aman.payment.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.InsuranceNumber;

@Repository
public interface InsuranceNumberRepository extends JpaRepository<InsuranceNumber, Long> {
	
	public Optional<InsuranceNumber> findByServiceIdAndLocationId(long serviceId, long locationId);
	
	public Optional<InsuranceNumber> findByBookTypeIdAndLocationIdAndServiceId(long bookTypeId, long locationId,
			long serviceId);
}