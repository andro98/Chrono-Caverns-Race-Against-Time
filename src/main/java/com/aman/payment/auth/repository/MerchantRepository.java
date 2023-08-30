package com.aman.payment.auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.Merchant;

@Repository
public interface MerchantRepository extends PagingAndSortingRepository<Merchant, Long> {
	
	Page<Merchant> findAll(Pageable pageable);
	Page<Merchant> findByNameContainsOrCodeContainsOrDescriptionContainsOrAddressContainsOrPhone1ContainsOrPhone2ContainsOrMobileContains(
	    		String param1, String param2, String param3,String param4,String param5,String param6,String param7,Pageable pageable);
	
}