package com.java.pip.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDTO (
	
	@NotBlank(message = "Name is required")
	String name,
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid Email")
	String email,
	
	@NotBlank(message = "Password is required")
	String password,
	
	@NotEmpty
	Set<String> roles) {

}