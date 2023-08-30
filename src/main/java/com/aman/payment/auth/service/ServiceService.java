package com.aman.payment.auth.service;

import java.util.List;

import com.aman.payment.auth.model.Service;
import com.aman.payment.core.service.GenericService;

public interface ServiceService extends GenericService<Service, Long> {
	public List<Service> getServiceActive(String statusFk);
}