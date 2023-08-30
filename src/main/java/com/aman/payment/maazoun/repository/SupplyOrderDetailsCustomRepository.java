package com.aman.payment.maazoun.repository;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.SupplyOrderDetails;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsSearchRequest;
 
@Repository
public interface SupplyOrderDetailsCustomRepository {
	
	public List<SupplyOrderDetails> searchInSupplyOrderDetails(SupplyOrderDetailsSearchRequest searchSupplyOrderDetailsRequest, Set<Long> sectors);

	 
	
	
}
