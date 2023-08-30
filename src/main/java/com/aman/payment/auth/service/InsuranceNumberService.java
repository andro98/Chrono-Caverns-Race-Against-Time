package com.aman.payment.auth.service;

import java.util.Optional;

import com.aman.payment.auth.model.InsuranceNumber;
import com.aman.payment.core.service.GenericService;

public interface InsuranceNumberService extends GenericService<InsuranceNumber, Long> {
	
    public Optional<InsuranceNumber> findByServiceIdAndLocationId(long serviceId, long locationId);
    
    public Optional<InsuranceNumber> findByBookTypeIdAndLocationIdAndServiceId(long bookTypeId, long locationId, long serviceId);
	
}