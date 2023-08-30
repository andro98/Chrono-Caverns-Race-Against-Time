package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public interface AuthBasePayload<T> {
	
	public T decrypt(CryptoMngrAuthService cryptoMngrAuthService);

}
