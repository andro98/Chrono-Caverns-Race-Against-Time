package com.aman.payment.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.MaazouniaChurch;
import com.aman.payment.auth.model.Sector;

@Repository
public interface MaazouniaChurchRepository extends JpaRepository<MaazouniaChurch, Long> {
	
	public List<MaazouniaChurch> findBySectorFk(Sector sectorFk);
	
	public List<MaazouniaChurch> findByNameContainsOrStatusFkContains(String param1, String param2);

	@Query("SELECT MAX(ch.id) FROM MaazouniaChurch ch")
	Long getMaxId();

}