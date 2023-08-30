package com.aman.payment.core.service;

import com.aman.payment.core.model.GenerateCode;
import com.aman.payment.core.repository.GenerateCodeRepository;

public class Singleton {

//	@Autowired
	private GenerateCodeRepository generateCodeRepository;
	private volatile static Singleton instance;
	private long insuranceNumber = 0;

	private Singleton(GenerateCodeRepository generateCodeRepository) {
		this.generateCodeRepository = generateCodeRepository;
    }

    public static Singleton getSingleton(GenerateCodeRepository generateCodeRepository) {
    	
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                	instance = new Singleton(generateCodeRepository);
                }
            }
        }
        return instance;
    }
    
	public long getInsuranceNumber(String key) {
		
		GenerateCode code = generateCodeRepository.findByKeyName(key);
		insuranceNumber = code.getSeqValue() + 1;
		code.setSeqValue(insuranceNumber);
		generateCodeRepository.save(code);
		
		return insuranceNumber;
		
	}
}
