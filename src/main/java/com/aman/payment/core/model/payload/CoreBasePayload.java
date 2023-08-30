package com.aman.payment.core.model.payload;

import com.aman.payment.core.service.CryptoMngrCoreService;

public interface CoreBasePayload<T> {
	
	public T decrypt(CryptoMngrCoreService cryptoMngrCoreService);

}
