package com.aman.payment.core.management;

import org.springframework.beans.factory.annotation.Autowired;

import com.aman.payment.core.service.CryptoMngrCoreService;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class CoreJsonObjectFactoryImpl {

	@Autowired
	private CryptoMngrCoreService cryptoMngrCoreService;

	public <T, E> E convertJsonStringToObject(String jsonString, Class<T> c) {
		ObjectMapper mapper = new ObjectMapper();
		E readValue = null;
		try {
			readValue = (E) mapper.readValue(cryptoMngrCoreService.decrypt(jsonString), c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readValue;
	}

}
