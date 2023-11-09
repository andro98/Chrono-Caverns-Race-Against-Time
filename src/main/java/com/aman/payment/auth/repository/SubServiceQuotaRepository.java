package com.aman.payment.auth.repository;

import java.util.List;

import com.aman.payment.auth.model.SubServicePriceTier;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServiceQuota;

@Repository
public interface SubServiceQuotaRepository extends PagingAndSortingRepository<SubServiceQuota, Long> {
	
	List<SubServiceQuota> findBySubServiceFk(SubService subServiceFk);

    List<SubServiceQuota> findBySubServiceFkAndSubServicePriceTierFk(SubService subServiceFk, SubServicePriceTier subServicePriceTierFK);
}