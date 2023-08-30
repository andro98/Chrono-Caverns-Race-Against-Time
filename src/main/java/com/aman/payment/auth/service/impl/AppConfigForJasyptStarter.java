package com.aman.payment.auth.service.impl;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfigForJasyptStarter {

	@Bean(name = "encryptorBean")
	public StringEncryptor stringEncryptor() {
	    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
	    //https://www.devglan.com/online-tools/jasypt-online-encryption-decryption
	    //jwt secret = DQM7HoyI4X0IfbRrX+ETSbsrbTduQF5x
	    //datasource pass = confedintal
	    //encryption.key=JO0KvgQ8S2uKXmvn
	    //encryption.iv=ojPLvtz3mb2IXmvn
	    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
	    config.setPassword("p4NhNUMZfZgwb");
	    config.setAlgorithm("PBEWithMD5AndDES");
	    config.setKeyObtentionIterations("1000");
	    config.setPoolSize("1");
	    config.setProviderName("SunJCE");
	    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
	    config.setStringOutputType("base64");
	    encryptor.setConfig(config);
	    return encryptor;
	}
	
}
