package com.aman.payment.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Setting;

@Repository
public interface SettingRepository
		extends PagingAndSortingRepository<Setting, Long>{
	
	public Optional<Setting> findByKeyOps(String keyOps);
	
}