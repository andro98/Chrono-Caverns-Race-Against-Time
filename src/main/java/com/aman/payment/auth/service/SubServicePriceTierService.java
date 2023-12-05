package com.aman.payment.auth.service;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServicePriceTier;
import com.aman.payment.core.service.GenericService;

import java.util.List;

public interface SubServicePriceTierService extends GenericService<SubServicePriceTier, Long> {
    List<SubServicePriceTier> getSubServiceBySubServiceFk(SubService subService);
    List<SubServicePriceTier> getSubServiceByIsActive(Boolean isActive);
}
