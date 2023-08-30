package com.aman.payment.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.lookup.PaymentType;

@Repository
public interface PaymentTypeRepository extends PagingAndSortingRepository<PaymentType, Long> {
}