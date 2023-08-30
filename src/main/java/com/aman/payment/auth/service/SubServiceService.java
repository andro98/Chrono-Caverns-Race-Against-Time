package com.aman.payment.auth.service;

import java.util.List;

import com.aman.payment.auth.model.Service;
import com.aman.payment.auth.model.SubService;
import com.aman.payment.core.service.GenericService;

public interface SubServiceService extends GenericService<SubService, Long> {
	
	public List<SubService> getSubServiceByServiceFk(Service service);

}