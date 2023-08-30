package com.aman.payment.auth.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.auth.model.SubServiceQuota;

@Repository
public interface SubServiceQuotaRepository extends PagingAndSortingRepository<SubServiceQuota, Long> {
}