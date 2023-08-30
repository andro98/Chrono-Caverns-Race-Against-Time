package com.aman.payment.maazoun.management;

import org.springframework.beans.factory.annotation.Autowired;

import com.aman.payment.maazoun.service.CryptoMngrMaazounService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class MaazounJsonObjectFactoryImpl {

	@Autowired
	private CryptoMngrMaazounService cryptoMngrMaazounService;

	public <T, E> E convertJsonStringToObject(String jsonString, Class<T> c) {
		ObjectMapper mapper = new ObjectMapper();
		E readValue = null;
		try {
			readValue = (E) mapper.readValue(cryptoMngrMaazounService.decrypt(jsonString), c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readValue;
	}
	
	public <T, E> E convertDecryptedJsonStringToObject(String jsonString, Class<T> c) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		E readValue = null;
		try {
			readValue = (E) mapper.readValue(jsonString, c);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return readValue;
	}
	
}
