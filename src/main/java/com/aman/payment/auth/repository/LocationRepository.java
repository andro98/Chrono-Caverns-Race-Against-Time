package com.aman.payment.auth.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Location;
import com.aman.payment.auth.model.Sector;

@Repository
public interface LocationRepository extends PagingAndSortingRepository<Location, Long>, LocationRepositoryCustom {
	
	Page<Location> findAll(Pageable pageable);
	
	Page<Location> findByNameContainsOrCodeContainsOrDescriptionContains(String param1, String param2, String param3, Pageable pageable);
	
	public Location findBySectors(Set<Sector> sectors);
	
}