package com.aman.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EntityScan(basePackageClasses = {
        AmanCollectionProsecutors.class,
        Jsr310JpaConverters.class
})
public class AmanCollectionProsecutors extends SpringBootServletInitializer{
	
    public static void main(String[] args) {
    	
//    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//                "FileContext.xml");
        SpringApplication.run(AmanCollectionProsecutors.class, args);
        
    }


}
