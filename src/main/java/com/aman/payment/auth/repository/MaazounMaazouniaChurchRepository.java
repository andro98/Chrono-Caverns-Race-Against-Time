package com.aman.payment.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.MaazounMaazouniaChurch;

@Repository
public interface MaazounMaazouniaChurchRepository extends JpaRepository<MaazounMaazouniaChurch, Long> {
	
	
}