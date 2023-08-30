package com.aman.payment.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aman.payment.core.repository.GenerateCodeRepository;
import com.aman.payment.core.service.Singleton;

@Configuration
public class SingletonConfig {

	@Autowired
	private GenerateCodeRepository generateCodeRepository;
	
    @Bean
    public Singleton transactionCache() {
        return Singleton.getSingleton(generateCodeRepository);
    }

}
