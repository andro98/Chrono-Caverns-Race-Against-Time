package com.aman.payment.auth.service;

import java.util.Optional;

import com.aman.payment.auth.model.Setting;
import com.aman.payment.core.service.GenericService;

public interface SettingService extends GenericService<Setting, Long> {
	
	public Optional<Setting> findByKeyOps(String keyOps);
}