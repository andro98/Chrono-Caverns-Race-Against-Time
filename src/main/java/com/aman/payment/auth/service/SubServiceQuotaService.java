package com.aman.payment.auth.service;

import java.util.List;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServicePriceTier;
import com.aman.payment.auth.model.SubServiceQuota;
import com.aman.payment.core.service.GenericService;

public interface SubServiceQuotaService extends GenericService<SubServiceQuota, Long> {

	List<SubServiceQuota> findBySubServiceFk(SubService subServiceFk);

	List<SubServiceQuota> findBySubServiceFkAndSubServicePriceTierFK(SubService subServiceFk, SubServicePriceTier subServicePriceTierFK);

}