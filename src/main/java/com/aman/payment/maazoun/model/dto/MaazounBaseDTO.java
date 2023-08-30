package com.aman.payment.maazoun.model.dto;

import com.aman.payment.maazoun.service.CryptoMngrMaazounService;

public interface MaazounBaseDTO<T> {
	
	public T encrypt(CryptoMngrMaazounService cryptoMngrMaazounService);

}
