package com.aman.payment.auth.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Service;
import com.aman.payment.auth.model.SubService;

@Repository
public interface SubServiceRepository extends PagingAndSortingRepository<SubService, Long> {
	
	public List<SubService> getSubServiceByServiceFk(Service service);
}