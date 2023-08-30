package com.aman.payment.auth.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.Location;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.core.service.GenericService;

public interface LocationService extends GenericService<Location, Long> {
	
	public Page<Location> lookforLocation(String searchBy, Pageable pageable);
	
	public Location findBySector(Sector sector);
}