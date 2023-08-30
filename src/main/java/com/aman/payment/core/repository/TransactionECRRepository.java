package com.aman.payment.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aman.payment.core.model.TransactionECR;

@Repository
public interface TransactionECRRepository extends PagingAndSortingRepository<TransactionECR, Long> {
}