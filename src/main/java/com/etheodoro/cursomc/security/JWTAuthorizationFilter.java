package com.etheodoro.cursomc.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.ExpiredJwtException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter (AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService; 
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");
		
		if ( header != null && header.startsWith("Bearer ") ) {
			UsernamePasswordAuthenticationToken auth = null;
			
			try {
				auth = getAuthentication(header.substring(7));
			} catch (ExpiredJwtException e) {
				 final String expiredMsg = e.getMessage();
			        logger.warn(expiredMsg);

			        final String msg = (expiredMsg != null) ? expiredMsg : "Unauthorized";
			        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED, msg);
			}
			
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			}
		}
		chain.doFilter(request, response);
		
	}

	 private UsernamePasswordAuthenticationToken getAuthentication(String token) throws ExpiredJwtException {
			System.out.println("Time: " + new Date(System.currentTimeMillis()));
		 if (jwtUtil.tokenValido(token)) {   
        	String username = jwtUtil.getUserName(token);
        	UserDetails user = userDetailsService.loadUserByUsername(username);
        	return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;
    }

}
