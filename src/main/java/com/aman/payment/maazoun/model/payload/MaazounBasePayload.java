package com.aman.payment.maazoun.model.payload;

import com.aman.payment.maazoun.service.CryptoMngrMaazounService;

public interface MaazounBasePayload<T> {
	
	public T decrypt(CryptoMngrMaazounService cryptoMngrMaazounService);

}
