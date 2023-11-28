package com.etheodoro.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.etheodoro.cursomc.services.exceptions.AuthorizationException;
import com.etheodoro.cursomc.services.exceptions.DataIntegrityException;
import com.etheodoro.cursomc.services.exceptions.ObjectNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandartError err = new StandartError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandartError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		StandartError err = new StandartError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandartError> authorization(ObjectNotFoundException e, HttpServletRequest request) {
		StandartError err = new StandartError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<StandartError> expiration(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationError err = new ValidationError(HttpStatus.UNAUTHORIZED.value(), "Token expirado.", System.currentTimeMillis());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
	}
	
}
