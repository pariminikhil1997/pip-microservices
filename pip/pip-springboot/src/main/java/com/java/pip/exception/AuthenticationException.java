package com.java.pip.exception;

public class AuthenticationException extends RuntimeException{
	
	public AuthenticationException(String msg) {
		super(msg);
	}
}