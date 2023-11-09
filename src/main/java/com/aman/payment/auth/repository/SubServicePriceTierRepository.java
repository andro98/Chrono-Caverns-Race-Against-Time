package com.aman.payment.auth.repository;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServicePriceTier;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubServicePriceTierRepository extends PagingAndSortingRepository<SubServicePriceTier, Long> {

    public List<SubServicePriceTier> getSubServiceBySubServiceFk(SubService subService);
}
