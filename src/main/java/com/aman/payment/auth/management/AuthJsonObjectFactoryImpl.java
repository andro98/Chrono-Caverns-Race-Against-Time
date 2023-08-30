package com.aman.payment.auth.management;

import org.springframework.beans.factory.annotation.Autowired;

import com.aman.payment.auth.service.CryptoMngrAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AuthJsonObjectFactoryImpl {

	@Autowired
	private CryptoMngrAuthService cryptoMngrAuthService;
	

	public <T, E> E convertJsonStringToObject(String jsonString, Class<T> c) {
		ObjectMapper mapper = new ObjectMapper();
		E readValue = null;
		try {
			readValue = (E) mapper.readValue(cryptoMngrAuthService.decrypt(jsonString), c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readValue;
	}
	
	public <T, E> E convertDecryptedJsonStringToObject(String jsonString, Class<T> c) {
		ObjectMapper mapper = new ObjectMapper();
		E readValue = null;
		try {
			readValue = (E) mapper.readValue(jsonString, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readValue;
	}
	
	

}
