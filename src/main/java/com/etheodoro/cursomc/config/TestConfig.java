package com.etheodoro.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.etheodoro.cursomc.services.DBServie;
import com.etheodoro.cursomc.services.EmailService;
import com.etheodoro.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBServie dbServie;
	
	@Bean
	boolean instantiateTestDatabase() throws ParseException {
		dbServie.instantiateTestDatabase();
		return true;
	}
	
	@Bean
	EmailService emailService() {
		return new MockEmailService();
	}
	
}
