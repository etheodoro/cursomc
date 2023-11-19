package com.etheodoro.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.jsonwebtoken.lang.Arrays;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private Environment env;
	
	private static final String[] PUBLIC_MATCHES = {
			"/h2-console/**"
	};

	private static final String[] PUBLIC_MATCHES_GET = {
			"/produtos/**"
			,"/categorias/**"
	};
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
    	if ( Arrays.asList(env.getActiveProfiles()).contains("test") ) {
    		http.headers(headers -> headers.frameOptions().disable());
    	}
    	
    	http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests((authz) -> authz
				.antMatchers(PUBLIC_MATCHES_GET).permitAll()
				.antMatchers(PUBLIC_MATCHES).permitAll()
				.anyRequest().authenticated()
            ).sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
 
        return http.build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
