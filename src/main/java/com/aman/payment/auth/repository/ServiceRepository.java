package com.aman.payment.auth.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Service;

@Repository
public interface ServiceRepository extends PagingAndSortingRepository<Service, Long> {
	
	public List<Service> findByStatusFk(String statusFk);
}