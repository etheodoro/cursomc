package com.etheodoro.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etheodoro.cursomc.dto.EmailDTO;
import com.etheodoro.cursomc.security.JWTUtil;
import com.etheodoro.cursomc.security.UserSecurity;
import com.etheodoro.cursomc.services.AuthService;
import com.etheodoro.cursomc.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;

	@PostMapping(value="/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		
		UserSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value="/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody  EmailDTO emailDTO) {
		authService.sendNewPassword(emailDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
	
}
