package com.aman.payment.auth.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.aman.payment.auth.model.Merchant;
import com.aman.payment.core.service.GenericService;

public interface MerchantService extends GenericService<Merchant, Long> {
	public Page<Merchant> lookforMerchant(String searchBy, Pageable pageable);

}