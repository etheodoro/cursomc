package com.etheodoro.cursomc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.etheodoro.cursomc.domain.PagamentoComBoleto;
import com.etheodoro.cursomc.domain.PagamentoComCartao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {

    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure (ObjectMapper objectmapper) {
				objectmapper.registerSubtypes(PagamentoComCartao.class);
				objectmapper.registerSubtypes(PagamentoComBoleto.class);
			}
		};
		return builder;
	}
	
}
