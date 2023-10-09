package com.aman.payment.maazoun.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.maazoun.model.SupplyOrder;
import com.aman.payment.maazoun.model.SupplyOrderDetails;
import com.aman.payment.maazoun.model.payload.SupplyOrderDetailsSearchRequest;

public interface SupplyOrderDetailsService extends MaazounGenericService<SupplyOrderDetails, Long> {

    public Page<SupplyOrderDetails> findAllBySectorFK(Set<Long> sectorIds, Pageable pageable,
                                                      CustomUserDetails customUserDetails);

    public List<SupplyOrderDetails> findAllByCreatedAtBetween(Date createdAt, Date createdAtEnd);

    public Page<SupplyOrderDetails> findAllByCreatedAtBetweenAndSectorFK(String createdAt, String createdAtEnd,
                                                                         Set<Long> sectorFK, Pageable pageable);

    public Page<SupplyOrderDetails> findBysectorFKIn(Set<String> sectors, Pageable pageable);

    public List<SupplyOrderDetails> searchSupplyOrderDetails(SupplyOrderDetailsSearchRequest supplyOrderDetailsSearchRequest,
                                                             Set<Long> sectorIds);

    public SupplyOrderDetails findBySupplyOrderFk(String bookTypeFK, SupplyOrder supplyOrderFk);

    List<SupplyOrderDetails> findBySupplyOrderFk(SupplyOrder supplyOrderFk);

}
