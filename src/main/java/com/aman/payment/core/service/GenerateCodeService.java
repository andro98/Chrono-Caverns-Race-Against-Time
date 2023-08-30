package com.aman.payment.core.service;

import com.aman.payment.core.model.GenerateCode;

public interface GenerateCodeService extends GenericService<GenerateCode, Long> {
	
	public GenerateCode findByKeyName(String keyName);
	
}