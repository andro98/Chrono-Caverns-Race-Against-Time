package com.aman.payment.maazoun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.maazoun.model.MaazounCheckListLookup;

@Repository
public interface MaazounCheckListRepository extends JpaRepository<MaazounCheckListLookup,Long> {

	public List<MaazounCheckListLookup> findBySubServiceId(long subServiceId);
	
}
