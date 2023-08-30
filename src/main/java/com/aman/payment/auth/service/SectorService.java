package com.aman.payment.auth.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.Location;
import com.aman.payment.auth.model.MaazouniaChurch;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.core.service.GenericService;

public interface SectorService extends GenericService<Sector, Long> {
	
	public Sector findByName(String name);
	
	public List<Sector> findByLocationFk(Location locationFk);
	
	public Page<Sector> lookforSector(String searchBy, Pageable pageable);

}