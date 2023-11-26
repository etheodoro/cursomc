package com.etheodoro.cursomc.security;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;

	public String generateToken(String username) {
				
		return Jwts.builder()
				.subject(username)
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
				.compact();
	}
	
	public boolean tokenValido(String token) throws ExpiredJwtException {
		
		Claims claims = getClaims(token);
		
		if ( claims != null ) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = Calendar.getInstance().getTime();
			
			if ( username != null && expirationDate != null && now.before(expirationDate)){
				return true;
			}
		}
		return false;
	}
	
	public String getUserName(String token) {
		Claims claims = getClaims(token);
		if ( claims != null ) {
			return claims.getSubject();
		}
		return null;
	}	
	
	@SuppressWarnings("deprecation")
	private Claims getClaims(String token) {			
		try {
			return  Jwts.parser()
	                .setSigningKey(secret.getBytes())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
		} catch (ExpiredJwtException e) {
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	private JwsHeader  getHeader(String token) {
		try {
			return Jwts.parser()
		        .setSigningKey(secret.getBytes())
		        .build()
		        .parseClaimsJws(token).getHeader();
		} catch (ExpiredJwtException e) {
			throw e;
		}
	}
	
	public String getSecret() {
		return secret;
	}

	public Long getExpiration() {
		return expiration;
	}

	
}
