package com.etheodoro.cursomc.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.etheodoro.cursomc.security.JWTAuthenticationFilter;
import com.etheodoro.cursomc.security.JWTAuthorizationFilter;
import com.etheodoro.cursomc.security.JWTUtil;

import io.jsonwebtoken.lang.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	private static final String[] PUBLIC_MATCHES = {
			"/h2-console/**"
	};

	private static final String[] PUBLIC_MATCHES_GET = {
			"/produtos/**"
			,"/categorias/**"
	};
	
	private static final String[] PUBLIC_MATCHES_POST = {
			"/clientes/**",
			"/auth/forgot/**"
	};
	
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
    	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    	authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    	AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
    	
    	if ( Arrays.asList(env.getActiveProfiles()).contains("test") ) {
    		http.headers(headers -> headers.frameOptions().disable());
    	}
    	
    	http
        .cors(withDefaults())
        .csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests((authz) -> authz	
        .antMatchers(PUBLIC_MATCHES_GET).permitAll()
		.antMatchers(PUBLIC_MATCHES).permitAll()
		.antMatchers(PUBLIC_MATCHES_POST).permitAll()
		.anyRequest().authenticated().and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager, jwtUtil)))
		.addFilter(new JWTAuthorizationFilter(authenticationManager, jwtUtil, userDetailsService))
		.authenticationManager(authenticationManager)
		.sessionManagement((session) -> session
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

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
