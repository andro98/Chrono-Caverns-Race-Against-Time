package com.aman.payment.auth.model.dto;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public interface AuthBaseDTO<T> {
	
	public T encrypt(CryptoMngrAuthService cryptoMngrAuthService);

}
