package com.java.pip.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String,String> handleNotFound(ResourceNotFoundException ex) {
		
		log.error("Resource not found exception: {}", ex);
		return Map.of("error", ex.getMessage());
		
	}
	
	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Map<String, String> handleAuthentication(AuthenticationException ex) {
		log.error("Authentication exception: {}", ex);
		return Map.of("error",ex.getMessage());
		
	}
	
	@ExceptionHandler(AuthorizationDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Map<String, String> handleAuthorization(AuthorizationDeniedException ex) {
		log.error("Access Denied: {}", ex);
		return Map.of("error",ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String,Object> handleValidationErrors(MethodArgumentNotValidException ex) {
		
		Map<String,String> errors = ex.getBindingResult().getFieldErrors().stream()
				                                         .collect(Collectors.toMap(
				                                        error -> error.getField(),
			                                            error -> error.getDefaultMessage(),
			                                            (msg1, msg2) -> msg1));
		
		log.error("Validation failed: {}", ex);
		return Map.of("error", "Validation failed", "details", errors);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, String> handleDuplicate(DataIntegrityViolationException ex) {
		
		log.error("Data integrity violation exception: {}", ex.getMessage());
	    return Map.of("error", "Duplicate key or constraint violation");
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String,String> handleGeneric(Exception ex) {
		
		log.error("Unexpected error: {}", ex);
		return Map.of("error", "Internal server error. Please contact support.");
	}
}