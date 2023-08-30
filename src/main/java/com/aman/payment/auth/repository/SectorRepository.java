package com.aman.payment.auth.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Location;
import com.aman.payment.auth.model.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
	
	public Sector findByName(String name);
	
	public List<Sector> findByLocationFk(Location locationFk);
	
	public Page<Sector> findByNameContainsOrStatusFkContains(String param1, String param2, Pageable pageable);
	
}