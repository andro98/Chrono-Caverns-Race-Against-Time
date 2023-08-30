package com.aman.payment.core.model.dto;

import com.aman.payment.core.service.CryptoMngrCoreService;

public interface CoreBaseDTO<T> {
	
	public T encrypt(CryptoMngrCoreService cryptoMngrCoreService);

}
