package com.aman.payment.auth.service;

import java.util.Set;

import com.aman.payment.auth.model.Menu;
import com.aman.payment.auth.model.Service;
import com.aman.payment.core.service.GenericService;

public interface MenuService extends GenericService<Menu, Long> {
	public Set<Menu> findByServiceFk(Service serviceFk);
	
}