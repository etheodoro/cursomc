package com.etheodoro.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.etheodoro.cursomc.services.DBServie;
import com.etheodoro.cursomc.services.EmailService;
import com.etheodoro.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBServie dbServie;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

    @Bean
    boolean instantiateTestDatabase() throws ParseException {
		if (!strategy.equals("create")) {
			return false;
		}
		dbServie.instantiateTestDatabase();
		return true;
	}

    @Bean
    EmailService emailService() {
		return new SmtpEmailService();
	}
	
	
}
